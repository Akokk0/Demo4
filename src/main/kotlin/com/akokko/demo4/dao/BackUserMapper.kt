package com.akokko.demo4.dao

import com.akokko.demo4.pojo.BackUser
import org.springframework.stereotype.Repository
import tk.mybatis.mapper.common.Mapper

@Repository
interface BackUserMapper: Mapper<BackUser> {
}