package latice.ihm;

import java.util.HashMap;
import java.util.List;
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
	
    private static final int TAILLE = 9;
    private static final int TUILE_TAILLE = 50;
    private static final String CHEMIN_IMAGE = "/latice/ihm/view/plateau_photo/";
    private static final int NB_JOUEURS = 2;
    private int totalTours = 0;
    private final int MAX_TOURS = 20;
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
    private Label labelToursRestants;
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
                        Alert alerte = new Alert(Alert.AlertType.ERROR);
                        alerte.setTitle("Erreur");
                        alerte.setHeaderText("Nom invalide");
                        alerte.setContentText("Le nom ne peut pas être vide !");
                        alerte.showAndWait();
                    }
                } else {
                    Alert alerte = new Alert(Alert.AlertType.ERROR);
                    alerte.setTitle("Erreur");
                    alerte.setHeaderText("Nom requis");
                    alerte.setContentText("Vous devez entrer un nom pour continuer.");
                    alerte.showAndWait();
                }
            }
        }

        VBox vboxADroite = new VBox(10);
        vboxADroite.setPadding(new Insets(15));
        tourLabel = new Label();
        joueurActuel = new Random().nextInt(NB_JOUEURS);
        labelToursRestants = new Label();
        labelToursRestants.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        majLabelTours();
        
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
        majRack();
        majTuilesRestantes();

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
        Button btnEchangerRack = new Button("Échanger le rack (1 pt)");
        Button btnTourSupplementaire = new Button("Acheter un tour supplémentaire (-2 pts)");
        
        btnPasserTour.setPrefWidth(300);
        btnEchangerRack.setPrefWidth(300);
        btnTourSupplementaire.setPrefWidth(300);

        btnPasserTour.setOnAction(e -> {
            montrerAlert("Tour passé.");
            changerDeTour();
        });

        btnEchangerRack.setOnAction(e -> {
            boolean possible = joueurs[joueurActuel].jouerActionSpeciale(
                joueurActuel,
                ActionSpeciale.ECHANGER_RACK,
                joueurs[joueurActuel].getRack(),
                joueurs[joueurActuel].getPioche(),
                arbitre
            );

            if (possible) {
                majRack();
                majPoints();
                changerDeTour();
                montrerAlert("Rack échangé avec succès (-1 point) !");
            } else {
                montrerAlert("Impossible d’échanger le rack (pioche vide ou pas assez de points).");
            }
        });

        btnTourSupplementaire.setOnAction(e -> {
            boolean possible = joueurs[joueurActuel].jouerActionSpeciale(joueurActuel, ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                    joueurs[joueurActuel].getRack(), joueurs[joueurActuel].getPioche(), arbitre);
            if (possible) {
            	tourSupplementaireActif = true;
                majPoints();
                montrerAlert("Tour supplémentaire accordé !");
            } else {
                montrerAlert("Pas assez de points !");
            }
        });

        actionsBox.getChildren().addAll(tourLabel, btnPasserTour, btnEchangerRack, btnTourSupplementaire);
        vboxADroite.getChildren().addAll(actionsBox, rackBox, labelToursRestants);
        root.setRight(vboxADroite);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Latice Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void montrerAlert(String msg) {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Arbitre");
        alerte.setHeaderText(null);
        alerte.setContentText(msg);
        alerte.showAndWait();
    }

    private GridPane createBoard() {
        GridPane grille = new GridPane();
        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int col = 0; col < TAILLE; col++) {
                final int ligneCourante = ligne;
                final int colCourante = col;
                ImageView tuile = new ImageView(new Image(CHEMIN_IMAGE + "ocean.png"));
                tuile.setFitWidth(TUILE_TAILLE);
                tuile.setFitHeight(TUILE_TAILLE);
                tuile.setUserData(false);

                PositionTuiles position = new PositionTuiles(ligne, col);
                if (PositionTuiles.estUneCaseSoleil(ligne, col)) tuile.setImage(new Image(CHEMIN_IMAGE + "soleil.png"));
                else if (position.estUneCaseLune(ligne, col)) tuile.setImage(new Image(CHEMIN_IMAGE + "lune.png"));

                tuile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (tuileSelectionnee != null && !(boolean) tuile.getUserData()) {
                        int resultat = arbitre.verifierCoup(ligneCourante, colCourante, tuileSelectionnee, plateau, premierCoup, joueurActuel);
                        if (resultat != -1) {
                            tuile.setImage(new Image(CHEMIN_IMAGE + tuileSelectionnee.getImagePath()));
                            tuile.setUserData(true);
                            plateau.put(new PositionTuiles(ligneCourante, colCourante), tuileSelectionnee);
                            arbitre.calculerPointsAprèsCoup(ligneCourante, colCourante, resultat, joueurActuel);
                            retirerTuileDuRack(joueurActuel, indexTuileSelectionnee);
                            premierCoup = false;
                            majPoints();
                            changerDeTour();
                        } else {
                            montrerAlert("Coup invalide !");
                        }
                    }
                });
                grille.add(tuile, col, ligne);
            }
        }
        return grille;
    }

    private void majRack() {
        for (int j = 0; j < NB_JOUEURS; j++) {
        	final int joueur = j;
            racksHbox[j].getChildren().clear();
            if (joueur != joueurActuel) continue;
            for (int i = 0; i < racks[j].getTuiles().size(); i++) {
                Tuile tuile = racks[j].getTuiles().get(i);
                String cheminImage = CHEMIN_IMAGE + tuile.getImagePath();
                ImageView tileVue = new ImageView(new Image(cheminImage));
                tileVue.setFitWidth(TUILE_TAILLE);
                tileVue.setFitHeight(TUILE_TAILLE);

                final int index = i;
                tileVue.setOnMouseClicked(event -> {
                    tuileSelectionnee = tuile;
                    indexTuileSelectionnee = index;
                    tuileBrillante(joueur, index);
                });

                racksHbox[j].getChildren().add(tileVue);
            }
        }
    }
    
    private void majLabelTours() {
        int toursRestants = MAX_TOURS - totalTours;
        labelToursRestants.setText("Tours restants : " + toursRestants + " / " + MAX_TOURS);
    }

    private void tuileBrillante(int joueur, int selectedIndex) {
        for (int i = 0; i < racksHbox[joueur].getChildren().size(); i++) {
            ImageView tuileView = (ImageView) racksHbox[joueur].getChildren().get(i);
            tuileView.setEffect(i == selectedIndex ? new DropShadow(20, Color.BLUE) : null);
        }
    }

    private void retirerTuileDuRack(int joueur, int index) {
        if (index >= 0 && index < racks[joueur].getTuiles().size()) {
            racks[joueur].getTuiles().remove(index);
            if (!pioches.estVide(joueur)) {
                Tuile nouvelle = pioches.piocher(joueur);
                if (nouvelle != null) racks[joueur].ajoutTuile(nouvelle);
            }
            tuileSelectionnee = null;
            indexTuileSelectionnee = -1;
            majRack();
            majTuilesRestantes();
        }
    }

    private void changerDeTour() {
        if (tourSupplementaireActif) {
            tourSupplementaireActif = false;
            montrerAlert("Vous avez joué votre tour supplémentaire.");
        } else {
            joueurActuel = (joueurActuel + 1) % NB_JOUEURS;
            totalTours++;
            majLabelTours();
        }

        if (arbitre.finDePartie(racks, totalTours, MAX_TOURS)) {
        	List<Integer> gagnants = arbitre.getGagnants(joueurs);
        	proclamerResultats(gagnants);
            return; // Stoppe ici, ne continue pas le tour
        }

        tourLabel.setText("Tour de " + joueurs[joueurActuel].getName());
        majRack();
        majTuilesRestantes();
    }


    private void majTuilesRestantes() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            int tuilesRestantes = pioches.taille(i);
            tuilesRestantesLabel[i].setText("Tuiles restantes " + joueurs[i].getName() + " : " + tuilesRestantes);
        }
    }


    private void majPoints() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            int score = arbitre.getScore(i);
            pointsLabels[i].setText("Points " + joueurs[i].getName() + " : " + score);
        }
    }

    private void proclamerResultats(List<Integer> gagnants) {
        StringBuilder resultats = new StringBuilder("Fin de la partie !\n");

        for (int i = 0; i < NB_JOUEURS; i++) {
            int rackTuiles = joueurs[i].getRack().getTuiles().size();
            int piocheTuiles = joueurs[i].getPioche().taille(i);
            int total = rackTuiles + piocheTuiles;
            resultats.append(joueurs[i].getName())
                     .append(" : ")
                     .append(total)
                     .append(" tuiles restantes (Rack: ")
                     .append(rackTuiles)
                     .append(", Pioche: ")
                     .append(piocheTuiles)
                     .append(")\n");
        }

        resultats.append("\n");

        if (gagnants.size() == 1) {
            int gagnant = gagnants.get(0);
            resultats.append("Le gagnant est : ")
                     .append(joueurs[gagnant].getName())
                     .append(" avec ")
                     .append(joueurs[gagnant].getRack().getTuiles().size() + joueurs[gagnant].getPioche().taille(gagnant))
                     .append(" tuiles restantes !");
        } else {
            resultats.append("Égalité entre : ");
            for (int i = 0; i < gagnants.size(); i++) {
                resultats.append(joueurs[gagnants.get(i)].getName());
                if (i < gagnants.size() - 1) resultats.append(", ");
            }
            resultats.append(" avec ")
                     .append(joueurs[gagnants.get(0)].getRack().getTuiles().size() + joueurs[gagnants.get(0)].getPioche().taille(gagnants.get(0)))
                     .append(" tuiles restantes !");
        }

        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Résultat Final");
        alerte.setHeaderText("Fin de partie !");
        alerte.setContentText(resultats.toString());

        alerte.setOnHidden(e -> {
            Stage stage = (Stage) tourLabel.getScene().getWindow();
            stage.close();
        });

        alerte.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
