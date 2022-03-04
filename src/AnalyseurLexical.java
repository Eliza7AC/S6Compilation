import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class AnalyseurLexical {
    private static int LONG_MAX_IDENT = 20;
    private static int LONG_MAX_CHAINE = 50;
    private static int NB_MOTS_RESERVES = 7;
    private static int MAXINT = Integer.MAX_VALUE;

    private static File SOURCE;
    private static Scanner sc;
    private static String STR_LINE; // Ligne actuelle lu dans LIRE_CAR()
    private static char[] CHAR_LINE; // STR_LINE en tableau de char

    private static char CARLU; // dernier char lu
    private static char NEXT_CARLU; // char après CARLU

    public static int NOMBRE; // dernier nombre lu
    public static String CHAINE; // dernier identificateur, mot-clé, ... lu
    private static boolean EOF; // fin de fichier
    private static ArrayList<String> TABLE_MOTS_RESERVES;

    public static int NUM_LIGNE; // num ligne lue pour ERREUR
    public static int CARLU_INDEX; // num char lu pour ERREUR


    /*************************************************
     * Procédures
     *************************************************/


    /**
     * Initialisation du début de programme
     * @param file Fichier source
     */
    public static void INITIALISER(File file) {
        SOURCE = file;
        try {
            sc = new Scanner(SOURCE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        NUM_LIGNE = 0;
        TABLE_MOTS_RESERVES= new ArrayList<>(
                Arrays.asList("CONST", "DEBUT", "ECRIRE", "FIN", "LIRE", "PROGRAMME", "VAR")
        );
    }

    /**
     * Instructions de fin de programme
     */
    public static void TERMINER() {
        sc.close();
        SOURCE = null;
    }

    /**
     * Insère un mot clé réservé dans la table de mots réservés
     * @param motClef Nom du mot réservé
     */
    public static void INSERE_TABLE_MOTS_RESERVES(String motClef){
        TABLE_MOTS_RESERVES.add(motClef);
        Collections.sort(TABLE_MOTS_RESERVES);
    }

    /**
     * Lit un caractère provenant du fichier source
     */
    public static void LIRE_CAR() {
        if (STR_LINE == null || CARLU_INDEX == CHAR_LINE.length) {
            while (sc.hasNextLine()) {
                STR_LINE = sc.nextLine();
                if (STR_LINE.length() != 0) {
                    CHAR_LINE = STR_LINE.toCharArray();
                    NUM_LIGNE++;
                    CARLU_INDEX = 0;
                    break;
                }
                if (!sc.hasNextLine()) {
                    EOF = true;
                    ERREUR(1); // Fin de fichier atteinte
                    return;
                }
            }
        }
        CARLU = CHAR_LINE[CARLU_INDEX];
        CARLU_INDEX++;

        if (CARLU_INDEX < CHAR_LINE.length) {
            NEXT_CARLU = CHAR_LINE[CARLU_INDEX];
        }
        else {
            NEXT_CARLU = '\0';
        }
    }

    /**
     * Recensement des messages d'erreur
     * @param nbError Numéro de l'erreur
     */
    public static void ERREUR(int nbError) {
        switch (nbError) {
            case 0:
                System.out.println("Erreur 0: Erreur inconnue");
                break;
            case 1:
                System.out.println("Erreur 1: Fin de fichier atteinte");
                return;
            case 2:
                System.out.println("Erreur 2: NOMBRE > MAXINT");
                break;
            case 3:
                System.out.println("Erreur 3: Erreur longueur de chaine");
                break;
            case 4:
                System.out.println("Erreur 4: Erreur syntaxique");
                break;
        }
        System.out.println("Erreur à la ligne "+ NUM_LIGNE +" caractère " + CARLU_INDEX);
        System.exit(-1);
    }

    /**
     * Lit le prochain caractère tant que CARLU est dans un commentaire ou si CARLU est un séparateur
     */
    public static void SAUTER_SEPARATEUR() {
        do {
            if (CARLU == '{') {
                int nb = 1;
                while (nb != 0 && (NEXT_CARLU != '\0' || sc.hasNextLine())) {
                    LIRE_CAR();
                    if (CARLU == '}') nb--;
                    if (CARLU == '{') nb++;
                }
                LIRE_CAR();
            }
            if (CARLU == ' ') {
                while (CARLU == ' ') {
                    LIRE_CAR();
                }
            }
        } while (CARLU == '{');
    }



    /*************************************************
     * Fonctions de reconnaissance d'unités lexicales
     *************************************************/


    /**
     * Noyau de l'analyseur lexical
     */
    public static T_UNILEX ANALEX() {
        LIRE_CAR();
        T_UNILEX t_unilex;
        SAUTER_SEPARATEUR();
        if (EOF) return null;
            if ((t_unilex = RECO_ENTIER()) == null) {
                if ((t_unilex = RECO_CHAINE()) == null) {
                    if ((t_unilex = RECO_IDENT_OU_MOT_RESERVE()) == null) {
                        if ((t_unilex = RECO_SYMB()) == null) {
                            ERREUR(0);
                        } else return t_unilex;
                    } else return t_unilex;
                } else return t_unilex;
            } else return t_unilex;
        return null;
    }

    /**
     * Lit le prochain caractère tant que CARLU forme un nombre entier
     * @return T_UNILEX.ent si nombre entier
     */
    public static T_UNILEX RECO_ENTIER(){
        if (Character.isDigit(CARLU)) {
            NOMBRE = Character.getNumericValue(CARLU);
            while (Character.isDigit(NEXT_CARLU)) {
                LIRE_CAR();
                NOMBRE = Integer.parseInt(Integer.toString(NOMBRE) + Character.getNumericValue(CARLU));
                if (NOMBRE >= MAXINT) {
                    ERREUR(2);
                }
            }
            return T_UNILEX.ent;
        }
        return null;
    }


    /**
     * Lit le prochain caractère tant que CARLU forme une chaîne de caractère
     * @return T_UNILEX.ch si chaîne de caractère
     */
    public static T_UNILEX RECO_CHAINE(){
        if (CARLU == '\'') {
            CHAINE = "";
            while (true) {
                LIRE_CAR();
                if (CARLU != '\'') {
                    CHAINE = CHAINE + CARLU;
                    if (CHAINE.length() > LONG_MAX_CHAINE) {
                        ERREUR(3);
                    }
                } else {
                    if (NEXT_CARLU == '\'') {
                        CHAINE = CHAINE + CARLU;
                        if (CHAINE.length() > LONG_MAX_CHAINE) {
                            ERREUR(3);
                        }
                        LIRE_CAR(); // Saute NEXT_CARLU pour la prochaine itération
                    } else break;
                }
            }
            return T_UNILEX.ch;
        }
        return null;
    }

    /**
     * Lit le prochain caractère et détermine si CARLU forme un mot clé réservé ou un identificateur
     * @return T_UNILEX.motcle si mot clé réservé ou T_UNILEX.ident si identificateur
     */
    public static T_UNILEX RECO_IDENT_OU_MOT_RESERVE(){
        if (Character.isLetter(CARLU)) {
            CHAINE = Character.toString(CARLU);
            while (Character.isLetter(NEXT_CARLU) || Character.isDigit(NEXT_CARLU) || NEXT_CARLU == '_') {
                LIRE_CAR();
                if (CHAINE.length() < LONG_MAX_IDENT) { // Si la longueur de la chaine est supérieure à la longueur maximale, on ignore les caractères superflus
                    CHAINE = CHAINE + CARLU;
                }
            }
            CHAINE = CHAINE.toUpperCase();
            if (TABLE_MOTS_RESERVES.contains(CHAINE)) {
                return T_UNILEX.motcle;
            }
//            Identificateur.INSERER(CHAINE, null, null);
            return T_UNILEX.ident;
        }
        return null;
    }

    /**
     * Lit le prochain caractère et détermine si CARLU est un symbole
     * @return T_UNILEX correspondant à un symbole
     */
    public static T_UNILEX RECO_SYMB(){
        switch(CARLU) {
            case ',': return T_UNILEX.virg;
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
                if (NEXT_CARLU == '=') {
                    LIRE_CAR(); // Saute NEXT_CARLU pour la prochaine itération
                    return T_UNILEX.infe;
                }
                if (NEXT_CARLU == '>') {
                    LIRE_CAR(); // Saute NEXT_CARLU pour la prochaine itération
                    return T_UNILEX.diff;
                }
                return T_UNILEX.inf;
            case '>':
                if (NEXT_CARLU == '=') {
                    LIRE_CAR(); // Saute NEXT_CARLU pour la prochaine itération
                    return T_UNILEX.supe;
                }
                return T_UNILEX.sup;
            case ':':
                if (NEXT_CARLU == '=') {
                    LIRE_CAR(); // Saute NEXT_CARLU pour la prochaine itération
                    return T_UNILEX.aff;
                }
                return T_UNILEX.deuxpts;
        }
        return null;
    }
}
