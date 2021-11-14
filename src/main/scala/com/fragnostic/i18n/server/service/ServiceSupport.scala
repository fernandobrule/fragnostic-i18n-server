package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.server.handler.{ HandlerCommons, HeaderHandler }
import com.fragnostic.i18n.server.support.{ Constants, DateSupport, JsonSupport }
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.io.Buf.ByteArray.Owned

trait ServiceSupport extends JsonSupport with HandlerCommons with HeaderHandler with Constants with DateSupport {

  def handle(request: Request, status: Status, json: ResponseJson): Response = {
    val response = Response(request.version, status)
    response.content = Owned(toBytes(json))
    response.contentType = CONTENT_TYPE_APPLICATION_JSON
    response.date = now
    response.server = SERVER_NAME
    response.headerMap.put(ACCESS_CONTROL_ALLOW_ORIGIN, STAR)
    response
  }

}
