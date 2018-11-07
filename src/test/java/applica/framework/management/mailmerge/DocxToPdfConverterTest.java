package applica.framework.management.mailmerge;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

public class DocxToPdfConverterTest extends TestCase {

    URL url;
    String tempFolder;

    @Before
    public void setUp() throws Exception {
        //recupero il file della documnetazione che fornisce un esempio
        url =  getClass().getResource("/Documentation.docx");

        //creo una directory temporanea
        TemporaryFolder folder = new TemporaryFolder();
        tempFolder = folder.newFolder("test").getAbsolutePath();

    }

    @After
    public void tearDown() throws Exception {
             FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void testConvertDocxToPdf() throws Exception {


        DocxToPdfConverter facade = new DocxToPdfConverter();
        String templatePath = url.getPath();



        //genero il pdf nella directory di test;
        String outputFile = tempFolder + "/testConvertToPdf.pdf";


        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        facade.ConvertDocxToPdf(templatePath, outputFile);


        //verifico che nella cartella test ci sia il file .pdf
        File createdFile = new File(outputFile);
        Assert.assertEquals(createdFile.exists(), true);

    }


}