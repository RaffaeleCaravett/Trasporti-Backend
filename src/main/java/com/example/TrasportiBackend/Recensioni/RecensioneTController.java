package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore/recensione")
public class RecensioneTController {
    @Autowired
    RecensioneService recensioneService;

    @PostMapping("")
    public RecensioneT save(@RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.saveRT(recensioneAzDTO);
    }
    @PutMapping("/{id}/{recensioneTDTOId}")
    public RecensioneT putById(@PathVariable long id, @PathVariable long recensioneTDTOId, @RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.putTbyId(id,recensioneTDTOId,recensioneAzDTO);
    }
    @GetMapping("/paginated/{TId}/{stato}")
    public Page<RecensioneT> getAllPaginatedT (@PathVariable long TId, @PathVariable String stato, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedT(page,size,orderBy,TId,stato);
    }
}
