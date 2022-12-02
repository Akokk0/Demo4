package com.akokko.demo4.dao

import com.akokko.demo4.pojo.User
import org.springframework.stereotype.Repository
import tk.mybatis.mapper.common.Mapper

@Repository
interface UserMapper: Mapper<User> {
}