import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyseur {

    List<String> words = new ArrayList<String>();

    Analyseur(File file) throws FileNotFoundException {

        Scanner sc = new Scanner(file);

        /**
         * scan du programme source mot par mot
         */
        while (sc.hasNextLine()){
            String line = sc.nextLine();

//            String[] wordsArray = line.split("\s");
//            for (int i = 0; i < wordsArray.length; i++) {
//                if (!wordsArray[i].equals("")){ // n'ajoute pas les espaces
//                    words.add(wordsArray[i]);
//                }
//            }

            for (Character c : line.toCharArray()){
                System.out.println("char " + c + " isMereSymbol = " + isMereSymbol(c));
            }
        }

    }

    public boolean isMereSymbol(Character c){
        return c == ',' || c == ';' || c == '.' || c== ':' ||
                c == '(' || c == ')' || c == '<' || c == '>' ||
                c == '+' || c == '-' || c == '*' || c == '/';
    }

    public boolean isComposedSymbol(String s){
        return s == "<=" || s == ">=" || s == "<>" || s == ":=";
    }

    public boolean isSpace(Character c){
        return c==' ';
    }



    /** todo 3 cas ouvrants/fermants: () <> {}
     * todo paramètres => adresse des variables enregistrés
     * todo reconnaître les nombres
     */



    public List<String> getWords() {
        return words;
    }

}
