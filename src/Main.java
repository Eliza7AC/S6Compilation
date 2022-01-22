import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // chargement du programme Minipasc simulé par un fichier texte
        Analyseur analyseur = new Analyseur(new File("src/JeuxEssais/test"));
        System.out.println(analyseur.getWords().toString());
        System.out.println(" ############################ ");


        Identificateur identificateurs = new Identificateur();
        String word = "ECRIRE(\'BONJOUR\');";
        identificateurs.execute(word);
        System.out.println("word: " + word);
        System.out.println(identificateurs.getIdentificateurs().containsKey(word));
        System.out.println(" ############################ ");


        Compilateur compilateur = new Compilateur(analyseur,identificateurs);
        compilateur.interpretation();

    }

}
