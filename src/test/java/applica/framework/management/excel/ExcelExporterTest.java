package applica.framework.management.excel;

import applica.framework.management.csv.CsvExporter;
import applica.framework.management.csv.CsvInfo;
import applica.framework.management.csv.CsvReader;
import applica.framework.management.csv.RowData;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by fgran on 25/09/2016.
 */
public class ExcelExporterTest extends TestCase {

    String tempFolder;

    @Before
    public void setUp() throws Exception {

        //creo una directory temporanea
        TemporaryFolder folder = new TemporaryFolder();
        tempFolder = folder.newFolder("test").getAbsolutePath();

    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(tempFolder));
    }

    @Test
    public void testExcelExporter() throws Exception {

        //qui testo l'esportazione a csv

        //creo una lista ordinata delle headers
        List<String> headers = new ArrayList<String>();
        headers.add("Nome");
        headers.add("Cognome");
        headers.add("Data di nascita");
        headers.add("Note");


        //creo 2 riga sotto forma di hash table
        Hashtable<String, String> person1 = new Hashtable<String, String>();
        person1.put("Nome", "Francesco");
        person1.put("Cognome", "Randazzo");
        person1.put("Data di nascita", "14/07/1977");
        person1.put("Note", "Francesco è un campione\n a bigliardino");


        Hashtable<String, String> person2 = new Hashtable<String, String>();
        person2.put("Nome", "Bruno");
        person2.put("Cognome", "Fortunato");
        person2.put("Data di nascita", "14/02/1983");
        person2.put("Note", "Sono gaio;");

        Hashtable<String, String> person3 = new Hashtable<String, String>();
        person3.put("Nome", "Silvana");
        person3.put("Cognome", "Colomba");
        SimpleDateFormat ff = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date d = new Date();
        person3.put("Data di nascita", ff.format(d));
        person3.put("Note", "Moglie");


        //creo la lista delle righe da inserire nel file
        List<Hashtable<String, String>> persons = new ArrayList();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        //creo il path del file da esportare
        String fileName = tempFolder+ "/test.xlsx";


        ExcelExporter exp = new ExcelExporter();
        exp.generateExcelFile(fileName, "dati persone",  persons,headers);


        //a questo punto posso leggere il file csv generato e verificare che non ha errori
        ExcelReader reader = new ExcelReader(fileName);
        ExcelInfo info = reader.readFile();

        //adesso posso verificare le righe ottenute
        RowData row1 = info.getImportedTableRows().get(0);
        RowData row2 = info.getImportedTableRows().get(1);


        assertEquals(info.getImportedTableRows().size(), 3);

//        assertEquals(row1.getData().get("Nome"), "Francesco");
//        assertEquals(row1.getData().get("Cognome"), "Randazzo");
//        assertEquals(row1.getData().get("Data di nascita"), "14/07/1977");
//        assertEquals(row1.getData().get("Note"), "Francesco è un campione a bigliardino"); //non ha lo \n
//
//
//        assertEquals(row2.getData().get("Nome"), "Bruno");
//        assertEquals(row2.getData().get("Cognome"), "Fortunato");
//        assertEquals(row2.getData().get("Data di nascita"), "14/02/1983");
//        assertEquals(row2.getData().get("Note"), "Sono gaio"); //non ha il ;

    }
}
