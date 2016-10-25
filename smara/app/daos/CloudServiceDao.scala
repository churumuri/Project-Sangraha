package dao

import scala.concurrent.Future
import play.api.libs.json.{Json, Writes}
import java.util.UUID

case class CloudService(key: Option[UUID], name: String, url: String, provider: UUID, status: Boolean) 

object CloudService {

  implicit val CloudServiceWrites = new Writes[CloudService] {
      def writes(cs: CloudService) = Json.obj(
        "key" -> cs.key.getOrElse("Problems").toString(),
        "name" -> cs.name,
        "url" -> cs.url,
        "provider" -> cs.provider.toString(),
        "status" -> cs.status
      )
    }
  }

trait CloudServiceDao  extends AbstractBaseDAO[CloudService] 
