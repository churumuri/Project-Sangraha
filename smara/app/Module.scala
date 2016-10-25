//import com.google.inject.name.Names
//import com.google.inject.{Provides, AbstractModule}
import java.time.Clock

import javax.inject.{Inject, Provider, Singleton}

import dao.{CloudProviderDao, CloudServiceDao}
import implementation.slick.{CloudProviderDaoImpl, CloudServiceDaoImpl}
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import play.api.inject.ApplicationLifecycle
import play.api.{Configuration, Environment}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module (environment: Environment,
             configuration: Configuration) extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    bind(classOf[Config]).toInstance(configuration.underlying)
    bind(classOf[CloudProviderDao]).to(classOf[CloudProviderDaoImpl])
    bind(classOf[CloudServiceDao]).to(classOf[CloudServiceDaoImpl])
    bind(classOf[slick.jdbc.JdbcBackend.Database]).toProvider(classOf[DatabaseProvider])

  }
}

@Singleton
class DatabaseProvider @Inject() (config: Config) extends Provider[slick.jdbc.JdbcBackend.Database] {

  private val db = slick.jdbc.JdbcBackend.Database.forConfig("smara.database", config)

  override def get(): slick.jdbc.JdbcBackend.Database = db
}



