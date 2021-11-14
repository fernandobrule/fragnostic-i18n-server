package com.fragnostic.i18n.server.handler

import java.util.Locale

trait HandlerCommons {

  def newLocale(language: String, region: String): Locale =
    new Locale.Builder() //
      .setLanguage(language) //
      .setRegion(region) //
      .build()

}
