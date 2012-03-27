package controllers

import play.api._
import play.api.mvc._
import views.html._

object Dummy extends Controller {
	def index = Action {
		Ok(views.html.index("I'm Dummy"))
	}
	
	def gotit = Action { request =>
		Ok("Got request [" + request + "]")
	}
}