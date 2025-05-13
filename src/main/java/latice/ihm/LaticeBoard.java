package latice.ihm;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;

import latice.model.Pioche;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Tuile;

public class LaticeBoard extends Application {

    private static final int SIZE = 9;
    private static final int TILE_SIZE = 50;
    private static final String BASE_PATH = "/latice/ihm/view/plateau_photo/";
    private static final int NB_JOUEURS = 2;

    private Rack[] racks = new Rack[NB_JOUEURS];
    private Pioche pioches = new Pioche(NB_JOUEURS);
    private HBox[] racksHbox = new HBox[NB_JOUEURS];
    private int joueurActuel = 0;

    private Tuile tuileSelectionnee = null;
    private int indexTuileSelectionnee = -1;
    private boolean premierCoup = true;
    private Map<PositionTuiles, Tuile> plateau = new HashMap<>();

    private Label tourLabel;
    private Label[] tuilesRestantesLabel = new Label[NB_JOUEURS];

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);


        // Plateau
        GridPane board = createBoard();

        // Zone info + racks
        VBox infoZone = new VBox(5);
        tourLabel = new Label("Tour du Joueur 1");
        
        racks = new Rack[NB_JOUEURS];
        racksHbox = new HBox[NB_JOUEURS];
              
        for (int i = 0; i < NB_JOUEURS; i++) {
            tuilesRestantesLabel[i] = new Label();
            tuilesRestantesLabel[i].setText("Tuiles restantes Joueur " + (i + 1) + ": 0");
        }

        // Création racks et pioches pour les deux joueurs
        pioches = new Pioche(NB_JOUEURS);
        distributionTuiles(NB_JOUEURS);
        updateRack();
        updateTuilesRestantes();

        root.getChildren().addAll(board, infoZone);

     // Ajouter les éléments pour chaque joueur
        infoZone.getChildren().add(tourLabel);
        for (int i = 0; i < NB_JOUEURS; i++) {
            HBox playerBox = new HBox(10);
            playerBox.getChildren().addAll(tuilesRestantesLabel[i], racksHbox[i]);
            infoZone.getChildren().add(playerBox);
        }

        Scene scene = new Scene(root);
        primaryStage.setTitle("Latice Game " + NB_JOUEURS + " Joueurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void distributionTuiles(int nombreJoueurs) {
        // On s'assure que chaque joueur reçoit un nombre correct de tuiles en fonction du nombre de joueurs
        int tuilesParJoueur = 72 / nombreJoueurs;  // Chaque joueur reçoit une portion égale des tuiles

        for (int i = 0; i < nombreJoueurs; i++) {
            racks[i] = new Rack(pioches);  // Associe un rack à chaque joueur
            racksHbox[i] = new HBox(5);
            
            // Distribution des tuiles à chaque joueur
            for (int j = 0; j < tuilesParJoueur; j++) {  // On distribue le bon nombre de tuiles par joueur
                Tuile tuile = pioches.piocher(i);  // Pioche une tuile spécifique pour le joueur
                racks[i].ajoutTuile(tuile);  // Ajoute la tuile au rack du joueur
            }
        }
    }



    private GridPane createBoard() {
        GridPane grid = new GridPane();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                final int currentRow = row;
                final int currentCol = col;
                ImageView tile = new ImageView(new Image(BASE_PATH + "ocean.png"));
                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setUserData(false); // vide
                
                if (isSunTile(row, col)) {
                    tile.setImage(new Image(BASE_PATH + "soleil.png"));
                } else if (isMoonTile(row, col)) {
                    tile.setImage(new Image(BASE_PATH + "lune.png"));
                }

                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (tuileSelectionnee != null && !(boolean) tile.getUserData()) {
                    	if (premierCoup && (currentRow != 4 || currentCol != 4)) return;
                        tile.setImage(new Image(BASE_PATH + tuileSelectionnee.getImagePath()));
                        tile.setUserData(true);
                        
                        plateau.put(new PositionTuiles(currentRow, currentCol), tuileSelectionnee);

                        removeTileFromRack(joueurActuel, indexTuileSelectionnee);
                        premierCoup = false;
                        changerDeTour();
                    }
                });
                

                grid.add(tile, col, row);
            }
        }
        return grid;
    }
    
    private boolean isSunTile(int row, int col) {
        return (row == 0 && col == 0) || (row == 0 && col == 8) || (row == 0 && col == 4) ||
               (row == 1 && col == 1) || (row == 1 && col == 7) || (row == 2 && col == 2) ||
               (row == 2 && col == 6) || (row == 4 && col == 0) || (row == 4 && col == 8) ||
               (row == 6 && col == 2) || (row == 6 && col == 6) || (row == 7 && col == 1) || 
               (row == 7 && col == 7) || (row == 8 && col == 0) || (row == 8 && col == 4) || 
               (row == 8 && col == 8);
    }

    private boolean isMoonTile(int row, int col) {
        return (row == 4 && col == 4);
    }

    private void updateRack() {
        for (int j = 0; j < racks.length; j++) {
            racksHbox[j].getChildren().clear(); // Efface les tuiles précédemment affichées dans le rack

            if (j != joueurActuel) continue; // Ne montre que le rack du joueur actuel

            // Limiter l'affichage à 5 tuiles dans le rack
            int nbTuilesAafficher = Math.min(racks[j].getTuiles().size(), 5);

            for (int i = 0; i < nbTuilesAafficher; i++) {  // Limité à 5 tuiles
                Tuile tuile = racks[j].getTuiles().get(i);
                String imagePath = BASE_PATH + tuile.getImagePath();

                try {
                    ImageView tileView = new ImageView(new Image(imagePath));
                    tileView.setFitWidth(TILE_SIZE);
                    tileView.setFitHeight(TILE_SIZE);

                    final int index = i;
                    final int joueur = j;

                    // Ajouter un événement pour la sélection de la tuile
                    tileView.setOnMouseClicked(event -> {
                        if (joueur == joueurActuel) {
                            tuileSelectionnee = tuile;
                            indexTuileSelectionnee = index;
                            highlightSelectedTile(joueurActuel, index);
                        }
                    });

                    racksHbox[j].getChildren().add(tileView);
                } catch (Exception e) {
                    racksHbox[j].getChildren().add(new Label("Image manquante"));
                }
            }
        }
    }


    private void highlightSelectedTile(int joueur, int selectedIndex) {
        for (int i = 0; i < racksHbox[joueur].getChildren().size(); i++) {
            ImageView tuileView = (ImageView) racksHbox[joueur].getChildren().get(i);
            if (i == selectedIndex) {
                DropShadow borderGlow = new DropShadow();
                borderGlow.setColor(Color.BLUE);
                borderGlow.setOffsetX(0f);
                borderGlow.setOffsetY(0f);
                borderGlow.setWidth(30);
                borderGlow.setHeight(30);
                tuileView.setEffect(borderGlow);
            } else {
                tuileView.setEffect(null);
            }
        }
    }
    
    private void updateTuilesRestantes() {
        if (tuilesRestantesLabel[0] == null) return;

        // Mise à jour du nombre de tuiles restantes dans le rack pour chaque joueur
        for (int i = 0; i < racks.length; i++) {
            int nbTuiles = racks[i].getTuiles().size();  // Compte les tuiles dans le rack du joueur
            tuilesRestantesLabel[i].setText("Tuiles restantes Joueur " + (i + 1) + ": " + nbTuiles);
        }
    }




    private void removeTileFromRack(int joueur, int index) {
        // Vérifie que l'indice est valide pour le joueur spécifié
        if (joueur >= 0 && joueur < racks.length && index >= 0 && index < racks[joueur].getTuiles().size()) {
            // Retirer la tuile du rack du joueur
            racks[joueur].getTuiles().remove(index);

            // Si la pioche du joueur n'est pas vide, ajouter une nouvelle tuile au rack
            if (!pioches.estVide(joueur)) {
                Tuile nouvelleTuile = pioches.piocher(joueur); // Pioche une tuile pour le joueur
                racks[joueur].ajoutTuile(nouvelleTuile); // Ajoute la tuile au rack du joueur
            }

            // Réinitialiser la tuile sélectionnée après l'action
            tuileSelectionnee = null;
            indexTuileSelectionnee = -1;

            // Mettre à jour l'affichage du rack et des tuiles restantes
            updateRack();
            updateTuilesRestantes();
        }
    }


    private void changerDeTour() {
        joueurActuel = (joueurActuel + 1) % racks.length;  // Passage au joueur suivant, en fonction du nombre de joueurs
        tourLabel.setText("Tour du Joueur " + (joueurActuel + 1));
        updateRack();
        updateTuilesRestantes();
    }
    
    public Tuile getTuilAt(int x, int y) {
    	return plateau.get(new PositionTuiles(x, y));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
