package gestion.de.stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


public class PreterStockController implements Initializable {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/stock";
    private final String USER = "root";
    private final String PASS = "";

    // Préparation des objets nécessaires à la gestion de la base de données
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    @FXML
    private TextField nomPreter;
    @FXML
    private TextField no_tel;
    @FXML
    private ComboBox<String> listProduit;
    @FXML
    private TextField quantitéProduit;
    @FXML
    private TableView<PreterModel> tvEmprunter;
    @FXML
    private TableColumn<PreterModel, Integer> tcIdPreter;
    @FXML
    private TableColumn<PreterModel, String> tcPreter;
    @FXML
    private TableColumn<PreterModel, String> tcNotel;
    @FXML
    private TableColumn<PreterModel, String> tcProduit;
    @FXML
    private TableColumn<PreterModel, Integer> tcquantité;
    @FXML
    private Button btnEmprunter;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnretour;
    @FXML
    private Button clearSelectButton;
    @FXML
    private Label errorLabel;


    private int id_Produit, id_Preter;
    private String query;
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private TableView.TableViewSelectionModel selectionModel;
    private ObservableList<PreterModel> selectedItems;

    public PreterStockController(){
        try{
            Class.forName(JDBC_DRIVER);
            // établit une connexion à la base de données
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // crée un objet statement
            this.stmt = this.conn.createStatement();
        }catch(ClassNotFoundException | SQLException e){
            e.getMessage();
        }
    }


    @FXML
    public void gotoMenu() throws IOException{
        Main.setRoot("StockMenu");
    }
    // Process Emprunter Produit
    @FXML
    public void Emprunter() throws SQLException{
        //        Create Id Produit
        String nomProduit = listProduit.getValue();
        int separator = nomProduit.indexOf("-");
        this.id_Produit = Integer.parseInt(nomProduit.substring(0,separator));
        this.query = "SELECT * FROM tb_Produit WHERE id_Produit = "+this.id_Produit+"";
        this.rs = this.stmt.executeQuery(this.query);
        this.rs.next();
        if(rs.getInt("quantité") < Integer.parseInt(quantitéProduit.getText())){
            System.out.println("Stok Produit tersisa : "+rs.getInt("quantité"));
            return;
        }

        this.query = "INSERT INTO tb_Preter VALUES(NULL, '"+nomPreter.getText()+"', '"+no_tel.getText()+"', "+this.id_Produit+", '"+quantitéProduit.getText()+"')";
        this.stmt.execute(this.query);
        System.out.println("Réussite de la requête : données insérées");
        sync();
    }
    // Processus de retour produit
    @FXML
    public void retour() throws SQLException{
        System.out.println(this.id_Preter);
        this.query = "DELETE FROM tb_Preter WHERE id_Preter = '"+this.id_Preter+"'";
        this.stmt.execute(this.query);
        System.out.println("Requête réussie : données supprimées");
        sync();
    }
    // Processus de mise à jour
    @FXML
    public void update() throws SQLException{
        this.query = "SELECT * FROM tb_Produit WHERE id_Produit = "+this.id_Produit+"";
        this.rs = this.stmt.executeQuery(this.query);
        this.rs.next();
        if(rs.getInt("quantité") < Integer.parseInt(quantitéProduit.getText())){
            System.out.println("Stok Produit tersisa : "+rs.getInt("quantité"));
            clearSelection();
            return;
        }
        this.query = "UPDATE tb_Preter SET nom = '"+nomPreter.getText()+"'"
                + ", no_tel = '"+no_tel.getText()+"', quantité = '"+quantitéProduit.getText()+""
                + "' WHERE id_Preter = '"+this.id_Preter+"'";
        this.stmt.execute(this.query);
        System.out.println("Requête réussie : données mises à jour");
        sync();
    }
    // Synchronisation des données
    @FXML
    public void sync(){
        Platform.runLater(()->{
            tvEmprunter.getItems().clear();
            listProduit.getItems().clear();
            clearSelection();
            try{
                this.rs = this.stmt.executeQuery("SELECT id_Preter, tb_Preter.nom AS nom_preter, "
                        + "no_tel, tb_Preter.id_Produit, tb_Produit.nom AS nom_Produit, "
                        + "tb_Preter.quantité FROM tb_Produit INNER JOIN tb_Preter ON "
                        + "tb_Produit.id_Produit = tb_Preter.id_Produit");
                while(rs.next()){
                    tvEmprunter.getItems().add(new PreterModel(rs.getInt("id_Preter"), rs.getString("nom_preter"), rs.getString("no_tel"), rs.getInt("id_Produit"), rs.getString("nom_Produit"), rs.getInt("quantité")));
                }

                this.rs = this.stmt.executeQuery("SELECT * FROM tb_Produit");
                while(this.rs.next()){
                    observableList.add(rs.getInt("id_Produit") + "-" + rs.getString("nom") +"(Stok : "+rs.getInt("quantité")+")");
                }
                listProduit.setItems(observableList);
            }catch(SQLException e){
                System.err.println(e.getSQLState());
            }


        });
    }

