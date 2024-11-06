package com.example.TrasportiBackend.Pdf;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.Annuncio.AnnuncioService;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.User.UserService;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class PdfJasperService {
    @Autowired
    UserService userService;
    @Autowired
    AnnuncioRepository annuncioRepository;
    @Value("#{'${base.url.path}'}")
    private String basePathReport;
    public byte[] richiedi (long annuncioId, long tId) throws Exception {


        Trasportatore trasportatore = userService.getTrasportatoreById(tId);
        Annuncio annuncio = annuncioRepository.findById(annuncioId).orElseThrow(()->new UserNotFoundException("Annuncio con id " + annuncioId + " non trovato in db"));

        Path richiestaAssegnazioneSpedizionePath = Paths.get(basePathReport + "richiesta.jrxml");
        InputStream fullReport = null;
        try {
            fullReport = Files.newInputStream(richiestaAssegnazioneSpedizionePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(fullReport);
        } catch (JRException e) {
            e.printStackTrace();
        }


        BufferedImage logoRep = null;
        try {
            logoRep = ImageIO
                    .read(new File(basePathReport + "/img/img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



        LocalDateTime ldtNow = LocalDateTime.now(ZoneId.of("CET"));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String anno = String.valueOf(ldtNow.getYear());
        String giorno = String.valueOf(ldtNow.getDayOfMonth());
        String mese = ldtNow.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
        String ora = ldtNow.format(timeFormatter);
        String nomeTrasportatore = trasportatore.getNome() + " " + trasportatore.getCognome();
        String eta = String.valueOf(trasportatore.getEta());
        String indirizzo = trasportatore.getIndirizzo() + " - " + trasportatore.getCitta()+ ", " + trasportatore.getCap();
        String codiceFiscale = trasportatore.getCodiceFiscale();
        String nomeAzienda = annuncio.getAzienda().getNomeAzienda();
        String partitaIva = annuncio.getAzienda().getPartitaIva();
        String indirizzoAzienda = annuncio.getAzienda().getIndirizzo() + " - " + annuncio.getAzienda().getCitta() + ", " + annuncio.getAzienda().getCap();

        String annoPubblicazioneAnnuncio = String.valueOf(annuncio.getDataPubblicazione().getYear());
        String mesePubblicazioneAnnuncio = String.valueOf(annuncio.getDataPubblicazione().getMonth());
        String giornoPubblicazioneAnnuncio = String.valueOf(annuncio.getDataPubblicazione().getDayOfMonth());
        String retribuzione = String.valueOf(annuncio.getRetribuzione());
        String dataPubblicazioneAnnuncio = annuncio.getDataPubblicazione().getDayOfMonth() + " - " + annuncio.getDataPubblicazione().getMonth() + " - " + annuncio.getDataPubblicazione().getYear();
        String partenzaDa = annuncio.getSpedizione().getDa();
        String arrivoA = annuncio.getSpedizione().getA();
        String dataDaSpedire = annuncio.getSpedizione().getDaSpedire().getDayOfMonth() + " - " + annuncio.getSpedizione().getDaSpedire().getMonth() + " - " + annuncio.getSpedizione().getDaSpedire().getYear();
        String descrizioneMerce = annuncio.getSpedizione().getDescrizioneMerce();
        String numeroPedane = String.valueOf(annuncio.getSpedizione().getNumeroPedane());
        String email = annuncio.getAzienda().getEmail();




        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nomeTrasportatore", nomeTrasportatore);
        parameters.put("eta", eta);
        parameters.put("indirizzo", indirizzo);
        parameters.put("codiceFiscale", codiceFiscale);
        parameters.put("nomeAzienda", nomeAzienda);
        parameters.put("partitaIva", partitaIva);
        parameters.put("indirizzoAzienda", indirizzoAzienda);
        parameters.put("annoPubblicazioneAnnuncio", annoPubblicazioneAnnuncio);
        parameters.put("mesePubblicazioneAnnuncio", mesePubblicazioneAnnuncio);
        parameters.put("giornoPubblicazioneAnnuncio", giornoPubblicazioneAnnuncio);
        parameters.put("logoRep", logoRep);
        parameters.put("anno", anno);
        parameters.put("giorno", giorno);
        parameters.put("mese", mese);
        parameters.put("ora", ora);
        parameters.put("numeroPedane",numeroPedane);
        parameters.put("descrizioneMerce",descrizioneMerce);
        parameters.put("dataDaSpedire",dataDaSpedire);
        parameters.put("arrivoA",arrivoA);
        parameters.put("partenzaDa",partenzaDa);
        parameters.put("dataPubblicazioneAnnuncio",dataPubblicazioneAnnuncio);
        parameters.put("retribuzione", retribuzione);
        parameters.put("email",email);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (JRException e) {
            e.printStackTrace();
        }

        Exporter exporter = new JRDocxExporter();
        SimpleExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));

        exporter.setConfiguration(configuration);
        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
