package latice.application;

public class Tuile {
	private Couleur couleur;
	private Symbole symbole;
	
	public Tuile(Couleur couleur, Symbole symbole) {
		this.couleur = couleur;
		this.symbole = symbole;
	}
	
	@Override
	public String toString() {
		return "Tuile avec la couleur" + this.couleur.getcouleur() + this.symbole.getSymbole() ;
	}
	
	
}
