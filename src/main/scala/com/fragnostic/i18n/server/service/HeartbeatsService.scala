package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.glue.ResponseJson
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.util.Future

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HeartbeatsService(who: String) extends AbstractService {

  override def apply(request: Request): Future[Response] = {
    Future.value(
      handle(request, Status.Ok, ResponseJson(body = s"Hello $who!, ${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}")) //
    )
  }

}