package latice.model;

public enum Couleur {
    ROUGE("rouge", "\u001B[31m"),    
    CYAN("cyan", "\u001B[36m"),      
    VERT("vert", "\u001B[32m"),      
    JAUNE("jaune", "\u001B[33m"),    
    VIOLET("violet", "\u001B[35m"),  
    BLEU("bleu", "\u001B[34m");
    
    private final String couleur;
    private final String ansiCode;
    
    private Couleur(String couleur, String ansiCode) {
        this.couleur = couleur;
        this.ansiCode = ansiCode;
    }
    
    public String getcouleur() {
        return this.couleur;
    }
    
    public String getAnsiCode() {
        return this.ansiCode;
    }
}
