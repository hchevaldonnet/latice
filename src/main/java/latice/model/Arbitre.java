package latice.model;

import java.util.Map;

public class Arbitre {
    private int[] pointsJoueur;

    public Arbitre(int nombreJoueurs) {
        setPointsJoueur(new int[nombreJoueurs]);
    }
    
    /**
     * Vérifie si un coup est valide et retourne le nombre de correspondances
     * @return -1 si coup invalide, sinon le nombre de correspondances (0-4)
     */
    public int verifierCoup(int currentRow, int currentCol, Tuile tuileSelectionnee, 
                           Map<PositionTuiles, Tuile> plateau, boolean premierCoup, int joueurActuel) {
        // Vérifier si la position est déjà occupée
        if (plateau.containsKey(new PositionTuiles(currentRow, currentCol))) {
            return -1; // Case déjà occupée
        }
        
        // Vérifier le premier coup (doit être au centre)
        if (premierCoup && (currentRow != 4 || currentCol != 4)) {
            return -1; // Le premier coup doit être au centre (5,5 pour l'utilisateur)
        }

        if (premierCoup) {
            return 0; // Premier coup valide, pas de correspondances à vérifier
        }

        // Pour les coups suivants, vérifier au moins une adjacence
        int correspondances = 0;
        boolean adjacenceExiste = false;

        // Vérifier en bas
        PositionTuiles posBas = new PositionTuiles(currentRow + 1, currentCol);
        if (plateau.containsKey(posBas)) {
            adjacenceExiste = true;
            Tuile tuileBas = plateau.get(posBas);
            if (!sontCompatibles(tuileSelectionnee, tuileBas)) {
                return -1; // Incompatible avec la tuile du bas
            }
            correspondances++;
        }

        // Vérifier en haut
        PositionTuiles posHaut = new PositionTuiles(currentRow - 1, currentCol);
        if (plateau.containsKey(posHaut)) {
            adjacenceExiste = true;
            Tuile tuileHaut = plateau.get(posHaut);
            if (!sontCompatibles(tuileSelectionnee, tuileHaut)) {
                return -1; // Incompatible avec la tuile du haut
            }
            correspondances++;
        }

        // Vérifier à gauche
        PositionTuiles posGauche = new PositionTuiles(currentRow, currentCol - 1);
        if (plateau.containsKey(posGauche)) {
            adjacenceExiste = true;
            Tuile tuileGauche = plateau.get(posGauche);
            if (!sontCompatibles(tuileSelectionnee, tuileGauche)) {
                return -1; // Incompatible avec la tuile de gauche
            }
            correspondances++;
        }

        // Vérifier à droite
        PositionTuiles posDroite = new PositionTuiles(currentRow, currentCol + 1);
        if (plateau.containsKey(posDroite)) {
            adjacenceExiste = true;
            Tuile tuileDroite = plateau.get(posDroite);
            if (!sontCompatibles(tuileSelectionnee, tuileDroite)) {
                return -1; // Incompatible avec la tuile de droite
            }
            correspondances++;
        }

        // Si aucune adjacence n'existe, le coup est invalide
        if (!adjacenceExiste) {
            return -1;
        }

        return correspondances;
    }

    /**
     * Vérifie si deux tuiles sont compatibles (même couleur ou même symbole)
     */
    private boolean sontCompatibles(Tuile tuile1, Tuile tuile2) {
        return tuile1.symbole == tuile2.symbole || tuile1.couleur == tuile2.couleur;
    }
    
    /**
     * Vérifie si la partie est terminée
     */
    public boolean finDePartie(Rack[] racks, Pioche pioche) {
        // Vérifier si un joueur a vidé son rack
        for (int i = 0; i < racks.length; i++) {
            if (racks[i].getTuiles().isEmpty()) {
                return true;
            }
        }
        
        // Vérifier si toutes les pioches sont vides et aucun coup n'est possible
        boolean piochesVides = true;
        for (int i = 0; i < racks.length; i++) {
            if (!pioche.estVide(i)) {
                piochesVides = false;
                break;
            }
        }
        
        return piochesVides; // Si toutes les pioches sont vides, fin de partie
    }

    /**
     * Retourne l'indice du joueur gagnant
     */
    public int getGagnant(Rack[] racks) {
        // Vérifier si un joueur a vidé son rack
        for (int i = 0; i < racks.length; i++) {
            if (racks[i].getTuiles().isEmpty()) {
                return i;
            }
        }
        
        // Si aucun joueur n'a vidé son rack, le gagnant est celui avec le plus de points
        int gagnant = 0;
        for (int i = 1; i < getPointsJoueur().length; i++) {
            if (getPointsJoueur()[i] > getPointsJoueur()[gagnant]) {
                gagnant = i;
            }
        }
        
        return gagnant;
    }

    /**
     * Distribue les tuiles aux joueurs
     */
    public void distribuerTuiles(Joueur[] joueurs) {
        for (int i = 0; i < joueurs.length; i++) {
            Rack rack = joueurs[i].getRack();
            Pioche pioche = joueurs[i].getPioche();
            rack.remplir(pioche, i);
        }
    }
    
    /**
     * Retourne le score d'un joueur
     */
    public int getScore(int joueurIndex) {
        if (joueurIndex >= 0 && joueurIndex < getPointsJoueur().length) {
            return getPointsJoueur()[joueurIndex];
        }
        return 0;
    }
    
    /**
     * Ajoute des points à un joueur
     */
    public void ajouterPoints(int joueurIndex, int points) {
        if (joueurIndex >= 0 && joueurIndex < getPointsJoueur().length) {
            getPointsJoueur()[joueurIndex] += points;
        }
    }

	public int[] getPointsJoueur() {
		return pointsJoueur;
	}

	public void setPointsJoueur(int[] pointsJoueur) {
		this.pointsJoueur = pointsJoueur;
	}
}

