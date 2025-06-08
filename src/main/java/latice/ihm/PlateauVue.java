package latice.ihm;

import javafx.scene.layout.Pane;
import latice.model.Plateau;

public interface PlateauVue {
	
	void afficherPlateau(Plateau plateau);
	
	public Pane getVuePlateau();
}
