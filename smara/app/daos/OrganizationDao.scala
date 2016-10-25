package dao

import scala.concurrent.Future
import play.api.libs.json.{Json, Writes}
import java.util.UUID

case class Organization(key: Option[UUID], name: String, url: String, contact: String) 

object Organization {

  implicit val OrganizationWrites = new Writes[Organization] {
      def writes(cp: Organization) = Json.obj(
        "key" -> cp.key.getOrElse("Problems").toString(),
        "name" -> cp.name,
        "url" -> cp.url,
        "contact" -> cp.contact
      )
    }
  }

trait OrganizationDao  extends AbstractBaseDAO[Organization]
