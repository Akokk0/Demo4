package com.akokko.demo4.controller

import com.akokko.demo4.entity.Result
import com.akokko.demo4.entity.StatusCode
import com.akokko.demo4.pojo.Token
import com.akokko.demo4.pojo.User
import com.akokko.demo4.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/signUp")
    fun signUp(@RequestBody user: User): Result<User> {
        return userService.signUp(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User, response: HttpServletResponse): Result<String> {
        return userService.login(user, response)
    }

    @GetMapping("/activation/{code}")
    fun activation(@PathVariable("code") code: String): Result<User> {
        return userService.activation(code)
    }

    @PostMapping("retrievePwd")
    fun retrievePwd(@RequestBody user: User): Result<User> {
        return userService.retrievePwd(user)
    }

    @PostMapping("/verifyCPCode")
    fun verifyCPCode(@RequestBody user: User): Result<User> {
        return userService.verifyCPCode(user)
    }

    @PostMapping("/changePwd")
    fun changePwd(@RequestBody user: User): Result<User> {
        return userService.changePwd(user)
    }

    @PostMapping("/verifyToken")
    fun verifyToken(@RequestBody token: Token): Result<Token> {
        return userService.verifyToken(token)
    }

    @PostMapping("/checkEmail")
    fun checkEmail(@RequestBody user: User): Result<User> {
        return userService.checkEmail(user)
    }

    @GetMapping("/getAllUser")
    fun getAllUser(): Result<List<User>> {
        return userService.getAllData()
    }

    @PostMapping("/addUser")
    fun addUser(@RequestBody user: User): Result<User> {
        return userService.addUser(user)
    }

    @DeleteMapping("/deleteUser/{id}")
    fun deleteUser(@PathVariable("id") id: Int): Result<User> {
        return userService.deletePerson(id)
    }

    @PostMapping("/editUser")
    fun editUser(@RequestBody user: User): Result<User> {
        return userService.editUser(user)
    }

}