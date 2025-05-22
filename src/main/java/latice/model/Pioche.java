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

        tuilesParJoueur = new ArrayList<>();
        for (int i = 0; i < nombreJoueurs; i++) {
            tuilesParJoueur.add(new Stack<>());
        }

        int index = 0; 
        while (index < listeTuiles.size()) {
            for (int i = 0; i < nombreJoueurs; i++) {
                if (index < listeTuiles.size()) {
                    tuilesParJoueur.get(i).add(listeTuiles.get(index));
                    index++;
                }
            }
        }
    }

    private List<Tuile> creerPiocheBase() {
        List<Tuile> listeTuiles = new ArrayList<>();
        for (Couleur couleur : Couleur.values()) {
            for (Symbole symbole : Symbole.values()) {
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
        return tuilesParJoueur.get(joueur).size();
    }
    
    public void ajouterTout(List<Tuile> tuiles, int joueur) {
        if (joueur >= 0 && joueur < tuilesParJoueur.size()) {
            Collections.shuffle(tuiles);
            Stack<Tuile> pile = tuilesParJoueur.get(joueur);
            pile.addAll(tuiles);
        }
    }


}
