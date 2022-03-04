import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

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
            FileWriter fileWriter = new FileWriter(compiledCode, false);
            fileWriter.write(Identificateur.TABLE_IDENT_ARRAY.size() + " mot(s) réservé(s) pour les variables globales\n");
            for (int localCO = 0; localCO < P_CODE.length; localCO++) {
                if (P_CODE[localCO] == Generateur.STOP) {
                    fileWriter.write(Generateur.getCodeName(Generateur.STOP));
                    break;
                }
                if (P_CODE[localCO] == Generateur.ECRC) {
                    StringBuilder line = new StringBuilder();
                    localCO++;
                    while (P_CODE[localCO] != Generateur.FINC) {
                        line.append((char) P_CODE[localCO]);
                        localCO++;
                    }
                    fileWriter.write(Generateur.getCodeName(Generateur.ECRC) + " " + line + " " + Generateur.getCodeName(Generateur.FINC) +"\n");
                } else if (P_CODE[localCO] == Generateur.EMPI) {
                    localCO++;
                    fileWriter.write(Generateur.getCodeName(Generateur.EMPI)+ " " + P_CODE[localCO] +"\n");
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
            }
        }
    }

    /*************************************************
     * Instructions associées à P_CODE
     *************************************************/

    /**
     * Instruction arithmétique d'addition
     */
    public static void ADDI() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt + firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de soustraction
     */
    public static void SOUS() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt - firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de multiplication
     */
    public static void MULT() {
        int firstInt = PILEX.pop();
        int secondInt = PILEX.pop();
        PILEX.push(secondInt * firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de division
     */
    public static void DIV() {
        int firstInt = PILEX.pop();
        if (firstInt == 0) {
            System.out.println("Erreur d'exécution: division par 0");
            System.exit(-1);
        }
        int secondInt = PILEX.pop();
        PILEX.push(secondInt / firstInt);
        CO++;
    }

    /**
     * Instruction arithmétique de négation
     */
    public static void MOIN() {
        int nb = PILEX.pop();
        PILEX.push(-nb);
        CO++;
    }

    /**
     * Instruction d'affectation
     */
    public static void AFFE() {
        int nb = PILEX.pop();
        int adr = PILEX.pop();
        MEMVAR[adr] = nb;
        CO++;
    }

    /**
     * Instruction de lecture
     */
    public static void LIRE() {
        Scanner sc = new Scanner(System.in);
        int adr = PILEX.pop();
        MEMVAR[adr] = sc.nextInt();
        CO++;
    }

    /**
     * Instruction d'écriture de saut de ligne
     */
    public static void ECRL() {
        System.out.println();
        CO++;
    }

    /**
     * Instruction d'écriture d'un entier
     */
    public static void ECRE() {
        System.out.print(PILEX.pop());
        CO++;
    }

    /**
     * Instruction d'écriture d'une chaîne
     */
    public static void ECRC() {
        int i = 1;
        char ch = (char) P_CODE[CO + i];
        StringBuilder line = new StringBuilder();
        while (ch != Generateur.FINC) {
            line.append(ch);
            i++;
            ch = (char) P_CODE[CO + i];
        }
        System.out.println(line);
        CO = CO + i + 1;
    }

    /**
     * Instruction d'empilement sur la pile d'exécution
     */
    public static void EMPI() {
        PILEX.push(P_CODE[CO + 1]);
        CO += 2;
    }

    /**
     * Instruction d'accès au contenu d'une adresse
     */
    public static void CONT() {
        int adr = PILEX.pop();
        PILEX.push(MEMVAR[adr]);
        CO++;
    }

    /**
     * Instruction d'arrêt
     */
    public static void STOP() {}
}
