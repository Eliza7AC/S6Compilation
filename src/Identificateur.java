import java.util.*;

public class Identificateur {
    public static final List<Identificateur> TABLE_IDENT_ARRAY = new ArrayList<>();

    private final String nom;
    private final T_IDENT type;
    private final Map<String, Object> properties = new HashMap<>();

    public Identificateur(String nom, T_IDENT type) {
        this.nom = nom;
        this.type = type;
    }

    /**
     * Cherche dans la table des identificateurs si un identificateur existe
     * @param id Nom de l'identificateur
     * @return L'indice de l'identificateur dans la table
     */
    public static int CHERCHER(String id) {
        return Collections.binarySearch(TABLE_IDENT_ARRAY, new Identificateur(id, null), Comparator.comparing(Identificateur::getNom));
    }

    /**
     * Insère un identificateur dans la table des identificateurs
     * @param nom Nom de l'identificateur
     * @param type Type T_IDENT de l'identificateur
     * @return L'indice de l'identificateur dans la table
     */
    public static int INSERER(String nom, T_IDENT type) {
        Identificateur id = new Identificateur(nom, type);
        TABLE_IDENT_ARRAY.add(id);
        TABLE_IDENT_ARRAY.sort(Comparator.comparing(Identificateur::getNom));
        return Identificateur.CHERCHER(nom);
    }

    /**
     * Affiche la table des identificateurs
     */
    public static void AFFICHE_TABLE_IDENT() {
        for (Identificateur id : TABLE_IDENT_ARRAY) {
            System.out.println("Nom: "+id.nom+"\n"+"Type: "+id.type);
            System.out.println("Propriétés: ");
            for (String key : id.properties.keySet()) {
                System.out.println(key+ " "+id.properties.get(key));
            }
            System.out.println();
        }
    }

    private String getNom() {
        return nom;
    }
}
