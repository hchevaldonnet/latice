package latice.application;

import latice.controller.JeuController;
import latice.service.InitialisationService;

public class LaticeApplicationConsole {
    
    public static void main(String[] args) {
        // Initialisation du jeu
        InitialisationService initialisationService = new InitialisationService();
        JeuController jeuController = initialisationService.initialiserJeu();
        
        // Démarrage du jeu
        jeuController.demarrerJeu();
    }
}









