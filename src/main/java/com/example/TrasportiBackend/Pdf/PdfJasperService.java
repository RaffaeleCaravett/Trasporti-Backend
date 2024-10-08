package com.example.TrasportiBackend.Pdf;

import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfJasperService {

    @Value("#{'${base.path}'}")
    private String basePathReport;
    public byte[] employeeJasperReportInBytes(AnnuncioDTO annuncioDTO, Trasportatore trasportatore) throws Exception {
        Path verbaleUCCPath = Paths.get(basePathReport + "emp24.jrxml");
        InputStream fullReport = null;
        try {
            fullReport = Files.newInputStream(verbaleUCCPath);
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

        var unifiedCandidate = loadUnifiedCandidateService.loadByTypeAndId(type,id);


        LocalDateTime ldtNow = LocalDateTime.now(ZoneId.of("CET"));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String anno = String.valueOf(ldtNow.getYear());
        String giorno = String.valueOf(ldtNow.getDayOfMonth());
        String mese = ldtNow.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
        String ora = ldtNow.format(timeFormatter);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tribunale", tribunalJpa.getCity().getName());
        parameters.put("type", type);
        parameters.put("reportType", reportType);
        parameters.put("logoRep", logoRep);
        parameters.put("candidato", unifiedCandidate.getFullName());
        parameters.put("luogoNascita", unifiedCandidate.getPlaceOfBirth());
        parameters.put("comune", unifiedCandidate.getPlaceOfResidence());
        parameters.put("indirizzo", unifiedCandidate.getAddress());
        parameters.put("anno", anno);
        parameters.put("giorno", giorno);
        parameters.put("mese", mese);
        parameters.put("ora", ora);
        parameters.put("id", id.toString());

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
