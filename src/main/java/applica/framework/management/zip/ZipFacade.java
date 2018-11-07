package applica.framework.management.zip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZipFacade {

    public void CompressFile(String inputFileName, String outputFileName) throws IOException {

        byte[] buffer = new byte[1024];

        //verifico che esista il file di input
        File f = new File(inputFileName);
        if (!f.exists())
            throw new IOException("File di inoput inesistente");

        FileOutputStream fos = new FileOutputStream(outputFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        ZipEntry ze= new ZipEntry(f.getName());
        zos.putNextEntry(ze);
        FileInputStream in = new FileInputStream(inputFileName);

        int len;
        while ((len = in.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }

        in.close();
        zos.closeEntry();

        zos.close();

    }


    public void CompressFolder(String inputDir, String outputFileName) throws IOException {
        FolderZipper appZip = new FolderZipper(inputDir,outputFileName);
        appZip.generateFileList(new File(inputDir));
        appZip.zipIt(outputFileName);

    }




}
