package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trasportatore/chat")
public class ChatTController {

    @Autowired
    ChatService chatService;
    @PostMapping("")
    public Chat save (@RequestBody @Validated ChatDTO chatDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

        }
        return chatService.save(chatDTO);
    }
}
