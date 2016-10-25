package implementation.slick

import scala.concurrent.Future
import javax.inject._
import models.persistence.Tables.{Organization, OrganizationRow}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.jdbc.JdbcBackend.Database

import slick.driver.MySQLDriver.api._
import slick.lifted.Rep
import dao.{ Organization => ApiOrganization, OrganizationDao} 


import java.util.UUID
import play.api.Logger


class abstract AbstractDaoImpl[T,A](db: Database) extends AbstractDao[T] {
}


@Singleton
class OrganizationDaoImpl @Inject() (db: Database) extends  OrganizationDao {
                                                                    
    def all(): Future[Seq[ApiOrganization]] = {
      Logger.debug(s"OrganizationDaoImpl::all()")
      db.run(Organization.result)
             .map ( a => for (row <- a) yield (OrganizationRowToApiOrganization(row))) 
    }

    def insert(row : ApiOrganization): Future[UUID] = {
      Logger.debug(s"OrganizationDaoImpl::insert($row)")
      val uuid = UUID.randomUUID()
      db.run(
        Organization += OrganizationRow(uuid.toString(), row.name, row.url, row.contact)
      )
      Future(uuid)
    }

    def update(row : ApiOrganization): Future[UUID] = {
      Logger.debug(s"OrganizationDaoImpl::update($row)")
      val q = queryById(row.key.toString())
      val action = q.update(ApiOrganizationToOrganizationRow(row))
      val affectedRowsCount: Future[Int] = db.run(action)
      Future(UUID.fromString(action.statements.head))
    }

    def findById(key : UUID): Future[Option[ApiOrganization]] = {
      Logger.debug(s"OrganizationDaoImpl::findById($key)")
      val f: Future[Option[OrganizationRow]] = db.run(queryById(key.toString()).result.headOption)
      f.map(maybeRow => maybeRow.map(OrganizationRowToApiOrganization(_)))
    }

    def deleteById(key : UUID): Future[UUID] = {
      Logger.debug(s"OrganizationDaoImpl::deleteById($key)")
      val q = queryById(key.toString())
      val action = q.delete
      val affectedRowsCount: Future[Int] = db.run(action) 
      Future(UUID.fromString(action.statements.head))
    }

    private def ApiOrganizationToOrganizationRow(apiOrganization: ApiOrganization): OrganizationRow = {
      OrganizationRow(apiOrganization.key.toString(), apiOrganization.name, apiOrganization.url, apiOrganization.contact)
    }

    private def OrganizationRowToApiOrganization(organizationRow: OrganizationRow): ApiOrganization = {
      ApiOrganization(Some(UUID.fromString(organizationRow.key)), organizationRow.name, organizationRow.url, organizationRow.contact)
    }

    private val queryById = Compiled(
    (id: Rep[String]) => Organization.filter(_.key === id))

}

