package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.server.support.{ Constants, DateSupport, JsonSupport }
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.io.Buf.ByteArray.Owned
import com.twitter.util.Future

trait Http404Handler extends JsonSupport with Constants with DateSupport {

  def handle404(request: Request): Future[Response] =
    Future({
      val response = Response(request.version, Status.NotFound)
      response.content = Owned(toBytes(ResponseJson(errors = List(s"${request.path} NOT YET"))))
      response.contentType = CONTENT_TYPE_APPLICATION_JSON
      response.date = now
      response.server = SERVER_NAME
      response.headerMap.put(ACCESS_CONTROL_ALLOW_ORIGIN, STAR)
      response
    })

}
