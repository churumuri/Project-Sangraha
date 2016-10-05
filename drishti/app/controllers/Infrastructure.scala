package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.http.{ MimeTypes, HeaderNames }
import play.api.libs.ws.WS
import play.api.mvc.{ Results, Action, Controller }
import play.api.libs.json._
import play.api.Play.current
import play.api.mvc.Results.Redirect
import javax.inject.Inject
import play.api.cache._
import helpers.Secured

class Infrastructure @Inject() (val webJarAssets: WebJarAssets, override val cache: CacheApi) extends Controller with Secured {
  def index = AuthenticatedAction { request =>
    val idToken = request.session.get("idToken").get
    val profile = cache.get[JsValue](idToken + "profile").get
    Ok(views.html.infrastructure(webJarAssets, profile))
  }
}