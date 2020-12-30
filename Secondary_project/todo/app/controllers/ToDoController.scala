//Inspired by:
//https://www.baeldung.com/scala/play-rest-api
package controllers

import javax.inject.{Inject, Singleton}
import models.{ToDoBody, ToDoItem}
import play.api.mvc._
import play.api.libs.json._

import scala.collection.mutable.ListBuffer
import scala.util.Try

//Following the finch API and using singletons to reuse a single instance for simplicity sake
@Singleton
class ToDoController @Inject()(val controllerComponents: ControllerComponents)
extends BaseController {

  //Mutable list of TodoItems
  private val todoList = new ListBuffer[ToDoItem]()
  implicit val todoListToJson = Json.format[ToDoItem]

  //Injecting som test subjects
  todoList += ToDoItem(1, "Test Todo 1", true)
  todoList += ToDoItem(1, "Test Todo 2", false)


  //Here we abstract from pure functions found in FP and start using states again.
  def getTodos: Action[AnyContent] = Action {
    if(todoList.isEmpty){
      NoContent
    }
    else{
      Ok(Json.toJson(todoList))
    }
  }

  //When working with scala you can still use functional programming techniques.
  def getToDoById(todoId: Long): Action[AnyContent] = Action {
    val ItemSearch = todoList.find(_.id == todoId)
    ItemSearch match {
      case Some(value) => Ok(Json.toJson(value))
      case None => NotFound
    }
  }

  //https://www.playframework.com/documentation/2.8.x/ScalaBodyParsers
  //https://www.playframework.com/documentation/2.0/ScalaJsonRequests
  //Using Option is smarter here but is not used for the sake of not using FP methods
  def addTodo = Action(parse.json){implicit request =>
    val todoName = (request.body \"taskName").get
    val todoCompleted = (request.body \"completed").get
    val randomId = new scala.util.Random
    //randomId.setSeed(100L)
    val id = randomId.nextLong(Long.MaxValue)

    if(todoName != null && todoCompleted != null){
      val todoNameStringified = todoName.toString()
      val todoBoolStringified = todoCompleted.toString().toBoolean
      val newTodo = ToDoItem(id, todoNameStringified, todoBoolStringified)
      todoList += newTodo
      Created(Json.toJson(newTodo))
    }
    else {
      BadRequest("Error when processing request")
    }
  }

  //Inspired bY: https://stackoverflow.com/questions/32736430/how-to-find-the-index-in-arraybuffer-using-scala
  def deleteToDoById(todoId: Long): Action[AnyContent] = Action {
    val ItemSearch = todoList.zipWithIndex.find({ case  (value, _) => value == Some(todoId)}).map(_._2)
    ItemSearch match {
      case Some(value) => {
        todoList.remove(value)
        Ok(Json.toJson(value))
      }
      case None => NotFound
    }
  }
}
