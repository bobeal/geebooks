package models

import java.sql.Date
import play.api.Play.current
import play.api.libs.json._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB

case class Book(
  id : Option[Int],
  isbn : String,
  author : String,
  title : String,
  description: Option[String],
  coverPicture : Option[String],
  genre : String,
  releaseDate : Date
)

object Books extends Table[Book]("books") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def isbn = column[String]("isbn")
  def author = column[String]("author")
  def title = column[String]("title")
  def description = column[Option[String]]("description")
  def coverPicture = column[Option[String]]("coverPicture")
  def genre = column[String]("genre")
  def releaseDate = column[Date]("releaseDate")

  def * = id.? ~ isbn ~ author ~ title ~ description ~ coverPicture ~ genre ~ releaseDate <> (Book, Book.unapply _)
  def autoInc = isbn ~ author ~ title ~ description ~ coverPicture ~ genre ~ releaseDate returning id
  implicit val formater = Json.format[User]

  def create(book: Book): Int = {
    DB.withSession{ implicit session:Session =>
      Books.autoInc.insert(book.isbn, book.author, book.title, book.description, book.coverPicture, book.genre, book.releaseDate)
    }
  }

  def list(): Seq[Book] = {
    DB.withSession{ implicit session:Session =>
      Query(Books).list()
    }
  }
}
