import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyseurSemantique {
    public static int DERNIERE_ADRESSE_VAR_GLOB;

    public static boolean DEFINIR_CONSTANTE(String nom, T_UNILEX ul) {
        if (Identificateur.CHERCHER(nom) > 0) {
            return false;
        }
        Map<String, Object> properties = new HashMap<>();
        if (ul == T_UNILEX.ent) {
            properties.put("typec", 0);
            properties.put("val", AnalyseurLexical.NOMBRE);
            Identificateur.INSERER(nom, T_IDENT.constante, properties);

        } else if (ul == T_UNILEX.ch) {
            properties.put("typec", 1);
            properties.put("val", AnalyseurLexical.CHAINE);
            Identificateur.INSERER(nom, T_IDENT.constante, properties);
        }
        return true;
    }

    public static boolean DEFINIR_VARIABLE(String nom) {
        if (Identificateur.CHERCHER(nom) > 0) {
            return false;
        }
        Map<String, Object> properties = new HashMap<>();
        properties.put("typec", 0);
        DERNIERE_ADRESSE_VAR_GLOB++;
        properties.put("val", DERNIERE_ADRESSE_VAR_GLOB);
        Identificateur.INSERER(nom, T_IDENT.variable, properties);
        return true;
    }



}
