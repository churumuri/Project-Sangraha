package helpers

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

trait Secured {
  val cache: CacheApi

  def AuthenticatedAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action { request =>
      (request.session.get("idToken").flatMap { idToken =>
        cache.get[JsValue](idToken + "profile")
      } map { profile =>
        f(request)
      }).orElse {
        Some(Redirect(controllers.routes.HomeController.index()))
      }.get
    }
  }
}