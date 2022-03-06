public class Generateur {
    private static int CO = 0;

    public static final int ADDI = 0;
    public static final int SOUS = 1;
    public static final int MULT = 2;
    public static final int DIV = 3;
    public static final int MOIN = 4;
    public static final int AFFE = 5;
    public static final int LIRE = 6;
    public static final int ECRL = 7;
    public static final int ECRE = 8;
    public static final int ECRC = 9;
    public static final int FINC = 10;
    public static final int EMPI = 11;
    public static final int CONT = 12;
    public static final int STOP = 13;
    public static final int ALLE = 14;
    public static final int ALSN = 15;

    /**
     * Génération du code lors de la fin du programme
     */
    public static void GENCODE_PROG_FIN() {
        Interpreteur.P_CODE[CO] = Generateur.STOP;
        CO++;
    }

    /**
     * Génération du code lors de l'affectation de l'identificateur
     */
    public static void GENCODE_AFFECTATION_IDENT() {
        Interpreteur.P_CODE[CO] =  Generateur.EMPI;
        Interpreteur.P_CODE[CO + 1] = (Integer) Identificateur.TABLE_IDENT_ARRAY.get(Identificateur.CHERCHER(AnalyseurLexical.CHAINE)).getProperties().get("adrv");
        CO += 2;
    }

    /**
     * Génération du code après affectation
     */
    public static void GENCODE_AFFECTATION_AFFE() {
        Interpreteur.P_CODE[CO] = Generateur.AFFE;
        CO++;
    }

    /**
     * Génération du code lors d'une instruction de lecture
     */
    public static void GENCODE_LECTURE() {
        Interpreteur.P_CODE[CO] = Generateur.EMPI;
        Interpreteur.P_CODE[CO + 1] = (Integer) Identificateur.TABLE_IDENT_ARRAY.get(Identificateur.CHERCHER(AnalyseurLexical.CHAINE)).getProperties().get("adrv");
        Interpreteur.P_CODE[CO + 2] = Generateur.LIRE;
        CO += 3;
    }

    /**
     * Génération du code lors d'une instruction d'écriture
     */
    public static void GENCODE_ECRITURE() {
        Interpreteur.P_CODE[CO] = Generateur.ECRL;
        CO++;
    }

    /**
     * Génération du code d'une expression lors de l'écriture d'une expression
     */
    public static void GENCODE_ECR_EXP_EXP() {
        Interpreteur.P_CODE[CO] = Generateur.ECRE;
        CO++;
    }

    /**
     * Génération du code d'une chaîne lors de l'écriture d'une expression
     */
    public static void GENCODE_ECR_EXP_CH() {
        Interpreteur.P_CODE[CO] = Generateur.ECRC;
        for (int i = 1; i < AnalyseurLexical.CHAINE.length() + 1; i++) {
            Interpreteur.P_CODE[CO + i] = (int) AnalyseurLexical.CHAINE.charAt(i-1);
        }
        Interpreteur.P_CODE[CO + AnalyseurLexical.CHAINE.length() + 1] = Generateur.FINC;
        CO = CO  + AnalyseurLexical.CHAINE.length() + 2;
    }

    /**
     * Génération du code lors d'une instruction avec terme, opérande et expression
     */
    public static void GENCODE_TERME_OP_BIN_EXP() {
        Interpreteur.P_CODE[CO] = Interpreteur.PILOP.pop();
        CO++;
    }

    /**
     * Génération du code d'un opérateur
     * @param code numéro de l'opérateur
     */
    public static void GENCODE_OP_BIN(int code) {
        Interpreteur.PILOP.push(code);
    }

    /**
     * Génération du code d'un entier lors d'une instruction de terme
     */
    public static void GENCODE_TERME_ENT() {
        Interpreteur.P_CODE[CO] = Generateur.EMPI;
        Interpreteur.P_CODE[CO + 1] = AnalyseurLexical.NOMBRE;
        CO += 2;
    }

    /**
     * Génération du code d'une identificateur lors d'une instruction de terme
     */
    public static void GENCODE_TERME_IDENT() {
        Interpreteur.P_CODE[CO] = Generateur.EMPI;
        Interpreteur.P_CODE[CO+ 1] = (Integer) Identificateur.TABLE_IDENT_ARRAY.get(Identificateur.CHERCHER(AnalyseurLexical.CHAINE)).getProperties().get("adrv");
        Interpreteur.P_CODE[CO + 2] = Generateur.CONT;
        CO += 3;
    }

    /**
     * Génération du code de la négation lors d'une instruction de terme
     */
    public static void GENCODE_TERME_MOINS() {
        Interpreteur.P_CODE[CO] = Generateur.MOIN;
        CO++;
    }

    /**
     * Génération du code d'une expression lors d'une instruction conditionnelle
     */
    public static void GENCODE_INST_COND_EXP() {
        Interpreteur.P_CODE[CO] = Generateur.ALSN;
        Interpreteur.PILOP.push(CO + 1);
        CO += 2;
    }

    /**
     * Génération du code d'une expression suivi d'une instruction lors d'une instruction conditionnelle
     */
    public static void GENCODE_INST_COND_EXP_INST() {
        Interpreteur.P_CODE[Interpreteur.PILOP.pop()] = CO + 2;
        Interpreteur.P_CODE[CO] = Generateur.ALLE;
        Interpreteur.PILOP.push(CO + 1);
        CO += 2;
    }

    /**
     * Génération du code de fin d'instruction conditionnelle
     */
    public static void GENCODE_INST_COND_FIN() {
        Interpreteur.P_CODE[Interpreteur.PILOP.pop()] = CO;
    }

    /**
     * Génération du code d'une instruction répétitive
     */
    public static void GENCODE_INST_REP() {
        Interpreteur.PILOP.push(CO);
    }

    /**
     * Génération du code d'une expression lors d'une instruction répétitive
     */
    public static void GENCODE_INST_REP_EXP() {
        Interpreteur.P_CODE[CO] = Generateur.ALSN;
        Interpreteur.PILOP.push(CO + 1);
        CO += 2;
    }

    /**
     * Génération du code d'une instruction lors d'une instruction répétitive
     */
    public static void GENCODE_INST_REP_INST() {
        Interpreteur.P_CODE[Interpreteur.PILOP.pop()] = CO + 2;
        Interpreteur.P_CODE[CO] = Generateur.ALLE;
        Interpreteur.P_CODE[CO + 1] = Interpreteur.PILOP.pop();
        CO += 2;
    }

    /**
     * Obtenir le nom de la fonction à partir de son numéro
     * @param code numéro de la fonction
     * @return nom de la fonction
     */
    public static String getCodeName(int code) {
        switch (code) {
            case Generateur.ADDI:
                return "ADDI";
            case Generateur.SOUS:
                return "SOUS";
            case Generateur.MULT:
                return "MULT";
            case Generateur.DIV:
                return "DIV";
            case Generateur.MOIN:
                return "MOIN";
            case Generateur.AFFE:
                return "AFFE";
            case Generateur.LIRE:
                return "LIRE";
            case Generateur.ECRL:
                return "ECRL";
            case Generateur.ECRE:
                return "ECRE";
            case Generateur.ECRC:
                return "ECRC";
            case Generateur.FINC:
                return "FINC";
            case Generateur.EMPI:
                return "EMPI";
            case Generateur.CONT:
                return "CONT";
            case Generateur.STOP:
                return "STOP";
            case Generateur.ALLE:
                return "ALLE";
            case Generateur.ALSN:
                return "ALSN";
            default:
                return "";
        }
    }
}
