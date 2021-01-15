import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * This class was created to test the class LexiNode.
 * @author Laflèche Chevrette
 */
public class test {

	@Test
	public void testLexiNode() {
        
        //A new tree is created. Every test will be based on this tree.
		LexiNode ln = new LexiNode();
		
		ln.addChild('j');
		ln.addChild('e');
		
		ln.addWord("de", "Voici une patate");
		ln.addWord("papa", "Voici un papa");
		ln.addWord("patate", "Patatatate");
		ln.addWord("bond", "petit saut");
		ln.addWord("bondir", "petit saut verbe");
		ln.addWord("bedaine", "bidon");
		
		ArrayList<String> al = new ArrayList<String>(); 
        al = (ArrayList)ln.searchAllWords(al);
             
        
		/*
		 * These tests verify if the LexiNode are stored in the correct order (ascending). If the tests work,
         * it means the method addChild(char character) works correctly.
		 */
		assertEquals('b', ln.next(0).getCharacter());
		assertEquals('d', ln.next(1).getCharacter());
		assertEquals('e', ln.next(2).getCharacter());
		assertEquals('j', ln.next(3).getCharacter());
		assertEquals('p', ln.next(4).getCharacter());
		
		
		/*
		 * These tests verify if the words are stored at the good place
		 *  when their are created with addWord(String word, String definition) method.
		 */
		assertEquals("bedaine", ln.next(0).next(0).next(0).next(0).next(0).next(0).next(0).next(0).getWord());
		assertEquals("bond", ln.next(0).next(1).next(0).next(0).next(0).getWord());
		assertEquals("bondir", ln.next(0).next(1).next(0).next(0).next(1).next(0).next(0).getWord());
		assertEquals("de", ln.next(1).next(0).next(0).getWord());
		assertEquals("papa", ln.next(4).next(0).next(0).next(0).next(0).getWord());
		assertEquals("patate", ln.next(4).next(0).next(1).next(0).next(0).next(0).next(0).getWord());
		// ln.next(3).getWord() {j} should return null
		assertEquals(null, ln.next(3).getWord());
		// ln.next(2).getWord() {e} should return null
		assertEquals(null, ln.next(2).getWord());
		// ln.next(4).next(0).next(1).getWord() {pat} should return null
		assertEquals(null, ln.next(4).next(0).next(1).getWord());
        
        
		/*
		 * These tests verify if the definitions are stored at the good place
		 *  when their are created with addWord(String word, String definition) method.
		 */
		assertEquals("bidon", ln.next(0).next(0).next(0).next(0).next(0).next(0).next(0).next(0).getDefinition());
		assertEquals("petit saut", ln.next(0).next(1).next(0).next(0).next(0).getDefinition());
		assertEquals("petit saut verbe", ln.next(0).next(1).next(0).next(0).next(1).next(0).next(0).getDefinition());
		assertEquals("Voici une patate", ln.next(1).next(0).next(0).getDefinition());
		assertEquals("Voici un papa", ln.next(4).next(0).next(0).next(0).next(0).getDefinition());
		assertEquals("Patatatate", ln.next(4).next(0).next(1).next(0).next(0).next(0).next(0).getDefinition());
		// ln.next(3).getWord() {j} should return null
		assertEquals(null, ln.next(3).getDefinition());
		// ln.next(2).getWord() {e} should return null
		assertEquals(null, ln.next(2).getDefinition());
		// ln.next(4).next(0).next(1).getWord() {pat} should return null
		assertEquals(null, ln.next(4).next(0).next(1).getDefinition());
        
        
		/*
		 * These tests verify if the getChild() method works correctly and if the tree was correctly created.
         * It test on 2 different level of the tree.
		 */
		ArrayList<Character> al2 = new ArrayList<Character>();
		al2.add('b');
		al2.add('d');
		al2.add('e');
		al2.add('j');
		al2.add('p');

		for(int i = 0; i < ln.getChild().size(); i++) {
			char character = al2.get(i);
			assertEquals(character,ln.getChild().get(i).getCharacter());
		}
		
		
		ArrayList<Character> al3 = new ArrayList<Character>();
		al3.add('p');
		al3.add('t');
		
		for(int i = 0; i < ln.next(4).next(0).getChild().size(); i++) {
			char character = al3.get(i);
			assertEquals(character, ln.next(4).next(0).getChild().get(i).getCharacter());
		}
        
        
		/*
		 * These tests verify if the method childPosition(char character) works correctly
		 */
		assertEquals(0, ln.childPosition('b'));
		assertEquals(1, ln.childPosition('d'));
		assertEquals(2, ln.childPosition('e'));
		assertEquals(3, ln.childPosition('j'));
		assertEquals(4, ln.childPosition('p'));
		// A test with a character that is not present in the list
		assertEquals(-1, ln.childPosition('w'));
		
		/*
		 * These tests verify if the method searchDefinition(String word) works correctly. 
		 * It also verifies if the method remove(String word) works correctly
		 */
		assertEquals("Voici une patate", ln.searchDefinition("de"));
		assertEquals("Voici un papa", ln.searchDefinition("papa"));
		assertEquals("Patatatate", ln.searchDefinition("patate"));
		assertEquals("petit saut", ln.searchDefinition("bond"));
		assertEquals("petit saut verbe", ln.searchDefinition("bondir"));
		assertEquals("bidon", ln.searchDefinition("bedaine"));
		ln.remove("bedaine");
		assertEquals(null, ln.searchDefinition("bedaine"));
		
		/*
		 * These tests verify if the method searchAllWord() works correctly.
		 */
		String[] tab = new String[5];
		tab[0] = "bond";
		tab[1] = "bondir";
		tab[2] = "de";
		tab[3] = "papa";
		tab[4] = "patate";
		
		ArrayList<String> strL = new ArrayList<String>();
		strL = (ArrayList<String>)ln.searchAllWords(strL);
		
		for(int i = 0; i < tab.length; i++) {
			assertEquals(tab[i], strL.get(i));
        }
        
        /*
		 * These tests verify if the method searchAll(List list, String constraint)
		 */
		ln.addWord("bedaine", "bidon");
        ArrayList<String> al4=  new ArrayList<String>();
        al4.add("bedaine");
		al4.add("bond");
		al4.add("bondir");
		
		ArrayList<String> alTest = new ArrayList<String>();
		alTest = (ArrayList<String>)ln.searchAllWords(alTest, "b");

		for(int i = 0; i < alTest.size(); i++) {
			assertEquals(al4.get(i), alTest.get(i));
        }


        ArrayList<String> al5=  new ArrayList<String>();
        al5.add("bond");
        al5.add("bondir");
        alTest.clear();
        alTest = (ArrayList<String>)ln.searchAllWords(alTest, "bo");

        for(int i = 0; i < alTest.size(); i++) {
			assertEquals(al5.get(i), alTest.get(i));
        }
				
	}
}
