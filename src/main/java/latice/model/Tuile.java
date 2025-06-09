package latice.model;

public class Tuile {
	public Couleur couleur;
	public Symbole symbole;
	
	public Tuile(Couleur couleur, Symbole symbole) {
		this.couleur = couleur;
		this.symbole = symbole;
	}
	
	@Override
	public String toString() {
		String symboleUnicode = this.symbole.getSymboleUnicode();
		String suffixePadding = "\u2004"; 
		
		return this.couleur.getAnsiCode() + symboleUnicode + suffixePadding + "\u001B[0m";
	}

	public String getCheminImage() {
		return this.symbole.getSymboleTexte() +"_"+this.couleur.getcouleur()+".png";	
	}	
}




