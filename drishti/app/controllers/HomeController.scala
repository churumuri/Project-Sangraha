package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import helpers.Auth0Config
import com.google.inject.Inject;

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject() (val webJarAssets: WebJarAssets, auth0config: Auth0Config) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index(webJarAssets, auth0config))
  }

}
