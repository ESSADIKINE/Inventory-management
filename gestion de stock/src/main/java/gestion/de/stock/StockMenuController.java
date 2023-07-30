package gestion.de.stock;

import java.io.IOException;

import javafx.fxml.FXML;


public class StockMenuController {



    @FXML
    public void gotoGestion() throws IOException{

        Main.setRoot("GestionStock");
    }

    @FXML
    public void gotoPreter() throws IOException{
        Main.setRoot("PreterStock");
    }

}
