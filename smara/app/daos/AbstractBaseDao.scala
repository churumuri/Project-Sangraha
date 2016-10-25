package dao

import scala.concurrent.Future
import java.util.UUID

trait AbstractBaseDAO[A] {
  def all(): Future[Seq[A]]
  def insert(row : A): Future[UUID]
  def update(row : A): Future[UUID]
  def findById(key : UUID): Future[Option[A]]
  def deleteById(key : UUID): Future[UUID]
}

