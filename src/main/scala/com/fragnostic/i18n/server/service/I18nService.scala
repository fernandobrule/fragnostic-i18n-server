package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.service.CakeService
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.util.Future
import org.slf4j.{ Logger, LoggerFactory }

class I18nService extends EcommService {

  private[this] val logger: Logger = LoggerFactory.getLogger("I18nService")

  private val localePattern = """([a-z]+)-([A-Z]+)""".r

  override def apply(request: Request): Future[Response] = {
    Future.value(

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
      })
  }

}