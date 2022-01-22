import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // chargement du programme Minipasc simulé par un fichier texte
        Analyseur an = new Analyseur(new File("src/test"));

        System.out.println(" ############################ ");
        System.out.println(an.getWords().toString());
        System.out.println(" ############################ ");


        Identificateur id = new Identificateur();
        String word = "ECRIRE(\"BONJOUR\")";
//        id.execute(word);

        Compilateur compilateur = new Compilateur(an,id);
        compilateur.interpretation();

    }

}
