package latice.service;

import java.util.Scanner;

import latice.config.ConfigurationJeu;
import latice.controller.JeuController;
import latice.ihm.SaisieConsole;
import latice.ihm.TexteConsole;
import latice.model.*;
import latice.model.setup.PreparerJeu;

public class InitialisationService {
    
    public JeuController initialiserJeu() {
        // Initialisation des composants du jeu
        Pioche pioche = new Pioche(ConfigurationJeu.NOMBRE_JOUEURS);
        Arbitre arbitre = new Arbitre(ConfigurationJeu.NOMBRE_JOUEURS);
        
        Rack[] racks = new Rack[ConfigurationJeu.NOMBRE_JOUEURS];
        for (int i = 0; i < ConfigurationJeu.NOMBRE_JOUEURS; i++) {
            racks[i] = new Rack();
            racks[i].remplir(pioche, i);
        }
        
        Plateau plateauDeJeu = new Plateau();
        
        // Paramètres d'affichage
        TexteConsole.effacerEcran();
        TexteConsole.afficherTitre();
        
        boolean afficherIndices = demanderAffichageIndices();
        
        // Initialisation des joueurs
        Joueur[] joueurs = initialiserJoueurs(racks, pioche);
        
        // Création du contrôleur de jeu
        JeuController jeuController = new JeuController(pioche, arbitre, plateauDeJeu, 
                                                      racks, joueurs, afficherIndices);
        
        // Configuration des couleurs des joueurs
        String[] couleursJoueurs = {TexteConsole.JOUEUR1, TexteConsole.JOUEUR2};
        jeuController.setCouleursJoueurs(couleursJoueurs);
        
        TexteConsole.afficherBienvenue();
        TexteConsole.afficherMenu();
        
        return jeuController;
    }
    
    private boolean demanderAffichageIndices() {
        Scanner lecteur = new Scanner(System.in);
        String reponseUtilisateur;
        do {
            System.out.println("Voulez-vous afficher les indices des lignes et colonnes? (O/N)");
            reponseUtilisateur = lecteur.nextLine().trim().toUpperCase();
        } while (!reponseUtilisateur.equals("O") && !reponseUtilisateur.equals("N"));
        return reponseUtilisateur.equals("O");
    }
    
    private Joueur[] initialiserJoueurs(Rack[] racks, Pioche pioche) {
        TexteConsole.formatJoueur(TexteConsole.JOUEUR1, "Joueur 1 :");
        String nomJoueur1 = SaisieConsole.saisieChar();
        TexteConsole.sautDeLigne();
        
        TexteConsole.formatJoueur(TexteConsole.JOUEUR2, "Joueur 2 :");
        String nomJoueur2 = SaisieConsole.saisieChar();
        
        // Vérifier que les noms des joueurs sont différents
        while (nomJoueur2.equals(nomJoueur1)) {
            System.out.println(TexteConsole.SURBRILLANCE + 
                              "Les noms des joueurs doivent être différents. Veuillez choisir un autre nom." + 
                              TexteConsole.REINITIALISATION);
            TexteConsole.formatJoueur(TexteConsole.JOUEUR2, "Joueur 2 :");
            nomJoueur2 = SaisieConsole.saisieChar();
        }
        System.out.println();
        
        // Détermine aléatoirement l'ordre des joueurs
        String[] ordreDesJoueurs = PreparerJeu.ordreJoueur(nomJoueur1, nomJoueur2);
        Joueur[] joueurs = new Joueur[ConfigurationJeu.NOMBRE_JOUEURS];
        
        joueurs[0] = new Joueur(ordreDesJoueurs[0], racks[0], pioche);
        joueurs[1] = new Joueur(ordreDesJoueurs[1], racks[1], pioche);
        
        TexteConsole.affichageJoueurs(joueurs[0], joueurs[1], 
                                     TexteConsole.JOUEUR1, TexteConsole.JOUEUR2);
        
        return joueurs;
    }
}

