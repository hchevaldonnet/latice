package latice.ihm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import latice.model.PositionCaseSoleil;
import latice.model.PositionTuiles;
import latice.model.Plateau;
import latice.model.Tuile;

public class PlateauVueJavaFX implements PlateauVue {

    static final int DIMENSION_GRILLE = 9;
    static final int DIMENSION_IMAGE_TUILE = 50;
    static final String CHEMIN_RESSOURCES_IMAGES = "/latice/ihm/view/plateau_photo/";

    private final LaticePlateau laticePlateau;
    private final GridPane grille = new GridPane();

    public PlateauVueJavaFX(LaticePlateau laticePlateau) {
        this.laticePlateau = laticePlateau;
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        grille.getChildren().clear();

        for (int ligne = 0; ligne < DIMENSION_GRILLE; ligne++) {
            for (int col = 0; col < DIMENSION_GRILLE; col++) {
                final int ligneCourante = ligne;
                final int colCourante = col;

                ImageView tuileView = new ImageView();
                tuileView.setFitWidth(DIMENSION_IMAGE_TUILE);
                tuileView.setFitHeight(DIMENSION_IMAGE_TUILE);

                PositionTuiles pos = new PositionTuiles(ligne, col);
                Tuile tuileSurPlateau = plateau.getTuile(pos);

                if (tuileSurPlateau != null) {
                    tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + tuileSurPlateau.getCheminImage()));
                    tuileView.setUserData(true);
                } else {
                    if (PositionCaseSoleil.estUneCaseSoleil(ligne, col)) {
                        tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "soleil.png"));
                    } else if (pos.estUneCaseLune(ligne, col)) {
                        tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "lune.png"));
                    } else {
                        tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "ocean.png"));
                    }
                    tuileView.setUserData(false);
                }

                tuileView.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                    laticePlateau.gererClicTuile(ligneCourante, colCourante, tuileView)
                );

                grille.add(tuileView, col, ligne);
            }
        }
    }

    @Override
    public Pane getVuePlateau() {
        return grille;
    }
}
