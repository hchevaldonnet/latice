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
<<<<<<< HEAD
		return "Tuile avec la couleur " + this.couleur.getcouleur() + " et le symbole "+ this.symbole.getSymbole() + " ." ;
	}

	public String getImagePath() {
		return this.symbole.getSymbole() +"_"+this.couleur.getcouleur()+".png";
	}
=======
		return this.couleur.getcouleur() + this.symbole.getSymbole() + "\u001B[0m ";
	}	
	
>>>>>>> d22160ac6c0ff20802b2cacfff5f004d9083b369
}
