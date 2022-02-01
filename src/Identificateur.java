import java.util.ArrayList;

public class Identificateur<T> {

    public ArrayList<Identificateur> identificateurs;

    String nom;
    T_IDENT type;



    Integer adresse;
    T valeur;

    public Identificateur(String nom, T_IDENT type) {
        this.nom = nom;
        this.type = type;
    }

    /** variable */
//    public Identificateur(T_IDENT type, Integer adresse) {
//        this.type = type;
//        this.adresse = adresse;
//    }

    /** constante */
//    public Identificateur(T_IDENT type, T valeur) {
//        this.type = type;
//        this.valeur = valeur;
//    }

    public int CHERCHER(String identificateurCherche){
        for (int i = 0; i < identificateurs.size(); i++) {
            if (identificateurs.get(i).equals(identificateurCherche)){
                return i;
            }
        }
        return -1;
    }

    public int INSERER(String nom, T_IDENT type){
        Identificateur id = new Identificateur(nom, type);
        identificateurs.add(id);
        return identificateurs.size();
    }

    public void AFFICHE_TABLE_IDENT(){
        System.out.println(this.identificateurs.toString());
    }



}
