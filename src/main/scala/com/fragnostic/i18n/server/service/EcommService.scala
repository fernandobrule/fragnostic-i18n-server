package com.fragnostic.i18n.server.service

import com.twitter.finagle.Service
import com.twitter.finagle.http.{ Request, Response }

trait EcommService extends Service[Request, Response] with ServiceSupport {

}
