import java.util.*;


/**
 * <p>This class create a LexiNode object.</p>
 * 
 * <p> Each LexiNode object represents a node of the tree ( data structure ). <br>
 *  The keys of the nodes are lowercase letter. The letter are automatically lowercase. <br>
 *  Each node keeps a reference to their child. <br>
 *  All the last nodes keep the full word and the definition.</p>
 * @author Laflèche Chevrette
 * @version 1.0
 * @since 2020-10-16
 */
public class LexiNode {


    private ArrayList<LexiNode> child;

    private String word = null;

    private String definition = null;
    
    private char character;

    /**
     * This constructor creates an empty LexiNode object. <br>
     * This contructor should strickly be used only for the creation of a new tree.
     */
    public LexiNode(){
    	this.child = new ArrayList<LexiNode>();
    }

    /**
     * This constructor creates a LexiNode object. It takes a character in parameter. The character will be used as <br>
     * the key of the LexiNode object. It should be used when a child (That is not the last) is created.
     * @param character A character that will be used as the the key of the tree.
     */
    private LexiNode(char character){

        this.character = Character.toLowerCase(character);
        this.child = new ArrayList<LexiNode>();
    }

    /**
     * This constructor creates a LexiNode object. It takes a character and two String in parameter. <br>
     * This contructor should be strickly used when the last node of a branch is created. It keeps the full word and the definition <br>
     * @param word The full word that was entered in the tree
     * @param definition The definition of the word that was entered in the tree.
     */
    private LexiNode(String word, String definition){

        this.word = word;
        this.definition = definition;
        this.child = new ArrayList<LexiNode>();
    }

    /**
     * This method returns the key of the LexiNode.
     * @return A character
     */
    public char getCharacter(){
        return this.character;
    }

    /**
     * This method returns the word stored in the LexiNode. It returns null if there is nothing.
     * @return A String
     */
    public String getWord(){
        return this.word;
    }

    /**
     * This method returns the definition of a word stored in the LexiNode. It returns null if there is nothing.
     * @return A String
     */
    public String getDefinition(){
        return this.definition;
    }

    /**
     * This method returns an ArrayList that contains the reference of all the child of the LexiNode.
     * @return An ArrayList
     */
    public ArrayList<LexiNode> getChild(){
        return this.child;
    }

    /**
     * This method is used to modify the word stored in the LexiNode.
     * @param word A String
     */
    public void setWord(String word){
        this.word = word;
    }

    /**
     * This method is used to modify the definition stored in the LexiNode.
     * @param definition A String
     */
    public void setDefinition(String definition){
        this.definition = definition;
    }


    /**
     * This method returns false if the LexiNode object does not have any child <br>
     * and true if it does.
     * @return A boolean
     */
    public boolean hasNext(){
        return !this.child.isEmpty();
    }


    /**
     * This method returns the child of the LexiNode object. The child is chosen via an index sent in parameter.<br>
     * This method returns null if the object does not exist.
     * @param index The index of the LexiNode 
     * @return A LexiNode object;
     */
    public LexiNode next(int index){

        if(!this.hasNext())
            return null;

        return this.child.get(index);
    }


    /**
     * This method returns the index of the LexiNode with the sent character as a key.<br>
     * This method returns -1 if it does not find the right LexiNode.
     * @param character The key of the LexiNode
     * @return The position of the child.
     */
    public int childPosition(char character){
        for(int i = 0; i < this.child.size(); i++){

            if(this.child.get(i).getCharacter() == character){
                return i;
            }
        }

        return -1;
    }

    /**
     * This method is used to add a child for the current LexiNode in ascending order. <br>
     * It takes a character in parameter. It will automatically creates a new LexiNode object at the good position.
     * @param character The key of the new LexiNode
     */
    public void addChild(char character){

    	int listSize = this.child.size();

        if(this.child.isEmpty()){

            this.child.add(new LexiNode(character));
            
        }else{

            //for all the elements of the list
            for(int i = 0; i < listSize; i++){
               
                //This line remove every potential duplicate
            	if(character == this.child.get(i).getCharacter()) {
            		break;
                
                /*
                * It create the child when the key is bigger than i - 1 and smaller than i.
                */
            	}else if(character < this.child.get(i).getCharacter()){
                    
            		if(i == 0 ) {
            			
            			this.child.add(0, new LexiNode(character));
            			break;
            			
            		}else {
            			
            			this.child.add(i, new LexiNode(character));
            			break;
            		}
                    
                    /*
                    * If the first statement is false, it continue the loop.
                    * it creates a new child at the end of the list if the key is bigger than
                    * all the other key
                    */
            	}else {
            		
            		if(i + 1 == this.child.size()) {
            			this.child.add(new LexiNode(character));
            			break;
            		}else {
            			continue;
            		}
            	}
                
            }
        }
    }

    
    /**
     * This is method is used to add a word in the Lexicon tree. <br>
     * It calls the other method addWord(String word, String copy, String definition)<br>
     * to do the actual works for more security and clarity.<br> This method returns nothing.
     * 
     * @param word A String
     * @param definition A String
     * @see addWord(String word, String copy, String definition)
     */
    public void addWord( String word, String definition){

        this.addWord(word, word, definition);
     }


