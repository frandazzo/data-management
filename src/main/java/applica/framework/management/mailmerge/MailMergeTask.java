package applica.framework.management.mailmerge;

import fr.opensagres.xdocreport.core.XDocReportException;
import org.apache.commons.lang.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailMergeTask implements Runnable {

    private String template;
    private Map<String, Object> paramters;
    private boolean convertToPdf;
    private String outputDir;



    public MailMergeTask(String template, Map<String, Object> paramters, boolean convertToPdf, String outputDir ){
        this.convertToPdf = convertToPdf;
        this.paramters = paramters;
        this.outputDir = outputDir;
        this.template = template;

    }






    @Override
    public void run() {

        MailMergeFacade fac = new MailMergeFacade();

        //calcolo il nome del file:
        String filename = createFileName();

        //avvio la procedura eseguendola in base alla necessità di converitre il mail merge in pdf

        if (convertToPdf){

            try {
                fac.executeMailMergeAndGeneratePdf(template, filename, paramters);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XDocReportException e) {
                e.printStackTrace();
            } catch (Docx4JException e) {
                e.printStackTrace();
            }

        }else{
            try {
                fac.executeMailMerge(template, filename, paramters);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XDocReportException e) {
                e.printStackTrace();
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }


    }

    private String createFileName() {


        //se trovo un campo nella mappa dei parameters che abbia il nome "filename" lo utilizzo come nome del file senza estensione
        //altrimenti genero un guid come nomne del file

        String filename = "";
        if (paramters.containsKey("filename")){
            filename = paramters.get("filename").toString();
            if (StringUtils.isEmpty(filename))
                filename = UUID.randomUUID().toString();
        }
        else
            filename = UUID.randomUUID().toString();

        // a questo punto aggiungo l'estensione del file
        if (convertToPdf)
            filename = filename + ".pdf";
        else
            filename = filename + ".docx";

        return outputDir + "/" + filename;
    }
}
