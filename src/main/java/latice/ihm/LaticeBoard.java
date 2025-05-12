package latice.ihm;
/*
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

import java.util.*;

import latice.model.Pioche;
import latice.model.PositionTuiles;
import latice.model.Rack;
import latice.model.Tuile;
import latice.model.Symbole;
import latice.model.Couleur;

public class LaticeBoard extends Application {

    private static final int SIZE = 9;
    private static final int TILE_SIZE = 60;
    private static final String BASE_PATH = "/latice/ihm/view/plateau_photo/";

    private final Rack[] racks = new Rack[2];
    private final Pioche[] pioches = new Pioche[2];
    private final HBox[] racksHbox = new HBox[2];
    private int joueurActuel = 0; // 0: joueur 1, 1: joueur 2

    private Tuile tuileSelectionnee = null;
    private int indexTuileSelectionnee = -1;
    private boolean premierCoup = true;

    private Map<PositionTuiles, Tuile> plateau = new HashMap<>();
    
    private Label tourLabel;
    private Label[] tuilesRestantesLabel = new Label[2];

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);

        // Plateau
        GridPane board = createBoard();

        // Zone info + racks
        VBox infoZone = new VBox(5);
        tourLabel = new Label("Tour du Joueur 1");
        
        for (int i = 0; i < 2; i++) {
            tuilesRestantesLabel[i] = new Label();
            tuilesRestantesLabel[i].setText("Tuiles restantes Joueur " + (i + 1) + ": 0");
        }

        // Création racks et pioches pour les deux joueurs
        for (int i = 0; i < 2; i++) {
            ArrayList<Tuile> tuiles = new ArrayList<>();
            for (Couleur couleur : Couleur.values()) {
                for (Symbole symbole : Symbole.values()) {
                    tuiles.add(new Tuile(couleur, symbole));
                    tuiles.add(new Tuile(couleur, symbole));
                }
            }
            Collections.shuffle(tuiles);
            pioches[i] = new Pioche(tuiles);
            racks[i] = new Rack(pioches[i]);
            for (int j = 0; j < 5; j++) {
                racks[i].ajoutTuile(pioches[i].piocher());
            }
            racksHbox[i] = new HBox(5);
        }

        updateRack();
        updateTuilesRestantes();

        infoZone.getChildren().addAll(tourLabel, tuilesRestantesLabel[0], tuilesRestantesLabel[1], racksHbox[0], racksHbox[1]);
        root.getChildren().addAll(board, infoZone);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Latice Game - 2 Joueurs");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        for (int j = 0; j < 2; j++) {
            racksHbox[j].getChildren().clear();

            if (j != joueurActuel) continue; // Ne montre que le rack du joueur actuel

            for (int i = 0; i < racks[j].getTuiles().size(); i++) {
                Tuile tuile = racks[j].getTuiles().get(i);
                String imagePath = BASE_PATH + tuile.getImagePath();
                try {
                    ImageView tileView = new ImageView(new Image(imagePath));
                    tileView.setFitWidth(TILE_SIZE);
                    tileView.setFitHeight(TILE_SIZE);

                    final int index = i;
                    final int joueur = j;

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
        if (tuilesRestantesLabel[0] == null || tuilesRestantesLabel[1] == null) return;
        for (int i = 0; i < 2; i++) {
            int nbTuiles = pioches[i].taille();
            tuilesRestantesLabel[i].setText("Tuiles restantes Joueur " + (i + 1) + ": " + nbTuiles);
        }
    }


    private void removeTileFromRack(int joueur, int index) {
        if (index >= 0 && index < racks[joueur].getTuiles().size()) {
            racks[joueur].getTuiles().remove(index);
            if (pioches[joueur].taille() > 0) {
                racks[joueur].ajoutTuile(pioches[joueur].piocher());
            }
        }
        tuileSelectionnee = null;
        indexTuileSelectionnee = -1;
        updateRack();
        updateTuilesRestantes();
    }

    private void changerDeTour() {
        joueurActuel = (joueurActuel + 1) % 2;
        tourLabel.setText("Tour du Joueur " + (joueurActuel + 1));
        updateRack();
        updateTuilesRestantes();
    }
    
    public Tuile getTuileAt(int x, int y) {
    	return plateau.get(new PositionTuiles(x,y));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
*/