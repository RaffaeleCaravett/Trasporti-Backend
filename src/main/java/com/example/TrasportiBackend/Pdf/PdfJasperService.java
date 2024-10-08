package com.example.TrasportiBackend.Pdf;

import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;
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

    @Value("#{'${base.url.path}'}")
    private String basePathReport;
    public byte[] employeeJasperReportInBytes(AnnuncioDTO annuncioDTO, Trasportatore trasportatore) throws Exception {
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

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("logoRep", logoRep);
        parameters.put("anno", anno);
        parameters.put("giorno", giorno);
        parameters.put("mese", mese);
        parameters.put("ora", ora);

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
