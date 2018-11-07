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



public class MailMergeFacadeTest extends TestCase {

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
    public void testExecuteMailMergeAndGeneratePdf() throws Exception {



        MailMergeFacade facade = new MailMergeFacade();
        String templatePath = url.getPath();



        //genero il pdf nella directory di test;
        String outputFile = tempFolder + "/testMailMerge.pdf";

        //imposto la tabella delle proprieà con cui fare il mail merge
        Hashtable<String, Object> prop = new Hashtable<String, Object>();
        //se guaro il file della documentazione vedo che richiede un oggetto doc, e una proprietà foter
        Doc doc = new Doc();
        doc.setTitle("Titolo di test");
        doc.setSection1("Sezione 1 di test");
        doc.setSection2("Sezione 2 di test");

        String footer = "Questo è un footer di test";

        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        prop.put("footer", footer);
        prop.put("documentation", doc);
        facade.executeMailMergeAndGeneratePdf(templatePath, outputFile, prop);


        //verifico che nella cartella test ci sia il file testMailMerge.pdf
        File createdFile = new File(outputFile);
        Assert.assertEquals(createdFile.exists(), true);



    }



    @Test
    public void testExecuteMailMerge() throws Exception {
        MailMergeFacade facade = new MailMergeFacade();
        String templatePath = url.getPath();



        //genero il pdf nella directory di test;
        String outputFile = tempFolder + "/testMailMerge.docx";

        //imposto la tabella delle proprieà con cui fare il mail merge
        Hashtable<String, Object> prop = new Hashtable<String, Object>();
        //se guaro il file della documentazione vedo che richiede un oggetto doc, e una proprietà foter
        Doc doc = new Doc();
        doc.setTitle("Titolo di test");
        doc.setSection1("Sezione 1 di test");
        doc.setSection2("Sezione 2 di test");

        String footer = "Questo è un footer di test";

        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        prop.put("footer", footer);
        prop.put("documentation", doc);
        facade.executeMailMerge(templatePath, outputFile, prop);


        //verifico che nella cartella test ci sia il file testMailMerge.pdf
        File createdFile = new File(outputFile);
        Assert.assertEquals(createdFile.exists(), true);

    }


}