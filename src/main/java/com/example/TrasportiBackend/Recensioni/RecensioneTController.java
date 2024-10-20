package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/azienda")
public class RecensioneTController {
    @Autowired
    RecensioneService recensioneService;

    @PostMapping("/recensione")
    @PreAuthorize("hasAuthority('Azienda')")
    public RecensioneT save(@RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.saveRT(recensioneAzDTO);
    }
    @PutMapping("/recensione/{id}/{recensioneTDTOId}")
    @PreAuthorize("hasAuthority('Azienda')")
    public RecensioneT putById(@PathVariable long id, @PathVariable long recensioneTDTOId, @RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.putTbyId(id,recensioneTDTOId,recensioneAzDTO);
    }
    @GetMapping("/recensione/paginatedAndStato/{TId}/{stato}")
    public Page<RecensioneT> getAllPaginatedTAndStato (@PathVariable long TId, @PathVariable String stato, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedT(page,size,orderBy,TId,stato);
    }
    @GetMapping("/recensioneTr/paginated/{TId}")
    public Page<RecensioneT> getAllPaginatedT (@PathVariable long TId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedT(page,size,orderBy,TId);
    }
    @DeleteMapping("/recensione/{daId}/{receId}")
    @PreAuthorize("hasAuthority('Azienda')")
    public boolean deleteByDaIdAndReceID (@PathVariable long daId, @PathVariable long receId){
        return recensioneService.deleteT(daId,receId);
    }
    @GetMapping("/recensioneAz/paginatedAndStato/{AzId}/{stato}")
    public Page<RecensioneAz> getAllPaginatedStatoAz (@PathVariable long AzId, @PathVariable String stato,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedAz(page,size,orderBy,AzId,stato);
    }
    @GetMapping("/recensioneAz/paginated/{AzId}")
    public Page<RecensioneAz> getAllPaginatedAz (@PathVariable long AzId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return recensioneService.getAllPaginatedAz(page,size,orderBy,AzId);
    }
}
