//Inspired by:
//https://www.baeldung.com/scala/play-rest-api
package controllers

import javax.inject.{Inject, Singleton}
import models.ToDoItem
import play.api.mvc._
import play.api.libs.json._

import scala.collection.mutable.ListBuffer

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
  def addTodo = Action{ implicit request =>
  val requestBody = request.body.asJson
  //val toDo: Option
    //todo: add get id and addtodo to routing file

  }
}
