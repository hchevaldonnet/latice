package latice.application;

import java.util.List;
import java.util.Scanner;

import latice.model.ActionSpeciale;
import latice.model.Arbitre;
import latice.model.Joueur;
import latice.model.Pioche;
import latice.model.Plateau;
import latice.model.PositionCaseSoleil;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Tuile;
import latice.model.setup.PreparerJeu;
import latice.ihm.*;


public class LaticeApplicationConsole {

    // Constantes (nombres magiques)
    private static final int NOMBRE_JOUEURS = 2;
    private static final int NOMBRE_MAX_TOURS = 10;
    private static final int OPTION_PLACER_TUILE = 1;
    private static final int OPTION_PIOCHER_MAIN = 2;
    private static final int OPTION_PASSER_TOUR = 3;
    private static final int OPTION_AFFICHER_REGLES = 4;
    private static final int OPTION_TOUR_SUPPLEMENTAIRE = 5;
    private static final int OPTION_QUITTER = 6;
    private static final int COUT_NOUVELLE_MAIN = 1;
    private static final int COUT_TOUR_SUPPLEMENTAIRE = 2;
    private static final int MAX_POINTS_PAR_TOUR = 3;
    private static final float POINTS_DOUBLE = 0.5f;
    private static final float POINTS_TREFOIL = 1.0f;
    private static final float POINTS_LATICE = 2.0f;
    private static final int POINTS_BONUS_SOLEIL = 1;
    private static final int PREMIER_JOUEUR_INDEX = 0;
    private static final int SECOND_JOUEUR_INDEX = 1;
    private static final int DOUBLE_CORRESPONDANCES = 2;
    private static final int TREFOIL_CORRESPONDANCES = 3;
    private static final int LATICE_CORRESPONDANCES = 4;

    // Attributs de classe
    private static Pioche pioche;
    private static Arbitre arbitre;
    private static Plateau plateauDeJeu;
    private static Rack rackDuJoueur1, rackDuJoueur2;
    private static Joueur joueurUn, joueurDeux, joueurActuel;
    private static String couleurJoueurUn, couleurJoueurDeux;
    private static boolean premierCoup;
    private static boolean afficherIndices;
    private static boolean partieTerminee;
    private static boolean tourSpecialActif;
    private static int tourActuel;
    private static Scanner lecteur;
    
    /**
	 * Point d'entrée de l'application Latice.
	 * Initialise le jeu, gère les joueurs et boucle jusqu'à la fin de la partie.
	 */
    //TODO: Organiser le code en plusieurs classes dans diffèrents package pour une meilleure lisibilité et maintenabilité.
    public static void main(String[] args) {
        initialiserJeu();
        gererJoueurs();

        while (!partieTerminee) {
            afficherEtatJeu();
            traiterTourJoueur();
            verifierFinPartie();
        }

        lecteur.close();
    }

    /**
     * Initialise les composants du jeu comme la pioche, l'arbitre, le plateau,
     * les racks des joueurs et les drapeaux de jeu.
     */
    private static void initialiserJeu() {
        pioche = new Pioche(NOMBRE_JOUEURS);
        arbitre = new Arbitre(NOMBRE_JOUEURS);
        premierCoup = true;
        tourActuel = 1; // Initialiser à 1 pour afficher le premier tour
        partieTerminee = false;
        tourSpecialActif = false;
        lecteur = new Scanner(System.in);

        rackDuJoueur1 = new Rack(pioche);
        rackDuJoueur1.remplir(pioche, PREMIER_JOUEUR_INDEX); // Le remplissage initial ne coûte pas de points.
        rackDuJoueur2 = new Rack(pioche);
        rackDuJoueur2.remplir(pioche, SECOND_JOUEUR_INDEX); // Le remplissage initial ne coûte pas de points.

        plateauDeJeu = new Plateau();

        TexteConsole.clearScreen();
        TexteConsole.afficherTitre();

        // Demander si les joueurs veulent voir les indices des lignes et colonnes
        String reponseUtilisateur;
        do {
            System.out.println("Voulez-vous afficher les indices des lignes et colonnes? (O/N)");
            reponseUtilisateur = lecteur.nextLine().trim().toUpperCase();
        } while (!reponseUtilisateur.equals("O") && !reponseUtilisateur.equals("N"));
        afficherIndices = reponseUtilisateur.equals("O");

        TexteConsole.afficherBienvenue();
        TexteConsole.afficherMenu(); // Afficher le menu initial une fois
    }

