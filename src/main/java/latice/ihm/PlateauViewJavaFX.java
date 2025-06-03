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
    private static final int GRID_SIZE = 9; // Changed from 8 to 9
    
    public PlateauViewJavaFX() {
        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(10));
        this.gridPane.setHgap(2);
        this.gridPane.setVgap(2);
    }
    
    @Override
    public void afficherPlateau(Plateau plateau) {
        gridPane.getChildren().clear();
        
        // Création de la grille 9x9
        for (int ligne = 0; ligne < GRID_SIZE; ligne++) {
            for (int colonne = 0; colonne < GRID_SIZE; colonne++) {
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
        
        // Optional: Add row and column indices for better usability
        if (ligne == GRID_SIZE - 1 || colonne == GRID_SIZE - 1) {
            Text indexText = new Text();
            if (ligne == GRID_SIZE - 1) {
                indexText.setText(Integer.toString(colonne + 1));
                indexText.setTranslateY(CELL_SIZE / 2 + 15);
            }
            if (colonne == GRID_SIZE - 1) {
                indexText.setText(Integer.toString(ligne + 1));
                indexText.setTranslateX(CELL_SIZE / 2 + 15);
            }
            indexText.setStyle("-fx-font-size: 10px;");
            indexText.setFill(Color.DARKGRAY);
            cell.getChildren().add(indexText);
        }
        
        return cell;
    }
    
    public GridPane getGridPane() {
        return gridPane;
    }
}

