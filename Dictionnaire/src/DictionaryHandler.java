import java.util.*;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;

/**
 * This class is used to manage the read and write from local files in the current computer.
 * 
 * @see saveDictionary
 * @see loadDictionary
 * 
 * @author Jean-Philippe
 * @version 1.0
 * @since 2020-10-16
 *
 */

public class DictionaryHandler {

	/**
	 * This method create a new dictionary and initiate it's GUI through the class DisplayWindows.
	 * Also create an object of type LexiNode and split the words and definitions from the dictionary in the object.
	 * 
	 * This method select a file using java.util.FileDialog class and prompt the user to choose a dictionary file.
	 * Uses java.util.Scanner class to read a dictionary file selected by the user on his PC.
	 * This method read the file as UTF-8 encoding and remove the BOM from the dictionary file.
	 * 
	 * Private method. Needs to be called through loadDictionary().
	 *
	 * @see LexiNode
	 * @see readDictionary
	 * 
	 * @return an object of type LexiNode.
	 * @return null if an object of type LexiNode could not be created or the file is not found.
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * @author Jean-Philippe Mongeau
	 * @version 1.0
	 * @since 2020-10-16
	 * 
	 */
    private LexiNode readDictionary() throws IOException {
    	
        try {
            String word = "";
            LexiNode dictionnaire = new LexiNode();
            String definition = "";
            List<String> wordDef = new ArrayList<String>();
            String line = "";
            int lineCount = 0;
            
            /* Start building the frame */
    		JFrame frame = new JFrame();
    		frame.setPreferredSize(new Dimension(400, 200));
    		frame.setFont(new Font("Arial", Font.PLAIN, 14));
    		
            FileDialog file = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
            file.setFile("*.txt");
            file.setVisible(true);
            String path = file.getDirectory() + file.getFile();

            if (file.getFile() == null) {
                return null;
            } else {
                File dico = new File(path);
                Scanner crossDictionary = new Scanner(dico, "UTF-8");
                while (crossDictionary.hasNextLine()) {
                	line = crossDictionary.nextLine().toLowerCase();
	                if (line.contains(" & ")) {
	                	try {
	                		word = line.split(" & ")[0];
	                        if (word.startsWith("\uFEFF")) {
	                        	word = word.substring(1);
	                        }

	                	} catch (Exception e) {
	                		word = "Mot inconnu";
	                	}
	                	try {
	                		definition = line.split(" & ")[1];
	                	} catch (Exception e) {
	                		definition = "Erreur : Aucune d�finition trouv�e";
	                	}
	                	wordDef.add(word + " & " + definition);
	                	  
	                } else if (line.contains(" ") && !line.contains("&")) {
	                	definition = line;
	                	wordDef.set(lineCount-1, word + " & " + definition);
	                	lineCount--;
	                }
	                lineCount++;
                }
                try {
    
                    for (int i = 0; i < wordDef.size(); i++) {
                        word = wordDef.get(i).split(" & ")[0];
                        definition = wordDef.get(i).split(" & ")[1];
	    				dictionnaire.addWord(word, definition);
    
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                crossDictionary.close();
                
            }
            return dictionnaire;
            

        } catch (FileNotFoundException error) {
        	System.out.println(error);
        	return null;
        } 
        
    } 

	/**
	 * This method allow to create or record over an existing file, a new dictionary file from the current dictionary.
	 * 
	 * This method select a file using java.util.FileDialog class and prompt the user to save a dictionary file.
	 * Uses java.util.PrintWriter class to write a dictionary file selected by the user on his PC.
	 * 
	 * Private method. Needs to be called through saveDictionary().
	 *
	 * @param LexiNode object 
	 * 
	 * @see LexiNode
	 * @see writeDictonary
	 *
	 * @return void
	 * @throws IOException
	 * @throws FileNotFoundException
	 * 
	 * 
	 * @author Jean-Philippe Mongeau
	 * @version 1.0
	 * @since 2020-10-16
	 * 
	 */
    private void writeDictionary(LexiNode dictionary) throws IOException {
        try {
            
            /* Start building the frame */
    		JFrame frame = new JFrame();
    		frame.setPreferredSize(new Dimension(400, 200));
    		frame.setFont(new Font("Arial", Font.PLAIN, 14));
            FileDialog file = new FileDialog(frame, "Choose a file", FileDialog.SAVE);
            file.setFile("*.txt");
            file.setVisible(true);
            String path = file.getDirectory() + file.getFile();
            
            Path pathExist = Paths.get(path);
            File dictionaryFile = new File(path);
            if (!dictionaryFile.createNewFile()) {
                Files.deleteIfExists(pathExist);
            }

            PrintWriter dictionaryWriter = new PrintWriter(path, "UTF-8");
            for (String word : dictionary.getAllWords()) {
                dictionaryWriter.println(word + " & " + dictionary.searchDefinition(word));
            }
            dictionaryWriter.close();

        } catch (FileNotFoundException e) {
            System.out.println(e + " : Le fichier n'existe pas.");
            
        } catch (IOException e) {
            System.out.println(e + " : Une erreur est survenue.");
        }
    }

    
    
    /**
     * This method is the public method used to call readDictionary.
     * 
     * @return LexiNode
     * @throws IOException
     * 
     */
    public LexiNode loadDictionary() throws IOException {
        return readDictionary();
    }

    /**
     * This method is the public method used to call writeDictionary.
     * 
     * @param LexiNode
     * @see LexiNode
     * 
     * @return LexiNode
     * @throws IOException
     * 
     */
    public void saveDictionary(LexiNode dictionary) throws IOException {
        writeDictionary(dictionary);
    }
}
