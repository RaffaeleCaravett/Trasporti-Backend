package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.exceptions.DtoHasErrors;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/azienda/chat")
public class ChatAzController {
    @Autowired
    ChatService chatService;
    @PostMapping("")
    @PreAuthorize("hasAuthority('Azienda')")
    public Chat save (@RequestBody @Validated ChatDTO chatDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new DtoHasErrors(bindingResult.getAllErrors());
        }
        return chatService.save(chatDTO);
    }
    @GetMapping("/byAzId/{id}")
    @PreAuthorize("hasAuthority('Azienda')")
    public List<Chat> getByAzId(@PathVariable long id){
        return chatService.getByAziendaId(id);
    }
    @GetMapping("/byAziendaIdAndTrasportatoreId/{aziendaId}/{trasportatoreId}")
    @PreAuthorize("hasAuthority('Azienda')")
    public Chat findByAzIdAndTId(@PathVariable long aziendaId,@PathVariable long trasportatoreId){
        return chatService.getByAziendaIdAndTrasportatoreId(aziendaId,trasportatoreId).orElseThrow(()-> new UserNotFoundException("Chat non trovata"));
    }
}
