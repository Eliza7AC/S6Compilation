import java.util.Objects;

public class Compilateur {

    Analyseur analyseur;
    Identificateur identificateurs;

    public Compilateur(Analyseur analyseur, Identificateur identificateurs) {
        this.analyseur = analyseur;
        this.identificateurs = identificateurs;
    }

    public void interpretation(){
        for (String word : analyseur.getWords()){
            System.out.println(word);

            if(identificateurs.getIdentificateurs().containsValue(word)){
                identificateurs.execute(word);
            }

        }
    }
}
