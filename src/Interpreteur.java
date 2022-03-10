import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Interpreteur {
    private static final int[] MEMVAR = new int[100]; // Zone mémoire des variable/constantes globales
    private static final Stack<Integer> PILEX = new Stack<>(); // Zone mémoire de la pile d'exécution
    public static final Stack<Integer> PILOP = new Stack<>(); // Zone mémoire de la pile d'opérateur
    public static final int[] P_CODE = new int[5000]; // Zone mémoire du code machine
    private static int CO = 0; // Pointeur de la zone mémoire du code machine

    /**
     * Créer le fichier du code compilé
     * @param initialName nom du fichier code source
     */
    public static void CREER_FICHIER_CODE(String initialName) {
        File compiledCode = new File("out/production/compiledCode/"+initialName+".COD");
        try {
            ArrayList<Integer> al = new ArrayList<>();
            FileWriter fileWriter = new FileWriter(compiledCode, false);
            fileWriter.write(Identificateur.TABLE_IDENT_ARRAY.size() + " mot(s) réservé(s) pour les variables globales\n");
            for (int index = 0; index < P_CODE.length; index++) {
                if (P_CODE[index] == Generateur.ALLE || P_CODE[index] == Generateur.ALSN || P_CODE[index] == Generateur.ALSDIFF
                        || P_CODE[index] == Generateur.ALSSUP || P_CODE[index] == Generateur.ALSSUPE
                        || P_CODE[index] == Generateur.ALSINF || P_CODE[index] == Generateur.ALSINFE
                ) {
                    al.add(P_CODE[index+1]);
                } else if (P_CODE[index] == Generateur.STOP) {
                    break;
                }
            }
            Collections.sort(al);
            for (int localCO = 0; localCO < P_CODE.length; localCO++) {
                if (al.size() != 0) {
                    if (al.get(0) == localCO) {
                        fileWriter.write((char) al.get(0).intValue() + ": ");
                        al.remove(0);
                    }
                }
                if (P_CODE[localCO] == Generateur.STOP) {
                    fileWriter.write(getCodeName(Generateur.STOP));
                    break;
                }
                if (P_CODE[localCO] == Generateur.ECRC) {
                    StringBuilder line = new StringBuilder();
                    localCO++;
                    while (P_CODE[localCO] != Generateur.FINC) {
                        line.append('\'').append((char) P_CODE[localCO]).append('\'');
                        localCO++;
                    }
                    fileWriter.write(getCodeName(Generateur.ECRC) + " " + line + " " + getCodeName(Generateur.FINC) + "\n");
                } else if (P_CODE[localCO] == Generateur.ALLE || P_CODE[localCO] == Generateur.ALSN || P_CODE[localCO] == Generateur.ALSDIFF
                        || P_CODE[localCO] == Generateur.ALSSUP || P_CODE[localCO] == Generateur.ALSSUPE
                        || P_CODE[localCO] == Generateur.ALSINF || P_CODE[localCO] == Generateur.ALSINFE
                ) {
                    localCO++;
                    fileWriter.write(getCodeName(P_CODE[localCO-1])+ " " + (char) P_CODE[localCO] + "\n");
                } else if (P_CODE[localCO] == Generateur.EMPI) {
                    localCO++;
                    fileWriter.write(getCodeName(Generateur.EMPI)+ " " + P_CODE[localCO] + "\n");
                } else {
                    fileWriter.write(getCodeName(P_CODE[localCO])+"\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interprète le code machine
     */
    public static void INTERPRETER() {
        while (P_CODE[CO] != Generateur.STOP) {
            switch (P_CODE[CO]) {
                case Generateur.ADDI:
                    ADDI();
                    break;
                case Generateur.SOUS:
                    SOUS();
                    break;
                case Generateur.MULT:
                    MULT();
                    break;
                case Generateur.DIV:
                    DIV();
                    break;
                case Generateur.MOIN:
                    MOIN();
                    break;
                case Generateur.AFFE:
                    AFFE();
                    break;
                case Generateur.LIRE:
                    LIRE();
                    break;
                case Generateur.ECRL:
                    ECRL();
                    break;
                case Generateur.ECRE:
                    ECRE();
                    break;
                case Generateur.ECRC:
                    ECRC();
                    break;
                case Generateur.EMPI:
                    EMPI();
                    break;
                case Generateur.CONT:
                    CONT();
                    break;
                case Generateur.ALLE:
                    ALLE();
                    break;
                case Generateur.ALSN:
                    ALSN();
                    break;
                case Generateur.ALSDIFF:
                    ALSDIFF();
                    break;
                case Generateur.ALSSUP:
                    ALSSUP();
                    break;
                case Generateur.ALSSUPE:
                    ALSSUPE();
                    break;
                case Generateur.ALSINF:
                    ALSINF();
                    break;
                case Generateur.ALSINFE:
                    ALSINFE();
                    break;
            }
        }
    }


    /*************************************************
     * Instructions de génération de code
     *************************************************/

    /**
     * Instruction arithmétique d'addition
     */
    private static void ADDI() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt + firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de soustraction
     */
    private static void SOUS() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt - firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de multiplication
     */
    private static void MULT() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt * firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de division
     */
    private static void DIV() {
        int firstInt = PILEX.pop();
        if (firstInt == 0) {
            System.out.println("Erreur d'exécution: division par 0");
            System.exit(1);
        } else {
            int secondInt = PILEX.pop();
            PILEX.push(secondInt / firstInt);
            CO++;
        }
    }

    /**
     * Instruction arithmétique de négation
     */
    private static void MOIN() {
        int nb = PILEX.pop();
        PILEX.push(-nb);
        CO++;
    }

    /**
     * Instruction d'affectation
     */
    private static void AFFE() {
        int nb = PILEX.pop();
        int adr = PILEX.pop();
        MEMVAR[adr] = nb;
        CO++;
    }

    /**
     * Instruction de lecture
     */
    private static void LIRE() {
        Scanner sc = new Scanner(System.in);
        int adr = PILEX.pop();
        MEMVAR[adr] = sc.nextInt();
        CO++;
    }

    /**
     * Instruction d'écriture de saut de ligne
     */
    private static void ECRL() {
        System.out.println();
        CO++;
    }

    /**
     * Instruction d'écriture d'un entier
     */
    private static void ECRE() {
        System.out.print(PILEX.pop());
        CO++;
    }

    /**
     * Instruction d'écriture d'une chaîne
     */
    private static void ECRC() {
        int i = 1;
        char ch = (char) P_CODE[CO + i];
        while (ch != Generateur.FINC) {
            System.out.print(ch);
            i++;
            ch = (char) P_CODE[CO + i];
        }
        CO = CO + i + 1;
    }

    /**
     * Instruction d'empilement sur la pile d'exécution
     */
    private static void EMPI() {
        PILEX.push(P_CODE[CO + 1]);
        CO += 2;
    }

    /**
     * Instruction d'accès au contenu d'une adresse
     */
    private static void CONT() {
        int adr = PILEX.pop();
        PILEX.push(MEMVAR[adr]);
        CO++;
    }

    /**
     * Instruction d'arrêt
     */
    private static void STOP() {}

    /**
     * Instruction de branchement inconditionnel
     */
    private static void ALLE() {
        CO++;
        CO = P_CODE[CO];
    }

    /**
     * Instruction de branchement conditionnel si nul (si égal)
     */
    private static void ALSN() {
        if (PILEX.pop() == 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Instruction de branchement conditionnel si différent
     */
    private static void ALSDIFF() {
        if (PILEX.pop() != 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Instruction de branchement conditionnel si supérieur
     */
    private static void ALSSUP() {
        if (PILEX.pop() > 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Instruction de branchement conditionnel si supérieur ou égal
     */
    private static void ALSSUPE() {
        if (PILEX.pop() >= 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Instruction de branchement conditionnel si inférieur
     */
    private static void ALSINF() {
        if (PILEX.pop() < 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Instruction de branchement conditionnel si inférieur ou égal
     */
    private static void ALSINFE() {
        if (PILEX.pop() <= 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }

    /**
     * Obtenir le nom de la fonction du code machine à partir de son code
     * @param code code de la fonction du code machine
     * @return nom de la fonction du code machine
     */
    private static String getCodeName(int code) {
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
            case Generateur.ALSDIFF:
                return "ALSDIFF";
            case Generateur.ALSSUP:
                return "ALSSUP";
            case Generateur.ALSSUPE:
                return "ALSSUPE";
            case Generateur.ALSINF:
                return "ALSINF";
            case Generateur.ALSINFE:
                return "ALSINFE";
            default:
                return "";
        }
    }
}