    @FXML
    public void clearSelection(){
        System.out.println("Clear Selection");
        this.id_Produit = 0;
        this.id_Preter = 0;
        nomPreter.setText("");
        no_tel.setText("");
        quantitéProduit.setText("");
        btnEmprunter.setDisable(false);
        btnUpdate.setDisable(true);
        btnretour.setDisable(true);
        listProduit.setDisable(false);
        clearSelectButton.setDisable(true);
        selectionModel.clearSelection();
    }
    // Initialisation du composant
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //        Mencegah inputan string
        quantitéProduit.textProperty().addListener((ov, oldValue, newValue) -> {
            quantitéProduit.setText(newValue.replaceAll("[^0-9$]", ""));
        });
        // Paramètres de colonne Preter
        tcIdPreter.setCellValueFactory((p) -> {
            ObservableValue<Integer> obsInt = new SimpleIntegerProperty(p.getValue().getIdPreter()).asObject();
            return obsInt;
        });

        tcPreter.setCellValueFactory((p) -> {
            ObservableValue<String> obsStr = new SimpleStringProperty(p.getValue().getnom());
            return obsStr;
        });

        tcProduit.setCellValueFactory((p) -> {
            ObservableValue<String> obsStr = new SimpleStringProperty(p.getValue().getnomProduit());
            return obsStr;
        });

        tcNotel.setCellValueFactory((p) -> {
            ObservableValue<String> obsStr = new SimpleStringProperty(p.getValue().getNotel());
            return obsStr;
        });

        tcquantité.setCellValueFactory((p) -> {
            ObservableValue<Integer> obsInt = new SimpleIntegerProperty(p.getValue().getquantité()).asObject();
            return obsInt;
        });

        try{
            //Charger les données de prêt
            this.rs = this.stmt.executeQuery("SELECT id_Preter, tb_Preter.nom AS nom_preter, "
                    + "no_tel, tb_Preter.id_Produit, tb_Produit.nom AS nom_Produit, "
                    + "tb_Preter.quantité FROM tb_Produit INNER JOIN tb_Preter ON "
                    + "tb_Produit.id_Produit = tb_Preter.id_Produit");
            while(this.rs.next()){
                tvEmprunter.getItems().add(new PreterModel(rs.getInt("id_Preter"), rs.getString("nom_preter"), rs.getString("no_tel"), rs.getInt("id_Produit"), rs.getString("nom_Produit"), rs.getInt("quantité")));
            }

            //SET LISTENER
            // Initialiser Listener
            selectionModel = tvEmprunter.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            //Obtenir les éléments sélectionnés
            selectedItems = selectionModel.getSelectedItems();

            selectedItems.addListener((ListChangeListener.Change<? extends PreterModel> change) -> {
                nomPreter.setText(change.getList().get(0).getnom());
                no_tel.setText(change.getList().get(0).getNotel());
                quantitéProduit.setText(""+change.getList().get(0).getquantité());
                this.id_Produit = change.getList().get(0).getIdProduit();
                this.id_Preter = change.getList().get(0).getIdPreter();
                listProduit.getSelectionModel().select(id_Produit+"-"+change.getList().get(0).getnomProduit()+"(Stok : "+change.getList().get(0).getquantité()+"");
                if(btnUpdate.isDisabled() == true && btnretour.isDisabled() == true && btnEmprunter.isDisabled() == false){
                    listProduit.setDisable(true);
                    btnEmprunter.setDisable(true);
                    btnUpdate.setDisable(false);
                    btnretour.setDisable(false);
                    clearSelectButton.setDisable(false);
                }
            });
            // Charger tous les éléments de la base de données
            this.rs = this.stmt.executeQuery("SELECT * FROM tb_Produit");
            while(this.rs.next()){
                observableList.add(rs.getInt("id_Produit") + "-" + rs.getString("nom") +"(Stok : "+rs.getInt("quantité")+")");
            }
            listProduit.setItems(observableList);
        }catch(SQLException e){
            errorLabel.setText(e.getMessage());
        }
    }

}