    /**
     * Gère la saisie des noms des joueurs et détermine l'ordre de jeu aléatoirement.
     */
    private static void gererJoueurs() {
        TexteConsole.formatPlayer(TexteConsole.PLAYER1, "Joueur 1 :");
        String nomJoueur1 = SaisieConsole.saisieChar();
        TexteConsole.sautDeLigne();

        TexteConsole.formatPlayer(TexteConsole.PLAYER2, "Joueur 2 :");
        String nomJoueur2 = SaisieConsole.saisieChar();

        // Vérifier que les noms des joueurs sont différents
        while (nomJoueur2.equals(nomJoueur1)) {
            System.out.println(TexteConsole.HIGHLIGHT + "Les noms des joueurs doivent être différents. Veuillez choisir un autre nom." + TexteConsole.RESET);
            TexteConsole.formatPlayer(TexteConsole.PLAYER2, "Joueur 2 :");
            nomJoueur2 = SaisieConsole.saisieChar();
        }
        System.out.println();

        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreDesJoueurs = PreparerJeu.ordreJoueur(nomJoueur1, nomJoueur2);
        joueurUn = new Joueur(ordreDesJoueurs[0], rackDuJoueur1, pioche);
        joueurDeux = new Joueur(ordreDesJoueurs[1], rackDuJoueur2, pioche);

        // Déterminer la couleur des joueurs par rapport à leur nom
        couleurJoueurUn = (joueurUn.getName().equals(nomJoueur1)) ? TexteConsole.PLAYER1 : TexteConsole.PLAYER2;
        couleurJoueurDeux = (joueurDeux.getName().equals(nomJoueur1)) ? TexteConsole.PLAYER1 : TexteConsole.PLAYER2;

        TexteConsole.affichageJoueurs(joueurUn, joueurDeux, couleurJoueurUn, couleurJoueurDeux);

        // Initialiser le joueur courant
        joueurActuel = joueurUn;
    }

    /**
     * Affiche l'état actuel du jeu : joueur courant, numéro de tour, scores et plateau.
     */
    private static void afficherEtatJeu() {
        TexteConsole.clearScreen();

        // Afficher les informations de l'état du jeu
        String couleurJoueurActuel = (joueurActuel == joueurUn) ? couleurJoueurUn : couleurJoueurDeux;
        TexteConsole.affichageEtatJeu(joueurActuel, couleurJoueurActuel);

        // Afficher le nombre de tours
        TexteConsole.affichageTour(tourActuel, NOMBRE_MAX_TOURS);

        // Afficher les scores
        TexteConsole.affichageScore(arbitre, joueurUn, joueurDeux, couleurJoueurUn, couleurJoueurDeux);

        // Afficher le plateau
        System.out.println(TexteConsole.HIGHLIGHT + "PLATEAU DE JEU:" + TexteConsole.RESET);
        PlateauVue plateauView = new PlateauVueConsole();
        PlateauVueConsole plateauViewConsole = (PlateauVueConsole) plateauView;
        plateauViewConsole.afficherPlateau(plateauDeJeu, afficherIndices);

        System.out.println();

        // Afficher le rack du joueur courant
        if (joueurActuel.equals(joueurUn)) {
            System.out.println(rackDuJoueur1.afficherRack(couleurJoueurActuel + "C'est au tour de " + joueurUn.getName() + ", voici votre rack :" + TexteConsole.RESET));
        } else {
            System.out.println(rackDuJoueur2.afficherRack(couleurJoueurActuel + "C'est au tour de " + joueurDeux.getName() + ", voici votre rack :" + TexteConsole.RESET));
        }
        System.out.println();

        // Afficher les options du menu
        TexteConsole.afficherMenu();
        System.out.println();
    }

