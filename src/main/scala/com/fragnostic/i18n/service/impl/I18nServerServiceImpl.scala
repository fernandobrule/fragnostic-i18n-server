package com.fragnostic.i18n.service.impl

import com.fragnostic.i18n.dao.api.I18nServerDaoApi
import com.fragnostic.i18n.server.glue.KeyValJson
import com.fragnostic.i18n.service.api.I18nServerServiceApi
import com.fragnostic.i18n.service.i18n.I18nServerServiceI18n
import org.slf4j.{ Logger, LoggerFactory }

import java.util.{ Locale, ResourceBundle }

trait I18nServerServiceImpl extends I18nServerServiceApi {
  this: I18nServerDaoApi =>

  private[this] val logger: Logger = LoggerFactory.getLogger("I18nServerServiceImpl")

  def i18nServerServiceApi = new DefaultI18nServerService

  class DefaultI18nServerService extends I18nServerServiceApi {

    private def loadMessages2(resourceBundle: ResourceBundle, iterator: java.util.Iterator[String]): List[KeyValJson] =
      if (iterator.hasNext) {
        val key: String = iterator.next()
        KeyValJson(key, resourceBundle.getString(key)) :: loadMessages2(resourceBundle, iterator)
      } else {
        Nil
      }

    override def loadMessages(locale: Locale): Either[String, List[KeyValJson]] =
      new I18nServerServiceI18n().getResourceBundle(Some(locale)) map (
        resourceBundle => {
          Right(loadMessages2(resourceBundle, resourceBundle.keySet().iterator()))
        }) getOrElse {
          logger.error(s"loadMessages() - ooops")
          Left("i18nserver.service.loadmessages.error")
        }

  }

}
