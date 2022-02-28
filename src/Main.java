import java.io.File;

import static java.lang.Character.getNumericValue;

public class Main {

    public static void main(String[] args) {
        /**
         * Programme principal pour l'Analyseur Lexical
         */
        AnalyseurLexical.INITIALISER(new File("src/JeuxEssais/test"));
        AnalyseurSyntaxique.ANASYNT();
        AnalyseurLexical.TERMINER();
        Identificateur.AFFICHE_TABLE_IDENT();
    }

}
