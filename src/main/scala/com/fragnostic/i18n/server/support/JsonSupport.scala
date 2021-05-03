package com.fragnostic.i18n.server.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.twitter.finagle.http.Request
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

import scala.util.Try

trait JsonSupport {

  private val mapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  implicit val formats: DefaultFormats.type = DefaultFormats

  def json2class[T](request: Request)(implicit m: Manifest[T]): Try[T] =
    Try(parse(request.getContentString()).extract[T])

  def toBytes[T](t: T): Array[Byte] =
    mapper.writeValueAsBytes(t)

}
