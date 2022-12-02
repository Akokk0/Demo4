package com.akokko.demo4.pojo

import javax.persistence.Column
import javax.persistence.Table

@Table(name = "tb_cookie")
data class Token(
    @Column(name = "name") var name: String?,
    @Column(name = "cookie") var cookie: String?
) {
    constructor(): this(null, null)
}
