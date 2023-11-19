package jp.glory.practice.measure.architecture.base.adaptor.web

import jp.glory.practice.measure.architecture.base.adaptor.web.WebError

class WebException(val error: WebError) : Exception()
