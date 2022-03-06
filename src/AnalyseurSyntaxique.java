public class AnalyseurSyntaxique {
    public static T_UNILEX UNILEX;

    /**
     * Noyau de l'Analyseur Syntaxique
     */
    public static void ANASYNT() {
        UNILEX = AnalyseurLexical.ANALEX();
        if (!PROG()) {
            AnalyseurLexical.ERREUR(4);
        }
    }



    /*************************************************
     * Règles de production
     *************************************************/


    /**
     * Règle de production du programme
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean PROG() {
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("PROGRAMME")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.ident) {
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.ptvirg) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    DECL_CONST();
                    DECL_VAR();
                    if (BLOC()) {
                        if (UNILEX == T_UNILEX.point) {
                            Generateur.GENCODE_PROG_FIN();
                            return true;
                        } else {
                            System.out.println("Erreur syntaxique dans une instruction de programme: . attendu");
                            return false;
                        }
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction de programme: bloc d'instruction attendu");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction de programme: ; attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction de programme: identificateur attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une instruction de programme: mot-clé PROGRAMME attendu");
            return false;
        }
    }


    /**
     * Règle de production d'une constante
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean DECL_CONST() {
        boolean fin, erreurVal, erreurEg, erreurIdent;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("CONST")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.ident) {
                String NOM_CONSTANTE = AnalyseurLexical.CHAINE;
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.eg) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    if (UNILEX == T_UNILEX.ent || UNILEX == T_UNILEX.ch) {
                        if (AnalyseurSemantique.DEFINIR_CONSTANTE(NOM_CONSTANTE, UNILEX)) {
                            fin = false;
                            erreurVal = false;
                            erreurEg = false;
                            erreurIdent = false;
                            UNILEX = AnalyseurLexical.ANALEX();
                            do {
                                if (UNILEX == T_UNILEX.virg) {
                                    UNILEX = AnalyseurLexical.ANALEX();
                                    if (UNILEX == T_UNILEX.ident) {
                                        NOM_CONSTANTE = AnalyseurLexical.CHAINE;
                                        UNILEX = AnalyseurLexical.ANALEX();
                                        if (UNILEX == T_UNILEX.eg) {
                                            UNILEX = AnalyseurLexical.ANALEX();
                                            if (UNILEX == T_UNILEX.ent || UNILEX == T_UNILEX.ch) {
                                                if (AnalyseurSemantique.DEFINIR_CONSTANTE(NOM_CONSTANTE, UNILEX)) {
                                                    UNILEX = AnalyseurLexical.ANALEX();
                                                } else {
                                                    System.out.println("Erreur sémantique dans une déclaration de variable: identificateur déjà déclaré");
                                                    return false;
                                                }
                                            } else {
                                                erreurVal = true;
                                                fin = true;
                                            }
                                        } else {
                                            erreurEg = true;
                                            fin = true;
                                        }
                                    } else {
                                        erreurIdent = true;
                                        fin = true;
                                    }
                                } else {
                                    fin = true;
                                }
                            } while (!fin);
                            if (erreurVal) {
                                System.out.println("Erreur syntaxique dans une déclaration de constante: entier ou chaine attendu");
                                return false;
                            } else if (erreurEg) {
                                System.out.println("Erreur syntaxique dans une déclaration de constante: = attendu");
                                return false;
                            } else if (erreurIdent) {
                                System.out.println("Erreur syntaxique dans une déclaration de constante: identificateur attendu");
                                return false;
                            } else if (UNILEX == T_UNILEX.ptvirg) {
                                UNILEX = AnalyseurLexical.ANALEX();
                                return true;
                            } else {
                                System.out.println("Erreur syntaxique dans une déclaration de constante: ; attendu");
                                return false;
                            }
                        } else {
                            System.out.println("Erreur sémantique dans une déclaration des constante: identificateur déjà déclaré");
                            return false;
                        }
                    } else {
                        System.out.println("Erreur syntaxique dans une déclaration de constante: entier ou chaine attendu");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une déclaration de constante: = attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une déclaration de constante: identificateur attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une déclaration de constante: mot-clé CONST attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une variable
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean DECL_VAR() {
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("VAR")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.ident) {
                if (AnalyseurSemantique.DEFINIR_VARIABLE(AnalyseurLexical.CHAINE)) {
                    fin = false;
                    erreur = false;
                    UNILEX = AnalyseurLexical.ANALEX();
                    do {
                        if (UNILEX == T_UNILEX.virg) {
                            UNILEX = AnalyseurLexical.ANALEX();
                            if (UNILEX == T_UNILEX.ident) {
                                if (AnalyseurSemantique.DEFINIR_VARIABLE(AnalyseurLexical.CHAINE)) {
                                    UNILEX = AnalyseurLexical.ANALEX();
                                } else {
                                    System.out.println("Erreur sémantique dans une déclaration de variable: identificateur déjà déclaré");
                                    return false;
                                }
                            } else {
                                fin = true;
                                erreur = true;
                            }
                        } else {
                            fin = true;
                        }
                    } while (!fin);
                    if (erreur) {
                        System.out.println("Erreur syntaxique dans une déclaration de variable: identificateur attendu");
                        return false;
                    } else if (UNILEX == T_UNILEX.ptvirg) {
                        UNILEX = AnalyseurLexical.ANALEX();
                        return true;
                    } else {
                        System.out.println("Erreur syntaxique dans une déclaration de variable: ; attendu");
                        return false;
                    }
                } else {
                    System.out.println("Erreur sémantique dans une déclaration de variable: identificateur déjà déclaré");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une déclaration de variable: identificateur attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une déclaration de variable: mot-clé VAR attendu");
            return false;
        }
    }

    /**
     * Règle de production d'un bloc
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean BLOC() {
        boolean fin;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("DEBUT")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (INSTRUCTION()) {
                fin = false;
                do {
                    if (UNILEX == T_UNILEX.ptvirg) {
                        UNILEX = AnalyseurLexical.ANALEX();
                        if (!INSTRUCTION()) {
                            fin = true;
                        }
                    } else {
                        fin = true;
                    }
                } while (!fin);
                if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("FIN")) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    return true;
                } else {
                    System.out.println("Erreur syntaxique dans un bloc d'instruction: mot-clé FIN attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans un bloc d'instruction: erreur dans une instruction");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans un bloc d'instruction: mot-clé DEBUT attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une instruction
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean INSTRUCTION() {
        return INST_NON_COND() || INST_COND();
    }

    public static boolean INST_NON_COND() {
        return AFFECTATION() || LECTURE() || ECRITURE() || BLOC() || INST_REPE();
    }

    public static boolean INST_REPE() {
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("TANTQUE")) {
            Generateur.GENCODE_INST_REP();
            UNILEX = AnalyseurLexical.ANALEX();
            if (EXP()) {
                Generateur.GENCODE_INST_REP_EXP();
                if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("FAIRE")) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    if (INSTRUCTION()) {
                        Generateur.GENCODE_INST_REP_INST();
                        return true;
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction répétitive: erreur d'instruction dans le bloc FAIRE");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction répétitive: mot-clé FAIRE attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction répétitive: erreur d'expression dans la condition TANTQUE");
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean INST_COND() {
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("SI")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (EXP()) {
                Generateur.GENCODE_INST_COND_EXP();
                if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("ALORS")) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    if (INST_COND() || INST_NON_COND()) {
                        Generateur.GENCODE_INST_COND_EXP_INST();
                        fin = false;
                        erreur = false;
                        do {
                            if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("SINON")) {
                                UNILEX = AnalyseurLexical.ANALEX();
                                erreur = !INSTRUCTION();
                                if (erreur) {
                                    fin = true;
                                }
                            } else {
                                fin = true;
                            }
                        } while (!fin);
                        if (erreur) {
                            System.out.println("Erreur syntaxique dans une instruction conditionnelle: erreur d'instruction dans le bloc SINON");
                            return false;
                        } else {
                            Generateur.GENCODE_INST_COND_RECO();
                            return true;
                        }
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction conditionnelle: instruction attendue");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction conditionnelle: mot-clé ALORS attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction conditionnelle: expression attendue");
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Règle de production d'une affectation
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean AFFECTATION() {
        if (UNILEX == T_UNILEX.ident) {
            int index;
            if ((index = Identificateur.CHERCHER(AnalyseurLexical.CHAINE)) < 0) {
                System.out.println("Erreur sémantique dans une instruction d'affectation: identificateur non déclaré");
                return false;
            } else if (Identificateur.TABLE_IDENT_ARRAY.get(index).getType() != T_IDENT.variable) {
                System.out.println("Erreur sémantique dans une instruction d'affectation: identificateur de type variable attendu");
                return false;
            } else {
                Generateur.GENCODE_AFFECTATION_IDENT();
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.aff) {
                    UNILEX = AnalyseurLexical.ANALEX();
                     if(EXP()) {
                         Generateur.GENCODE_AFFECTATION_AFFE();
                         return true;
                     } else {
                         System.out.println("Erreur sémantique dans une instruction d'affectation: erreur d'expression");
                         return false;
                     }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction d'affectation: := attendu");
                    return false;
                }
            }
        } else {
//            System.out.println("Erreur syntaxique dans une instruction d'affectation: identificateur attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une lecture
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean LECTURE() {
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("LIRE")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.parouv) {
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.ident) {
                    int index;
                    if ((index = Identificateur.CHERCHER(AnalyseurLexical.CHAINE)) < 0) {
                        System.out.println("Erreur sémantique dans une instruction de lecture: identificateur non déclaré");
                        return false;
                    } else if (Identificateur.TABLE_IDENT_ARRAY.get(index).getType() != T_IDENT.variable) {
                        System.out.println("Erreur sémantique dans une instruction de lecture: identificateur de type variable attendu");
                        return false;
                    } else {
                        Generateur.GENCODE_LECTURE();
                        UNILEX = AnalyseurLexical.ANALEX();
                        fin = false;
                        erreur = false;
                        do {
                            if (UNILEX == T_UNILEX.virg) {
                                UNILEX = AnalyseurLexical.ANALEX();
                                if (UNILEX == T_UNILEX.ident) {
                                    if ((index = Identificateur.CHERCHER(AnalyseurLexical.CHAINE)) < 0) {
                                        System.out.println("Erreur sémantique dans une instruction de lecture: identificateur non déclaré");
                                        return false;
                                    } else if (Identificateur.TABLE_IDENT_ARRAY.get(index).getType() != T_IDENT.variable) {
                                        System.out.println("Erreur sémantique dans une instruction de lecture: identificateur de type variable attendu");
                                        return false;
                                    } else {
                                        Generateur.GENCODE_LECTURE();
                                        UNILEX = AnalyseurLexical.ANALEX();
                                    }
                                } else {
                                    fin = true;
                                    erreur = true;
                                }
                            } else {
                                fin = true;
                            }
                        } while (!fin);
                    }
                    if (erreur) {
                        System.out.println("Erreur syntaxique dans une instruction de lecture: identificateur attendu");
                        return false;
                    } else if (UNILEX == T_UNILEX.parfer) {
                        UNILEX = AnalyseurLexical.ANALEX();
                        return true;
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction de lecture: ) attendu");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction de lecture: identificateur attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction de lecture: ( attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une instruction de lecture: mot-clé LIRE attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une écriture
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean ECRITURE() {
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("ECRIRE")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.parouv) {
                UNILEX = AnalyseurLexical.ANALEX();
                erreur = false;
                if (ECR_EXP()) {
                    fin = false;
                    do {
                        if (UNILEX == T_UNILEX.virg) {
                            UNILEX = AnalyseurLexical.ANALEX();
                            erreur = !ECR_EXP();
                            if (erreur) {
                                fin = true;
                            }
                        } else {
                            fin = true;
                        }
                    } while (!fin);
                } else {
                    Generateur.GENCODE_ECRITURE();
                }
                if (erreur) {
                    System.out.println("Erreur syntaxique dans une instruction d'écriture: expression incorrecte");
                    return false;
                } else if (UNILEX == T_UNILEX.parfer) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    return true;
                } else {
                    System.out.println("Erreur syntaxique dans une instruction d'écriture: ) attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction d'écriture: ( attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une instruction d'écriture: mot-clé ECRIRE attendu");
            return false;
        }
    }

    /**
     * Règle de production de l'écriture d'une expression
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean ECR_EXP() {
        if (EXP()) {
            Generateur.GENCODE_ECR_EXP_EXP();
            return true;
        } else if (UNILEX == T_UNILEX.ch) {
            Generateur.GENCODE_ECR_EXP_CH();
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else {
//            System.out.println("Erreur syntaxique dans une instruction d'écriture d'expression: chaine attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une expression
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean EXP() {
        return TERME() && SUITE_TERME();
    }

    /**
     * Règle de prodution de la suite d'un terme
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean SUITE_TERME() {
        if (OP_BIN()) {
            if (EXP()) {
                Generateur.GENCODE_TERME_OP_BIN_EXP();
                return true;
            } else {
                System.out.println("Erreur syntaxique dans une instruction de terme: terme après opérateur manquant");
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Règle de production d'un terme
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean TERME() {
        if (UNILEX == T_UNILEX.ent) {
            Generateur.GENCODE_TERME_ENT();
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.ident) {
            int index;
            if ((index = Identificateur.CHERCHER(AnalyseurLexical.CHAINE)) < 0) {
                System.out.println("Erreur sémantique dans une instruction de terme: identificateur non déclaré");
                return false;
            } else {
                if (Identificateur.TABLE_IDENT_ARRAY.get(index).getType() == T_IDENT.constante) {
                    if ((int) Identificateur.TABLE_IDENT_ARRAY.get(index).getProperties().get("typec") != 0) {
                        System.out.println("Erreur sémantique dans une instruction de terme: constante de type entier attendu");
                        return false;
                    }
                }
                Generateur.GENCODE_TERME_IDENT();
                UNILEX = AnalyseurLexical.ANALEX();
                return true;
            }
        } else if (UNILEX == T_UNILEX.parouv) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (EXP()) {
                if (UNILEX == T_UNILEX.parfer) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    return true;
                } else {
                    System.out.println("Erreur syntaxique dans une instruction de terme: ) attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction de terme: expression attendue");
                return false;
            }
        } else if (UNILEX == T_UNILEX.moins) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (TERME()) {
                Generateur.GENCODE_TERME_MOINS();
                return true;
            } else {
                System.out.println("Erreur syntaxique dans une instruction de terme: terme attendu");
                return false;
            }
        } else {
//            System.out.println("Erreur syntaxique dans une instruction de terme: entier ou identificateur ou ( ou - attendu");
            return false;
        }
    }

    /**
     * Règle de production d'un opérateur
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean OP_BIN() {
        if (UNILEX == T_UNILEX.plus) {
            Generateur.GENCODE_OP_BIN(Generateur.ADDI);
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.moins || UNILEX == T_UNILEX.diff) {
            Generateur.GENCODE_OP_BIN(Generateur.SOUS);
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.mult) {
            Generateur.GENCODE_OP_BIN(Generateur.MULT);
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.divi || UNILEX == T_UNILEX.sup || UNILEX == T_UNILEX.supe) {
            Generateur.GENCODE_OP_BIN(Generateur.DIV);
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else {
//            System.out.println("Erreur syntaxique dans une instruction d'opérateur: + ou - ou * ou / attendu");
            return false;
        }
    }
}
