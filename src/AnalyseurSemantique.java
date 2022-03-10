import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyseurSemantique {
    private static int DERNIERE_ADRESSE_VAR_GLOB = -1;

    /**
     * Définition d'une constante
     * @param nom nom de la constante
     * @param ul unité lexicale associée
     * @return true si la constante a bien été ajouté à la table, false si elle existe déjà
     */
    public static boolean DEFINIR_CONSTANTE(String nom, T_UNILEX ul) {
        Map<String, Object> properties = new HashMap<>();
        if (ul == T_UNILEX.ent) {
            properties.put("typec", 0);
            properties.put("val", AnalyseurLexical.NOMBRE);
            return Identificateur.INSERER(nom, T_IDENT.constante, properties) != -1;
        } else if (ul == T_UNILEX.ch) {
            properties.put("typec", 1);
            properties.put("val", AnalyseurLexical.CHAINE);
            return Identificateur.INSERER(nom, T_IDENT.constante, properties) != -1;
        } else {
            return false;
        }
    }

    /**
     * Définition d'une variable
     * @param nom nom de la variable
     * @return true si la variable a bien été ajouté à la table, false si elle existe déjà
     */
    public static boolean DEFINIR_VARIABLE(String nom) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("typec", 0);
        DERNIERE_ADRESSE_VAR_GLOB++;
        properties.put("adrv", DERNIERE_ADRESSE_VAR_GLOB);
        return Identificateur.INSERER(nom, T_IDENT.variable, properties) != -1;
    }
}
