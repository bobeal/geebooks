package controllers

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import play.api._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws.WS
import play.api.mvc._
import models._
import models.ws.OAuth2Client

object Application extends Controller {

  def login = Action { implicit request =>
    Ok(views.html.login())
  }

  def registerWithGoogle = Action {
    val authorizationUrl = OAuth2Client.authorizationUrl
    Redirect(authorizationUrl)
  }

  def googleCallback(error: String, code: String) = Action { implicit request =>
    if (error != "") {
      Ok(views.html.login(error))
    } else {
      
      val futureTokenJson = WS.url(OAuth2Client.TOKEN_URI)
        .withHeaders("Content-Type" -> "application/x-www-form-urlencoded")
        .post(OAuth2Client.accessTokenParameters(code)) map { response =>
           response.status match {
             case 200 => Some(response.json)
             case _ => None
           }
        }
      val tokenJson = Await.result(futureTokenJson, Duration.Inf).get
      val accessToken = tokenJson \ "access_token"
      Logger.debug("Got access token : " + accessToken)

      val futureResponseJson = WS.url(OAuth2Client.EMAIL_URI)
           .withHeaders("Authorization" -> "Bearer %s".format(accessToken))
            .get() map { response =>
              response.status match {
                case 200 => Some(response.json)
                case _ => None
            }
         }
      val responseJson = Await.result(futureResponseJson, Duration.Inf).get
      Logger.debug("Got response to email get : " + responseJson)
      val email = (responseJson \ "email").as[String]
      Users.findByEmail(email) match {
        case Some(user) => user
        case None => Users.createFromGoogleInfos(responseJson)
      }

      Redirect("/").withSession("email" -> email)
    }
  }
}

/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected user email.
   */
  private def username(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) =
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
}
