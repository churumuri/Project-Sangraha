// controllers/Callback.scala
package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Play
import play.api.Play.current
import play.api.cache._
import play.api.http.HeaderNames
import play.api.http.MimeTypes
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.ws.WS
import play.api.mvc.Action
import play.api.mvc.Controller
import javax.inject.Inject
import play.api.Logger
import helpers.Secured
import helpers.Auth0Config

class Auth0Impl @Inject() (override val cache: CacheApi) extends Controller with Secured {

  // callback route
  def callback(codeOpt: Option[String] = None) = Action.async {
    (for {
      code <- codeOpt
    } yield {
      // Get the token
      getToken(code).flatMap {
        case (idToken, accessToken) =>
          // Get the user
          getUser(accessToken).map { user =>
            // Cache the user and tokens into cache and session respectively
            cache.set(idToken + "profile", user)
            Logger.info("Successful login")
            Redirect(routes.Dashboard.index())
              .withSession(
                "idToken" -> idToken,
                "accessToken" -> accessToken
              )
          }

      }.recover {
        case ex: IllegalStateException => Unauthorized(ex.getMessage)
      }
    }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
  }

  def getToken(code: String): Future[(String, String)] = {
    val tokenResponse = WS.url(String.format("https://%s/oauth/token", "sangraha.auth0.com"))(Play.current).
      withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON).
      post(
        Json.obj(
          "client_id" -> (Auth0Config.get().clientId),
          "client_secret" -> "v77n6M_D8qhW4oRaV2eiL4XXr4V5_VJ19xUYen2IdYm7ktQsZ1USpa2ClzaaVs9r",
          "redirect_uri" -> "http://localhost:9000/callback",
          "code" -> code,
          "grant_type" -> "authorization_code"
        )
      )

    tokenResponse.flatMap { response =>
      (for {
        idToken <- (response.json \ "id_token").asOpt[String]
        accessToken <- (response.json \ "access_token").asOpt[String]
      } yield {
        Future.successful((idToken, accessToken))
      }).getOrElse(Future.failed[(String, String)](new IllegalStateException("Tokens not sent")))
    }

  }

  def getUser(accessToken: String): Future[JsValue] = {
    val userResponse = WS.url(String.format("https://%s/userinfo", "sangraha.auth0.com"))(Play.current)
      .withQueryString("access_token" -> accessToken)
      .get()

    userResponse.flatMap(response => Future.successful(response.json))
  }

  def logout = AuthenticatedAction { request =>
    val idToken = request.session.get("idToken").get
    cache.remove(idToken + "profile")
    val l = WS.url(String.format("https://%s/logout", "sangraha.auth0.com"))(Play.current)
      .withQueryString("client_id" -> (Auth0Config.get().clientId))
      .withQueryString("returnTo" -> "http://localhost:9000")
      .get()

    Redirect(routes.HomeController.index())
      .withSession(
        "idToken" -> "",
        "accessToken" -> ""
      )
  }

}