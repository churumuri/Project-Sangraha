package controllers

import javax.inject._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import slick.driver.MySQLDriver._
import slick.driver._
import scala.concurrent.ExecutionContext.Implicits.global
import dao.{ CloudService, CloudServiceDao} 
import play.api.libs.json.{Json, Writes}
import java.util.UUID

@Singleton
class CloudServicesController @Inject()(cloudserviceDao: CloudServiceDao) extends Controller {

  def CloudService(id : String) = Action.async {
    cloudserviceDao.findById(UUID.fromString(id)) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def insertCloudService = Action.async(parse.json) {
    request => {
      {
        for {
          name <- (request.body \ "name").asOpt[String]
          url <- (request.body \ "url").asOpt[String]
          provider <- (request.body \ "provider").asOpt[String]
          status <- (request.body \ "status").asOpt[Boolean]
        } yield {
          cloudserviceDao.insert(dao.CloudService(None, name, url, UUID.fromString(provider), status)) map { n => Ok("Id of Cloud service Added : " + n) }
        }
      }.getOrElse(Future{BadRequest("Wrong json format")})
    }
  }

  def CloudServices() = Action.async {
    cloudserviceDao.all.map ( a => Ok(Json.toJson(for (row <- a) yield (Json.toJson(row)))))
    /*
    cloudserviceDao.all.map { a =>
      val k = (for (row <- a) yield (Json.toJson(row))).toList
      Ok(Json.toJson(k))
    }
    */
  }


}
