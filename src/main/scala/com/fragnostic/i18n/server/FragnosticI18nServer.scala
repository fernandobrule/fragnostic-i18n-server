package com.fragnostic.i18n.server

import com.fragnostic.conf.env.service.CakeConfEnvService
import com.fragnostic.conf.facade.service.support.ConfIntSupport
import com.fragnostic.i18n.server.handler.Http404Handler
import com.fragnostic.i18n.server.service.{ HeartbeatsService, I18nService }
import com.fragnostic.i18n.server.support.JsonSupport
import com.twitter.finagle.http.path.{ ->, /, Path, Root }
import com.twitter.finagle.http.{ Method, Request, Response }
import com.twitter.finagle.{ Http, Service }
import com.twitter.util.Await
import org.slf4j.{ Logger, LoggerFactory }
import sun.management.jmxremote.ConnectorBootstrap.DefaultValues

import java.net.InetSocketAddress

object FragnosticI18nServer extends App
  with ConfIntSupport
  with JsonSupport
  with Http404Handler
  //with PreflightHandler
  with DefaultValues {
  //with Constants {

  private[this] val logger: Logger = LoggerFactory.getLogger("FragnosticI18nServer")

  private val routes: Service[Request, Response] = (request: Request) => {
    if (logger.isInfoEnabled) {
      logger.info(s"route - enter - request.method[${request.method}], request.path[${request.path}]")
    }
    // https://twitter.github.io/finagle/guide/Names.html#paths
    (request.method, Path(request.path)) match {

      //
      // Heartbeats
      //
      case Method.Get -> Root / "heartbeats" => new HeartbeatsService().apply(request)

      //
      // I18n
      //
      case Method.Get -> Root / "i18n" => new I18nService().apply(request)

      // 404
      case (_, _) => handle404(request)
    }
  }

  private val portDefault = 8080
  private val port: Int = CakeConfEnvService.confEnvService.getInt("FRAGNOSTIC_I18N_SERVER_PORT") fold (
    error => {
      logger.error(s"getInt FRAGNOSTIC_I18N_SERVER_PORT, $error")
      portDefault
    },
    opt => opt map (
      port => port //
    ) getOrElse (portDefault) //
  )

  logger.info(s"The Fragnostic I18N Server Port is:$port")

  private val server = Http.serve(new InetSocketAddress(port), routes)

  Await.ready(server)

}
