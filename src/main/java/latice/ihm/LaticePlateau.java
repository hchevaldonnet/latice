package latice.ihm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import latice.model.*;

public class LaticePlateau extends Application {

    private static final int NB_JOUEURS = 2;
    private final int MAX_TOURS = 20;

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
    private int totalTours = 0;
    private boolean tourSupplementaireActif = false;

    private Label tourLabel;
    private Label labelToursRestants;
    private Label[] tuilesRestantesLabel = new Label[NB_JOUEURS];
    private Label[] pointsLabels = new Label[NB_JOUEURS];

    private PlateauVue plateauVue;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        plateauVue = new PlateauVueJavaFX(this);
        root.setCenter(plateauVue.getVuePlateau());

        String[] nomsJoueurs = new String[NB_JOUEURS];
        for (int i = 0; i < NB_JOUEURS; i++) {
            boolean nomValide = false;
            while (!nomValide) {
                TextInputDialog dialogue = new TextInputDialog();
                dialogue.setTitle("Nom du Joueur");
                dialogue.setHeaderText("Entrez le nom du Joueur " + (i + 1));
                dialogue.setContentText("Nom:");

                String nomEntre = dialogue.showAndWait().orElse(null);

                if (nomEntre != null) {
                    nomEntre = nomEntre.trim();
                    if (!nomEntre.isEmpty()) {
                        nomsJoueurs[i] = nomEntre;
                        nomValide = true;
                    } else {
                        montrerAlerte("Le nom ne peut pas être vide !");
                    }
                } else {
                    montrerAlerte("Vous devez entrer un nom pour continuer.");
                }
            }
        }

        VBox vboxADroite = new VBox(10);
        vboxADroite.setPadding(new Insets(15));

        tourLabel = new Label();
        labelToursRestants = new Label();
        labelToursRestants.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        joueurActuel = new Random().nextInt(NB_JOUEURS);
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
        plateauVue.afficherPlateau(new Plateau(plateau)); // Initialisation visuelle

        VBox rackBox = new VBox(10);
        rackBox.setPadding(new Insets(10));
        for (int i = 0; i < NB_JOUEURS; i++) {
            VBox joueurBox = new VBox(5);
            joueurBox.getChildren().addAll(pointsLabels[i], tuilesRestantesLabel[i], racksHbox[i]);
            rackBox.getChildren().add(joueurBox);
        }

        VBox actionsBox = new VBox(10);
        actionsBox.setPadding(new Insets(10));
        Button btnPasserTour = new Button("Passer le tour");
        Button btnEchangerRack = new Button("Échanger le rack (-1 pt)");
        Button btnTourSupplementaire = new Button("Acheter un tour supplémentaire (-2 pts)");

        btnPasserTour.setPrefWidth(300);
        btnEchangerRack.setPrefWidth(300);
        btnTourSupplementaire.setPrefWidth(300);

        btnPasserTour.setOnAction(e -> {
            montrerAlerte("Tour passé.");
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
                montrerAlerte("Rack échangé avec succès (-1 point) !");
            } else {
                montrerAlerte("Impossible d’échanger le rack (pioche vide ou pas assez de points).");
            }
        });

        btnTourSupplementaire.setOnAction(e -> {
            boolean possible = joueurs[joueurActuel].jouerActionSpeciale(joueurActuel, ActionSpeciale.ACTION_SUPPLEMENTAIRE,
                    joueurs[joueurActuel].getRack(), joueurs[joueurActuel].getPioche(), arbitre);
            if (possible) {
                tourSupplementaireActif = true;
                majPoints();
                montrerAlerte("Tour supplémentaire accordé !");
            } else {
                montrerAlerte("Pas assez de points !");
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

    public void gererClicTuile(int ligne, int col, ImageView tuileView) {
        if (tuileSelectionnee != null && !(boolean) tuileView.getUserData()) {
            int resultat = arbitre.verifierCoup(ligne, col, tuileSelectionnee, plateau, premierCoup);
            if (resultat != -1) {
                tuileView.setImage(new javafx.scene.image.Image(PlateauVueJavaFX.CHEMIN_RESSOURCES_IMAGES + tuileSelectionnee.getCheminImage()));
                tuileView.setUserData(true);
                plateau.put(new PositionTuiles(ligne, col), tuileSelectionnee);
                arbitre.calculerPointsApresCoup(ligne, col, resultat, joueurActuel);
                retirerTuileDuRack(joueurActuel, indexTuileSelectionnee);
                premierCoup = false;
                majPoints();
                changerDeTour();
                plateauVue.afficherPlateau(new Plateau(plateau)); // Rafraîchissement visuel
            } else {
                montrerAlerte("Coup invalide !");
            }
        }
    }

    private void majRack() {
        for (int j = 0; j < NB_JOUEURS; j++) {
            final int joueur = j;
            racksHbox[j].getChildren().clear();
            if (joueur != joueurActuel) continue;
            for (int i = 0; i < racks[j].getTuiles().size(); i++) {
                Tuile tuile = racks[j].getTuiles().get(i);
                String cheminImage = PlateauVueJavaFX.CHEMIN_RESSOURCES_IMAGES + tuile.getCheminImage();
                ImageView tileVue = new ImageView(new javafx.scene.image.Image(cheminImage));
                tileVue.setFitWidth(PlateauVueJavaFX.DIMENSION_IMAGE_TUILE);
                tileVue.setFitHeight(PlateauVueJavaFX.DIMENSION_IMAGE_TUILE);

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
            montrerAlerte("Vous avez joué votre tour supplémentaire.");
        } else {
            joueurActuel = (joueurActuel + 1) % NB_JOUEURS;
            totalTours++;
            majLabelTours();
        }

        if (arbitre.finDePartie(racks, totalTours, MAX_TOURS)) {
            List<Integer> gagnants = arbitre.getGagnants(joueurs);
            proclamerResultats(gagnants);
            return;
        }

        tourLabel.setText("Tour de " + joueurs[joueurActuel].getName());
        majRack();
        majTuilesRestantes();
    }

    private void majLabelTours() {
        int toursRestants = MAX_TOURS - totalTours;
        labelToursRestants.setText("Tours restants : " + toursRestants + " / " + MAX_TOURS);
    }

    private void majTuilesRestantes() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            int tuilesRestantes = pioches.taille(i);
            tuilesRestantesLabel[i].setText("Tuiles restantes " + joueurs[i].getName() + " : " + tuilesRestantes);
        }
    }

    private void majPoints() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            pointsLabels[i].setText("Points " + joueurs[i].getName() + " : " + arbitre.getScore(i));
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

    private void montrerAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Latice");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
