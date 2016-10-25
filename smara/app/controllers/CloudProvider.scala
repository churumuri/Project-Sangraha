package controllers

import javax.inject._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import slick.driver.MySQLDriver._
import slick.driver._
import scala.concurrent.ExecutionContext.Implicits.global
import dao.{ CloudProvider, CloudProviderDao} 
import play.api.libs.json.{Json, Writes}
import java.util.UUID

@Singleton
class CloudProvidersController @Inject()(cloudProviderDao: CloudProviderDao) extends Controller {

  def CloudProvider(id : String) = Action.async {
    cloudProviderDao.findById(UUID.fromString(id)) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def insertCloudProvider = Action.async(parse.json) {
    request => {
      {
        for {
          name <- (request.body \ "name").asOpt[String]
          url <- (request.body \ "url").asOpt[String]
        } yield {
          cloudProviderDao.insert(dao.CloudProvider(None, name, url)) map { n => Ok("Id of Cloud Provider Added : " + n) }
        }
      }.getOrElse(Future{BadRequest("Wrong json format")})
    }
  }

  def CloudProviders() = Action.async {
    cloudProviderDao.all.map ( a => Ok(Json.toJson(for (row <- a) yield (Json.toJson(row)))))
    /*
    cloudProviderDao.all.map { a =>
      val k = (for (row <- a) yield (Json.toJson(row))).toList
      Ok(Json.toJson(k))
    }
    */
  }


}
