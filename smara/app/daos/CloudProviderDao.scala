package dao

import scala.concurrent.Future
import play.api.libs.json.{Json, Writes}
import java.util.UUID

case class CloudProvider(key: Option[UUID], name: String, url: String) 

object CloudProvider {

  implicit val CloudProviderWrites = new Writes[CloudProvider] {
      def writes(cp: CloudProvider) = Json.obj(
        "key" -> cp.key.getOrElse("Problems").toString(),
        "name" -> cp.name,
        "url" -> cp.url
      )
    }
  }

trait CloudProviderDao  extends AbstractBaseDAO[CloudProvider] 
