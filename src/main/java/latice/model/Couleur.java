package latice.model;

public enum Couleur {
	ROUGE("rouge"),    
    CYAN("cyan"),      
    VERT("vert"),      
    JAUNE("jaune"),    
    VIOLET("violet"),  
    BLEU("bleu");
	private final String couleur;
	
	private Couleur(String couleur) {
		this.couleur = couleur;
		
	}
	
	public String getcouleur() {
		return this.couleur;
	}
	
}
