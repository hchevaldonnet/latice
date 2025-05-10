package latice.model;

public class Tuile {
	private Couleur couleur;
	private Symbole symbole;
	
	public Tuile(Couleur couleur, Symbole symbole) {
		this.couleur = couleur;
		this.symbole = symbole;
	}
	
	@Override
	public String toString() {
		return this.couleur.getcouleur() + this.symbole.getSymbole() + "\u001B[0m" + " ";
	}	
	
}
