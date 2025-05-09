package latice.model;

public enum Couleur {
	ROUGE("\u001B[31mrouge\u001B[0m"),    
    CYAN("\u001B[36mcyan\u001B[0m"),      
    VERT("\u001B[32mvert\u001B[0m"),      
    JAUNE("\u001B[33mjaune\u001B[0m"),    
    VIOLET("\u001B[35mviolet\u001B[0m"),  
    BLEU("\u001B[34mbleu\u001B[0m");     	
	private final String couleur;
	
	private Couleur(String couleur) {
		this.couleur = couleur;
		
	}
	
	public String getcouleur() {
		return this.couleur;
	}
	
}
