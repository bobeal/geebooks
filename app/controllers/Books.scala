package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._
import play.api.mvc._
import models._
import play.Logger
import views.html.defaultpages.badRequest

object Books extends Controller with Secured with Users {

  val bookForm = Form(
    mapping(
      "isbn" -> text,
      "author" -> text,
      "title" -> text,
      "description" -> optional(text),
      "genre" -> text,
      "releaseDate" -> date
    )((isbn,author,title, description,genre,releaseDate) => Book(None,isbn,author,title,description,None,genre,new java.sql.Date(releaseDate.getTime())))
     ((book:Book) => Some(book.isbn,book.author,book.title,book.description,book.genre,new java.util.Date(book.releaseDate.getTime())))
  )

  def list = IsAuthenticated { username => implicit request =>
    Ok(views.html.books.list())
  }

  def add = IsAuthenticated { username => implicit request =>
    Ok(views.html.books.add())
  }

  def addBook = IsAuthenticated { username => implicit request =>
    bookForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.books.add()),
      value => {
        var bookId = models.Books.create(value)
        Logger.debug("created book")
        Ok(String.valueOf(bookId))
      }
    )
  }
}