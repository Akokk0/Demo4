package com.akokko.demo4.pojo

import javax.persistence.Id
import javax.persistence.Table

@Table(name = "tb_user")
data class User(
    @Id var id: Int?,
    var name: String?,
    var email: String?,
    var password: String?,
    var code: String?,
    var verify: Int?,
) {
    constructor(): this(null, null, null, null, null, null)
}