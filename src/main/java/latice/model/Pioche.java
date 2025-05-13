package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Pioche {
    private List<Stack<Tuile>> tuilesParJoueur;

    public Pioche(int nombreJoueurs) {
        List<Tuile> listeTuiles = creerPiocheBase();
        Collections.shuffle(listeTuiles);

        // Initialisation des piles de tuiles pour chaque joueur
        tuilesParJoueur = new ArrayList<>();
        for (int i = 0; i < nombreJoueurs; i++) {
            tuilesParJoueur.add(new Stack<>());
        }

        // Distribution des tuiles
        int index = 0; // Index de la tuile dans la liste de tuiles
        while (index < listeTuiles.size()) {
            for (int i = 0; i < nombreJoueurs; i++) {
                if (index < listeTuiles.size()) {
                    tuilesParJoueur.get(i).add(listeTuiles.get(index));
                    index++; // Passer à la tuile suivante
                }
            }
        }
    }

    private List<Tuile> creerPiocheBase() {
        List<Tuile> listeTuiles = new ArrayList<>();
        for (Couleur couleur : Couleur.values()) {
            for (Symbole symbole : Symbole.values()) {
                // Ajout de deux tuiles pour chaque combinaison de couleur et symbole
                listeTuiles.add(new Tuile(couleur, symbole));
                listeTuiles.add(new Tuile(couleur, symbole));
            }
        }
        return listeTuiles;
    }

    public Tuile piocher(int joueur) {
        if (joueur >= 0 && joueur < tuilesParJoueur.size() && !tuilesParJoueur.get(joueur).isEmpty()) {
            return tuilesParJoueur.get(joueur).pop();
        }
        return null;
    }

    public boolean estVide(int joueur) {
        return (joueur >= 0 && joueur < tuilesParJoueur.size()) && tuilesParJoueur.get(joueur).isEmpty();
    }

    public int taille(int joueur) {
        // Retourne le nombre de tuiles restantes dans le rack du joueur, pas dans la pioche globale
        return tuilesParJoueur.get(joueur).size();
    }
}
