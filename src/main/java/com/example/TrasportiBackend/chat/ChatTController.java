package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.exceptions.DtoHasErrors;
import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trasportatore/chat")
public class ChatTController {

    @Autowired
    ChatService chatService;
    @PostMapping("")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public Chat save (@RequestBody @Validated ChatDTO chatDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
throw new DtoHasErrors(bindingResult.getAllErrors());
        }
        return chatService.save(chatDTO);
    }
    @GetMapping("/byTId/{id}")
    @PreAuthorize("hasAuthority('Trasportatore')")
public List<Chat> getByTId(@PathVariable long id){
        return chatService.getByTrasportatoreId(id);
    }
}
