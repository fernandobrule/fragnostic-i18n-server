package com.fragnostic.i18n.service

import com.fragnostic.i18n.service.api.I18nServerServiceApi
import com.fragnostic.i18n.service.impl.I18nServerServiceImpl

object CakeService {

  lazy val i18nServerService = i18nServerServicePiece.i18nServerServiceApi

  lazy val i18nServerServicePiece: I18nServerServiceApi = new I18nServerServiceImpl {
  }

}
