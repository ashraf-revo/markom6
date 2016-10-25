package org.revo.Controller

import org.revo.Domain.Message
import org.revo.Service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * Created by ashraf on 30/08/16.
 */
@Controller
@RequestMapping(value = "api/message")
class MessageController {
    @Autowired
    MessageService messageService

    @GetMapping
    ResponseEntity messages(@RequestParam(required = false) String id) {
        ResponseEntity.ok(messageService.Messages(id))
    }

    @PostMapping(value = "new")
    ResponseEntity NewMessage(@RequestBody Message message) {
        ResponseEntity.ok(messageService.save(message))
    }
}
