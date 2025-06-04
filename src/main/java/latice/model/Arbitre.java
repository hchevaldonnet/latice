package latice.model;

import java.util.ArrayList;
import java.util.List;
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
    
    public void calculerPointsAprèsCoup(int currentRow, int currentCol, int correspondances, int joueurActuel) {
        int points = 0;

        if (correspondances == 2) {
            points = 1;
        } else if (correspondances == 3) {
            points = 2;
        } else if (correspondances == 4) {
            points = 4;
        }

        if (PositionTuiles.estUneCaseSoleil(currentRow, currentCol)) {
            points += 1;
        }

        ajouterPoints(joueurActuel, points);
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
    public boolean finDePartie(Rack[] racks, int totalTours, int maxTours) {
        return totalTours >= maxTours;
    }

    /**
     * Retourne la liste des indices des joueurs ayant le moins de tuiles restantes (rack + pioche).
     */
    public List<Integer> getGagnants(Joueur[] joueurs) {
        List<Integer> gagnants = new ArrayList<>();
        int minTuilesRestantes = Integer.MAX_VALUE;

        for (int i = 0; i < joueurs.length; i++) {
            int rackSize = joueurs[i].getRack().getTuiles().size();
            int piocheSize = joueurs[i].getPioche().taille(i);
            int totalRestantes = rackSize + piocheSize;

            if (totalRestantes < minTuilesRestantes) {
                gagnants.clear();
                gagnants.add(i);
                minTuilesRestantes = totalRestantes;
            } else if (totalRestantes == minTuilesRestantes) {
                gagnants.add(i);
            }
        }

        return gagnants;
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

