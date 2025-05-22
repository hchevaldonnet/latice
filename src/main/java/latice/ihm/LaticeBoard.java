package latice.ihm;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import latice.model.*;

public class LaticeBoard extends Application {

    private static final int SIZE = 9;
    private static final int TILE_SIZE = 50;
    private static final String BASE_PATH = "/latice/ihm/view/plateau_photo/";
    private static final int NB_JOUEURS = 2;
    private boolean tourSupplementaireActif = false;

    private Rack[] racks = new Rack[NB_JOUEURS];
    private Pioche pioches = new Pioche(NB_JOUEURS);
    private HBox[] racksHbox = new HBox[NB_JOUEURS];
    private Arbitre arbitre = new Arbitre(NB_JOUEURS);
    private Joueur[] joueurs = new Joueur[NB_JOUEURS];
    private int joueurActuel = 0;

    private Tuile tuileSelectionnee = null;
    private int indexTuileSelectionnee = -1;
    private boolean premierCoup = true;
    private Map<PositionTuiles, Tuile> plateau = new HashMap<>();

    private Label tourLabel;
    private Label[] tuilesRestantesLabel = new Label[NB_JOUEURS];
    private Label[] pointsLabels = new Label[NB_JOUEURS];

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane board = createBoard();
        root.setCenter(board);

        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(15));
        tourLabel = new Label("Tour du Joueur 1");
        tourLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");

        for (int i = 0; i < NB_JOUEURS; i++) {
            racks[i] = new Rack(pioches);
            racksHbox[i] = new HBox(5);
            tuilesRestantesLabel[i] = new Label();
            pointsLabels[i] = new Label("Points Joueur " + (i + 1) + ": 0");
            joueurs[i] = new Joueur("Joueur " + (i + 1), racks[i], pioches, 0, 0);
        }

        arbitre.distribuerTuiles(joueurs);
        updateRack();
        updateTuilesRestantes();

        VBox rackBox = new VBox(10);
        rackBox.setPadding(new Insets(10));
        for (int i = 0; i < NB_JOUEURS; i++) {
            VBox playerBox = new VBox(5);
            playerBox.getChildren().addAll(pointsLabels[i], tuilesRestantesLabel[i], racksHbox[i]);
            rackBox.getChildren().add(playerBox);
        }

        VBox actionsBox = new VBox(10);
        actionsBox.setPadding(new Insets(10));
        Button btnPasserTour = new Button("Passer le tour");
        Button btnEchangerRack = new Button("Échanger le rack");
        Button btnTourSupplementaire = new Button("Acheter un tour supplémentaire (-2 pts)");

        btnPasserTour.setOnAction(e -> {
            showAlert("Tour passé.");
            changerDeTour();
        });

        btnEchangerRack.setOnAction(e -> {
            boolean possible = !joueurs[joueurActuel].getPioche().estVide(joueurActuel);
            if (possible) {
                joueurs[joueurActuel].jouerActionSpeciale(joueurActuel, ActionSpeciale.ECHANGER_RACK,
                        joueurs[joueurActuel].getRack(), joueurs[joueurActuel].getPioche(), arbitre);
                updateRack();
                changerDeTour();
                showAlert("Rack échangé !");
            } else {
                showAlert("Impossible d’échanger, la pioche est vide.");
            }
        });

        btnTourSupplementaire.setOnAction(e -> {
            boolean success = joueurs[joueurActuel].jouerActionSpeciale(joueurActuel, ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                    joueurs[joueurActuel].getRack(), joueurs[joueurActuel].getPioche(), arbitre);
            if (success) {
            	tourSupplementaireActif = true;
                updatePoints();
                showAlert("Tour supplémentaire accordé !");
            } else {
                showAlert("Pas assez de points !");
            }
        });

        actionsBox.getChildren().addAll(tourLabel, btnPasserTour, btnEchangerRack, btnTourSupplementaire);
        sidePanel.getChildren().addAll(actionsBox, rackBox);
        root.setRight(sidePanel);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Latice Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Arbitre");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
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
                tile.setUserData(false);

                PositionTuiles position = new PositionTuiles(row, col);
                if (position.isSunTile(row, col)) tile.setImage(new Image(BASE_PATH + "soleil.png"));
                else if (position.isMoonTile(row, col)) tile.setImage(new Image(BASE_PATH + "lune.png"));

                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (tuileSelectionnee != null && !(boolean) tile.getUserData()) {
                        int result = arbitre.verifierCoup(currentRow, currentCol, tuileSelectionnee, plateau, premierCoup, joueurActuel);
                        if (result != -1) {
                            tile.setImage(new Image(BASE_PATH + tuileSelectionnee.getImagePath()));
                            tile.setUserData(true);
                            plateau.put(new PositionTuiles(currentRow, currentCol), tuileSelectionnee);
                            removeTileFromRack(joueurActuel, indexTuileSelectionnee);
                            premierCoup = false;
                            updatePoints();
                            changerDeTour();
                        } else {
                            showAlert("Coup invalide !");
                        }
                    }
                });
                grid.add(tile, col, row);
            }
        }
        return grid;
    }

    private void updateRack() {
        for (int j = 0; j < NB_JOUEURS; j++) {
        	final int joueur = j;
            racksHbox[j].getChildren().clear();
            if (joueur != joueurActuel) continue;
            for (int i = 0; i < racks[j].getTuiles().size(); i++) {
                Tuile tuile = racks[j].getTuiles().get(i);
                String imagePath = BASE_PATH + tuile.getImagePath();
                ImageView tileView = new ImageView(new Image(imagePath));
                tileView.setFitWidth(TILE_SIZE);
                tileView.setFitHeight(TILE_SIZE);

                final int index = i;
                tileView.setOnMouseClicked(event -> {
                    tuileSelectionnee = tuile;
                    indexTuileSelectionnee = index;
                    highlightSelectedTile(joueur, index);
                });

                racksHbox[j].getChildren().add(tileView);
            }
        }
    }

    private void highlightSelectedTile(int joueur, int selectedIndex) {
        for (int i = 0; i < racksHbox[joueur].getChildren().size(); i++) {
            ImageView tuileView = (ImageView) racksHbox[joueur].getChildren().get(i);
            tuileView.setEffect(i == selectedIndex ? new DropShadow(20, Color.BLUE) : null);
        }
    }

    private void removeTileFromRack(int joueur, int index) {
        if (index >= 0 && index < racks[joueur].getTuiles().size()) {
            racks[joueur].getTuiles().remove(index);
            if (!pioches.estVide(joueur)) {
                Tuile nouvelle = pioches.piocher(joueur);
                if (nouvelle != null) racks[joueur].ajoutTuile(nouvelle);
            }
            tuileSelectionnee = null;
            indexTuileSelectionnee = -1;
            updateRack();
            updateTuilesRestantes();
        }
    }

    private void changerDeTour() {
        if (tourSupplementaireActif) {
            tourSupplementaireActif = false;
            showAlert("Vous avez joué votre tour supplémentaire.");
        } else {
            joueurActuel = (joueurActuel + 1) % NB_JOUEURS;
        }
        tourLabel.setText("Tour du Joueur " + (joueurActuel + 1));
        updateRack();
        updateTuilesRestantes();
        if (arbitre.finDePartie(racks, pioches)) proclamerResultats();
    }

    private void updateTuilesRestantes() {
        for (int i = 0; i < NB_JOUEURS; i++) {
        	int tuilesRestantes = pioches.taille(joueurActuel);
            tuilesRestantesLabel[i].setText("Tuiles restantes Joueur " + (i + 1) + ": " + tuilesRestantes);
        }
    }

    private void updatePoints() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            pointsLabels[i].setText(arbitre.getPoints(i));
        }
    }

    private void proclamerResultats() {
        StringBuilder resultats = new StringBuilder("Fin de la partie !\n");
        int meilleurScore = -1, gagnant = -1;
        for (int i = 0; i < NB_JOUEURS; i++) {
            int score = arbitre.getScore(i);
            resultats.append("Joueur ").append(i + 1).append(" : ").append(score).append(" points\n");
            if (score > meilleurScore) {
                meilleurScore = score;
                gagnant = i;
            }
        }
        resultats.append("\nLe gagnant est : Joueur ").append(gagnant + 1).append(" !");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat Final");
        alert.setHeaderText("La partie est terminée");
        alert.setContentText(resultats.toString());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
