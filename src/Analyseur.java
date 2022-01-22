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

            String[] wordsArray = line.split("\s");

            for (int i = 0; i < wordsArray.length; i++) {
                if (!wordsArray[i].equals("")){ // n'ajoute pas les espaces
                    words.add(wordsArray[i]);
                }
            }
        }

    }

    public List<String> getWords() {
        return words;
    }

}
