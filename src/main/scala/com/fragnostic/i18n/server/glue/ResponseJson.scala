package com.fragnostic.i18n.server.glue

case class ResponseJson(
  success: Boolean = false,
  body: String = "",
  messages: List[KeyValJson] = Nil,
  errors: List[String] = Nil //
)
