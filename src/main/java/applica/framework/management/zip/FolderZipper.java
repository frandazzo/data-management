package applica.framework.management.zip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class FolderZipper {

    List<String> fileList;
    private  String OUTPUT_ZIP_FILE;
    private  String SOURCE_FOLDER;

    public FolderZipper(String inputDir, String outputFileName ){
        fileList = new ArrayList<String>();
        this.OUTPUT_ZIP_FILE = outputFileName;
        this.SOURCE_FOLDER = inputDir;
    }



    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile) throws IOException {

        byte[] buffer = new byte[1024];



            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for(String file : this.fileList){

                System.out.println("File Added : " + file);
                ZipEntry ze= new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in =
                        new FileInputStream(SOURCE_FOLDER + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();


    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     * @param node file or directory
     */
    public void generateFileList(File node){

        //add file only
        if(node.isFile()){
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }

        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                generateFileList(new File(node, filename));
            }
        }

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
        return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }


}
