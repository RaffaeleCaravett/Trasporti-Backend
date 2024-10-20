package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore")
public class RecensioneAzController {

@Autowired
    RecensioneService recensioneService;


@PostMapping("/recensione")
@PreAuthorize("hasAuthority('Trasportatore')")
public RecensioneAz save(@RequestBody @Validated RecensioneAzDTO recensioneAzDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        throw new BadRequestException(bindingResult.getAllErrors());
    }
    return recensioneService.saveRAz(recensioneAzDTO);
}
    @PutMapping("/recensione/{id}/{recensioneAzDTOId}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public RecensioneAz putById(@PathVariable long id, @PathVariable long recensioneAzDTOId, @RequestBody @Validated RecensioneAzDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.putAzbyId(id,recensioneAzDTOId,recensioneAzDTO);
    }
    @GetMapping("/recensione/paginatedAndStato/{AzId}/{stato}")
    public Page<RecensioneAz> getAllPaginatedStatoAz (@PathVariable long AzId, @PathVariable String stato,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
    return recensioneService.getAllPaginatedAz(page,size,orderBy,AzId,stato);
    }
    @GetMapping("/recensioneAz/paginated/{AzId}")
    public Page<RecensioneAz> getAllPaginatedAz (@PathVariable long AzId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedAz(page,size,orderBy,AzId);
    }
    @DeleteMapping("/recensione/{daId}/{receId}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public boolean deleteByDaIdAndReceID (@PathVariable long daId, @PathVariable long receId){
        return recensioneService.deleteAz(daId,receId);
    }
    @GetMapping("/recensioneAz/paginatedAndStato/{TId}/{stato}")
    public Page<RecensioneT> getAllPaginatedTAndStato (@PathVariable long TId, @PathVariable String stato, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedT(page,size,orderBy,TId,stato);
    }
    @GetMapping("/recensioneTr/paginated/{TId}")
    public Page<RecensioneT> getAllPaginatedT (@PathVariable long TId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedT(page,size,orderBy,TId);
    }
}
