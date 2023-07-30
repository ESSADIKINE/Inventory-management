
package gestion.de.stock;

public class ProduitModel {
    private String nom;
    private int idProduit, quantité;

    public ProduitModel(){

    }
    public ProduitModel(int idProduit, String nom, int quantité){
        this.idProduit = idProduit;
        this.nom = nom;
        this.quantité = quantité;
    }

    public int getIdProduit() {
        return this.idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getnom() {
        return this.nom;
    }

    public void setnom(String nom) {
        this.nom = nom;
    }

    public int getquantité() {
        return this.quantité;
    }

    public void setquantité(int quantité) {
        this.quantité = quantité;
    }
}

