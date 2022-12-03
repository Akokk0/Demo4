package com.akokko.demo4

import com.akokko.demo4.util.EmailUtil
import com.akokko.demo4.util.IdWorker
import com.akokko.demo4.util.IpAddrUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import tk.mybatis.spring.annotation.MapperScan

@SpringBootApplication
@EnableScheduling
@MapperScan("com.akokko.demo4.dao")
class Demo4Application {
    @Bean
    fun idWorker(): IdWorker {
        return IdWorker()
    }

    @Bean
    fun emailUtil(): EmailUtil {
        return EmailUtil()
    }

    @Bean
    fun ipAddrUtil(): IpAddrUtil {
        return IpAddrUtil()
    }

}

fun main(args: Array<String>) {
    runApplication<Demo4Application>(*args)
}
