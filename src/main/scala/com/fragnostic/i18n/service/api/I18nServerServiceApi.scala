package com.fragnostic.i18n.service.api

import com.fragnostic.i18n.server.glue.KeyValJson

import java.util.Locale

trait I18nServerServiceApi {

  def i18nServerServiceApi: I18nServerServiceApi

  trait I18nServerServiceApi {

    def loadMessages(locale: Locale): Either[String, List[KeyValJson]]

  }

}
