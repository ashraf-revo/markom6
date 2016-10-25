package org.revo.Controller

import org.revo.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * Created by ashraf on 30/08/16.
 */
@Controller
@RequestMapping(value = "api/user")
class UserController {
    @Autowired
    UserService userService

    @Autowired
    Map<String, Boolean> onlineUsers

    @GetMapping
    ResponseEntity user() {
        ResponseEntity.ok(userService.current())
    }

    @PostMapping
    ResponseEntity search(@RequestBody String Regex) {
        ResponseEntity.ok(userService.search(Regex))
    }

    @GetMapping("{id}")
    ResponseEntity userById(@PathVariable String id) {
        ResponseEntity.ok(userService.user(id))
    }

    @GetMapping("online/{id}")
    ResponseEntity online(@PathVariable String id) {
        return (onlineUsers.get(userService.findOneById(id).username)) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build()
    }
}
