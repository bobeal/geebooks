package models

import play.api.libs.json._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.DateTime

case class User(
  id: Option[Int],
  email: String,
  verifiedEmail: Boolean,
  name: String,
  picture: Option[String],
  gender: String,
  birthday: Option[String])

object Users extends Table[User]("users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email", O.NotNull)
  def verifiedEmail = column[Boolean]("verifiedEmail", O.NotNull)
  def name = column[String]("name", O.NotNull)
  def picture = column[String]("picture", O.Nullable)
  def gender = column[String]("gender")
  def birthday = column[String]("birthday", O.Nullable)
  def * = id.? ~ email ~ verifiedEmail ~ name ~ picture.? ~ gender ~ birthday.? <> (User, User.unapply _)

  def autoInc = email ~ verifiedEmail ~ name ~ picture.? ~ gender ~ birthday.? returning id
  
  implicit val formater = Json.format[User]

  def createOrMerge(user: User): Int = {
    DB.withSession{ implicit session:Session =>
      Users.autoInc.insert(user.email, user.verifiedEmail, user.name, user.picture, user.gender, user.birthday)
    }
  }

  def findById(id: Int): Option[User] = {
    DB.withSession{ implicit session:Session =>
      Query(Users).take(id).firstOption
    }
  }

  def findByEmail(email: String): Option[User] = {
    DB.withSession{ implicit session:Session =>
      Query(Users).filter(_.email === email).firstOption
    }
  }

  def fromSession(id: Int): Option[User] = findById(id)
}
