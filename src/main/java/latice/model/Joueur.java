package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String name;
    private Rack rack;
    private Pioche pioche;

    public Joueur(String name, Rack rack, Pioche pioche) {
        this.name = name;
        this.rack = rack;
        this.pioche = pioche;
    }

    public String getName() {
        return name;
    }

    public Rack getRack() {
        return rack;
    }

    public Pioche getPioche() {
        return pioche;
    }

    public boolean jouerActionSpeciale(int joueurIndex, ActionSpeciale action, Rack rack, Pioche pioche, Arbitre arbitre) {
        switch (action) {
            case ECHANGER_RACK:
                // Coût : 1 point
                if (arbitre.getScore(joueurIndex) < 1) {
                    return false;
                }
                // Vérifier si la pioche est vide
                if (pioche.estVide(joueurIndex)) {
                    return false;
                }
                // Sauvegarder les tuiles actuelles
                List<Tuile> anciennesTuiles = new ArrayList<>(rack.getTuiles());
                // Vider le rack
                rack.vider();
                // Remplir le rack avec de nouvelles tuiles de la pioche
                rack.remplir(pioche, joueurIndex);
                // Remettre les anciennes tuiles dans la pioche
                pioche.ajouterTout(anciennesTuiles, joueurIndex);
                // Déduire le coût
                arbitre.retirerPoints(joueurIndex, 1);
                return true;

            case ACTION_SUPPLEMENTAIRE:
                // Coût : 2 points
                if (arbitre.getScore(joueurIndex) < 2) {
                    return false;
                }
                arbitre.retirerPoints(joueurIndex, 2);
                return true;

            default:
                return false;
        }
    }

}




