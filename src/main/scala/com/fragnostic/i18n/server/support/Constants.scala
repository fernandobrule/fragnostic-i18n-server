package com.fragnostic.i18n.server.support

trait Constants extends HeaderNames {

  val CONTENT_TYPE_APPLICATION_JSON = "application/json"

  val SERVER_PORT = "DIST_ECOMM_LOJA_SERVER_PORT"
  val SERVER_NAME = "Distronica * Loja * Server"

  val STAR = "*"

  val ALLOWED_METHODS = "POST, GET, OPTIONS, PATCH, DELETE"
  val ALLOWED_HEADERS = s"Content-Type, $HEADER_X_DI_ECOMM_LOJA_CHANNEL, $HEADER_X_DI_ECOMM_LOJA_COUNTRY"

}
