package applica.framework.management.zip;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URL;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZipperTester extends TestCase {


    URL url;
    URL urlProp;
    URL urlCsv;
    String tempFolder;

    @Before
    public void setUp() throws Exception {
        //recupero il file della documnetazione che fornisce un esempio da zippare
        url =  getClass().getResource("/Documentation.docx");
        urlProp =  getClass().getResource("/example.csv");
        urlCsv =  getClass().getResource("/log4j.properties");

        //creo una directory temporanea
        TemporaryFolder folder = new TemporaryFolder();
        tempFolder = folder.newFolder("test").getAbsolutePath();

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(tempFolder));
    }


    @Test
    public void testZipFileTest() throws Exception {

        ZipFacade zipper = new ZipFacade();

        zipper.CompressFile(url.getPath() ,tempFolder + "/prova.zip");


        File createdFile = new File(tempFolder + "/prova.zip");
        Assert.assertEquals(createdFile.exists(), true);


    }



    @Test
    public void testZipFolderTest() throws Exception {

        //inserisco nella cartella temporanea tre file che ho nelle properties
        File f1 = new File(url.getPath());
        FileUtils.copyFile(f1, new File(tempFolder + "/" + f1.getName()));

        File f2 = new File(urlCsv.getPath());
        FileUtils.copyFile(f2, new File(tempFolder + "/" + f2.getName()));

        ZipFacade zipper = new ZipFacade();
//
        zipper.CompressFolder(tempFolder , tempFolder + "/prova.zip");
//
//
        File createdFile = new File(tempFolder + "/prova.zip");
        Assert.assertEquals(createdFile.exists(), true);


    }






}
