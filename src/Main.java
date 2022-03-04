import java.io.File;
import java.util.Arrays;

import static java.lang.Character.getNumericValue;

public class Main {

    public static void main(String[] args) {
        /**
         * Programme principal
         */
        File sourceCode = new File("src/JeuxEssais/equationSecondDegre");
        Compilateur.compile(sourceCode);
        Interpreteur.CREER_FICHIER_CODE(sourceCode.getName());
        Interpreteur.INTERPRETER();
    }

}
