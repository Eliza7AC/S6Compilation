import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Character.getNumericValue;

public class Analyseur {

    final private int LONG_MAX_IDENT = 20;
    final private int LONG_MAX_CHAINE = 50;
    final private int NB_MOTS_RESERVES = 7;
    final private int MAXINT = Integer.MAX_VALUE;
    char[] arrayOfNb = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    public File SOURCE;
    public Scanner sc;
    public char CARLU; // dernier char lu
    public char NEXT_CARLU;

    public Integer NOMBRE = null; // dernier nbre lu
    public String CHAINE = null; // dernier identificateur, mot-clé, ... lu
    public boolean SCANNING_STRING = false;
    public ArrayList<String> TABLE_MOTS_RESERVES;

    public int NUM_LIGNE; // num ligne lue pour ERREUR
    public int CARLU_INDEX; // num char lu pour ERREUR
    public ArrayList<String> ERREURS = new ArrayList<String>(Arrays.asList(
            "null", "fin de fichier atteinte", "NOMBRE > MAXINT", "erreur symbole","erreur RECO CHAINE"
    ));


    Analyseur(File file) throws FileNotFoundException {

        INITIALISER(file);
        LIRE_CAR();
        TERMINER();
    }


    /**************
     * procédures
     **************/

    public void INITIALISER(File file) throws FileNotFoundException {
        SOURCE = file;
        sc = new Scanner(SOURCE);
        NUM_LIGNE = 0;

        TABLE_MOTS_RESERVES= new ArrayList<String>(
                Arrays.asList("CONST", "DEBUT", "ECRIRE", "FIN", "LIRE", "PROGRAMME", "VAR")
        );

    }

    public void TERMINER(){
        sc.close();
        SOURCE = null;
    }

    public void INSERE_TABLE_MOTS_RESERVES(String motClef){
        TABLE_MOTS_RESERVES.add(motClef);
        Collections.sort(TABLE_MOTS_RESERVES);
    }

    public void LIRE_CAR() throws FileNotFoundException {
//        Scanner sc = new Scanner(SOURCE);
//        NUM_LIGNE = 0;

        try{
            // scan line by line
            while (sc.hasNextLine()){
                NUM_LIGNE++; // à chaque nouvelle ligne
                CARLU_INDEX = 0;

                String strLine = sc.nextLine();
                char[] charLine = strLine.toCharArray();

                // scan char by char inside the line
                int j;
                for (int i = 0; i < charLine.length; i++) {
                    CARLU_INDEX++;
                    CARLU = charLine[i];


                    int charLineEnd = charLine.length-1;
                    if (i+1 <= charLineEnd){
                        j = i+1; // char suivant
                        NEXT_CARLU = charLine[j];
                    }

//                    System.out.println(CARLU + " AT INDEX " + CARLU_INDEX + " | " + NEXT_CARLU );

                    RECO_ENTIER();
                    RECO_CHAINE();
                    RECO_IDENT_OU_MOT_RESERVE();
                    RECO_SYMB();


                } // fin ligne
            } // fin fichier
        } catch (Exception e){
            ERREUR(0); // erreur en scannant
        }
        ERREUR(1); // erreur si fin fichier
    }


    public void ERREUR(int nbError){
        System.out.println(
            "erreur n°" + nbError + " [ ligne " + NUM_LIGNE +
            " char " + CARLU_INDEX + " ] message: " + ERREURS.get(nbError)
        );
        System.exit(-1);
    }

    public void AFFICHE_CARLU(){
        System.out.println(CARLU);
    }



    // TODO
//    public void SAUTER_SEPARATEUR() throws FileNotFoundException {
//        if (CARLU == ' '){
//            System.out.println("ceci est un espace ->" + CARLU + "<-");
//        } else {
//            LIRE_CAR();
//        }
//    }


    // TODO
    public void ANALEX(){

    }






    /************************************
     * reconnaissances d'unités lexicales
     ************************************/
    public T_UNILEX RECO_ENTIER(){
        if (isDigit()){

            try{
                if (NOMBRE == null){ // toute première fois
                    NOMBRE = getNumericValue(CARLU);
                }
                else{ // incrémentation de l'entiereté du nombre char by char
                    NOMBRE = Integer.valueOf(String.valueOf(NOMBRE) + String.valueOf(CARLU));
//                    System.out.println(NOMBRE);
                }
            } catch (Exception e){
                ERREUR(2);
            }
//            AFFICHE_CARLU()
            return T_UNILEX.ent;

        }
        else { // pour repartir à 0 pour le prochain nombre
        NOMBRE = null;
        return null;
        }
    }

    public boolean isDigit(){
        for (Character nb : arrayOfNb){
            if (CARLU == nb){
                return true;
            }
        }
        return false;
    }

    // TODO
    public T_UNILEX RECO_CHAINE(){

        System.out.print(CARLU =='\'');
        System.out.println(" | " + CARLU + " / CHAINE = " + CHAINE );

            try{
                if (CARLU == '\'' && !SCANNING_STRING){ // début de chaine
                    SCANNING_STRING = true;
                    CHAINE = "";
                    return null;
                }
                else if (CARLU != '\'' && SCANNING_STRING){ // milieu de chaine
                    CHAINE = CHAINE + String.valueOf(CARLU);
                    if (CHAINE.length() > LONG_MAX_CHAINE) { ERREUR(4); }
                    return null;
                }
                else if (CARLU == '\'' && SCANNING_STRING){ // fin de chaine
                    SCANNING_STRING = false;
                    if (CHAINE.length() > LONG_MAX_CHAINE) { ERREUR(4); }

//                    AFFICHE_CARLU();
                    return T_UNILEX.ch;
                }

            } catch (Exception e){
                ERREUR(4);
            }
            return null;
    }


    // TODO
    public T_UNILEX RECO_IDENT_OU_MOT_RESERVE(){
        return null;
    }


    public T_UNILEX RECO_SYMB(){
        try{
            switch(CARLU)
            {
                case ';': return T_UNILEX.ptvirg;
                case '.': return T_UNILEX.point;
                case '=': return T_UNILEX.eg;
                case '+': return T_UNILEX.plus;
                case '-': return T_UNILEX.moins;
                case '*': return T_UNILEX.mult;
                case '/': return T_UNILEX.divi;
                case '(': return T_UNILEX.parouv;
                case ')': return T_UNILEX.parfer;
                case '<':
                    if (NEXT_CARLU == '='){ return T_UNILEX.infe; } // <=
                    if (NEXT_CARLU == '>'){ return T_UNILEX.diff; } // <>
                    if (NEXT_CARLU != '=' && NEXT_CARLU != '>'){return T_UNILEX.inf;} // <

                case '>':
                    if (NEXT_CARLU == '='){return T_UNILEX.supe; } // >=
                    if (NEXT_CARLU != '='){ return T_UNILEX.sup; } // >

                case ':':
                    if (NEXT_CARLU == '='){ return T_UNILEX.aff; } // :=
                    if (NEXT_CARLU != '='){ return T_UNILEX.deuxpts; } // :
            }
        } catch (Exception e){
            ERREUR(3);
        }
        return null;
    }





}
