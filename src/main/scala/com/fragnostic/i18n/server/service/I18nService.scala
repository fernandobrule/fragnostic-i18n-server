package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.glue.ResponseJson
import com.fragnostic.i18n.server.support.Constants
import com.fragnostic.i18n.service.CakeService
import com.twitter.finagle.http.{ Request, Response, Status }
import com.twitter.util.Future
import org.slf4j.{ Logger, LoggerFactory }

import java.util.Locale

class I18nService extends AbstractService with Constants {

  private[this] val logger: Logger = LoggerFactory.getLogger("I18nService")

  private val localePattern = """([a-z]+)-([A-Z]+)""".r

  private def newLocale(language: String, region: String): Locale =
    new Locale.Builder() //
      .setLanguage(language) //
      .setRegion(region) //
      .build()

  override def apply(request: Request): Future[Response] = {
    Future.value(
      request.headerMap.get(ACCEPT_LANGUAGE) match {
        case Some(acceptLanguage) =>
          acceptLanguage match {
            case localePattern(language, region) =>
              CakeService.i18nServerService.loadMessages(newLocale(language, region)) fold (
                error => {
                  logger.error(s"handleGetI18n() - {}", error)
                  handle(request, Status.BadRequest, ResponseJson(errors = List(error)))
                },
                messages => handle(request, Status.Ok, ResponseJson(success = true, messages = messages)) //
              )
            case _ => handle(request, Status.BadRequest, ResponseJson(errors = List("i18n.service.error.no.locale")))
          }
        case None => handle(request, Status.BadRequest, ResponseJson(errors = List("i18n.service.error.no.locale")))
      } //
    )
  }

}