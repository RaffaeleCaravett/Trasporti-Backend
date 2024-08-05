package com.example.TrasportiBackend.Pdf;

import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {
@Autowired
    TrasporatoreRepository trasporatoreRepository;
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public byte[] generatePdf(@RequestBody @Validated AnnuncioDTO annuncioDTO, BindingResult bindingResult, @PathVariable long id){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        try{

            PDFont font =   new PDType1Font(Standard14Fonts.FontName.COURIER);
            PDFont fontBold =   new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);
            PDPageContentStream contentStream;
            ByteArrayOutputStream output =new ByteArrayOutputStream();
            PDDocument document =new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile("C:/Users/Utente/Pictures/451987055_3624459951153481_7494526139017243318_n.jpg", document);

            contentStream = new PDPageContentStream(document, page);

            contentStream.drawImage( pdImage, 10, 670, 100, 100 );

            contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.newLineAtOffset(150, 750);
            contentStream.showText(
                    "La tua prenotazione"
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(50, 634);
            Trasportatore user = trasporatoreRepository.findById(id).orElseThrow(()->new BadRequestException("Trasportatore con id "+ id + " non trovato."));


            contentStream.showText(
                    "Informazioni sulla persona : " + " " +
                            user.getNome() + " " + user.getCognome() + " " +
                            user.getEmail() + " "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 14);
            contentStream.newLineAtOffset(300, 618);
            contentStream.showText(
                    user.getEta() + " anni."
            );
            contentStream.endText();


            contentStream.beginText();
            contentStream.setFont(fontBold, 16);
            contentStream.newLineAtOffset(290, 200);
            contentStream.showText(
                    "P.P.V. Data, luogo e firma."
            );
            contentStream.endText();


            contentStream.moveTo(300, 140);
            contentStream.lineTo(540, 140);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(150, 55);
            contentStream.setFont(fontBold, 30);
            contentStream.showText(
                    "Trasporti S.p.a."
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 36);
            contentStream.showText(
                    "Tutti i diritti sono riservati"
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 28);
            contentStream.showText(
                    "L'attuale documento non rappresenta "
            );
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(font, 8);
            contentStream.newLineAtOffset(200, 20);
            contentStream.showText(
                    "Una reale prenotazione."
            );
            contentStream.endText();

            contentStream.close();


            document.save(output);
            document.close();


            return output.toByteArray();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

}
