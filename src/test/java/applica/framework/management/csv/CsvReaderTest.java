package applica.framework.management.csv;

import applica.framework.management.csv.CsvInfo;
import applica.framework.management.csv.CsvReader;
import applica.framework.management.csv.SimpleValidator;
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
 * Date: 10/17/14
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvReaderTest extends TestCase {


    URL url;
    URL url1;
    String tempFolder;

    @Before
    public void setUp() throws Exception {
        //recupero il file della documnetazione che fornisce un esempio
        url =  getClass().getResource("/example.csv");
        url1 =  getClass().getResource("/examplawithnotextdelimiter.csv");

        //creo una directory temporanea
        TemporaryFolder folder = new TemporaryFolder();
        tempFolder = folder.newFolder("test").getAbsolutePath();

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void testReadCsv() throws Exception {

        //qui testo il reader del csv

        CsvReader reader = new CsvReader(url1.getPath(), ",");

        CsvInfo info = reader.readFile();


        Assert.assertNull(info.getError());
        Assert.assertEquals(info.getFieldsNumber(), 6);
        Assert.assertEquals(info.getRecordNumber(), 7);
        Assert.assertEquals(info.getNonValidatedRowIndexes().size(),0);
        Assert.assertEquals(info.getRowValidationErrors(), 0);

    }


    @Test
    public void testReadCsvWithValidator() throws Exception {

        //qui testo il reader del csv

        CsvReader reader = new CsvReader(url1.getPath(), ",", new SimpleValidator());

        CsvInfo info = reader.readFile();


        Assert.assertNull(info.getError());
        Assert.assertEquals(info.getFieldsNumber(), 6);
        Assert.assertEquals(info.getRecordNumber(), 7);
        Assert.assertEquals((int)info.getNonValidatedRowIndexes().get(0),7);
        Assert.assertEquals(info.getRowValidationErrors(), 1);

    }



    @Test
    public void testReadCsvWithTextDelimiter() throws Exception {

        //qui testo il reader del csv

        CsvReader reader = new CsvReader(url.getPath(), ",", "\"");

        CsvInfo info = reader.readFile();


        Assert.assertNull(info.getError());
        Assert.assertEquals(info.getFieldsNumber(), 6);
        Assert.assertEquals(info.getRecordNumber(), 7);
//        Assert.assertEquals((int)info.getNonValidatedRowIndexes().get(0),7);
//        Assert.assertEquals(info.getRowValidationErrors(), 1);

    }


}
