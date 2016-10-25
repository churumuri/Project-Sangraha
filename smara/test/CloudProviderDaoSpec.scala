import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._
import dao.{ CloudProvider => ApiCloudprovider, CloudProviderDao} 
import implementation.slick.CloudProviderDaoImpl
import play.api.inject.guice._
import slick.jdbc.JdbcBackend.{Database}
import slick.backend.DatabaseConfig
import com.google.inject.AbstractModule

import play.api.inject.bind
import play.api.inject.guice.GuiceInjector
import play.api.inject.guice.GuiceableModule
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Future
import java.util.UUID
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

//import play.api.db.DBApi

import slick.driver.H2Driver.api._



//----- TEST on MODELS NOT WORKING NEED TO READ MODE ABOUT IT ------ abandoing for now
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class CloudProviderDaoSpec extends PlaySpec with OneAppPerTest {

  val testDb = Database.forURL("jdbc:h2:mem:play;INIT=RUNSCRIPT FROM 'sql/test.sql'", driver="org.h2.Driver")

  "Cloud Provide Dao" should {

    /*
     * Initial setup using the inmemory Database
     */
    
    val guice = new GuiceInjectorBuilder()
                    .overrides(bind[Database].toInstance(testDb))
                    .build()
     val cloudProviderDao = guice.instanceOf[CloudProviderDaoImpl]

     cloudProviderDao.insert(ApiCloudprovider(Option(UUID.randomUUID()),"asasasa","paksaskapskp"))

    /*
     * now the test cases
     */
    "Cloud Provider Dao should return all providers when queried for all" in  {
      val providers = cloudProviderDao.all()
      Console.println("provider = " + providers)
    }

  }

}
