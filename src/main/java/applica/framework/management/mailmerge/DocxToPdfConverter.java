package applica.framework.management.mailmerge;

import fr.opensagres.xdocreport.core.XDocReportException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.PropertyConfigurator;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.*;
import java.net.URL;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 10/15/14
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocxToPdfConverter {

    private DocxDocumentMergerAndPdfConverter converter;

    public DocxToPdfConverter(){


        converter = new DocxDocumentMergerAndPdfConverter();

    }


    private void checkDocxFile(String source) throws FileNotFoundException {
        File f = new File(source);
        if(f.exists() && !f.isDirectory()){

            String ext = FilenameUtils.getExtension(source);
            if (!ext.toLowerCase().equals("docx")){
                throw new FileNotFoundException("Nessun file word xml trovato");
            }
        }else{
            throw new FileNotFoundException("Nessun file word trovato");
        }
    }

    public void ConvertDocxToPdf(String docxFile, String outputFileName) throws IOException, XDocReportException, Docx4JException {


        converter = new DocxDocumentMergerAndPdfConverter();

        InputStream docxStream = converter.loadDocumentAsStream(docxFile);
        byte[] result =converter.generatePDFOutputFromDocx(IOUtils.toByteArray(docxStream));
        FileOutputStream os = new FileOutputStream(outputFileName);
        os.write(result);
        os.flush();
        os.close();

    }


    public byte[] ConvertDocxToPdfByteArray(String docxFile) throws IOException, XDocReportException, Docx4JException {

        converter = new DocxDocumentMergerAndPdfConverter();
        InputStream docxStream = converter.loadDocumentAsStream(docxFile);
        return converter.generatePDFOutputFromDocx(IOUtils.toByteArray(docxStream));
    }

}
