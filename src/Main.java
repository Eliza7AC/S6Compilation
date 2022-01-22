import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // chargement du programme simulé par un fichier texte
        Analyseur analyseur = new Analyseur(new File("src/JeuxEssais/test"));

        System.out.println(" ############################ ");
        System.out.println("mots du programme: ");
        System.out.println(analyseur.getWords().toString());
        System.out.println(" ############################ ");

        Identificateur identificateurs = new Identificateur();


        Compilateur compilateur = new Compilateur(analyseur,identificateurs);
        compilateur.interpretation();


        System.out.println(" ");
        Analyseur an = new Analyseur(new File("src/JeuxEssais/simplesSymboles"));




    }

}
