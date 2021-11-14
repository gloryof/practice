package jp.glory.practicegraphql.app.base.adaptor.web.error

class WebException(val error: WebError) : Exception()