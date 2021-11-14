package com.fragnostic.i18n.server.support

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

trait DateSupport {

  def now: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

}
