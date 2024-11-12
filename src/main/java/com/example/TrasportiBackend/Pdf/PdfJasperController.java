package com.example.TrasportiBackend.Pdf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore/jasper")
public class PdfJasperController {



    @Autowired
    PdfJasperService pdfJasperService;
    @GetMapping("/{spedizioneId}/{id}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    @Transactional
    public byte[] GeneraPdfIncaricoSpedizione(@PathVariable long spedizioneId, @PathVariable long id) throws Exception {

       return pdfJasperService.richiedi(spedizioneId,id,"");
    }
}
