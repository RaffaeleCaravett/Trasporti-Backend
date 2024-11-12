package com.example.TrasportiBackend.Pdf;

import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.User.UserService;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.DtoHasErrors;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/trasportatore/jasper")
public class PdfJasperController {



    @Autowired
    PdfJasperService pdfJasperService;
    @PostMapping("/{id}/{spedizioneId}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public byte[] GeneraPdfIncaricoSpedizione(@PathVariable long spedizioneId, @PathVariable long id) throws Exception {

       return pdfJasperService.richiedi(spedizioneId,id,"");
    }
}
