package latice.ihm;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import latice.model.Rack;
import latice.model.Tuile;

public class RackViewFX implements RackView {
    
    private HBox rackContainer;
    private static final int TILE_SIZE = 50;
    
    public RackViewFX() {
        this.rackContainer = new HBox(5);
        this.rackContainer.setPadding(new Insets(10));
    }
    
    @Override
    public void afficherRack(Rack rack) {
        rackContainer.getChildren().clear();
        
        if (rack.getTuiles().isEmpty()) {
            Text emptyText = new Text("Rack vide");
            rackContainer.getChildren().add(emptyText);
            return;
        }
        
        for (int i = 0; i < rack.getTuiles().size(); i++) {
            Tuile tuile = rack.getTuiles().get(i);
            StackPane tileView = createTileView(tuile, i);
            rackContainer.getChildren().add(tileView);
        }
    }
    
    private StackPane createTileView(Tuile tuile, int index) {
        StackPane tilePane = new StackPane();
        
        // Créer un rectangle pour la tuile
        Rectangle background = new Rectangle(TILE_SIZE, TILE_SIZE);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);
        
        // Ajouter le symbole avec la couleur appropriée
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
        
        // Ajouter un petit numéro pour indiquer l'index
        Text indexText = new Text(String.valueOf(index + 1));
        indexText.setStyle("-fx-font-size: 10px;");
        indexText.setTranslateX(-TILE_SIZE/2 + 5);
        indexText.setTranslateY(-TILE_SIZE/2 + 10);
        
        tilePane.getChildren().addAll(background, symboleText, indexText);
        return tilePane;
    }
    
    public HBox getRackContainer() {
        return rackContainer;
    }
}
//Sert a rien

