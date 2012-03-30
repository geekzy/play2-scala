package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views.html._

object Dummy extends Controller {
	def index = Action {
		Ok(views.html.index("I'm Dummy"))
	}
	
	def gotit = Action { request =>
		Ok("Got request [" + request + "]")
	}
	
	def param(name: String) = Action {
		Ok("Hello " + name)	
	}
	
	def jump(page: Int) = Action {
		Ok(views.html.index("Page : " + page))
	}
	
	def ctype = Action {
		Ok("Hello World!").withHeaders(
			CACHE_CONTROL -> "max-age=3600",
			ETAG -> "xxx"
		)
	}
	
	def cookie = Action {
		Ok("Hello Cookie").withCookies(
			Cookie("theme", "blue")
		)
	}
	
	def nocookie = Action {
		Ok("Goodbye Cookie").discardingCookies("theme")
	}
	
	def connect(name: String, email: String) = Action {
		Ok("Welcome " + name).withSession(
			"connected" -> email,
			"name" -> name
		)
	}
	
	def getEmail = Action { request =>
		request.session.get("connected").map { email =>
			Ok("Email: " + email)
		}.getOrElse {
			Unauthorized("Opps.. you are not Connected")
		}
	}
	
	def bye = Action {
		Ok("Bye...").withNewSession
	}
	
	val data = Form(
		"callme" -> nonEmptyText
	)
	
	def showFlash = Action { implicit request =>
		Ok {
			flash.get("callme").getOrElse("Welcome!")
		}	
	}
	
	def flashIt = Action {
		Ok(views.html.flash(data))
	}
	
	def save = Action { implicit request =>
		data.bindFromRequest.fold(
			errors => BadRequest(views.html.flash(errors)),
			callme => {
				Redirect(routes.Dummy.showFlash).flashing(
					"success" -> "Item has been created",
					"name" -> callme
				)
			}
		)
			
	}
}
