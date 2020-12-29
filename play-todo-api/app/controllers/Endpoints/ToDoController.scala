package controllers.api

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Todo}
import java.util.UUID

//inspiration links:
//https://www.playframework.com/documentation/2.8.x/ScalaActions#Actions,-Controllers-and-Results
//https://ixorasolution.com/blog/how-to-build-rest-api-with-scala-play-framework-1


// A controller in Play is nothing more than an object that generates Action values. 
// Controllers are typically defined as classes to take advantage of Dependency Injection.
//https://www.playframework.com/documentation/2.8.x/ScalaActions#Controllers-are-action-generators

trait StorageFunctionality{
    def getToDos()
    def addTodo(todo: Todo)
    def getToDoById(id: Long)
    def deleteToDo(id: Long)
    def getCompletedToDos()
    def getInProgressToDos()
}


  class ToDoController @Inject() (cc: ControllerComponents) extends AbstractController(cc)
    with StorageFunctionality {
      //Basic example from documentation 
      //requests recieved are handled by an action in a play framework application. 
    def index = Action {
      Ok("It works!")
    }

    override def getToDos(): Unit = TODO

    override def addTodo(todo: Todo): Unit = TODO

    override def getToDoById(id: Long): Unit = TODO

    override def deleteToDo(id: Long): Unit = TODO

    override def getCompletedToDos(): Unit = TODO

    override def getInProgressToDos(): Unit = TODO
  }