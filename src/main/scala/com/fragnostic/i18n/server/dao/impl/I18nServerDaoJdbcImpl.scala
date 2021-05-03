package com.fragnostic.i18n.server.dao.impl

import com.fragnostic.i18n.server.dao.api.I18nServerDaoApi
import org.slf4j.{ Logger, LoggerFactory }

trait I18nServerDaoJdbcImpl extends I18nServerDaoApi {

  def I18nServerCrud: I18nServerCrud = new I18nServerDaoJdbcImpl()

  class I18nServerDaoJdbcImpl() extends I18nServerCrud {

    private[this] val logger: Logger = LoggerFactory.getLogger(getClass.getName)

  }

}

