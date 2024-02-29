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

import java.net.InetSocketAddress

object FragnosticI18nServer extends App
  with ConfIntSupport
  with JsonSupport
  with Http404Handler {

  private[this] val logger: Logger = LoggerFactory.getLogger("FragnosticI18nServer")

  //
  // https://twitter.github.io/finagle/guide/Names.html#paths
  //
  private val routes: Service[Request, Response] = (request: Request) => {

    if (logger.isDebugEnabled) {
      logger.debug(s"route - enter - request.method[${request.method}], request.path[${request.path}]")
    }

    (request.method, Path(request.path)) match {
      case Method.Get -> Root / "heartbeats" / who => new HeartbeatsService(who).apply(request)
      case Method.Get -> Root / "i18n" => new I18nService().apply(request)
      case (_, _) => handle404(request)
    }
  }

  private val portDefault = 8080
  private val port: Int = CakeConfEnvService.confEnvService.getInt(SERVER_PORT) fold (
    error => {
      logger.error(s"getInt $SERVER_PORT, $error")
      portDefault
    },
    port => port //
  )

  logger.info(
    s"""
       |=============================================================================================
       |   __                                 _   _        _ __  ___
       |  / _|                               | | (_)      (_)_ |/ _ \\
       | | |_ _ __ __ _  __ _ _ __   ___  ___| |_ _  ___   _ | | (_) |_ __    ___  ___ _ ____   _____ _ __
       | |  _| '__/ _` |/ _` | '_ \\ / _ \\/ __| __| |/ __| | || |> _ <| '_ \\  / __|/ _ \\ '__\\ \\ / / _ \\ '__|
       | | | | | | (_| | (_| | | | | (_) \\__ \\ |_| | (__  | || | (_) | | | | \\__ \\  __/ |   \\ V /  __/ |
       | |_| |_|  \\__,_|\\__, |_| |_|\\___/|___/\\__|_|\\___| |_||_|\\___/|_| |_| |___/\\___|_|    \\_/ \\___|_|
       |                 __/ |
       |                |___/
       |=============================================================================================
       |                                 The Fragnostic I18N Server port is:$port
       |
       | <<<<<<<<<<<<<<<<<<<<<<<<<<< developed by atacamasoft\u00ae2022 >>>>>>>>>>>>>>>>>>>>>>>>>>>
       |
       |=============================================================================================
       |""".stripMargin)

  private val server = Http.serve(new InetSocketAddress(port), routes)

  Await.ready(server)

}
