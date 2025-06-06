package latice.controller;

import java.util.List;
import java.util.Scanner;

import latice.config.ConfigurationJeu;
import latice.ihm.PlateauVue;
import latice.ihm.PlateauVueConsole;
import latice.ihm.SaisieConsole;
import latice.ihm.TexteConsole;
import latice.model.*;
import latice.service.PlacementService;
import latice.service.TourService;

public class JeuController {
    private Pioche pioche;
    private Arbitre arbitre;
    private Plateau plateauDeJeu;
    private Rack[] racks;
    private Joueur[] joueurs;
    private boolean premierCoup;
    private boolean afficherIndices;
    private boolean partieTerminee;
    private boolean tourSpecialActif;
    private int tourActuel;
    private int joueurActuelIndex;
    private Scanner lecteur;
    private String[] couleursJoueurs;
    private PlacementService placementService;
    private TourService tourService;

    public JeuController(Pioche pioche, Arbitre arbitre, Plateau plateauDeJeu, 
                        Rack[] racks, Joueur[] joueurs, boolean afficherIndices) {
        this.pioche = pioche;
        this.arbitre = arbitre;
        this.plateauDeJeu = plateauDeJeu;
        this.racks = racks;
        this.joueurs = joueurs;
        this.afficherIndices = afficherIndices;
        this.premierCoup = true;
        this.tourActuel = 1;
        this.partieTerminee = false;
        this.tourSpecialActif = false;
        this.joueurActuelIndex = 0;
        this.lecteur = new Scanner(System.in);
        this.couleursJoueurs = new String[ConfigurationJeu.NOMBRE_JOUEURS];
        this.placementService = new PlacementService();
        this.tourService = new TourService();
    }

    public void demarrerJeu() {
        while (!partieTerminee) {
            afficherEtatJeu();
            traiterTourJoueur();
            verifierFinPartie();
        }
        lecteur.close();
    }

    public void afficherEtatJeu() {
        TexteConsole.effacerEcran();

        // Afficher les informations de l'état du jeu
        String couleurJoueurActuel = couleursJoueurs[joueurActuelIndex];
        TexteConsole.affichageEtatJeu(joueurs[joueurActuelIndex], couleurJoueurActuel);

        // Afficher le nombre de tours
        TexteConsole.affichageTour(tourActuel, ConfigurationJeu.NOMBRE_MAX_TOURS);

        // Afficher les scores
        TexteConsole.affichageScore(arbitre, joueurs[0], joueurs[1], couleursJoueurs[0], couleursJoueurs[1]);

        // Afficher le plateau
        System.out.println(TexteConsole.HIGHLIGHT + "PLATEAU DE JEU:" + TexteConsole.RESET);
        PlateauVue plateauView = new PlateauVueConsole();
        PlateauVueConsole plateauViewConsole = (PlateauVueConsole) plateauView;
        plateauViewConsole.afficherPlateau(plateauDeJeu, afficherIndices);

        System.out.println();

        // Afficher le rack du joueur courant
        System.out.println(racks[joueurActuelIndex].afficherRack(couleurJoueurActuel + 
                "C'est au tour de " + joueurs[joueurActuelIndex].getName() + 
                ", voici votre rack :" + TexteConsole.RESET));
        System.out.println();

        // Afficher les options du menu
        TexteConsole.afficherMenu();
        System.out.println();
    }

    public void traiterTourJoueur() {
        int choixMenu = SaisieConsole.saisieChoix() + 1;

        switch (choixMenu) {
            case ConfigurationJeu.OPTION_PLACER_TUILE:
                placementService.placerTuile(joueurs[joueurActuelIndex], racks[joueurActuelIndex], 
                                           plateauDeJeu, pioche, arbitre, premierCoup, 
                                           joueurActuelIndex, couleursJoueurs[joueurActuelIndex],
                                           tourSpecialActif);
                if (placementService.isPlacementReussi()) {
                    premierCoup = false;
                    tourSpecialActif = placementService.isTourSpecialActif();
                    if (!tourSpecialActif) {
                        changerJoueur();
                    }
                }
                break;
            case ConfigurationJeu.OPTION_PIOCHER_MAIN:
                tourService.piocherNouvelleMain(joueurs[joueurActuelIndex], arbitre, 
                                              pioche, joueurActuelIndex, 
                                              couleursJoueurs[joueurActuelIndex]);
                changerJoueur();
                break;
            case ConfigurationJeu.OPTION_PASSER_TOUR:
                tourService.passerTour(joueurs[joueurActuelIndex], couleursJoueurs[joueurActuelIndex]);
                changerJoueur();
                break;
            case ConfigurationJeu.OPTION_AFFICHER_REGLES:
                TexteConsole.afficherRegles();
                TexteConsole.appuyerEnter();
                break;
            case ConfigurationJeu.OPTION_TOUR_SUPPLEMENTAIRE:
                tourSpecialActif = tourService.acheterTourSupplementaire(
                    joueurs[joueurActuelIndex], arbitre, joueurActuelIndex, 
                    couleursJoueurs[joueurActuelIndex]);
                break;
            case ConfigurationJeu.OPTION_QUITTER:
                quitterPartie();
                break;
            default:
                TexteConsole.afficherErreurSaisie();
                TexteConsole.appuyerEnter();
                break;
        }
    }

    private void changerJoueur() {
        joueurActuelIndex = (joueurActuelIndex + 1) % ConfigurationJeu.NOMBRE_JOUEURS;
        if (joueurActuelIndex == 0) { // Seulement incrémenter le tour quand le joueur 1 recommence
            tourActuel++;
        }
    }

    private void verifierFinPartie() {
        if (arbitre.finDePartie(racks, tourActuel, ConfigurationJeu.NOMBRE_MAX_TOURS)) {
            TexteConsole.effacerEcran();

            // Affichage du tour final
            System.out.println(TexteConsole.HIGHLIGHT + "TOUR FINAL: " + tourActuel + "/" + 
                              ConfigurationJeu.NOMBRE_MAX_TOURS + TexteConsole.RESET);
            System.out.println();

            List<Integer> indicesGagnants = arbitre.getGagnants(joueurs);
            int indexDuGagnant = indicesGagnants.get(0); // On suppose un seul gagnant pour l'instant

            String nomDuGagnant = joueurs[indexDuGagnant].getName();
            String couleurDuGagnant = couleursJoueurs[indexDuGagnant];

            TexteConsole.finPartie(arbitre, joueurs[0], joueurs[1], 
                                  couleursJoueurs[0], couleursJoueurs[1], 
                                  nomDuGagnant, couleurDuGagnant);
            partieTerminee = true;
        }
    }

    private void quitterPartie() {
        TexteConsole.remerciement();
        partieTerminee = true;
    }

    public void setCouleursJoueurs(String[] couleursJoueurs) {
        this.couleursJoueurs = couleursJoueurs;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }
}

