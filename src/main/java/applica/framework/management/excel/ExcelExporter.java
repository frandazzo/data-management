package applica.framework.management.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by fgran on 25/09/2016.
 */
public class ExcelExporter {

    public  void generateExcelFile(String sFileName, String sheetName, List<Hashtable<String, String>> data, List<String> headers) throws IOException {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet1 = wb.createSheet(sheetName);

        //aggiungo le intestazioni
        addHeaders(headers, sheet1, wb);


        for (int i = 1; i <= data.size(); i++) {
            Hashtable<String, String> rowData = data.get(i-1);

            addRow(rowData, headers, sheet1, wb, i);

        }


        FileOutputStream fileOut = new FileOutputStream(sFileName);
        wb.write(fileOut);
        fileOut.close();

    }

    private void addRow(Hashtable<String, String> rowData, List<String> headers, Sheet sheet1, Workbook wb, int index) {
        Row row = sheet1.createRow(index);
        for (int j = 0; j < headers.size() ; j++) {
            Cell cell = row.createCell(j);
            String headerValue = headers.get(j);
            cell.setCellValue(rowData.get(headerValue));
            //cell.setCellStyle(cellStyle);
        }

    }

    private void addHeaders(List<String> headers, Sheet sheet1, Workbook wb) {
        Row row = sheet1.createRow(0);

        Font font = wb.createFont();
        font.setFontName("Arial");
        font.setBold(true);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        for (int j = 0; j < headers.size() ; j++) {
            Cell cell = row.createCell(j);
            cell.setCellValue(headers.get(j));
            cell.setCellStyle(cellStyle);
        }

    }

}
