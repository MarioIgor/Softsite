package util;

public class Conversor {

	public static boolean podeConverterParaInteiro(String texto) {		
		try {  
			Integer.parseInt(texto);  
			return true;  
		} catch (NumberFormatException e) {  
		    return false;  
		}  		
	}	
}
