import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyseurSemantique {
    private static final ArrayList<String> VAL_DE_CONST_CHAINE = new ArrayList<>();
    private static int NB_CONST_CHAINE = 0;

    public static boolean DEFINIR_CONSTANTE(String nom, T_UNILEX ul) {
        if (Identificateur.CHERCHER(AnalyseurLexical.CHAINE) > 0) {
            System.out.println("ERREUR: identificateur déjà déclaré");
            return false;
        }
        else if (AnalyseurSyntaxique.UNILEX == T_UNILEX.ent) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("typec", 0);
            properties.put("val", AnalyseurLexical.CHAINE);
            Identificateur.INSERER(AnalyseurLexical.CHAINE, T_IDENT.constante, properties);
            return true;
        } else if (AnalyseurSyntaxique.UNILEX == T_UNILEX.ch) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("typec", 1);
            Identificateur.INSERER(AnalyseurLexical.CHAINE, T_IDENT.constante, properties);
            VAL_DE_CONST_CHAINE.add(NB_CONST_CHAINE, AnalyseurLexical.CHAINE);
        }
        return false;
    }





}
