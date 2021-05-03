package com.fragnostic.i18n.server.handler

import com.fragnostic.i18n.server.glue.ResponseJson
import com.twitter.finagle.http.{ Request, Response, Status }

trait DefaultHandler extends Handler {

  def handlePost(request: Request): Response =
    handle(request, Status.NotImplemented, ResponseJson(errors = List(s"POST to ${request.path} NOT YET")))

  def handleGet(request: Request): Response =
    handle(request, Status.NotImplemented, ResponseJson(errors = List(s"GET to ${request.path} NOT YET")))

  def handlePut(request: Request): Response =
    handle(request, Status.NotImplemented, ResponseJson(errors = List(s"PUT to ${request.path} NOT YET")))

  def handlePatch(request: Request): Response =
    handle(request, Status.NotImplemented, ResponseJson(errors = List(s"PATCH to ${request.path} NOT YET")))

  def handleDelete(request: Request): Response =
    handle(request, Status.NotImplemented, ResponseJson(errors = List(s"DELETE to ${request.path} NOT YET")))

  def handle404(request: Request): Response =
    handle(request, Status.NotFound, ResponseJson(errors = List(s"${request.path} NOT YET")))

}
