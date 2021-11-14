package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.service.CakeService
import com.twitter.finagle.http.{ Request, Response, Status }
import org.slf4j.{ Logger, LoggerFactory }

import java.util.Locale

trait GetI18nHandler extends Handler {

  private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)
  private val localePattern = """([a-z]+)-([A-Z]+)""".r

  private def newLocale(language: String, region: String): Locale =
    new Locale.Builder() //
      .setLanguage(language) //
      .setRegion(region) //
      .build()

  def handleGetI18n(request: Request): Response = {
    if (logger.isDebugEnabled) {
      logger.debug(s"handleGetI18n() - enter")
    }

    request.headerMap.get("Accept-Language") match {
      case Some(acceptLanguage) =>
        acceptLanguage match {
          case localePattern(language, region) =>
            CakeService.i18nServerService.loadMessages(newLocale(language, region)) fold (
              error => {
                logger.error(s"handleGetI18n() - {}", error)
                handle(request, Status.BadRequest, ResponseJson(errors = List(error)))
              },
              messages => {
                if (logger.isDebugEnabled) {
                  logger.debug(s"handleGetI18n() - messages are ready")
                }
                handle(request, Status.Ok, ResponseJson(success = true, messages = messages))
              })
          case _ => handle(request, Status.BadRequest, ResponseJson(errors = List("get.i18n.handler.error.no.locale")))
        }
      case None => handle(request, Status.BadRequest, ResponseJson(errors = List("get.i18n.handler.error.no.locale")))
    }

  }

}
