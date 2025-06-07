package latice.service;

import latice.config.ConfigurationJeu;
import latice.ihm.TexteConsole;
import latice.model.*;

public class TourService {
    
    public void piocherNouvelleMain(Joueur joueurActuel, Arbitre arbitre, 
                                    Pioche pioche, int indexJoueur, String couleurJoueur) {
        System.out.println();
        
        // Vérifier si le joueur a au moins 1 point à dépenser
        if (arbitre.getScore(indexJoueur) < ConfigurationJeu.COUT_NOUVELLE_MAIN) {
            System.out.println(TexteConsole.SURBRILLANCE + 
                              "Vous n'avez pas assez de points pour piocher une nouvelle main (coût: " + 
                              ConfigurationJeu.COUT_NOUVELLE_MAIN + " point)." + TexteConsole.REINITIALISATION);
        } else {
            // Déduire 1 point du score
            arbitre.ajouterPoints(indexJoueur, -ConfigurationJeu.COUT_NOUVELLE_MAIN);
            
            // Vider le rack actuel et le remplir avec de nouvelles tuiles
            joueurActuel.getRack().vider();
            joueurActuel.getRack().remplir(pioche, indexJoueur);
            
            System.out.println(couleurJoueur + "Vous avez pioché une nouvelle main (-" + 
                              ConfigurationJeu.COUT_NOUVELLE_MAIN + " point)." + TexteConsole.REINITIALISATION);
        }
        
        TexteConsole.appuyerEnter();
    }
    
    public void passerTour(Joueur joueurActuel, String couleurJoueur) {
        TexteConsole.passerTour(joueurActuel, couleurJoueur);
        TexteConsole.appuyerEnter();
    }
    
    public boolean acheterTourSupplementaire(Joueur joueurActuel, Arbitre arbitre, 
                                          int indexJoueur, String couleurJoueur) {
        System.out.println();
        
        // Appeler la méthode existante du joueur pour acheter un tour supplémentaire
        boolean actionSpecialeReussie = joueurActuel.jouerActionSpeciale(
                indexJoueur,
                ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                joueurActuel.getRack(),
                joueurActuel.getPioche(),
                arbitre
        );
        
        if (actionSpecialeReussie) {
            System.out.println(couleurJoueur + "Tour supplémentaire acheté avec succès (-" + 
                              ConfigurationJeu.COUT_TOUR_SUPPLEMENTAIRE + " points)." + TexteConsole.REINITIALISATION);
            TexteConsole.appuyerEnter();
            return true;
        } else {
            System.out.println(TexteConsole.SURBRILLANCE + 
                              "Vous n'avez pas assez de points pour acheter un tour supplémentaire." + 
                              TexteConsole.REINITIALISATION);
            TexteConsole.appuyerEnter();
            return false;
        }
    }
}

