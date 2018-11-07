package applica.framework.management.excel;

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
 * Created by fgran on 25/09/2016.
 */
public class ExcelReaderTest extends TestCase {
    URL url;
    URL url1;
    URL url2;
    String tempFolder;

    @Before
    public void setUp() throws Exception {
        //recupero il file della documnetazione che fornisce un esempio
        url =  getClass().getResource("/randazzo.xlsx");
        url1 =  getClass().getResource("/randazzo1.xls");
        url2 =  getClass().getResource("/Documentation.docx");
        //creo una directory temporanea
        TemporaryFolder folder = new TemporaryFolder();
        tempFolder = folder.newFolder("test").getAbsolutePath();

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void testReadExcelOld() throws Exception {

        //qui testo il reader del csv

        ExcelReader reader = new ExcelReader(url1.getPath());

        ExcelInfo info = reader.readFile();


        Assert.assertNull(info.getError());
//        Assert.assertEquals(info.getFieldsNumber(), 6);
//        Assert.assertEquals(info.getRecordNumber(), 7);
//        Assert.assertEquals(info.getNonValidatedRowIndexes().size(),0);
//        Assert.assertEquals(info.getRowValidationErrors(), 0);

    }
    @Test
    public void testReadExcel() throws Exception {

        //qui testo il reader del csv

        ExcelReader reader = new ExcelReader(url.getPath());

        ExcelInfo info = reader.readFile();


        Assert.assertNull(info.getError());
//        Assert.assertEquals(info.getFieldsNumber(), 6);
//        Assert.assertEquals(info.getRecordNumber(), 7);
//        Assert.assertEquals(info.getNonValidatedRowIndexes().size(),0);
//        Assert.assertEquals(info.getRowValidationErrors(), 0);

    }
}
