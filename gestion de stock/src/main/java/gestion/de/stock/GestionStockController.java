package gestion.de.stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;


public class GestionStockController implements Initializable {
    //    Initialisation la base de données
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/stock";
    private final String USER = "root";
    private final String PASS = "";

    // Création d'un objet pour la base de données MySQL
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    @FXML
    private TextField nomProduit;
    @FXML
    private TextField quantitéProduit;
    @FXML
    private TableView<ProduitModel> tbview;
    @FXML
    private TableColumn<ProduitModel, Integer> tcIdProduit;
    @FXML
    private TableColumn<ProduitModel,String> tcnom;
    @FXML
    private TableColumn<ProduitModel,Integer> tcquantité;
    @FXML
    private Button btnPlus;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnEssuyer;
    @FXML
    private Button clearSelectButton;

    //    Modèle réactif
    private TableViewSelectionModel selectionModel;
    private ObservableList<ProduitModel> selectedItems;
    private int id_Produit;
    private String query;


    public GestionStockController(){
        try{
            // enregistre le pilote à utiliser
            Class.forName(JDBC_DRIVER);
            // établit une connexion à la base de données
            this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // crée un objet de déclaration
            this.stmt = this.conn.createStatement();

        }catch(ClassNotFoundException | SQLException e){
            e.getMessage();
        }
    }
    // aller au menu principal
    @FXML
    public void gotoMenu() throws IOException{
        Main.setRoot("StockMenu");
    }
    // ajouter des données
    @FXML
    public void afficher() throws IOException{
        System.out.println(nomProduit.getText());
        System.out.println(quantitéProduit.getText());
        this.query = "INSERT INTO tb_Produit VALUES(NULL,'"+nomProduit.getText()+"','"+quantitéProduit.getText()+"')";

        try{
            this.stmt.execute(this.query);
            sync();
            System.out.println("Réussite de la requête : données insérées");
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    // mettre à jour les données
    @FXML
    public void update(){
        System.out.println(this.id_Produit);
        System.out.println(nomProduit.getText());
        System.out.println(quantitéProduit.getText());
        this.query = "UPDATE tb_Produit SET nom = '"+nomProduit.getText()+"', quantité = '"+quantitéProduit.getText()+"' WHERE id_Produit = '"+this.id_Produit+"'";
        try{
            this.stmt.execute(this.query);
            sync();
            System.out.println("Requête réussie : données mises à jour");
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    // Données Essuyer
    @FXML
    public void delete(){
        System.out.println(this.id_Produit);
        System.out.println(nomProduit.getText());
        System.out.println(quantitéProduit.getText());
        this.query = "DELETE FROM tb_Produit WHERE id_Produit = '"+this.id_Produit+"'";
        try{
            this.stmt.execute(this.query);
            sync();
            System.out.println("Requête réussie : données mises à jour");
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    // synchronisation des données
    @FXML
    public void sync(){
        Platform.runLater(()->{
            tbview.getItems().clear();
            clearSelection();
            try{
                this.rs = this.stmt.executeQuery("SELECT * FROM tb_Produit");
                while(rs.next()){
                    tbview.getItems().add(new ProduitModel(rs.getInt("id_Produit"), rs.getString("nom"), rs.getInt("quantité")));
                }
            }catch(SQLException e){
                System.err.println(e.getSQLState());
            }

        });
    }
    // Essuyer toutes options
    @FXML
    public void clearSelection(){
        System.out.println("Clear Selection");
        this.id_Produit = 0;
        nomProduit.setText("");
        quantitéProduit.setText("");
        btnPlus.setDisable(false);
        btnModifier.setDisable(true);
        btnEssuyer.setDisable(true);
        clearSelectButton.setDisable(true);
        selectedItems.clear();
        selectionModel.clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quantitéProduit.textProperty().addListener((ov, oldValue, newValue) -> {
            //Sebuah regular expression untuk mencegah string selain angka ditulis pada textfield quantitéProduit
            quantitéProduit.setText(newValue.replaceAll("[^0-9$]", ""));
        });
        try{
            // Récupère toutes les données de la table
            this.rs = this.stmt.executeQuery("SELECT * FROM tb_Produit");
            //Set Collumn
            tcIdProduit.setCellValueFactory((p) -> {
                ObservableValue<Integer> obsInt = new SimpleIntegerProperty(p.getValue().getIdProduit()).asObject();
                return obsInt;
            });
            tcnom.setCellValueFactory((p) -> {
                ObservableValue<String> obsStr = new SimpleStringProperty(p.getValue().getnom());
                return obsStr;
            });
            tcquantité.setCellValueFactory((p) -> {
                ObservableValue<Integer> obsInt = new SimpleIntegerProperty(p.getValue().getquantité()).asObject();
                return obsInt;
            });
            while(rs.next()){
                tbview.getItems().add(new ProduitModel(rs.getInt("id_Produit"), rs.getString("nom"), rs.getInt("quantité")));
            }

            selectionModel = tbview.getSelectionModel();
            selectionModel.setSelectionMode(SelectionMode.SINGLE);

            // Récupère l'élément sélectionné
            selectedItems = selectionModel.getSelectedItems();

            // Crée un listener lorsqu'un élément de la liste est cliqué
            selectedItems.addListener((Change<? extends ProduitModel> change) -> {
                nomProduit.setText(change.getList().get(0).getnom());
                quantitéProduit.setText(""+change.getList().get(0).getquantité());
                this.id_Produit = change.getList().get(0).getIdProduit();
                if(btnModifier.isDisabled() == true && btnEssuyer.isDisabled() == true && btnPlus.isDisabled() == false){
                    btnPlus.setDisable(true);
                    btnModifier.setDisable(false);
                    btnEssuyer.setDisable(false);
                    clearSelectButton.setDisable(false);
                }
            });
        }catch(SQLException e){
            e.getMessage();
        }
    }

}

