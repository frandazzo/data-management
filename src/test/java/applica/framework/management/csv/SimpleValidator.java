package applica.framework.management.csv;

import applica.framework.management.csv.RowValidator;

import java.util.Hashtable;

/**
 * Applica
 * User: Alberto Montemurro
 * Date: 10/18/14
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleValidator extends RowValidator {

    @Override
    public void validateRow(Hashtable<String, String> row) {

        //questaclasse deve validare il record
        //in questo caso devo verificare che il campo paese esiste e dve esser = a ITA

        if (row.get("Paese") == null){
            this.error = "Manca il campo Paese";
            this.valid = false;
        }
        if (row.get("Paese") != null){
            String paese = row.get("Paese");
            if (!paese.equals("ITA")){
                this.error = "Il campo Paese non e corretto";
                this.valid = false;
            }
        }


    }
}
