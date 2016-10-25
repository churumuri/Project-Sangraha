package implementation.slick

import scala.concurrent.Future
import javax.inject._
import models.persistence.Tables.{Cloudservice, CloudserviceRow}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.Database

import slick.driver.MySQLDriver.api._
import slick.lifted.Rep
import dao.{ CloudService => ApiCloudService, CloudServiceDao} 


import java.util.UUID
import play.api.Logger


@Singleton
class CloudServiceDaoImpl @Inject() (db: Database) extends  CloudServiceDao {
                                                                    
    def all(): Future[Seq[ApiCloudService]] = {
      Logger.debug(s"CloudServiceDaoImpl::all()")
      db.run(Cloudservice.result)
             .map ( a => for (row <- a) yield (CloudServiceRowToApiCloudService(row))) 
    }

    def insert(row : ApiCloudService): Future[UUID] = {
      Logger.debug(s"CloudServiceDaoImpl::insert($row)")
      val uuid = UUID.randomUUID()
      db.run(
        Cloudservice += CloudserviceRow(uuid.toString(), row.name, row.url, row.provider.toString(), row.status)
      )
      Future(uuid)
    }

    def update(row : ApiCloudService): Future[UUID] = {
      Logger.debug(s"CloudServiceDaoImpl::update($row)")
      val q = queryById(row.key.toString())
      val action = q.update(ApiCloudServiceToCloudServiceRow(row))
      val affectedRowsCount: Future[Int] = db.run(action)
      Future(UUID.fromString(action.statements.head))
    }

    def findById(key : UUID): Future[Option[ApiCloudService]] = {
      Logger.debug(s"CloudServiceDaoImpl::findById($key)")
      val f: Future[Option[CloudserviceRow]] = db.run(queryById(key.toString()).result.headOption)
      f.map(maybeRow => maybeRow.map(CloudServiceRowToApiCloudService(_)))
    }

    def deleteById(key : UUID): Future[UUID] = {
      Logger.debug(s"CloudServiceDaoImpl::deleteById($key)")
      val q = queryById(key.toString())
      val action = q.delete
      val affectedRowsCount: Future[Int] = db.run(action) 
      Future(UUID.fromString(action.statements.head))
    }

    private def ApiCloudServiceToCloudServiceRow(apiCloudService: ApiCloudService): CloudserviceRow = {
      CloudserviceRow(apiCloudService.key.toString(), apiCloudService.name, apiCloudService.url, apiCloudService.provider.toString(), apiCloudService.status)
    }

    private def CloudServiceRowToApiCloudService(cloudServiceRow: CloudserviceRow): ApiCloudService = {
      ApiCloudService(Some(UUID.fromString(cloudServiceRow.key)), cloudServiceRow.name, cloudServiceRow.url, 
                          UUID.fromString(cloudServiceRow.fkCloudprovider), cloudServiceRow.status)
    }

    private val queryById = Compiled(
    (id: Rep[String]) => Cloudservice.filter(_.key === id))

}