package controllers

import javax.inject._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import slick.driver.MySQLDriver._
import slick.driver._
import scala.concurrent.ExecutionContext.Implicits.global
import dao.{ Organization, OrganizationDao} 
import play.api.libs.json.{Json, Writes}
import java.util.UUID

@Singleton
class OrganizationController @Inject()(organizationDao: OrganizationDao) extends Controller {

  def Organization(id : String) = Action.async {
    organizationDao.findById(UUID.fromString(id)) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def insertOrganization = Action.async(parse.json) {
    request => {
      {
        for {
          name <- (request.body \ "name").asOpt[String]
          url <- (request.body \ "url").asOpt[String]
          contact <- (request.body \ "contact").asOpt[String]
        } yield {
          organizationDao.insert(dao.Organization(None, name, url, contact)) map { n => Ok("Id of Organization Added : " + n) }
        }
      }.getOrElse(Future{BadRequest("Wrong json format")})
    }
  }

  def Organizations() = Action.async {
    organizationDao.all.map ( a => Ok(Json.toJson(for (row <- a) yield (Json.toJson(row)))))
  }


}