    /**
     * This method adds a word to the Lexicon tree. It works recusively. <br>
     * This method can only be call by the method the method addWord(String word, String definition) <br>
     * for a reason of clarity and security.<br> This method use a copy of the initial word to stop the recursivity <br>
     * and to create new LexiNode child.<br> This method returns nothing.
     * 
     *
     * La complexité de la méthode est O(n) où n représente la taille du mot envoyé en paramètre.
     * @param word 
     * @param copy
     * @param definition 
     */
    private void addWord( String word, String copy, String definition) {
    	
        if(copy.length() != 0) {
            
            char character = copy.toLowerCase().charAt(0);
            this.addChild(character);
            this.next(this.childPosition(character)).addWord(word, copy.substring(1), definition);
        }else{
            this.child.add(new LexiNode(word, definition));
        }

   }
   
   /**
    * This method search for a word in the LexiNode tree. It returns true if the word exist in the tree <br>
    * or false if it does not.
    * @param word A String
    * @return a boolean
    */
 
    public boolean searchWord(String word) {
    	
		boolean test = true;
		
    	if(word.length() != 0) {
    		
    		int index = this.childPosition(word.charAt(0));
    		
        	if(index == -1)
        		test = false;
    		
        	if(!test)
        		return test;
        	
    		 test = this.next(index).searchWord(word.substring(1));
    	}else {
    		
    		if(!this.hasNext() || this.child.get(0).getWord() == null )
    			test = false;	

    	}
    	
    	return test;	
    }


   /**
    * This method returns the definition of a word sent in parameter­. It returns null if the word does not exist. <br>
    *
    * Cette méthode à une complexité de O(n) où n représente la longueur du mot envoyé en paramètre.
    *@param word A String
    *@return A string that contains the definition of the word
    */
    public  String searchDefinition(String word) {
    	
    	String str = "";
    		
    	if(word.length() != 0) {
    		
    		int index = this.childPosition(word.charAt(0));
    		
        	if(index == -1)
        		return null;
    		
    		str = this.next(index).searchDefinition(word.substring(1));
    	}else {
    		
    		if(!this.hasNext() || this.child.get(0).getWord() == null )
    			return null;
    		
    		str = this.next(0).getDefinition();
    	}
    	
    	return str;	
    	
    }
    


    /**
     * This method modify an existing word.<br>
     * 
     * Cette méthode à une complexité de O(n) où n est la longueur du mot envoyé en paramètre.
     * @param word A String
     * @param definition A String
     * @see addWord(String word, String definition)
     */
    public void modify(String word, String definition) {
  	
   	     if(word.length() != 0) {
		 
            char character = word.charAt(0);
         this.addChild(character);
         this.next(this.childPosition(character)).modify(word.substring(1), definition);
        } else {
          this.child.get(0).setDefinition(definition);
        }
       
    }
    
    
    /**
     * This method remove a word from the LexiNode tree. On the other hand, the path will always exist.<br>
     * 
     * Cette méthode à une complexité de O(n) si n réprésente la longueur du mot envoyé en paramètre.
     * @param word A String
     */
    public void remove(String word) {
    	 
    	if(word.length() != 0) {
    		
    		int index = this.childPosition(word.charAt(0));
    		
    		if(index != -1) {
    			this.next(index).remove(word.substring(1));
    		}
    	}else {   		
    		this.child.remove(0);
    	}
    	

    }

    /**
     * This method returns a list of string with all <br>
     * the words of the LexiNode by calling the method searchAllWords().<br>
     * This method is easier to used than the other one.
     * @return a list of String
     */
    public List<String> getAllWords(){
        ArrayList<String> l = new ArrayList<String>();
        return searchAllWords(l);
    }

    /**
     * This method returns a list that contains all the words of<br>
     * the LexiNode tree. The words are already in alphabital order.<br>
     * 
     * Cette méthode a une complexité de O(n*log(n)) où n représente toutes les feuilles de l'arbres
     * @param list All kinds of list
     * @return A list with all the words of the LexiNode tree. 
     */
    public List<String> searchAllWords(List<String> list) {
    		
    	if(this.getWord() != null) {
			list.add(this.getWord());
        }
    	
    	for(int i = 0; i < this.child.size(); i++) {

    		list = this.next(i).searchAllWords(list);
    	}	
    	return list;
    }


    /**
     * This method returns a list that contains all the existing word under a constraint. <br>
     * If there is no word that respects the constraint, it returns null. <br>
     * Exemple : If the constraint is "be", it will returns a list that contains all word that start with be. 
     * 
     * Cette méthode a une complexité de O(n*long(n))  où n représente toutes les feuilles de l'arbre à partir de la contrainte.
     * @param list A list of String
     * @param constraint A constraint for the research
     * @return A list of String
     */
    public List<String> searchAllWords(List list, String constraint) {
		
    	if(constraint.length() > 0) {
    		
        	if(this.childPosition(constraint.charAt(0)) == -1) {
        		return null;
        	}
        	
    		list = this.next(this.childPosition(constraint.charAt(0))).searchAllWords(list, constraint.substring(1));
    	}else {
    		
    		if(this.getWord() != null) 
    			list.add(this.getWord());
        	
        	for(int i = 0; i < this.child.size(); i++) {

        		list = this.next(i).searchAllWords(list);
        	}
    	}
	
    	return list;
    }

}