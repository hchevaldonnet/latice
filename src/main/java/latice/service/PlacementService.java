package latice.service;

import latice.config.ConfigurationJeu;
import latice.ihm.TexteConsole;
import latice.model.*;

public class PlacementService {
    private boolean placementReussi = false;
    private boolean tourSpecialActif = false;
    
    public void placerTuile(Joueur joueurActuel, Rack rackActuel, Plateau plateauDeJeu, 
                           Pioche pioche, Arbitre arbitre, boolean premierCoup, 
                           int indexJoueur, String couleurJoueur, boolean tourSpecialActif) {
        this.placementReussi = false;
        this.tourSpecialActif = tourSpecialActif;
        
        System.out.println();
        int indexTuileSelectionnee = latice.ihm.SaisieConsole.saisieTuiles();
        
        if (premierCoup) {
            System.out.println(TexteConsole.SURBRILLANCE + 
                              "Premier coup: vous devez jouer au centre (position 5,5)" + 
                              TexteConsole.REINITIALISATION);
            System.out.println();
        }
        
        int ligneSelectionnee = latice.ihm.SaisieConsole.saisieLigneColonne("ligne");
        int colonneSelectionnee = latice.ihm.SaisieConsole.saisieLigneColonne("colonne");
        
        // Vérifier si l'indice est valide
        if (indexTuileSelectionnee >= 0 && indexTuileSelectionnee < rackActuel.getTuiles().size()) {
            Tuile tuileSelectionnee = rackActuel.getTuiles().get(indexTuileSelectionnee);
            
            // Utiliser l'arbitre pour vérifier le coup
            int resultatVerificationCoup = arbitre.verifierCoup(
                ligneSelectionnee, colonneSelectionnee, tuileSelectionnee, 
                plateauDeJeu.getCases(), premierCoup);
            
            if (resultatVerificationCoup >= 0) {
                executerPlacementValide(joueurActuel, rackActuel, plateauDeJeu, pioche, arbitre, 
                                       ligneSelectionnee, colonneSelectionnee, indexTuileSelectionnee, 
                                       tuileSelectionnee, resultatVerificationCoup, indexJoueur, couleurJoueur);
            } else {
                afficherErreurPlacement(premierCoup);
            }
        } else {
            TexteConsole.tuileInvalide();
            TexteConsole.appuyerEnter();
        }
    }
    
    private void executerPlacementValide(Joueur joueurActuel, Rack rackActuel, Plateau plateauDeJeu, 
                                        Pioche pioche, Arbitre arbitre, int ligne, int colonne, 
                                        int indexTuile, Tuile tuile, int resultatVerification, 
                                        int indexJoueur, String couleurJoueur) {
        // Placer la tuile sur le plateau
        PositionTuiles positionTuile = new PositionTuiles(ligne, colonne);
        plateauDeJeu.placerTuile(tuile, positionTuile);
        rackActuel.retirerTuile(indexTuile);
        rackActuel.remplir(pioche, indexJoueur);
        
        // Feedback sur le coup joué
        System.out.println();
        System.out.println(couleurJoueur + "Tuile placée avec succès!" + TexteConsole.REINITIALISATION);
        
        // Points gagnés
        if (resultatVerification > 0) {
            calculerEtAfficherPoints(arbitre, positionTuile, resultatVerification, indexJoueur);
        }
        
        this.placementReussi = true;
        
        // Gérer le tour supplémentaire
        if (this.tourSpecialActif) {
            this.tourSpecialActif = false;
            System.out.println(TexteConsole.SURBRILLANCE + "Vous jouez votre tour supplémentaire." + TexteConsole.REINITIALISATION);
        }
        
        TexteConsole.appuyerEnter();
    }
    
    private void calculerEtAfficherPoints(Arbitre arbitre, PositionTuiles position, 
                                         int correspondances, int indexJoueur) {
        System.out.println(TexteConsole.SURBRILLANCE + "Correspondances: " + correspondances + TexteConsole.REINITIALISATION);
        float pointsGagnesCeTour = 0;
        
        if (correspondances == ConfigurationJeu.DOUBLE_CORRESPONDANCES) {
            pointsGagnesCeTour = ConfigurationJeu.POINTS_DOUBLE;
            System.out.println(TexteConsole.SURBRILLANCE + "Double! +" + 
                              ConfigurationJeu.POINTS_DOUBLE + " point" + TexteConsole.REINITIALISATION);
        } else if (correspondances == ConfigurationJeu.TREFOIL_CORRESPONDANCES) {
            pointsGagnesCeTour = ConfigurationJeu.POINTS_TREFOIL;
            System.out.println(TexteConsole.SURBRILLANCE + "Trefoil! +" + 
                              ConfigurationJeu.POINTS_TREFOIL + " point" + TexteConsole.REINITIALISATION);
        } else if (correspondances == ConfigurationJeu.LATICE_CORRESPONDANCES) {
            pointsGagnesCeTour = ConfigurationJeu.POINTS_LATICE;
            System.out.println(TexteConsole.SURBRILLANCE + "Latice! +" + 
                              ConfigurationJeu.POINTS_LATICE + " points" + TexteConsole.REINITIALISATION);
        }
        
        // Vérifier les cases spéciales
        if (PositionCaseSoleil.estUneCaseSoleil(position.getX(), position.getY())) {
            TexteConsole.afficherSiCaseSoleil(true, ConfigurationJeu.POINTS_BONUS_SOLEIL);
            pointsGagnesCeTour += ConfigurationJeu.POINTS_BONUS_SOLEIL;
        } else {
            TexteConsole.afficherSiCaseSoleil(false, ConfigurationJeu.POINTS_BONUS_SOLEIL);
        }
        
        System.out.println(TexteConsole.SURBRILLANCE + "Total des points gagnés: " + 
                          pointsGagnesCeTour + TexteConsole.REINITIALISATION);
        
        // D'abord, on calcule le nombre total de points à ajouter
        int pointsAAjouter = Math.round(pointsGagnesCeTour);
        
        // Règle : si plus de 3 points en un tour, limiter à 3 points
        if (pointsAAjouter > ConfigurationJeu.MAX_POINTS_PAR_TOUR) {
            System.out.println(TexteConsole.SURBRILLANCE + "Maximum de " + 
                              ConfigurationJeu.MAX_POINTS_PAR_TOUR + 
                              " points par tour ! Points en excès perdus." + TexteConsole.REINITIALISATION);
            arbitre.ajouterPoints(indexJoueur, ConfigurationJeu.MAX_POINTS_PAR_TOUR);
        } else {
            arbitre.ajouterPoints(indexJoueur, pointsAAjouter);
        }
    }
    
    private void afficherErreurPlacement(boolean premierCoup) {
        System.out.println();
        if (premierCoup) {
            System.out.println(TexteConsole.SURBRILLANCE + 
                              "Le premier coup doit être joué au centre (position 5,5). Veuillez réessayer." + 
                              TexteConsole.REINITIALISATION);
        } else {
            TexteConsole.placementImpossible();
        }
        TexteConsole.appuyerEnter();
    }
    
    public boolean isPlacementReussi() {
        return placementReussi;
    }
    
    public boolean isTourSpecialActif() {
        return tourSpecialActif;
    }
}

