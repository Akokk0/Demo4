package com.akokko.demo4.pojo

import javax.persistence.Column
import javax.persistence.Table

@Table(name = "tb_backUser")
data class BackUser(
    @Column(name = "id") var id: Int?,
    @Column(name = "name") var name: String?,
    @Column(name = "password") var password: String?
) {
    constructor(): this(null, null, null)
}
