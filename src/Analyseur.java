import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Character.getNumericValue;

public class Analyseur {

    final private int LONG_MAX_IDENT = 20;
    final private int LONG_MAX_CHAINE = 50;
    final private int NB_MOTS_RESERVES = 7;
    final private int MAXINT = Integer.MAX_VALUE;

    public File SOURCE;
    public char CARLU; // dernier char lu
    public boolean PREVIOUS_CHAR_IS_SIMPLE = false;
    public boolean IS_COMPOSED_SYMBOL = false;
    public Integer NOMBRE = null; // dernier nbre lu
    public String CHAINE; // dernier identificateur, mot-clé, ... lu
    public int NUM_LIGNE; // num ligne lue pour les ERREUR
    public int CARLU_INDEX;

    public ArrayList<String> TABLE_MOTS_RESERVES = new ArrayList<String>(
            Arrays.asList("DEBUT", "ECRIRE", "ENT", "FIN", "LIRE", "SI", "VAR")
    );

    public ArrayList<String> ERREURS = new ArrayList<String>(Arrays.asList(
            "null",
            "fin de fichier atteinte",
            "NOMBRE > MAXINT"
    ));


    /**
     * constructeur
     */
    Analyseur(File file) throws FileNotFoundException {
        SOURCE = file;
        LIRE_CAR();
    }



    /**
     * procédures
     */
    public void LIRE_CAR() throws FileNotFoundException {
        Scanner sc = new Scanner(SOURCE);
        NUM_LIGNE = 0;

        try{
            // scan char by char
            while (sc.hasNextLine()){
                NUM_LIGNE++; // à chaque nouvelle ligne
                CARLU_INDEX = 0;

                String strLine = sc.nextLine();
                char[] charLine = strLine.toCharArray();

                for (int i = 0; i < charLine.length; i++) {
                    CARLU_INDEX++;
                    CARLU = charLine[i];
                    System.out.println(CARLU + " AT INDEX " + CARLU_INDEX);


                    if (isDigit(CARLU)){
                        // concaténation de l'entièreté du nombre char by char
                        RECO_ENTIER();
                    }
                    else{
                        // pour repartir à 0 pour le prochain nombre
                        NOMBRE = null;
                    }
                } // fin ligne
            } // fin fichier
        } catch (Exception e){
            ERREUR(0); // en cas d'erreur en scannant
        }

        ERREUR(1); // lorsque la fin du fichier est lue

    }


    public void ERREUR(int nbError){
        System.out.println("erreur n°" + nbError +
                " [ ligne " + NUM_LIGNE + " char " + CARLU_INDEX +
                " ] message: " + ERREURS.get(nbError));

        System.exit(-1);
    }


    public T_UNILEX RECO_ENTIER(){
        try{
            if (NOMBRE == null){ // toute première fois
                NOMBRE = getNumericValue(CARLU);
            }
            else{
                NOMBRE = Integer.valueOf(String.valueOf(NOMBRE) + String.valueOf(CARLU));
                System.out.println(NOMBRE);
            }
        } catch (Exception e){
            ERREUR(2);
        }
        return T_UNILEX.ent;
    }


    /**
     * unités lexicales
     */
    public boolean isDigit(Character c){
        char[] arrayOfNb = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        for (Character nb : arrayOfNb){
            if (nb == c){
                PREVIOUS_CHAR_IS_SIMPLE = false;
                return true;
            }
        }
        return false;
    }

    public boolean isMereSymbol(Character c){
        if(c == ',' || c == ';' || c == '.' || c== ':' ||
                c == '(' || c == ')' || c == '<' || c == '>' ||
                c == '+' || c == '-' || c == '*' || c == '/'){

            // if previous char is simple && actual char is simple
            if (PREVIOUS_CHAR_IS_SIMPLE = true){
                IS_COMPOSED_SYMBOL = true;
            }
            PREVIOUS_CHAR_IS_SIMPLE = true;
            return true;
        }
        else {
            return false;
        }

    }

    public boolean isSpace(Character c){
        if (c== ' '){
            PREVIOUS_CHAR_IS_SIMPLE = false;
            return true;
        }
        return false;
    }


}
