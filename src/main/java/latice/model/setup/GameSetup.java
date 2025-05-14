package latice.model.setup;

import java.util.Random;

public class GameSetup {
	
	public static String[]  ordreJoueur(String nom1, String nom2){
		
		Random random = new Random();
		String[] list = new String[2];
		
		boolean choix = random.nextBoolean();
		
		if(choix) {
			list[0] = nom1;
			list[1] = nom2;
		}
		else {
			list[0] = nom2;
			list[1] = nom1;
		}
		
		return list;
		
	}
	

}
