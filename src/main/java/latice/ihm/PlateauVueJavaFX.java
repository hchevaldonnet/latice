package latice.ihm;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import latice.model.PositionCaseSoleil;
import latice.model.PositionTuiles;
import latice.model.Plateau;

public class PlateauVueJavaFX implements PlateauVue {

    static final int TAILLE = 9;
    static final int TUILE_TAILLE = 50;
    static final String CHEMIN_IMAGE = "/latice/ihm/view/plateau_photo/";

    private final LaticePlateau laticeBoard;

    public PlateauVueJavaFX(LaticePlateau laticeBoard) {
        this.laticeBoard = laticeBoard;
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        // Méthode d'affichage que tu peux utiliser plus tard pour rafraîchir visuellement le plateau.
    }

    public GridPane createBoard() {
        GridPane grille = new GridPane();

        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int col = 0; col < TAILLE; col++) {
                final int ligneCourante = ligne;
                final int colCourante = col;

                ImageView tuileView = new ImageView();
                tuileView.setFitWidth(TUILE_TAILLE);
                tuileView.setFitHeight(TUILE_TAILLE);
                tuileView.setImage(new Image(CHEMIN_IMAGE + "ocean.png"));
                tuileView.setUserData(false);

                PositionTuiles position = new PositionTuiles(ligne, col);
                if (PositionCaseSoleil.estUneCaseSoleil(ligne, col)) {
                    tuileView.setImage(new Image(CHEMIN_IMAGE + "soleil.png"));
                } else if (position.estUneCaseLune(ligne, col)) {
                    tuileView.setImage(new Image(CHEMIN_IMAGE + "lune.png"));
                }

                tuileView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    laticeBoard.gererClicTuile(ligneCourante, colCourante, tuileView);
                });

                grille.add(tuileView, col, ligne);
            }
        }

        return grille;
    }
}
