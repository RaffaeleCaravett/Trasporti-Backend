package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.exceptions.DtoHasErrors;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/azienda/messaggi")
public class MessaggiAzController {

    @Autowired
    private MessaggiService messaggiService;


    @CrossOrigin
    @GetMapping("/room/{room}")
    public ResponseEntity<List<Messaggi>> getMessages(@PathVariable String room){
      return ResponseEntity.ok(messaggiService.findAllByRoom(room));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('Azienda')")
    public Messaggi save(@RequestBody @Validated MessaggioDTO messaggioDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new DtoHasErrors(bindingResult.getAllErrors());
        }
        return messaggiService.save(messaggioDTO);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Azienda')")
    public List<Messaggi> getByChatId(@PathVariable long id){
        return messaggiService.getByChatId(id);
    }
    @PutMapping("/{uId}/{messaggioId}")
    @PreAuthorize("hasAuthority('Azienda')")
    public Messaggi putById(@RequestBody @Validated MessaggioDTO messaggioDTO,BindingResult bindingResult, @PathVariable long uId,@PathVariable long messaggioId){
        return messaggiService.putById(messaggioId,uId,"Azienda",messaggioDTO);
    }
    @DeleteMapping("/{uId}/{messaggioId}")
    @PreAuthorize("hasAuthority('Azienda')")
    public Messaggi deleteById(@PathVariable long id, @PathVariable long messaggioId){
        return messaggiService.deleteById(messaggioId,id,"Azienda");
    }

}
