package applica.framework.management.mailmerge;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.PropertyConfigurator;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 10/15/14
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailMergeFacade {

    private DocxDocumentMergerAndPdfConverter merger;
    private String docPath;



     public MailMergeFacade(){

         //recupero dalle risorse il file di configuraizone di log4j per disabilitare il logging
//         URL url =  getClass().getResource("/log4j.properties");
//
//         PropertyConfigurator.configure(url.getPath());

         merger = new DocxDocumentMergerAndPdfConverter();
     }


    public void executeMailMergeAndGeneratePdf(String template, String outputFilename, Map<String, Object> parameters) throws IOException, XDocReportException, Docx4JException {

        //per prima cosa verifico se esiste il file
        checkTemplateFile(template);

        InputStream h =merger.loadDocumentAsStream(template);
        IXDocReport report =  merger.loadDocumentAsIDocxReport(h, TemplateEngineKind.Velocity);

        merger.replaceVariabalesInTemplateOtherThanImages(report,parameters);
        byte[] mergedOutput = merger.mergeAndGeneratePDFOutput(template, TemplateEngineKind.Velocity, parameters,  null);
        FileOutputStream os = new FileOutputStream(outputFilename);
        os.write(mergedOutput);
        os.flush();
        os.close();

    }

    public byte[] executeMailMergeAndGeneratePdfByteArray(String template,  Map<String, Object> parameters) throws IOException, XDocReportException, Docx4JException {

        //per prima cosa verifico se esiste il file
        checkTemplateFile(template);

        InputStream h =merger.loadDocumentAsStream(template);
        IXDocReport report =  merger.loadDocumentAsIDocxReport(h, TemplateEngineKind.Velocity);

        merger.replaceVariabalesInTemplateOtherThanImages(report,parameters);
        return merger.mergeAndGeneratePDFOutput(template, TemplateEngineKind.Velocity, parameters,  null);


    }

    private void checkTemplateFile(String template) throws FileNotFoundException {
        File f = new File(template);
        if(f.exists() && !f.isDirectory()){

            String ext = FilenameUtils.getExtension(template);
            if (!ext.toLowerCase().equals("docx")){
                throw new FileNotFoundException("Nessun file word xml trovato");
            }
        }else{
            throw new FileNotFoundException("Nessun file word trovato");
        }
    }

    public void executeMailMerge(String template, String outputFilename, Map<String, Object> parameters) throws IOException, XDocReportException, Docx4JException {


        //l'output file name è il nome di un file docx da generare....

        //per prima cosa verifico se esiste il file
        checkTemplateFile(template);


        InputStream h =merger.loadDocumentAsStream(template);
        IXDocReport report =  merger.loadDocumentAsIDocxReport(h, TemplateEngineKind.Velocity);

        merger.replaceVariabalesInTemplateOtherThanImages(report,parameters);
        byte[] mergedOutput = merger.mergeAndGenerateOutput(template, TemplateEngineKind.Velocity, parameters,  null);
        FileOutputStream os = new FileOutputStream(outputFilename);
        os.write(mergedOutput);
        os.flush();
        os.close();

    }

    public byte[] executeMailMerge(String template,  Map<String, Object> parameters) throws IOException, XDocReportException, Docx4JException {

        //per prima cosa verifico se esiste il file
        checkTemplateFile(template);


        InputStream h =merger.loadDocumentAsStream(template);
        IXDocReport report =  merger.loadDocumentAsIDocxReport(h, TemplateEngineKind.Velocity);

        merger.replaceVariabalesInTemplateOtherThanImages(report,parameters);
        return merger.mergeAndGenerateOutput(template, TemplateEngineKind.Velocity, parameters,  null);


    }


}