    /**
     * Traite le choix de l'action du joueur pour le tour en cours.
     */
    private static void traiterTourJoueur() {
        int choixMenu = SaisieConsole.saisieChoix() + 1;

        switch (choixMenu) {
            case OPTION_PLACER_TUILE:
                placerTuile();
                break;
            case OPTION_PIOCHER_MAIN:
                piocherNouvelleMain();
                break;
            case OPTION_PASSER_TOUR:
                passerTour();
                break;
            case OPTION_AFFICHER_REGLES:
                afficherRegles();
                break;
            case OPTION_TOUR_SUPPLEMENTAIRE:
                acheterTourSupplementaire();
                break;
            case OPTION_QUITTER:
                quitterPartie();
                break;
            default:
                TexteConsole.afficherErreurSaisie();
                TexteConsole.appuyerEnter();
                break;
        }
    }

    /**
     * Gère la logique de placement d'une tuile par le joueur courant.
     */
    private static void placerTuile() {
        System.out.println();
        int indexTuileSelectionnee = SaisieConsole.saisieTuiles();

        if (premierCoup) {
            System.out.println(TexteConsole.HIGHLIGHT + "Premier coup: vous devez jouer au centre (position 5,5)" + TexteConsole.RESET);
            System.out.println();
        }

        int ligneSelectionnee = SaisieConsole.saisieLigneColonne("ligne");
        int colonneSelectionnee = SaisieConsole.saisieLigneColonne("colonne");

        Rack rackActuelDuJoueur = joueurActuel.getRack();
        int indexDuJoueur = (joueurActuel == joueurUn) ? PREMIER_JOUEUR_INDEX : SECOND_JOUEUR_INDEX;

        // Vérifier si l'indice est valide
        if (indexTuileSelectionnee >= 0 && indexTuileSelectionnee < rackActuelDuJoueur.getTuiles().size()) {
            Tuile tuileSelectionnee = rackActuelDuJoueur.getTuiles().get(indexTuileSelectionnee);

            // Utiliser l'arbitre pour vérifier le coup
            int resultatVerificationCoup = arbitre.verifierCoup(ligneSelectionnee, colonneSelectionnee, tuileSelectionnee, plateauDeJeu.getCases(), premierCoup, indexDuJoueur);

            if (resultatVerificationCoup >= 0) {
                // Placer la tuile sur le plateau
                PositionTuiles positionTuile = new PositionTuiles(ligneSelectionnee, colonneSelectionnee);
                plateauDeJeu.placerTuile(tuileSelectionnee, positionTuile);
                rackActuelDuJoueur.retirerTuile(indexTuileSelectionnee);
                rackActuelDuJoueur.remplir(pioche, indexDuJoueur);

                // Feedback sur le coup joué
                System.out.println();
                String couleurJoueurActuel = (joueurActuel == joueurUn) ? couleurJoueurUn : couleurJoueurDeux;
                System.out.println(couleurJoueurActuel + "Tuile placée avec succès!" + TexteConsole.RESET);

                // Points gagnés
                if (resultatVerificationCoup > 0) {
                    System.out.println(TexteConsole.HIGHLIGHT + "Correspondances: " + resultatVerificationCoup + TexteConsole.RESET);
                    float pointsGagnesCeTour = 0;

                    if (resultatVerificationCoup == DOUBLE_CORRESPONDANCES) {
                        pointsGagnesCeTour = POINTS_DOUBLE;
                        System.out.println(TexteConsole.HIGHLIGHT + "Double! +" + POINTS_DOUBLE + " point" + TexteConsole.RESET);
                    } else if (resultatVerificationCoup == TREFOIL_CORRESPONDANCES) {
                        pointsGagnesCeTour = POINTS_TREFOIL;
                        System.out.println(TexteConsole.HIGHLIGHT + "Trefoil! +" + POINTS_TREFOIL + " point" + TexteConsole.RESET);
                    } else if (resultatVerificationCoup == LATICE_CORRESPONDANCES) {
                        pointsGagnesCeTour = POINTS_LATICE;
                        System.out.println(TexteConsole.HIGHLIGHT + "Latice! +" + POINTS_LATICE + " points" + TexteConsole.RESET);
                    }

                    // Vérifier les cases spéciales
                    if (PositionCaseSoleil.estUneCaseSoleil(positionTuile.getX(), positionTuile.getY())) {
                        TexteConsole.caseSunStone();
                        System.out.println(TexteConsole.HIGHLIGHT + "Bonus soleil: +" + POINTS_BONUS_SOLEIL + " point" + TexteConsole.RESET);
                        pointsGagnesCeTour += POINTS_BONUS_SOLEIL;
                    } else {
                        TexteConsole.notCaseSunStone();
                    }

                    if (positionTuile.estUneCaseLune(positionTuile.getX(), positionTuile.getY())) {
                        TexteConsole.caseMoonStone();
                    } else {
                        TexteConsole.notCaseMoonStone();
                    }

                    System.out.println(TexteConsole.HIGHLIGHT + "Total des points gagnés: " + pointsGagnesCeTour + TexteConsole.RESET);

                    // D'abord, on calcule le nombre total de points à ajouter
                    int pointsAAjouter = Math.round(pointsGagnesCeTour);

                    // Règle : si plus de 3 points en un tour, limiter à 3 points
                    if (pointsAAjouter > MAX_POINTS_PAR_TOUR) {
                        System.out.println(TexteConsole.HIGHLIGHT + "Maximum de " + MAX_POINTS_PAR_TOUR + " points par tour ! Points en excès perdus." + TexteConsole.RESET);
                        arbitre.ajouterPoints(indexDuJoueur, MAX_POINTS_PAR_TOUR); // Ajouter exactement 3 points (le maximum)
                    } else {
                        arbitre.ajouterPoints(indexDuJoueur, pointsAAjouter); // Ajouter les points calculés
                    }
                }

                premierCoup = false;

                // Pause for readability
                TexteConsole.appuyerEnter();

                // Changer de joueur ou accorder un tour supplémentaire
                if (tourSpecialActif) {
                    tourSpecialActif = false;
                    System.out.println(TexteConsole.HIGHLIGHT + "Vous jouez votre tour supplémentaire." + TexteConsole.RESET);
                } else {
                    changerJoueur();
                }

            } else { // Si le coup n'est pas valide
                System.out.println();
                if (premierCoup) {
                    System.out.println(TexteConsole.HIGHLIGHT + "Le premier coup doit être joué au centre (position 5,5). Veuillez réessayer." + TexteConsole.RESET);
                } else {
                    TexteConsole.placementImpossible();
                }
                TexteConsole.appuyerEnter();
            }
        } else { // Si l'indice de tuile est invalide
            TexteConsole.tuileInvalide();
            TexteConsole.appuyerEnter();
        }
    }

