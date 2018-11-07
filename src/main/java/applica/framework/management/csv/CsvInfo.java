package applica.framework.management.csv;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 10/17/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvInfo {

    //restituisce la lista di righe del file cvs
    //p.s. ogni riga è rappresentata da una hash table do la chiave è il rispettivo campo di intestazione
    protected List<RowData> importedTableRows;

    //restituisce il numero dei record trovati
    protected int recordNumber;

    //restituisce il numero di campi trovati
    protected int fieldsNumber;
    //restituisce la lista ordinata dei campi di intestazione del file
    protected List<String> headerFields;

    //restituisce il percorso del file sorgente
    protected String sourceFile;

    //restituisce l'errore del parsing del file
    protected String error;



    //indica se ci sono errori di validazione nelle righe e quanti sono
    protected int rowValidationErrors;



    //indica gli indici delle righe che non hanno passato la validazione
    public List<Integer> nonValidatedRowIndexes = new ArrayList();


    public List<RowData> getOnlyValidRows(){
        if (importedTableRows == null)
            return new ArrayList<>();

        if (importedTableRows.size() == 0)
            return new ArrayList<>();

        List<RowData> result = new ArrayList<>();

        for (RowData rowData : importedTableRows) {
            if (rowData.isValid())
                result.add(rowData);
        }


        return result;

    }

    public void setRowValidationErrors(int rowValidationErrors) {
        this.rowValidationErrors = rowValidationErrors;
    }


    public void setError(String error) {
        this.error = error;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setHeaderFields(List<String> headerFields) {
        this.headerFields = headerFields;
    }

    public void setFieldsNumber(int fieldsNumber) {
        this.fieldsNumber = fieldsNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public void setImportedTableRows(List<RowData> importedTableRows) {
        this.importedTableRows = importedTableRows;
    }


    public List<Integer> getNonValidatedRowIndexes() {
        return nonValidatedRowIndexes;
    }

    public String getError() {
        return error;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public List<String> getHeaderFields() {
        return headerFields;
    }

    public int getFieldsNumber() {
        return fieldsNumber;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public List<RowData> getImportedTableRows() {
        return importedTableRows;
    }

    public int getRowValidationErrors() {
        return rowValidationErrors;
    }


}
