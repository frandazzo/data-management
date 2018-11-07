package applica.framework.management.excel;

import applica.framework.management.csv.CsvInfo;
import applica.framework.management.csv.FieldsNumberException;
import applica.framework.management.csv.RowData;
import applica.framework.management.csv.RowValidator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fgran on 25/09/2016.
 */
public class ExcelReader {

    String file;
    int firstRow = 0;
    int firstSheetNumber = 0;
    RowValidator validator;

    public ExcelReader(String file){
        this.file = file;

    }

    public ExcelReader(String file, int firstSheetNumber, int firstRow, RowValidator validator){
        this.file = file;

        this.firstSheetNumber = firstSheetNumber;
        this.firstRow = firstRow;
        this.validator = validator;
    }
    private ExcelInfo getInfoError(String error) {

        ExcelInfo info = new ExcelInfo();
        info.setError(error);
        return info;
    }

    public ExcelInfo readFile()  {

        //per prima cosa verifico l'esistenza del file da parserizzare
        File fileToParse = new File(file);
        if (!fileToParse.exists()){
            return getInfoError("Il file non esiste");
        }

        //verifico il file sia un file csv
        String ext = FilenameUtils.getExtension(file);
        if (!ext.toLowerCase().equals("xls") && !ext.toLowerCase().equals("xlsx")){
            return getInfoError("Il file indicato non è un file Excel o non ha estensione xls o xlsx");
        }


        try{


            Workbook w1 = WorkbookFactory.create(new File(this.file));
            //posso adesso leggere il file
            Sheet firstSheet = w1.getSheetAt(firstSheetNumber);
            Iterator<Row> iterator = firstSheet.iterator();
            //definisco il risultato
            ExcelInfo result = new ExcelInfo();
            int rowNumber = firstRow;
            //creo la lista delle righe
            List<RowData> data = new ArrayList<RowData>();

            if (firstRow > 0){
                //posiziono l'iteratore delle righe sulla first row
                int j = firstRow;
                while (j >0){
                    iterator.next();
                    j--;
                }
            }


            while (iterator.hasNext()) {

                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();


                if (rowNumber == firstRow){ //si tratta dell'intestazione
                    result.setHeaderFields(getHeaders(cellIterator));
                    result.setFieldsNumber(result.getHeaderFields().size());
                }else{

                    //se trovo una riga vuota o con qualche spazio la lascio perdere
                    String[] rowdata =  getData(firstSheet.getRow(rowNumber), result.getFieldsNumber()); //line.split(separator);

                    //adesso posso creare una hashtable con i valori della riga
                    int dataNumber = 0;
                    RowData row = new RowData();

                    for (String propName : result.getHeaderFields()) {
                        //tolgo eventuali apici
                        String value = "";
                        try{
                            value = rowdata[dataNumber];
                        }catch(IndexOutOfBoundsException  ex){
                            value = "";
                        }
                        row.getData().put(propName, value);
                        dataNumber++;
                    }

                    row.validateRow(validator);
                    data.add(row);


                }
                //incremento il numero di riga parserizzata
                rowNumber++;
            }

            //imposto il numero di rghe
            result.setImportedTableRows(data);
            result.setRecordNumber(data.size());
            result.setSourceFile(this.file);

            w1.close();


            //verifico il numero di righe che non hanno passato la validazione
            int rowErrors = 0;
            int index = 1;// è il riferimento alla riga di cui si sta valutando l'esito della validazione
            for (RowData rowData : data) {
                if (!rowData.isValid()){
                    rowErrors++;
                    result.getNonValidatedRowIndexes().add(index);
                }
                index++;
            }

            result.setRowValidationErrors(rowErrors);


            return result;


        }catch(Exception ex){
            return getInfoError("Impossibile aprire il file: "  + ex.getMessage());
        }



    }

    private String[] getData(Row row, int rowsize) {
        List<String> result = new ArrayList<>();
        if (row == null)
            return new String[result.size()];

        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        for (int cc = 0 ; cc < rowsize; cc++ ){
            Cell cell = row.getCell(cc, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    result.add("");
                    break;
                case Cell.CELL_TYPE_STRING:
                    result.add(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))


                        result.add(f1.format(cell.getDateCellValue()));
                    else
                        result.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                default:
                    result.add(String.valueOf(cell.toString()));
            }
        }


        return result.toArray(new String[result.size()]);
    }

    private List<String> getHeaders(Iterator<Cell> celliterator) {
        List<String> result = new ArrayList<>();
        while (celliterator.hasNext()) {
            Cell cell = celliterator.next();

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    result.add("");
                    break;
                case Cell.CELL_TYPE_STRING:
                    result.add(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                        result.add(String.valueOf(cell.getDateCellValue()));
                    else
                        result.add(String.valueOf(cell.getNumericCellValue()));
                    break;

            }

        }
        return result;
    }


    private Workbook createWorkbook(FileInputStream fileStream) throws IOException {


        Workbook workbook = null;

        //provo a creare supponendo si tratti di file excel 2007
        workbook = new XSSFWorkbook(fileStream);

        return workbook;
        //provo altrimenti

    }
    private Workbook createWorkbookOld(FileInputStream fileStream) throws IOException {


        Workbook workbook = null;

        //provo a creare supponendo si tratti di file excel 2007
        workbook = new HSSFWorkbook(fileStream);

        return workbook;

    }

}
