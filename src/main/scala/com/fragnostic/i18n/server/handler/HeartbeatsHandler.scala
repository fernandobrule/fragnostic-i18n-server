package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.glue.ResponseJson
import com.twitter.finagle.http.{ Request, Response, Status }

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

trait HeartbeatsHandler extends Handler {

  def handleHeartbeats(request: Request): Response = {
    val body = s"${LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)}"
    handle(request, Status.Ok, ResponseJson(success = true, body = body))
  }

}