    /**
     * Gère la pioche d'une nouvelle main (-1 point).
     */
    private static void piocherNouvelleMain() {
        System.out.println();
        String couleurJoueurActuel = (joueurActuel == joueurUn) ? couleurJoueurUn : couleurJoueurDeux;
        int indexJoueurActuel = (joueurActuel == joueurUn) ? PREMIER_JOUEUR_INDEX : SECOND_JOUEUR_INDEX;

        // Vérifier si le joueur a au moins 1 point à dépenser
        if (arbitre.getScore(indexJoueurActuel) < COUT_NOUVELLE_MAIN) {
            System.out.println(TexteConsole.HIGHLIGHT + "Vous n'avez pas assez de points pour piocher une nouvelle main (coût: " + COUT_NOUVELLE_MAIN + " point)." + TexteConsole.RESET);
        } else {
            // Déduire 1 point du score
            arbitre.ajouterPoints(indexJoueurActuel, -COUT_NOUVELLE_MAIN);

            // Vider le rack actuel et le remplir avec de nouvelles tuiles
            joueurActuel.getRack().vider();
            joueurActuel.getRack().remplir(pioche, indexJoueurActuel);

            System.out.println(couleurJoueurActuel + "Vous avez pioché une nouvelle main (-" + COUT_NOUVELLE_MAIN + " point)." + TexteConsole.RESET);
        }

        TexteConsole.appuyerEnter();
        changerJoueur();
    }

