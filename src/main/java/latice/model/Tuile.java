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
		return "Tuile avec la couleur " + this.couleur.getcouleur() + " et le symbole "+ this.symbole.getSymbole() + " ." ;
	}

	public String getImagePath() {
		return this.symbole.getSymbole() +"_"+this.couleur.getcouleur()+".png";
	}
}
