package com.fragnostic.i18n.server.service

import com.twitter.finagle.Service
import com.twitter.finagle.http.{ Request, Response }

abstract class AbstractService extends Service[Request, Response] with ServiceSupport {

}