    /**
     * Gère le fait de passer son tour.
     */
    private static void passerTour() {
        String couleurJoueurActuel = (joueurActuel == joueurUn) ? couleurJoueurUn : couleurJoueurDeux;
        TexteConsole.passerTour(joueurActuel, couleurJoueurActuel);
        TexteConsole.appuyerEnter();
        changerJoueur();
    }

    /**
     * Affiche les règles du jeu.
     */
    private static void afficherRegles() {
        System.out.println();
        TexteConsole.afficherRegles();
        TexteConsole.appuyerEnter();
    }

    /**
     * Gère l'achat d'un tour supplémentaire par le joueur.
     */
    private static void acheterTourSupplementaire() {
        System.out.println();
        String couleurJoueurActuel = (joueurActuel == joueurUn) ? couleurJoueurUn : couleurJoueurDeux;

        // Appeler la méthode existante du joueur pour acheter un tour supplémentaire
        boolean actionSpecialeReussie = joueurActuel.jouerActionSpeciale(
                (joueurActuel == joueurUn) ? PREMIER_JOUEUR_INDEX : SECOND_JOUEUR_INDEX,    // index du joueur actuel
                ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                joueurActuel.getRack(),
                joueurActuel.getPioche(), // La pioche est maintenant passée directement
                arbitre
        );

        if (actionSpecialeReussie) {
            System.out.println(couleurJoueurActuel + "Tour supplémentaire acheté avec succès (-" + COUT_TOUR_SUPPLEMENTAIRE + " points)." + TexteConsole.RESET);
            tourSpecialActif = true;
        } else {
            System.out.println(TexteConsole.HIGHLIGHT + "Vous n'avez pas assez de points pour acheter un tour supplémentaire." + TexteConsole.RESET);
        }
        TexteConsole.appuyerEnter();
    }

    /**
     * Gère la fin de partie.
     */
    private static void quitterPartie() {
        TexteConsole.remerciement();
        partieTerminee = true;
    }

    /**
     * Change le joueur courant et incrémente le compteur de tours si nécessaire.
     */
    private static void changerJoueur() {
        joueurActuel = (joueurActuel == joueurUn) ? joueurDeux : joueurUn;
        if (joueurActuel == joueurUn) { // Seulement incrémenter le tour quand le joueur 1 recommence
            tourActuel++;
        }
    }

    /**
     * Vérifie les conditions de fin de partie et affiche le gagnant si la partie est terminée.
     */
    private static void verifierFinPartie() {
        if (arbitre.finDePartie(new Rack[]{rackDuJoueur1, rackDuJoueur2}, tourActuel, NOMBRE_MAX_TOURS)) {
            TexteConsole.clearScreen();

            // Affichage du tour final
            System.out.println(TexteConsole.HIGHLIGHT + "TOUR FINAL: " + tourActuel + "/" + NOMBRE_MAX_TOURS + TexteConsole.RESET);
            System.out.println();

            Joueur[] tableauJoueurs = {joueurUn, joueurDeux};
            List<Integer> indicesGagnants = arbitre.getGagnants(tableauJoueurs);
            int indexDuGagnant = indicesGagnants.get(0); // On suppose un seul gagnant pour l'instant

            String nomDuGagnant = (indexDuGagnant == PREMIER_JOUEUR_INDEX) ? joueurUn.getName() : joueurDeux.getName();
            String couleurDuGagnant = (indexDuGagnant == PREMIER_JOUEUR_INDEX) ? couleurJoueurUn : couleurJoueurDeux;

            TexteConsole.finPartie(arbitre, joueurUn, joueurDeux, couleurJoueurUn, couleurJoueurDeux, nomDuGagnant, couleurDuGagnant);
            partieTerminee = true;
        }
    }
}








