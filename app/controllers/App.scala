package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views.html._
import models._

object App extends Controller {
	val formData = Form(
		"label" -> nonEmptyText
	)
	
	def index = Action {
		Redirect(routes.App.tasks)
	}

	def tasks = Action {
		Ok(task(Task.all(), formData))
	}
	
	def newTask = Action { implicit request =>
		formData.bindFromRequest.fold(
			errors => BadRequest(task(Task.all(), errors)),
			label => {
				Task.create(label)
				Redirect(routes.App.index)
			}
		)
	}
	
	def deleteTask(id: Long) = Action {
		Task.delete(id)
		Redirect(routes.App.tasks)
	}
}