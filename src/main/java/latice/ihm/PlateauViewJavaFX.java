package latice.ihm;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import latice.model.Plateau;
import latice.model.PositionTuiles;
import latice.model.Tuile;

public class PlateauViewJavaFX implements PlateauView {
    
    private GridPane gridPane;
    private static final int CELL_SIZE = 60;
    
    public PlateauViewJavaFX() {
        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(10));
        this.gridPane.setHgap(2);
        this.gridPane.setVgap(2);
    }
    
    @Override
    public void afficherPlateau(Plateau plateau) {
        gridPane.getChildren().clear();
        
        // Création de la grille 8x8
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                StackPane cell = createCell(ligne, colonne, plateau);
                gridPane.add(cell, colonne, ligne);
            }
        }
    }
    
    private StackPane createCell(int ligne, int colonne, Plateau plateau) {
        StackPane cell = new StackPane();
        Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
        
        // Déterminer la couleur de fond de la case
        PositionTuiles position = new PositionTuiles(ligne, colonne);
        if (plateau.caseIsMoon(position)) {
            rectangle.setFill(Color.SILVER);
        } else if (plateau.caseIsSunStones(position)) {
            rectangle.setFill(Color.GOLD);
        } else {
            rectangle.setFill(Color.LIGHTBLUE);
        }
        
        rectangle.setStroke(Color.BLACK);
        cell.getChildren().add(rectangle);
        
        // Ajouter la tuile si la case est occupée
        if (!plateau.caseLibre(position)) {
            Tuile tuile = plateau.getTuile(position);
            Text symboleText = new Text(tuile.symbole.getSymboleUnicode());
            
            // Définir la couleur du texte
            switch (tuile.couleur) {
                case ROUGE: symboleText.setFill(Color.RED); break;
                case BLEU: symboleText.setFill(Color.BLUE); break;
                case VERT: symboleText.setFill(Color.GREEN); break;
                case JAUNE: symboleText.setFill(Color.YELLOW); break;
                case VIOLET: symboleText.setFill(Color.PURPLE); break;
                case CYAN: symboleText.setFill(Color.CYAN); break;
            }
            
            symboleText.setStyle("-fx-font-size: 24px;");
            cell.getChildren().add(symboleText);
        }
        
        return cell;
    }
    
    public GridPane getGridPane() {
        return gridPane;
    }
}
