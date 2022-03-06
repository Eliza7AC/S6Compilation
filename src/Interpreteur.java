import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Interpreteur {
    private static final int[] MEMVAR = new int[500];
    public static final Stack<Integer> PILOP = new Stack<>();
    private static final Stack<Integer> PILEX = new Stack<>();
    public static final int[] P_CODE = new int[1000];
    public static int CO = 0;

    /**
     * Créer le fichier du code compilé
     * @param initialName nom du fichier code source
     */
    public static void CREER_FICHIER_CODE(String initialName) {
        File compiledCode = new File("out/production/compiledCode/"+initialName+".COD");
        try {
            ArrayList<Integer> alle = new ArrayList<>();
            ArrayList<Integer> alsn = new ArrayList<>();
            FileWriter fileWriter = new FileWriter(compiledCode, false);
            fileWriter.write(Identificateur.TABLE_IDENT_ARRAY.size() + " mot(s) réservé(s) pour les variables globales\n");
            for (int index = 0; index < P_CODE.length; index++) {
                if (P_CODE[index] == Generateur.ALLE) {
                    alle.add(P_CODE[index+1]);
                } else if (P_CODE[index] == Generateur.ALSN) {
                    alsn.add(P_CODE[index+1]);
                }
            }
            Collections.sort(alle);
            Collections.sort(alsn);
            for (int localCO = 0; localCO < P_CODE.length; localCO++) {
                if (alle.size() != 0) {
                    if (alle.get(0) == localCO) {
                        fileWriter.write((char) alle.get(0).intValue() + ": ");
                        alle.remove(0);
                    }
                }
                if (alsn.size() != 0) {
                    if (alsn.get(0) == localCO) {
                        fileWriter.write( (char) alsn.get(0).intValue() + ": ");
                        alsn.remove(0);
                    }
                }
                if (P_CODE[localCO] == Generateur.STOP) {
                    fileWriter.write(Generateur.getCodeName(Generateur.STOP));
                    break;
                }
                if (P_CODE[localCO] == Generateur.ECRC) {
                    StringBuilder line = new StringBuilder();
                    localCO++;
                    while (P_CODE[localCO] != Generateur.FINC) {
                        line.append('\'').append((char) P_CODE[localCO]).append('\'');
                        localCO++;
                    }
                    fileWriter.write(Generateur.getCodeName(Generateur.ECRC) + " " + line + " " + Generateur.getCodeName(Generateur.FINC) + "\n");
                } else if (P_CODE[localCO] == Generateur.EMPI) {
                    localCO++;
                    fileWriter.write(Generateur.getCodeName(Generateur.EMPI)+ " " + P_CODE[localCO] + "\n");
                } else if (P_CODE[localCO] == Generateur.ALLE) {
                    localCO ++;
                    fileWriter.write(Generateur.getCodeName(Generateur.ALLE) + " " + (char) P_CODE[localCO] + "\n");
                } else if (P_CODE[localCO] == Generateur.ALSN) {
                    localCO ++;
                    fileWriter.write(Generateur.getCodeName(Generateur.ALSN) + " " + (char) P_CODE[localCO] + "\n");
                } else {
                    fileWriter.write(Generateur.getCodeName(P_CODE[localCO])+"\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interprète le code compilé
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
            }
        }
    }

    /*************************************************
     * Instructions de la machine virtuelle
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
            System.exit(-1);
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
     * Instruction de branchement conditionnel
     */
    private static void ALSN() {
        if (PILEX.pop() == 0) {
            CO++;
            CO = P_CODE[CO];
        } else {
            CO +=2;
        }
    }
}
