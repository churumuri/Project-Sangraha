package implementation.slick

import scala.concurrent.Future
import javax.inject._
import models.persistence.Tables.{Cloudprovider, CloudproviderRow}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.Database

import slick.driver.MySQLDriver.api._
import slick.lifted.Rep
import dao.{ CloudProvider => ApiCloudprovider, CloudProviderDao} 


import java.util.UUID
import play.api.Logger


@Singleton
class CloudProviderDaoImpl @Inject() (db: Database) extends  CloudProviderDao {
                                                                    
    def all(): Future[Seq[ApiCloudprovider]] = {
      Logger.debug(s"CloudProviderDaoImpl::all()")
      db.run(Cloudprovider.result)
      .map ( a => for (row <- a) yield (CloudproviderRowToApiCloudprovider(row)))
    } 

    def insert(row : ApiCloudprovider): Future[UUID] = {
      Logger.debug(s"CloudProviderDaoImpl::insert($row)")
      val uuid = UUID.randomUUID()
      db.run(
        Cloudprovider += CloudproviderRow(uuid.toString(), row.name, row.url)
      )
      Future(uuid)
    }

    def update(row : ApiCloudprovider): Future[UUID] = {
      Logger.debug(s"CloudProviderDaoImpl::update($row)")
      val q = queryById(row.key.toString())
      val action = q.update(ApiCloudproviderToCloudproviderRow(row))
      val affectedRowsCount: Future[Int] = db.run(action)
      Future(UUID.fromString(action.statements.head))
    }

    def findById(key : UUID): Future[Option[ApiCloudprovider]] = {
      Logger.debug(s"CloudProviderDaoImpl::findById($key)")
      val f: Future[Option[CloudproviderRow]] = db.run(queryById(key.toString()).result.headOption)
      f.map(maybeRow => maybeRow.map(CloudproviderRowToApiCloudprovider(_)))
    }

    def deleteById(key : UUID): Future[UUID] = {
      Logger.debug(s"CloudProviderDaoImpl::deleteById($key)")
      val q = queryById(key.toString())
      val action = q.delete
      val affectedRowsCount: Future[Int] = db.run(action) 
      Future(UUID.fromString(action.statements.head))
    }

    private def ApiCloudproviderToCloudproviderRow(apiCloudprovider: ApiCloudprovider): CloudproviderRow = {
      CloudproviderRow(apiCloudprovider.key.toString(), apiCloudprovider.name, apiCloudprovider.url)
    }

    private def CloudproviderRowToApiCloudprovider(cloudProviderRow: CloudproviderRow): ApiCloudprovider = {
      ApiCloudprovider(Some(UUID.fromString(cloudProviderRow.key)), cloudProviderRow.name, cloudProviderRow.url)
    }

    private val queryById = Compiled(
    (id: Rep[String]) => Cloudprovider.filter(_.key === id))

}