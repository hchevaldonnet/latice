package latice.ihm;

import java.util.Scanner;

public class SaisieConsole {
	
	static Scanner scanner = new Scanner(System.in);
	
	
	public static int saisieLigneColonne(String string) { //emplacement prend la valeur ligne ou colonne
		TexteConsole.demanderEmplacement(string);
		return saisieEntierBorne( 1, 8);
	}
	
	public static int saisieTuiles() {
		TexteConsole.demanderTuile();
		return saisieEntierBorne( 1, 5);
	}
	
	public static int saisieChoix() {
	    TexteConsole.demanderAction();
	    return saisieEntierBorne(1, 6); // Modifié pour accepter l'option 6
	}
	
	
	public static int saisieEntierBorne(int min, int max) {
	    int entier = -1;
	    do {
	        if (scanner.hasNextInt()) {
	            entier = scanner.nextInt();
	        } else {
	            scanner.next();
	        }
	    } while (entier < min || entier > max);
	    return entier - 1; 
	}
	
	public static String saisieChar() { //pour enter le nom du joueur
		String string = "";
			TexteConsole.demanderNom();
			if (scanner.hasNext()) {
	            string = scanner.next();
	        } else {
	            scanner.next();
	        }
	    
	    return string; 
	    
 }
	
}
	
	


