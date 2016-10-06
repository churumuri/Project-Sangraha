package helpers

import play.api.Play
import play.api.{ Configuration }
import javax.inject.{ Inject, Singleton }

/*
case class Auth0Config(secret: String, clientId: String, callbackURL: String, domain: String)
object Auth0Config {
  def get() = {
    Auth0Config(
      Play.current.configuration.getString("auth0.clientSecret").get,
      Play.current.configuration.getString("auth0.clientId").get,
      Play.current.configuration.getString("auth0.callbackURL").get,
      Play.current.configuration.getString("auth0.domain").get
    )
  }
}
*/
@Singleton
class Auth0Config @Inject() (config: Configuration) {
  val secret = config.getString("auth0.clientSecret").get
  val clientId = config.getString("auth0.clientId").get
  val callbackURL = config.getString("auth0.callbackURL").get
  val domain = config.getString("auth0.domain").get
}
