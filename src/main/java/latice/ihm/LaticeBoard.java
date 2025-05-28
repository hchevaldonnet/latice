package latice.ihm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        
        String[] nomsJoueurs = new String[NB_JOUEURS];
        for (int i = 0; i < NB_JOUEURS; i++) {
            boolean nomValide = false;
            while (!nomValide) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Nom du Joueur");
                dialog.setHeaderText("Entrez le nom du Joueur " + (i + 1));
                dialog.setContentText("Nom:");

                String nomEntre = dialog.showAndWait().orElse(null);

                if (nomEntre != null) {
                    nomEntre = nomEntre.trim();
                    if (!nomEntre.isEmpty()) {
                        nomsJoueurs[i] = nomEntre;
                        nomValide = true;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Nom invalide");
                        alert.setContentText("Le nom ne peut pas être vide !");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Nom requis");
                    alert.setContentText("Vous devez entrer un nom pour continuer.");
                    alert.showAndWait();
                }
            }
        }

        VBox sidePanel = new VBox(10);
        sidePanel.setPadding(new Insets(15));
        tourLabel = new Label();
        joueurActuel = new Random().nextInt(NB_JOUEURS);
        
        for (int i = 0; i < NB_JOUEURS; i++) {
            racks[i] = new Rack(pioches);
            racksHbox[i] = new HBox(5);
            tuilesRestantesLabel[i] = new Label();
            pointsLabels[i] = new Label("Points " + nomsJoueurs[i] + " : 0");
            joueurs[i] = new Joueur(nomsJoueurs[i], racks[i], pioches);
        }
        
        tourLabel.setText("Tour de " + joueurs[joueurActuel].getName());
        tourLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px");

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
        
        btnPasserTour.setPrefWidth(300);
        btnEchangerRack.setPrefWidth(300);
        btnTourSupplementaire.setPrefWidth(300);

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
        primaryStage.setResizable(false);
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

        if (arbitre.finDePartie(racks, pioches)) {
            int gagnant = arbitre.getGagnant(racks);
            proclamerResultats(gagnant);
            return; // Stoppe ici, ne continue pas le tour
        }

        tourLabel.setText("Tour de " + joueurs[joueurActuel].getName());
        updateRack();
        updateTuilesRestantes();
    }


    private void updateTuilesRestantes() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            int tuilesRestantes = pioches.taille(i);
            tuilesRestantesLabel[i].setText("Tuiles restantes " + joueurs[i].getName() + " : " + tuilesRestantes);
        }
    }


    private void updatePoints() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            int score = arbitre.getScore(i);
            pointsLabels[i].setText("Points " + joueurs[i].getName() + " : " + score);
        }
    }

    private void proclamerResultats(int gagnant) {
        StringBuilder resultats = new StringBuilder("Fin de la partie !\n");
        for (int i = 0; i < NB_JOUEURS; i++) {
            resultats.append(joueurs[i].getName())
                     .append(" : ")
                     .append(arbitre.getScore(i))
                     .append(" points\n");
        }
        resultats.append("\nLe gagnant est : ").append(joueurs[gagnant].getName()).append(" !");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat Final");
        alert.setHeaderText("Un joueur a posé toutes ses tuiles !");
        alert.setContentText(resultats.toString());

        alert.setOnHidden(e -> {
            Stage stage = (Stage) tourLabel.getScene().getWindow();
            stage.close();
        });

        alert.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
