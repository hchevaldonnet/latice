package latice.ihm;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import latice.model.PositionCaseSoleil;
import latice.model.PositionTuiles;
import latice.model.Plateau;
import latice.model.Tuile;

public class PlateauVueJavaFX implements PlateauVue {

    static final int DIMENSION_GRILLE = 9;
    static final int DIMENSION_IMAGE_TUILE = 50;
    static final String CHEMIN_RESSOURCES_IMAGES = "/latice/ihm/view/plateau_photo/";

    private final LaticePlateau laticePlateau;
    private GridPane grille; 

    public PlateauVueJavaFX(LaticePlateau laticePlateau) {
        this.laticePlateau = laticePlateau;
    }

    @Override
    public void afficherPlateau(Plateau plateau) {
        if (grille == null) {
            return; 
        }
        for (int ligne = 0; ligne < DIMENSION_GRILLE; ligne++) {
            for (int col = 0; col < DIMENSION_GRILLE; col++) {
                ImageView tuileView = getImageViewFromGridPane(grille, col, ligne);
                if (tuileView == null) continue;

                PositionTuiles pos = new PositionTuiles(ligne, col);
                Tuile tuileSurPlateau = plateau.getTuile(pos);

                if (tuileSurPlateau != null) {
                    tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + tuileSurPlateau.getImagePath()));
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
            }
        }
    }

    public GridPane creerPlateau() {
        grille = new GridPane(); 

        for (int ligne = 0; ligne < DIMENSION_GRILLE; ligne++) {
            for (int col = 0; col < DIMENSION_GRILLE; col++) {
                final int ligneCourante = ligne;
                final int colCourante = col;

                ImageView tuileView = new ImageView();
                tuileView.setFitWidth(DIMENSION_IMAGE_TUILE);
                tuileView.setFitHeight(DIMENSION_IMAGE_TUILE);
                tuileView.setUserData(false); 

                PositionTuiles position = new PositionTuiles(ligne, col);
                if (PositionCaseSoleil.estUneCaseSoleil(ligne, col)) {
                    tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "soleil.png"));
                } else if (position.estUneCaseLune(ligne, col)) {
                    tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "lune.png"));
                } else {
                    tuileView.setImage(new Image(CHEMIN_RESSOURCES_IMAGES + "ocean.png"));
                }

                tuileView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    laticePlateau.gererClicTuile(ligneCourante, colCourante, tuileView);
                });

                grille.add(tuileView, col, ligne);
            }
        }
        return grille;
    }

    
    private ImageView getImageViewFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row && node instanceof ImageView) {
                return (ImageView) node;
            }
        }
        return null;
    }
}

