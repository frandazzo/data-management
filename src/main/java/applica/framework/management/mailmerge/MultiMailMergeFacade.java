package applica.framework.management.mailmerge;

import applica.framework.management.zip.ZipFacade;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiMailMergeFacade {


    public String executeMultiMailMergeAndZipOutput(String template, List<Map<String, Object>> parameterList, boolean convertToPdf, String outputDir, String outputFileNameWithoutExtension) throws IOException {

        //verifico che la cartella di output esista (ossia l acartella dove verrà riposto il file zippato
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists())
            throw new IOException("La directory di output non esiste");



        //creo una cartella temporanea che possa contenere tutti i file che genererò
        String outputPath = createTempFolder();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (Map<String, Object> param : parameterList) {
            Runnable task = new MailMergeTask(template, param,convertToPdf,outputPath);
            executor.execute(task);

        }
        executor.shutdown();

        while (!executor.isTerminated()){
            //non fa nulla e attende che tutti i task siano terminati
            try {
                // thread to sleep for 1000 milliseconds
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //a questo punto posso zippare la cartella temporanea e porla nella direcotry di output
        //creo il nome del file zip che sarà creato
        String finalFileName = "";

        if (StringUtils.endsWith(outputDir, "/"))
            finalFileName = outputDir + outputFileNameWithoutExtension + ".zip";
        else
            finalFileName = outputDir + "/" + outputFileNameWithoutExtension + ".zip";

        //adesso posso eseguire la compressione della cartella
        ZipFacade zipper = new ZipFacade();
        zipper.CompressFolder(outputPath, finalFileName);



        return finalFileName;
    }

    private String createTempFolder() throws IOException {


        File temp = File.createTempFile(UUID.randomUUID().toString(),"");
        temp.delete();
        temp.mkdir();
        return temp.getAbsolutePath();
    }


    public byte[] executeMultiMailMergeAndZipOutputToByteArray(String template, List<Map<String, Object>> parameterList, boolean convertToPdf, String outputDir, String outputFileNameWithoutExtension) throws IOException {


        String zippedDataFile = executeMultiMailMergeAndZipOutput(template, parameterList, convertToPdf, outputDir, outputFileNameWithoutExtension);

        FileInputStream fileInputStream=null;

        File file = new File(zippedDataFile);

        byte[] bFile = new byte[(int) file.length()];

        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bFile);
        fileInputStream.close();

       return bFile;


    }




}
