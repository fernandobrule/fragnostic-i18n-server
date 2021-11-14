package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.support.HeaderNames
import com.twitter.finagle.http.Request
import org.slf4j.{ Logger, LoggerFactory }

import scala.util.matching.Regex

trait HeaderHandler extends HeaderNames {

  private[this] val logger: Logger = LoggerFactory.getLogger("HeaderHandler")

  protected val LOCALE_PATTERN_REGEX: Regex = """([a-z]+)-([A-Z]+)""".r

  protected def getHeader(request: Request, headerName: String): Option[String] =
    request.headerMap.get(headerName) map (
      header => Some(header) //
    ) getOrElse {
        logger.error(s"getHeader() - fail on get Header: $headerName")
        None
      }

  protected def getHeaderLanguage(request: Request): Option[(String, String)] =
    getHeader(request, HEADER_ACCEPT_LANGUAGE) map (
      header => header match {
        case LOCALE_PATTERN_REGEX(lang, rgn) => Some((lang, rgn): (String, String))
        case _ =>
          logger.error(s"getHeaderLanguage() - fail on get Header: $HEADER_ACCEPT_LANGUAGE with $header")
          None
      }) getOrElse None

  protected def getHeaderXDiEcommLojaCountry(request: Request): Option[String] =
    getHeader(request, HEADER_X_DI_ECOMM_LOJA_COUNTRY)

  protected def getHeaderXDiEcommLojaChannel(request: Request): Option[String] =
    getHeader(request, HEADER_X_DI_ECOMM_LOJA_CHANNEL)

}
