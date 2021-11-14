package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.server.support.JsonSupport
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.io.Buf.ByteArray.Owned

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

trait Handler extends JsonSupport {

  protected val APPLICATION_JSON = "application/json"
  protected val SERVER_NAME = "Fragnostic * I18N * Server"
  protected val ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"
  protected val STAR = "*"

  protected def now: String =
    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

  protected def handle(request: Request, status: Status, json: ResponseJson): Response = {
    val response = Response(request.version, status)
    response.content = Owned(toBytes(json))
    response.contentType = APPLICATION_JSON
    response.date = now
    response.server = SERVER_NAME
    response.headerMap.put(ACCESS_CONTROL_ALLOW_ORIGIN, STAR)
    response
  }

}
