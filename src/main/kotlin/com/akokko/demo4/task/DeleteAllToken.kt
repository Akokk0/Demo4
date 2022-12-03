package com.akokko.demo4.task

import com.akokko.demo4.dao.TokenMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DeleteAllToken {

    @Autowired
    lateinit var tokenMapper: TokenMapper

    @Scheduled(cron = "0 0 0 */1 * ?")
    fun delete() {
        val count = tokenMapper.deleteAll()
        println("本次删除用户Cookie：${count}个")
    }
}