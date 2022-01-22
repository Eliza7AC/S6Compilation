import java.util.HashMap;

public class Identificateur {

    /**
     * clé: mot clé MiniPasc
     * valeur: fonction Java
     */
    HashMap<String, Runnable> identificateurs = new HashMap<>();

    Identificateur(){
        this.identificateurs.put("ECRIRE(\"BONJOUR\")", () -> System.out.println("BONJOUR"));
    }


    public boolean showIdentificateurs(){
        for (String key: identificateurs.keySet()) {
            String value = identificateurs.get(key).toString();
//            System.out.println(key + " " + value);
        }
        return false;
    }

    public void execute(String word){
        identificateurs.get(word).run();
    }



    public HashMap<String, Runnable> getIdentificateurs() {
        return identificateurs;
    }
}
