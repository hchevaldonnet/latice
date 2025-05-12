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

		return this.couleur.getcouleur() +  this.symbole.getSymboleUnicode() + "\u001B[0m" ;
	}

	public String getImagePath() {
		return this.symbole.getSymboleTexte() +"_"+this.couleur.getcouleur()+".png";
	

		
	}	
	

}
