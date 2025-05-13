package latice.model;

public enum Symbole {
    FLEUR("fleur", "✿"),
    LEZARD("lezard", "🦎"),
    PLUME("plume", "🪶"),
    OISEAU("oiseau", "🐦"),
    DAUPHIN("dauphin", "🐬"),
    TORTUE("tortue", "🐢");

    private final String nomTexte;
    private final String symboleUnicode;

    private Symbole(String nomTexte, String symboleUnicode) {
        this.nomTexte = nomTexte;
        this.symboleUnicode = symboleUnicode;
    }

    public String getSymboleTexte() {
        return this.nomTexte;
    }

    public String getSymboleUnicode() {
        return this.symboleUnicode;
    }

    @Override
    public String toString() {
        return nomTexte;
    }
}


