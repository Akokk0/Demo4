package com.akokko.demo4.dao

import com.akokko.demo4.pojo.Token
import org.apache.ibatis.annotations.Delete
import org.springframework.stereotype.Repository
import tk.mybatis.mapper.common.Mapper

@Repository
interface TokenMapper: Mapper<Token> {

    @Delete("delete from tb_cookie where 1 = 1")
    fun deleteAll();

}