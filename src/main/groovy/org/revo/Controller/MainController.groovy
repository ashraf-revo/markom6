package org.revo.Controller

import org.revo.Domain.User
import org.revo.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.user.SimpUserRegistry
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import javax.validation.Valid

/**
 * Created by ashraf on 8/17/2016.
 */
@Controller
@RequestMapping("api")
@Cacheable
class MainController {
    @Autowired
    UserService userService;

    @RequestMapping("register")
    public ResponseEntity Account(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            def collect = result.fieldErrors
                    .collect { [field: it.field, defaultMessage: it.defaultMessage] }
                    .groupBy({ it.field })
            return ResponseEntity.badRequest().body(collect)
        }
        if (userService.findByUsername(user.username).present) return ResponseEntity.badRequest().body([username: [[field:"username",defaultMessage: "please change your username"]]])
        if (userService.findByPhone(user.phone).present) return ResponseEntity.badRequest().body([phone: [[field:"phone",defaultMessage: "please change your phone"]]])
        else {
            String pass = user.password
            userService.create(user)
            return ResponseEntity.ok(userService.createToken(user, pass))
        }
    }


}
