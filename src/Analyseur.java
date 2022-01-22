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

        while (sc.hasNextLine()){
            String line = sc.nextLine();

            String[] wordsArray = line.split("\s");
            List wordsToAdd = Arrays.asList(wordsArray);
            this.words = (List<String>) Stream.concat(this.words.stream(), wordsToAdd.stream())
                    .collect(Collectors.toList());
        }

    }

    public List<String> getWords() {
        return words;
    }

}
