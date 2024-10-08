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
@RequestMapping("/azienda/jasper")
public class PdfJasperController {

    @Autowired
    UserService userService;

    @Autowired
    PdfJasperService pdfJasperService;
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('Azienda')")
    public void GeneraPdfIncaricoSpedizione(@RequestBody @Validated AnnuncioDTO annuncioDTO, BindingResult bindingResult, @PathVariable long id) throws Exception {
        if(bindingResult.hasErrors()){
            throw new DtoHasErrors(bindingResult.getAllErrors());
        }

        Trasportatore trasportatore = userService.getTrasportatoreById(id);
        byte[] bytes = pdfJasperService.employeeJasperReportInBytes(annuncioDTO,trasportatore);
        if (null != bytes) {
            ByteArrayResource resource = new ByteArrayResource(bytes);
            String fileName = "Employee24_JasperReport" + "_" + LocalDateTime.now() + ".docx";
            return ResponseEntity.ok()
                    .header(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            throw new BadRequestException("File Download Failed");
        }
    }
}