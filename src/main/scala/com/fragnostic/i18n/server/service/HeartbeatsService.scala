package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.glue.ResponseJson
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.util.Future

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HeartbeatsService extends EcommService {

  override def apply(request: Request): Future[Response] = {
    Future.value(
      {
        val body = s"${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}"
        handle(request, Status.Ok, ResponseJson(body = body))
      })
  }

}