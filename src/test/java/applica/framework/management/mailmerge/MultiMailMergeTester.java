package applica.framework.management.mailmerge;

import applica.framework.management.zip.ZipFacade;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 12/18/14
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiMailMergeTester extends TestCase {


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
    public void testMultiMailMerge() throws Exception {

        MultiMailMergeFacade m = new MultiMailMergeFacade();

        String templatePath = url.getPath();

        //String outputFile = tempFolder + "/testMailMerge1.docx";

        //imposto la tabella delle proprieà con cui fare il mail merge
        Hashtable<String, Object> prop = new Hashtable<String, Object>();
        //se guaro il file della documentazione vedo che richiede un oggetto doc, e una proprietà foter
        Doc doc = new Doc();
        doc.setTitle("Titolo di test1");
        doc.setSection1("Sezione 1 di test1");
        doc.setSection2("Sezione 2 di test1");

        String footer = "Questo è un footer di test1";

        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        prop.put("footer", footer);
        prop.put("documentation", doc);
        prop.put("filename", "222.docx");

        //imposto la tabella delle proprieà con cui fare il mail merge
        Hashtable<String, Object> prop1 = new Hashtable<String, Object>();
        //se guaro il file della documentazione vedo che richiede un oggetto doc, e una proprietà foter
        Doc doc1 = new Doc();
        doc1.setTitle("Titolo di test12");
        doc1.setSection1("Sezione 1 di test12");
        doc1.setSection2("Sezione 2 di test12");

        String footer1 = "Questo è un footer di test12";

        //inserisco tutto nella mappa delle proprietà con i nomi specificati nel documento(vedi file documentazione nei campi merge field)
        prop1.put("footer", footer1);
        prop1.put("documentation", doc1);
        prop1.put("filename", "111.docx");



        List<Map<String,Object>> data = new ArrayList<>();
        data.add(prop);
        data.add(prop1);

        m.executeMultiMailMergeAndZipOutput(templatePath, data, false,tempFolder,"result");

        File createdFile = new File(tempFolder + "/result.zip");
        Assert.assertEquals(createdFile.exists(), true);

    }
}
