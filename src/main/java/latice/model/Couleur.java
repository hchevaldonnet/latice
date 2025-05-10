package latice.model;

public enum Couleur {
	ROUGE("\u001B[31m"),    
    CYAN("\u001B[36m"),      
    VERT("\u001B[32m"),      
    JAUNE("\u001B[33m"),    
    VIOLET("\u001B[35m"),  
    BLEU("\u001B[34m");     	
	private final String couleur;
	
	private Couleur(String couleur) {
		this.couleur = couleur;
		
	}
	
	public String getcouleur() {
		return this.couleur;
	}
	
}
