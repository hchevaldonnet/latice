package latice.model;

import java.util.Map;

public class Arbitre {
    public int[] pointsJoueur;    // Les points sont illimitées

    public Arbitre(int nombreJoueurs) {
        pointsJoueur = new int[nombreJoueurs];
    }
	
 // Nouvelle signature de la méthode estCoupValide qui renvoie le nombre de correspondances (0 à 4)
    public int verifierCoup(int currentRow, int currentCol, Tuile tuileSelectionnee, 
                                            Map<PositionTuiles, Tuile> plateau, boolean premierCoup, int joueurActuel) {
        if (premierCoup && (currentRow != 4 || currentCol != 4)) {
            return -1; // Coup invalide car ce n'est pas au centre
        }

        int correspondances = 0;
        boolean jouable = true;

        if (!premierCoup) {
            jouable = false;

            // Vérifie la tuile en dessous
            if (plateau.containsKey(new PositionTuiles(currentRow + 1, currentCol))) {
                jouable = true;
                Tuile tuileBas = plateau.get(new PositionTuiles(currentRow + 1, currentCol));
                if (!sontCompatibles(tuileSelectionnee, tuileBas)) return -1;
                correspondances++;
            }

            // Vérifie la tuile au-dessus
            if (plateau.containsKey(new PositionTuiles(currentRow - 1, currentCol))) {
                jouable = true;
                Tuile tuileHaut = plateau.get(new PositionTuiles(currentRow - 1, currentCol));
                if (!sontCompatibles(tuileSelectionnee, tuileHaut)) return -1;
                correspondances++;
            }

            // Vérifie la tuile à gauche
            if (plateau.containsKey(new PositionTuiles(currentRow, currentCol - 1))) {
                jouable = true;
                Tuile tuileGauche = plateau.get(new PositionTuiles(currentRow, currentCol - 1));
                if (!sontCompatibles(tuileSelectionnee, tuileGauche)) return -1;
                correspondances++;
            }

            // Vérifie la tuile à droite
            if (plateau.containsKey(new PositionTuiles(currentRow, currentCol + 1))) {
                jouable = true;
                Tuile tuileDroite = plateau.get(new PositionTuiles(currentRow, currentCol + 1));
                if (!sontCompatibles(tuileSelectionnee, tuileDroite)) return -1;
                correspondances++;
            }
        }

        if (jouable) {
            calculerPoints(correspondances, new PositionTuiles(currentRow, currentCol), joueurActuel);
            return correspondances; // Retourne le nombre de correspondances
        }

        return -1; // Coup non jouable
    }


     // Vérifie si deux tuiles sont compatibles (même symbole ou même couleur)
     private boolean sontCompatibles(Tuile tuile1, Tuile tuile2) {
         return tuile1.symbole == tuile2.symbole || tuile1.couleur == tuile2.couleur;
     }
	
     public void calculerPoints(int correspondances, PositionTuiles position, int joueurActuel) {
    	    if (correspondances == 2) { // Double
    	        pointsJoueur[joueurActuel] += 1;
    	    } else if (correspondances == 3) { // Trefoil
    	        pointsJoueur[joueurActuel] += 2;
    	    } else if (correspondances == 4) { // Latice
    	    	pointsJoueur[joueurActuel] += 4;
    	            }

    	    // Bonus pour une case soleil
    	    if (position.isSunTile(position.getX(), position.getY())) {
    	    	pointsJoueur[joueurActuel] += 1;
    	        }
    	    }


     // Méthode pour obtenir les points des joueurs (affichable dans l'interface)
     public String getPoints(int joueur) {
         return "Joueur " + (joueur + 1) + " Points : " + pointsJoueur[joueur];
     }
	
	public void finDeTour() {
		//TODO
	}
	
	public boolean finDePartie() {
		return true;
		//TODO
	}
	
	public Joueur proclamerResultats() {
		return null;
		//return new Joueur();
		//TODO
	}
}
