import java.io.File;

public class Compilateur {

    /**
     * Compile le fichier source
     * @param sourceCode fichier source
     */
    public static void COMPILE(File sourceCode) {
        AnalyseurLexical.INITIALISER(sourceCode);
        AnalyseurSyntaxique.ANASYNT();
        AnalyseurLexical.TERMINER();
    }
}
