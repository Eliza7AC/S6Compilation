public class AnalyseurSyntaxique {
    private static T_UNILEX UNILEX;

    /**
     * Noyau de l'Analyseur Syntaxique
     */
    public static void ANASYNT() {
        UNILEX = AnalyseurLexical.ANALEX();
        if (PROG()) {
            System.out.println("Le programme source est syntaxiquement correct");
        } else {
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
        System.out.println("PROG");
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
                            return true;
                        } else {
                            System.out.println("Erreur syntaxique dans une instruction de programme: . attendu");
                            return false;
                        }
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction de programme: erreur dans une instruction");
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
            System.out.println("Erreur syntaxique dans une instruction de programme: mot-clé PROGRAMME attendu");
            return false;
        }
    }


    /**
     * Règle de production d'une constante
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean DECL_CONST() {
        System.out.println("DECL_CONST");
        boolean fin, erreurEnt, erreurEg, erreurIdent;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("CONST")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.ident) {
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.eg) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    if (UNILEX == T_UNILEX.ent) {
                        fin = false;
                        erreurEnt = false;
                        erreurEg = false;
                        erreurIdent = false;
                        UNILEX = AnalyseurLexical.ANALEX();
                        do {
                            if (UNILEX == T_UNILEX.virg) {
                                UNILEX = AnalyseurLexical.ANALEX();
                                if (UNILEX == T_UNILEX.ident) {
                                    UNILEX = AnalyseurLexical.ANALEX();
                                    if (UNILEX == T_UNILEX.eg) {
                                        UNILEX = AnalyseurLexical.ANALEX();
                                        if (UNILEX == T_UNILEX.ent) {
                                            UNILEX = AnalyseurLexical.ANALEX();
                                        } else {
                                            fin = true;
                                            erreurEnt = true;
                                        }
                                    } else {
                                        fin = true;
                                        erreurEg = true;
                                    }
                                } else {
                                    fin = true;
                                    erreurIdent = true;
                                }
                            } else {
                                fin = true;
                            }
                        } while (!fin);
                        if (erreurEnt) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: entier attendu");
                            return false;
                        } else if (erreurEg) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: = attendu");
                            return false;
                        } else if (erreurIdent) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: identificateur attendu");
                            return false;
                        } else if (UNILEX == T_UNILEX.ptvirg) {
                            UNILEX = AnalyseurLexical.ANALEX();
                            return true;
                        } else {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: ; attendu");
                            return false;
                        }
                    } else if (UNILEX == T_UNILEX.ch) {
                        fin = false;
                        erreurEnt = false;
                        erreurEg = false;
                        erreurIdent = false;
                        UNILEX = AnalyseurLexical.ANALEX();
                        do {
                            if (UNILEX == T_UNILEX.virg) {
                                UNILEX = AnalyseurLexical.ANALEX();
                                if (UNILEX == T_UNILEX.ident) {
                                    UNILEX = AnalyseurLexical.ANALEX();
                                    if (UNILEX == T_UNILEX.eg) {
                                        UNILEX = AnalyseurLexical.ANALEX();
                                        if (UNILEX == T_UNILEX.ch) {
                                            UNILEX = AnalyseurLexical.ANALEX();
                                        } else {
                                            fin = true;
                                            erreurEnt = true;
                                        }
                                    } else {
                                        fin = true;
                                        erreurEg = true;
                                    }
                                } else {
                                    fin = true;
                                    erreurIdent = true;
                                }
                            } else {
                                fin = true;
                            }
                        } while (!fin);
                        if (erreurEnt) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: chaine attendu");
                            return false;
                        } else if (erreurEg) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: = attendu");
                            return false;
                        } else if (erreurIdent) {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: identificateur attendu");
                            return false;
                        } else if (UNILEX == T_UNILEX.ptvirg) {
                            UNILEX = AnalyseurLexical.ANALEX();
                            return true;
                        } else {
                            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: ; attendu");
                            return false;
                        }
                    } else {
                        System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: entier ou chaine attendu");
                        return false;
                    }
                } else {
                    System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: = attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: identificateur attendu");
                return false;
            }
        } else {
            System.out.println("Erreur syntaxique dans une instruction de déclaration de constante: mot-clé CONST attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une variable
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean DECL_VAR() {
        System.out.println("DECL_VAR");
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("VAR")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.ident) {
                fin = false;
                erreur = false;
                UNILEX = AnalyseurLexical.ANALEX();
                do {
                    if (UNILEX == T_UNILEX.virg) {
                        UNILEX = AnalyseurLexical.ANALEX();
                        if (UNILEX == T_UNILEX.ident) {
                            UNILEX = AnalyseurLexical.ANALEX();
                        } else {
                            fin = true;
                            erreur = true;
                        }
                    } else {
                        fin = true;
                    }
                } while (!fin);
                if (erreur) {
                    System.out.println("Erreur syntaxique dans une instruction de déclaration de variable: identificateur attendu");
                    return false;
                } else if (UNILEX == T_UNILEX.ptvirg) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    return true;
                } else {
                    System.out.println("Erreur syntaxique dans une instruction de déclaration de variable: ; attendu");
                    return false;
                }
            } else {
                System.out.println("Erreur syntaxique dans une instruction de déclaration de variable: identificateur attendu");
                return false;
            }
        } else {
            System.out.println("Erreur syntaxique dans une instruction de déclaration de variable: mot-clé VAR attendu");
            return false;
        }
    }

    /**
     * Règle de production d'un bloc
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean BLOC() {
        System.out.println("BLOC");
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
            System.out.println("Erreur syntaxique dans un bloc d'instruction: mot-clé DEBUT attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une instruction
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean INSTRUCTION() {
        System.out.println("INSTRUCTION");
        return AFFECTATION() || LECTURE() || ECRITURE() || BLOC();
    }

    /**
     * Règle de production d'une affectation
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean AFFECTATION() {
        System.out.println("AFFECTATION");
        if (UNILEX == T_UNILEX.ident) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.aff) {
                UNILEX = AnalyseurLexical.ANALEX();
                return EXP();
            } else {
                System.out.println("Erreur syntaxique dans une instruction d'affectation: := attendu");
                return false;
            }
        } else {
            System.out.println("Erreur syntaxique dans une instruction d'affectation: identificateur attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une lecture
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean LECTURE() {
        System.out.println("LECTURE");
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("LIRE")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.parouv) {
                UNILEX = AnalyseurLexical.ANALEX();
                if (UNILEX == T_UNILEX.ident) {
                    UNILEX = AnalyseurLexical.ANALEX();
                    fin = false;
                    erreur = false;
                    do {
                        if (UNILEX == T_UNILEX.virg) {
                            UNILEX = AnalyseurLexical.ANALEX();
                            if (UNILEX == T_UNILEX.ident) {
                                UNILEX = AnalyseurLexical.ANALEX();
                            } else {
                                fin = true;
                                erreur = true;
                            }
                        } else {
                            fin = true;
                        }
                    } while (!fin);
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
            System.out.println("Erreur syntaxique dans une instruction de lecture: mot-clé LIRE attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une écriture
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean ECRITURE() {
        System.out.println("ECRITURE");
        boolean fin, erreur;
        if (UNILEX == T_UNILEX.motcle && AnalyseurLexical.CHAINE.equals("ECRIRE")) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (UNILEX == T_UNILEX.parouv) {
                UNILEX = AnalyseurLexical.ANALEX();
                erreur = false;
                if (ECR_EXP()) {
                    UNILEX = AnalyseurLexical.ANALEX();
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
            System.out.println("Erreur syntaxique dans une instruction d'écriture: mot-clé ECRIRE attendu");
            return false;
        }
    }

    /**
     * Règle de production de l'écriture d'une expression
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean ECR_EXP() {
        System.out.println("ECR_EXP");
        if (EXP()) {
            return true;
        } else if (UNILEX == T_UNILEX.ch) {
            return true;
        } else {
            System.out.println("Erreur syntaxique dans une instruction d'écriture d'expression: chaine attendu");
            return false;
        }
    }

    /**
     * Règle de production d'une expression
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean EXP() {
        System.out.println("EXP");
        return TERME() && SUITE_TERME();
    }

    /**
     * Règle de prodution de la suite d'un terme
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean SUITE_TERME() {
        System.out.println("SUITE_TERME");
        return !OP_BIN() || EXP();
    }

    /**
     * Règle de production d'un terme
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean TERME() {
        System.out.println("TERME");
        if (UNILEX == T_UNILEX.ent) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.ident) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.parouv) {
            UNILEX = AnalyseurLexical.ANALEX();
            if (EXP()) {
                UNILEX = AnalyseurLexical.ANALEX();
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
                UNILEX = AnalyseurLexical.ANALEX();
                return true;
            } else {
                System.out.println("Erreur syntaxique dans une instruction de terme: terme attendu");
                return false;
            }
        } else {
            System.out.println("Erreur syntaxique dans une instruction de terme: entier ou identificateur ou ( ou - attendu");
            return false;
        }
    }

    /**
     * Règle de production d'un opérateur
     * @return true si aucune erreur syntaxique, false sinon
     */
    public static boolean OP_BIN() {
        System.out.println("OP_BIN");
        if (UNILEX == T_UNILEX.plus) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.moins) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.mult) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else if (UNILEX == T_UNILEX.divi) {
            UNILEX = AnalyseurLexical.ANALEX();
            return true;
        } else {
            System.out.println("Erreur syntaxique dans une instruction d'opérateur: + ou - ou * ou / attendu");
            return false;
        }
    }
}
