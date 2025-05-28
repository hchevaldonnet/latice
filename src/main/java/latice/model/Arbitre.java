package latice.model;

import java.util.Map;

public class Arbitre {
    public int[] pointsJoueur;  // Les points sont illimitées

    public Arbitre(int nombreJoueurs) {
        pointsJoueur = new int[nombreJoueurs];
    }
	
    public int verifierCoup(int currentRow, int currentCol, Tuile tuileSelectionnee, Map<PositionTuiles, Tuile> plateau, boolean premierCoup, int joueurActuel) {
        if (premierCoup && (currentRow != 4 || currentCol != 4)) {
            return -1; // Coup invalide car ce n'est pas au centre
        }

        int correspondances = 0;
        boolean jouable = true;

        if (!premierCoup) {
            jouable = false;

            if (plateau.containsKey(new PositionTuiles(currentRow + 1, currentCol))) {
                jouable = true;
                Tuile tuileBas = plateau.get(new PositionTuiles(currentRow + 1, currentCol));
                if (!sontCompatibles(tuileSelectionnee, tuileBas)) return -1;
                correspondances++;
            }

            if (plateau.containsKey(new PositionTuiles(currentRow - 1, currentCol))) {
                jouable = true;
                Tuile tuileHaut = plateau.get(new PositionTuiles(currentRow - 1, currentCol));
                if (!sontCompatibles(tuileSelectionnee, tuileHaut)) return -1;
                correspondances++;
            }

            if (plateau.containsKey(new PositionTuiles(currentRow, currentCol - 1))) {
                jouable = true;
                Tuile tuileGauche = plateau.get(new PositionTuiles(currentRow, currentCol - 1));
                if (!sontCompatibles(tuileSelectionnee, tuileGauche)) return -1;
                correspondances++;
            }

            if (plateau.containsKey(new PositionTuiles(currentRow, currentCol + 1))) {
                jouable = true;
                Tuile tuileDroite = plateau.get(new PositionTuiles(currentRow, currentCol + 1));
                if (!sontCompatibles(tuileSelectionnee, tuileDroite)) return -1;
                correspondances++;
            }
        }

        if (jouable) {
            calculerPoints(correspondances, new PositionTuiles(currentRow, currentCol), joueurActuel);
            return correspondances;
        }

        return -1;
    }

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
     
     public boolean finDePartie(Rack[] racks, Pioche pioche) {
    	    for (int i = 0; i < racks.length; i++) {
    	        if (racks[i].getTuiles().isEmpty()) {
    	            return true;
    	        }
    	    }
    	    return false;
    	}

    	public int getGagnant(Rack[] racks) {
    	    for (int i = 0; i < racks.length; i++) {
    	        if (racks[i].getTuiles().isEmpty()) {
    	            return i;
    	        }
    	    }
    	    return -1;
    	}

     
     public void distribuerTuiles(Joueur[] joueurs) {
    	    for (int i = 0; i < joueurs.length; i++) {
    	        Rack rack = joueurs[i].getRack();
    	        Pioche pioche = joueurs[i].getPioche();
    	        while (rack.getTuiles().size() < 5 && pioche.taille(i) > 0) {
    	            Tuile tuile = pioche.piocher(i);
    	            if (tuile != null) {
    	                rack.ajoutTuile(tuile);
    	            }
    	        }
    	    }
    	}
     
     public int getScore(int joueurActuel) {
    	    return pointsJoueur[joueurActuel];
    	}

}
