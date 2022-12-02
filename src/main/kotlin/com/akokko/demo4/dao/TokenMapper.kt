package com.akokko.demo4.dao

import com.akokko.demo4.pojo.Token
import org.springframework.stereotype.Repository
import tk.mybatis.mapper.common.Mapper

@Repository
interface TokenMapper: Mapper<Token> {

}