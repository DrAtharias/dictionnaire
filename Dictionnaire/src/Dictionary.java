import java.io.IOException;

/**
 * This class is used to initiate the dictionary application.
 * @author Jean-Philippe
 * 
 */

public class Dictionary {

	/**
	 * This method is the main method for the Dictionary. It's purpose is to run the dictionary application.
	 * 
	 * @param String[] args
	 * @see main
	 * 
	 * @return void
	 * @throws IOException
	 * 
	 */
	public static void main(String[] args){
        	try{
            	DisplayWindow.createWindow("Dictio", 900, 500);
        	} catch(IOException e) {
            	System.out.println(e);
            	System.exit(1);
        	}  
	}
}
