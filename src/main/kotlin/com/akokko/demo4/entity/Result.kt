package com.akokko.dataanalyze.entity

data class Result<T>(
    var flag: Boolean,
    var code: Int,
    var message: String,
    var data: T?
) {
    constructor(flag: Boolean, code: Int, message: String): this(flag, code, message, null)
}