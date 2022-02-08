import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.Character.getNumericValue;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // chargement du programme (fichier texte)
        Analyseur analyseur = new Analyseur(new File("src/JeuxEssais/test"));

    }

}
