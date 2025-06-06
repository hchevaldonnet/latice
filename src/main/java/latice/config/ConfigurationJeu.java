package latice.config;

public final class ConfigurationJeu {
    // Constantes du jeu
    public static final int NOMBRE_JOUEURS = 2;
    public static final int NOMBRE_MAX_TOURS = 10;
    public static final int OPTION_PLACER_TUILE = 1;
    public static final int OPTION_PIOCHER_MAIN = 2;
    public static final int OPTION_PASSER_TOUR = 3;
    public static final int OPTION_AFFICHER_REGLES = 4;
    public static final int OPTION_TOUR_SUPPLEMENTAIRE = 5;
    public static final int OPTION_QUITTER = 6;
    public static final int COUT_NOUVELLE_MAIN = 1;
    public static final int COUT_TOUR_SUPPLEMENTAIRE = 2;
    public static final int MAX_POINTS_PAR_TOUR = 3;
    public static final float POINTS_DOUBLE = 0.5f;
    public static final float POINTS_TREFOIL = 1.0f;
    public static final float POINTS_LATICE = 2.0f;
    public static final int POINTS_BONUS_SOLEIL = 1;
    public static final int PREMIER_JOUEUR_INDEX = 0;
    public static final int SECOND_JOUEUR_INDEX = 1;
    public static final int DOUBLE_CORRESPONDANCES = 2;
    public static final int TREFOIL_CORRESPONDANCES = 3;
    public static final int LATICE_CORRESPONDANCES = 4;
    
    // Constructeur privé pour empêcher l'instanciation
    private ConfigurationJeu() {
        throw new AssertionError("Cette classe ne doit pas être instanciée");
    }
}


