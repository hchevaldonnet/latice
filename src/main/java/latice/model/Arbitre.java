package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Arbitre {
    private double[] scores;
    private final int nbJoueurs;

    public Arbitre(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
        this.scores = new double[nbJoueurs];
    }

    public int verifierCoup(int ligne, int col, Tuile tuile, java.util.Map<PositionTuiles, Tuile> plateau, boolean premierCoup) {
        // Vérifier si la position est valide
        if (ligne < 0 || ligne > 8 || col < 0 || col > 8) {
            return -1;
        }

        // Vérifier si la case est déjà occupée
        PositionTuiles position = new PositionTuiles(ligne, col);
        if (plateau.containsKey(position)) {
            return -1;
        }

        // Pour le premier coup, vérifier que c'est sur la case lune (4,4)
        if (premierCoup) {
            return (ligne == 4 && col == 4) ? 0 : -1;
        }

        // Pour les coups suivants, vérifier l'adjacence et la compatibilité
        int nbCorrespondances = 0;
        boolean estAdjacent = false;

        // Vérifier les 4 cases adjacentes
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // haut, bas, gauche, droite
        
        for (int[] dir : directions) {
            int newLigne = ligne + dir[0];
            int newCol = col + dir[1];
            
            PositionTuiles posAdjacente = new PositionTuiles(newLigne, newCol);
            Tuile tuileAdjacente = plateau.get(posAdjacente);
            
            if (tuileAdjacente != null) {
                estAdjacent = true;
                // Vérifier la compatibilité (même couleur ou même symbole)
                if (tuileAdjacente.couleur == tuile.couleur || tuileAdjacente.symbole == tuile.symbole) {
                    nbCorrespondances++;
                } else {
                    // Si une tuile adjacente n'est pas compatible, le coup est invalide
                    return -1;
                }
            }
        }

        // Si aucune tuile adjacente, le coup est invalide sauf premier coup
        if (!estAdjacent) {
            return -1;
        }

        return nbCorrespondances;
    }

    public void calculerPointsApresCoup(int ligne, int col, int nbCorrespondances, int joueur) {
        double points = 0;
        
        // Points de base selon le nombre de correspondances
        if (nbCorrespondances == 2) {
            points = 0.5;
        } else if (nbCorrespondances == 3) {
            points = 1.0;
        } else if (nbCorrespondances == 4) {
            points = 2.0;
        }
        
        // Bonus pour case soleil
        if (PositionCaseSoleil.estUneCaseSoleil(ligne, col)) {
            points += 1.0;
        }
        
        ajouterPoints(joueur, points);
    }

    public void ajouterPoints(int joueur, double points) {
        if (joueur >= 0 && joueur < nbJoueurs) {
            scores[joueur] += points;
            // Limiter à un maximum de 3 points
            if (scores[joueur] > 3) {
                scores[joueur] = 3;
            }
        }
    }

    public void retirerPoints(int joueur, double points) {
        if (joueur >= 0 && joueur < nbJoueurs) {
            scores[joueur] -= points;
            if (scores[joueur] < 0) {
                scores[joueur] = 0;
            }
        }
    }

    public double getScore(int joueur) {
        return (joueur >= 0 && joueur < nbJoueurs) ? scores[joueur] : 0;
    }

    public boolean finDePartie(Rack[] racks, int totalTours, int maxTours) {
        // Vérifier si le nombre de tours maximal est atteint
        if (totalTours >= maxTours) {
            return true;
        }
        
        // Vérifier si un joueur a un rack vide et sa pioche vide
        for (int i = 0; i < nbJoueurs; i++) {
            if (racks[i].getTuiles().isEmpty()) {
                return true;
            }
        }
        
        return false;
    }

    public List<Integer> getGagnants(Joueur[] joueurs) {
        List<Integer> gagnants = new ArrayList<>();
        int minTuiles = Integer.MAX_VALUE;
        
        // Trouver le nombre minimum de tuiles restantes
        for (int i = 0; i < nbJoueurs; i++) {
            int tuileRestantes = joueurs[i].getRack().getTuiles().size() + joueurs[i].getPioche().taille(i);
            if (tuileRestantes < minTuiles) {
                minTuiles = tuileRestantes;
            }
        }
        
        // Ajouter tous les joueurs qui ont ce nombre minimum de tuiles
        for (int i = 0; i < nbJoueurs; i++) {
            int tuileRestantes = joueurs[i].getRack().getTuiles().size() + joueurs[i].getPioche().taille(i);
            if (tuileRestantes == minTuiles) {
                gagnants.add(i);
            }
        }
        
        return gagnants;
    }

    public void distribuerTuiles(Joueur[] joueurs) {
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs[i].getRack().remplir(joueurs[i].getPioche(), i);
        }
    }
}



