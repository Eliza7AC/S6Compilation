import java.io.File;

public class Main {

    public static void main(String[] args) {
        /**
         * Programme principal
         */
        File sourceCode = new File("src/JeuxEssais/testBoucles");
        Compilateur.COMPILE(sourceCode);
        Interpreteur.CREER_FICHIER_CODE(sourceCode.getName());
        Interpreteur.INTERPRETER();
    }

}
