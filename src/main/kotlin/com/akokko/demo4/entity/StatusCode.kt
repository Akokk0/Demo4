package com.akokko.dataanalyze.entity

class StatusCode {
    companion object{
        const val OK = 20000  // 成功
        const val ERROR = 20001  // 失败
        const val LOGINERROR = 20002  // 用户名或密码错误
        const val ACCESSERROR = 20003  // 权限不足
        const val REMOTEERROR = 20004  // 远程调用失败
        const val REPERROR = 20005  // 重复操作
    }
}