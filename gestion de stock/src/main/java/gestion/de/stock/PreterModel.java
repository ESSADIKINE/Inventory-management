
package gestion.de.stock;


public class PreterModel {
    private String nom, nomProduit, notel;
    private int idPreter, idProduit, quantité;

    public PreterModel(){

    }
    public PreterModel(int idPreter, String nom, String notel, int idProduit, String nomProduit, int quantité){
        this.idPreter = idPreter;
        this.idProduit = idProduit;
        this.nomProduit = nomProduit;
        this.nom = nom;
        this.notel = notel;
        this.quantité = quantité;
    }

    public int getIdPreter() {
        return this.idPreter;
    }

    public void setIdPreter(int idPreter) {
        this.idProduit = idPreter;
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

    public String getnomProduit() {
        return this.nomProduit;
    }

    public void setnomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getNotel() {
        return this.notel;
    }

    public void setNotel(String notel) {
        this.notel = notel;
    }

    public int getquantité() {
        return this.quantité;
    }

    public void setquantité(int quantité) {
        this.quantité = quantité;
    }
}
