package com.fragnostic.i18n.server

import com.fragnostic.i18n.server.handler.{ DefaultHandler, GetI18nHandler }
import com.fragnostic.i18n.server.support.JsonSupport
import com.fragnostic.conf.facade.service.support.ConfIntSupport
import com.twitter.finagle.http.{ Method, Request, Response }
import com.twitter.finagle.{ Http, Service }
import com.twitter.util.{ Await, Future }
import org.slf4j.{ Logger, LoggerFactory }

import java.net.InetSocketAddress

object FragnosticI18nServer extends App
  with ConfIntSupport
  with GetI18nHandler
  with JsonSupport
  with DefaultHandler {

  private[this] val logger: Logger = LoggerFactory.getLogger("FragnosticI18nServer")

  private val service: Service[Request, Response] = (request: Request) => {
    val response: Response = (request.method, request.path) match {
      case (Method.Get, "/i18n") => handleGetI18n(request)
      case (_, _) => handle404(request)
    }

    Future.value(response)
  }

  private val port: Int = cacheGetInt("FRAGNOSTIC_I18N_SERVER_PORT", 8080)
  logger.info(s"The Fragnostic I18N Server Port is:$port")

  private val server = Http.serve(new InetSocketAddress(port), service)

  Await.ready(server)

}
