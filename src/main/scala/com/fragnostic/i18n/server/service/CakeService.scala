package com.fragnostic.i18n.server.service

import com.fragnostic.i18n.server.service.api.I18nServerServiceApi
import com.fragnostic.i18n.server.service.impl.I18nServerServiceImpl
import com.fragnostic.i18n.server.dao.impl.I18nServerDaoJdbcImpl

object CakeService {

  lazy val i18nServerService = i18nServerServicePiece.i18nServerServiceApi

  lazy val i18nServerServicePiece: I18nServerServiceApi = new I18nServerServiceImpl with I18nServerDaoJdbcImpl {
  }

}
