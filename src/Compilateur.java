import java.util.Objects;

public class Compilateur {

    Analyseur analyseur;
    Identificateur identificateurs;

    public Compilateur(Analyseur analyseur, Identificateur identificateurs) {
        this.analyseur = analyseur;
        this.identificateurs = identificateurs;
    }

    public void interpretation(){

//        for (String word : analyseur.getWords()){
//
//            if(identificateurs.getIdentificateurs().containsKey(word)){
//                identificateurs.execute(word);
//            }
//
//        }
    }
}
