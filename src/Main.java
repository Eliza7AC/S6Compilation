import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // chargement du programme (fichier texte)
        Analyseur analyseur = new Analyseur(new File("src/JeuxEssais/test"));



//        System.out.println(" ############################ ");
//        System.out.println("mots du programme: ");
//        System.out.println(analyseur.getWords().toString());
//        System.out.println(" ############################ ");
//
//        Identificateur identificateurs = new Identificateur();
//
//
//        Compilateur compilateur = new Compilateur(analyseur,identificateurs);
//        compilateur.interpretation();
//
//
//        System.out.println(" ");
//        Analyseur an = new Analyseur(new File("src/JeuxEssais/simplesSymboles"));






//        Scanner sc = new Scanner(new File("src/JeuxEssais/test"));
//
//        while (sc.hasNextLine()){
//            String strLine = sc.nextLine();
//            char[] charLine = strLine.toCharArray();
//
//            for (int i = 0; i < charLine.length; i++) {
//                System.out.println(charLine[i]);
//            }
//        }

//        System.out.println();
//        char a = '9';
//        System.out.println(Character.getNumericValue(a));
//
//        char[] arrayOfNb = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
//
//        for (Character nb : arrayOfNb){
//            if (nb == a){
//                System.out.println("TRUE");
//            }
//
//        }
//        System.out.println("FIN");


    }

}
