package controllers

import play.api.mvc._
import models._

object Books extends Controller with Secured with Users {

  def list = IsAuthenticated { username => implicit request =>
    Ok(views.html.books.list())
  }
}