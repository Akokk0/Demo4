package com.akokko.demo4.service

import com.akokko.demo4.dao.TokenMapper
import com.akokko.demo4.dao.UserMapper
import com.akokko.demo4.entity.Result
import com.akokko.demo4.entity.StatusCode
import com.akokko.demo4.pojo.Token
import com.akokko.demo4.pojo.User
import com.akokko.demo4.util.CookieUtil
import com.akokko.demo4.util.EmailUtil
import com.akokko.demo4.util.IdWorker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.mail.MessagingException
import javax.servlet.http.HttpServletResponse

@Service
class UserService {

    @Value("\${cookieDomain}")
    private lateinit var cookieDomain: String

    @Value("\${cookieMaxAge}")
    private lateinit var cookieMaxAge: Number

    @Autowired
    private lateinit var emailUtil: EmailUtil

    @Autowired
    private lateinit var idWorker: IdWorker

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var tokenMapper: TokenMapper

    fun signUp(user: User): Result<User> {
        if (user.name.isNullOrBlank() || user.password.isNullOrBlank() || user.email.isNullOrBlank()) {
            return Result(false, StatusCode.ERROR, "用户名、密码或邮箱为空！")
        } else {
            user.verify = 0
            user.code = idWorker.nextId().toString()
            try {
                emailUtil.sendHtmlMail(user.email, "激活您的账号", "<h1>这是您的验证链接，请点击验证链接激活账号:</h1><a>http://${cookieDomain}/activation/${user.code}</a>")
            } catch (e: MessagingException) {
                return Result(false, StatusCode.ERROR, "服务器正忙，请稍后重试！")
            }
            userMapper.insertSelective(user)
            return Result(true, StatusCode.OK, "注册成功")
        }
    }

    fun activation(code: String): Result<User> {
        if (code.isNullOrBlank()) {
            return Result(false, StatusCode.ERROR, "激活码为空！")
        } else {
            val user = User()
            user.code = code
            val _user = userMapper.selectOne(user)
            return if (_user == null) {
                Result(false, StatusCode.ERROR, "您的链接可能有误，请检查后重试！")
            } else {
                return if (_user.verify == 1) {
                    Result(false, StatusCode.ERROR, "您的账号已经激活过了，请不要重复激活！")
                } else {
                    val user = User()
                    user.id = _user.id
                    user.verify = 1
                    userMapper.updateByPrimaryKeySelective(user)
                    Result(true, StatusCode.OK, "您的账号已成功激活！")
                }
            }
        }
    }

    fun login(user: User, response: HttpServletResponse): Result<String> {
        val _user = userMapper.selectOne(user)
        return if (_user == null) {
            Result(false, StatusCode.ACCESSERROR, "登录失败，账号密码不匹配！")
        } else {
            if (_user.verify == 0) {
                CookieUtil.deleteCookie(response, "uid")
                Result(false, StatusCode.ERROR, "登录失败，账号未验证！")
            } else {
                val cookieValue = addCookie(response)
                if (cookieValue.isNullOrEmpty()) {
                    Result(false, StatusCode.ERROR, "登录失败，请稍后重试！")
                } else {
                    Result(true, StatusCode.OK, "登录成功！", cookieValue)
                }
            }
        }
    }

    fun retrievePwd(user: User): Result<User> {
        if (user.email.isNullOrBlank()) return Result(false, StatusCode.ERROR, "用户名或邮箱为空！")
        val _user = userMapper.selectOne(user)
        return if (_user == null) {
            Result(false, StatusCode.ERROR, "未查询到该账号！")
        } else {
            val sendUser = User()
            sendUser.id = _user.id
            sendUser.email = _user.email
            sendUser.password = idWorker.nextId().toString()
            userMapper.updateByPrimaryKeySelective(sendUser)
            try {
                emailUtil.sendHtmlMail(sendUser.email, "更改密码", "<h1>请点击链接更改密码:<h1><a>http://${cookieDomain}/forget/${sendUser.email}/${sendUser.password}</a>")
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
            Result(true, StatusCode.OK, "请检查邮箱更改密码！")
        }
    }

    fun verifyCPCode(user: User): Result<User> {
        if (user.email.isNullOrEmpty() || user.password.isNullOrEmpty()) return Result(false, StatusCode.ERROR, "邮箱或验证码为空，请检查链接后重试！")
        val _user = userMapper.selectOne(user)
        return if (_user == null) {
            Result(false, StatusCode.ERROR, "邮箱和验证码不匹配，请检查链接后重试！")
        } else {
            Result(true, StatusCode.OK, "验证成功！")
        }
    }

    fun changePwd(user: User): Result<User> {
        if (user.email.isNullOrEmpty() || user.name.isNullOrEmpty() || user.password.isNullOrEmpty()) return Result(false, StatusCode.ERROR, "您的输入有误，请检查链接后重试！")
        val verifyUser = User()
        verifyUser.email = user.email
        verifyUser.password = user.name
        val _verifyUser = userMapper.selectOne(verifyUser)
        if (_verifyUser == null) {
            return Result(false, StatusCode.ACCESSERROR, "您的输入有误，请检查链接后重试！")
        } else {
            val sendUser = User()
            sendUser.id = _verifyUser.id
            sendUser.password = user.password
            return if (userMapper.updateByPrimaryKeySelective(sendUser) == 0) {
                Result(false, StatusCode.ERROR, "修改密码失败，请稍后重试！")
            } else {
                Result(true, StatusCode.OK, "修改密码成功，请重新访问！")
            }
        }
    }

    fun verifyToken(token: Token): Result<Token> {
        return if (tokenMapper.selectOne(token) == null) {
            Result(false, StatusCode.ACCESSERROR, "验证失败，请重新登录！")
        } else {
            Result(true, StatusCode.OK, "验证成功！")
        }
    }

    fun checkEmail(user: User): Result<User> {
        if (user.email.isNullOrEmpty()) return Result(false, StatusCode.ERROR, "输入的邮箱为空，请检查后重试！")
        return if (userMapper.selectOne(user) != null) {
            Result(false, StatusCode.ERROR, "该邮箱已被注册，请换其他邮箱重试！")
        } else {
            Result(true, StatusCode.OK, "该邮箱未被注册！")
        }
    }

    private fun addCookie(response: HttpServletResponse): String? {
        val cookieValue: String = idWorker.nextId().toString()
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", cookieValue, cookieMaxAge.toInt(), false)
        val token = Token()
        token.name = "uid"
        token.cookie = cookieValue
        val count: Int = tokenMapper.insertSelective(token)
        if (count > 0) return cookieValue else return null

    }
}