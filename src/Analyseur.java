import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Character.getNumericValue;

public class Analyseur {

    final private int LONG_MAX_IDENT = 20;
    final private int LONG_MAX_CHAINE = 50;
    final private int NB_MOTS_RESERVES = 7;
    final private int MAXINT = Integer.MAX_VALUE;

    public File SOURCE;
    public Scanner sc;
    public char CARLU; // dernier char lu
    public char CARLU_SEPARATEUR;
    public char NEXT_CARLU;

    public Integer NOMBRE; // dernier nbre lu
    public String CHAINE; // dernier identificateur, mot-clé, ... lu
    public boolean SCANNING_STRING;
    public boolean SCANNING_IDENT_OU_MOT_RESERVE;
    public ArrayList<String> TABLE_MOTS_RESERVES;

    public int NUM_LIGNE; // num ligne lue pour ERREUR
    public int CARLU_INDEX; // num char lu pour ERREUR
//    public ArrayList<String> ERREURS = new ArrayList<String>(Arrays.asList(
//            "null", "fin de fichier atteinte", "NOMBRE > MAXINT", "erreur symbole","erreur LONGUEUR CHAINE"
//    ));

    public Identificateur IDENTIFICATEURS = new Identificateur<>();


    Analyseur(File file) throws FileNotFoundException {

        INITIALISER(file);
        LIRE_CAR();
        TERMINER();
        IDENTIFICATEURS.AFFICHE_TABLE_IDENT();
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


                    ANALEX();



                } // fin ligne
            } // fin fichier
        } catch (Exception e){
            ERREUR(0); // erreur en scannant
        }
        ERREUR(1); // erreur si fin fichier
    }


    public void ERREUR(int nbError){
        switch (nbError) {
            case 0:
                System.out.println("Erreur 0: null");
            case 1:
                System.out.println("Erreur 1: Fin de fichier atteinte");
                return;
            case 2:
                System.out.println("Erreur 2: NOMBRE > MAXINT");
            case 3:
                System.out.println("Erreur 3: Erreur symbole");
            case 4:
                System.out.println("Erreur 4: Erreur longueur de chaine");
        }

        System.out.println("Erreur à la ligne "+ NUM_LIGNE +" char " + CARLU_INDEX);
        System.exit(-1);
    }

    public void AFFICHE_CARLU(){
        System.out.println(CARLU);
    }



    // TODO
    public boolean SAUTER_SEPARATEUR() {
        if (CARLU == ' ' && !SCANNING_STRING && !SCANNING_IDENT_OU_MOT_RESERVE){
//            System.out.println("ceci est un espace ->" + CARLU + "<-");
            return true; //continue
        }
        else if (CARLU == '{'){
            CARLU_SEPARATEUR = CARLU;
        }
        else if (CARLU_SEPARATEUR == '{' && CARLU != '}'){
            return true; //continue
        }
        else if (CARLU_SEPARATEUR == '{' && CARLU == '}') {
            CARLU_SEPARATEUR = '\0';
            return true;
        }
        return false;
    }


    // TODO
    public void ANALEX(){
        if (!SAUTER_SEPARATEUR()){
            RECO_SYMB();
            RECO_ENTIER();
            RECO_CHAINE();
            if (!SCANNING_STRING) { RECO_IDENT_OU_MOT_RESERVE(); }
        }
    }






    /************************************
     * reconnaissances d'unités lexicales
     ************************************/
    public T_UNILEX RECO_ENTIER(){

        if (Character.isDigit(CARLU) && !SCANNING_STRING){

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
            System.out.println(NOMBRE);
            return T_UNILEX.ent;

        }
        else { // pour repartir à 0 pour le prochain nombre
            NOMBRE = null;
            return null;
        }
    }


    // TODO
    public T_UNILEX RECO_CHAINE(){

//        System.out.print(CARLU =='\'');
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

            } catch (Exception e){ ERREUR(4);
            }
            return null;
    }


    // TODO
    public T_UNILEX RECO_IDENT_OU_MOT_RESERVE(){

            try{
                if(CARLU_INDEX == 0){
                    CHAINE = "";
                }
                if (Character.isLetter(CARLU) && !SCANNING_IDENT_OU_MOT_RESERVE){ // début de chaine
                    SCANNING_IDENT_OU_MOT_RESERVE = true;
                    CHAINE = "";
                }
                if ((Character.isLetter(CARLU) || Character.isDigit(CARLU) || CARLU == '_')
                        && SCANNING_IDENT_OU_MOT_RESERVE){ // milieu de chaine
                    CHAINE = CHAINE + String.valueOf(CARLU);
                    if (CHAINE.length() > LONG_MAX_CHAINE) { ERREUR(4); }
                    return null;
                }
                else if (!(Character.isLetter(CARLU) || Character.isDigit(CARLU) || CARLU == '_')
                        && SCANNING_IDENT_OU_MOT_RESERVE){ // fin de chaine
                    SCANNING_IDENT_OU_MOT_RESERVE = false;
                    if (CHAINE.length() > LONG_MAX_CHAINE) { ERREUR(4); }

                    if (TABLE_MOTS_RESERVES.contains(CHAINE)) {
                        System.out.println(CHAINE + " :------- MOT CLEF RECONNU : ");
                        return T_UNILEX.motcle;
                    } else {
//                    AFFICHE_CARLU();

                        // todo
                        IDENTIFICATEURS.INSERER(CHAINE,null);
                        return T_UNILEX.ident;
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
                ERREUR(4);
            }

        return null;
    }

    public boolean EST_UN_MOT_RESERVE() {
        
        return false;
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
