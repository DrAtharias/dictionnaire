import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.awt.image.BufferedImage;
import java.util.Collections;

/**
 * The class DisplayWindow is used to create a GUI.<br>
 * These object are used to display a JFrame window.
 * 
 * @author Laflèche Chevrette
 * @author Jean-Philippe Mongeau
 * @version 1.0
 * @since 2020-10-14
 * 
 */

public class DisplayWindow extends JFrame{


	/**
	 * This method creates a new JFrame object that will be used as the main window of the program.<br>
	 * This method needs a title for the window, a width and a height. The window created by this
	 * method is not resizable.<br>
	 * The title will automaticaly be aligned to the center of the window with the help of an other method : centerTitle()<br>
	 * Will call for DictionaryHandler class to manage the building of a LexiNode.
	 * Needs to be provided a dictionary to work.
	 * 
	 * @param title a string that contains a title
	 * @param width an int that contains the width of the desired window
	 * @param height an int that contains the heigth of the desired window
	 * 
	 * @see JFrame
	 * @see createJframe
	 * 
	 * @throws IOException 
	 * @return false if the window has not been able to be created
	 * @return true if the window has been properly executed
	 * 
	 * @author Jean-Philippe Mongeau
	 * @author Laflèche Chevrette
	 * @version 1.1
	 * @since 2020-10-16
	 * 
	 */
	public static boolean createWindow(String title, int width, int height) throws IOException {
		
		try {
			DictionaryHandler handler = new DictionaryHandler();
			final LexiNode dictionnaire = handler.loadDictionary();
	
			List<String> listSearch = new ArrayList<String>();
			listSearch.addAll(dictionnaire.getAllWords());
			
			List<String> listDictionary = new ArrayList<String>();
			listDictionary.addAll(dictionnaire.getAllWords());
			Collections.sort(listDictionary);
		
		
			/* Start building the frame */
			JFrame frame = new JFrame();
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			
			frame.setPreferredSize(new Dimension(width, height));

			frame.setTitle(title);
			frame.setFont(new Font("Arial", Font.PLAIN, 14));
			

			/* Call a private method to align the title to the center of the window */
			frame.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent componentEvent) {
					frame.setTitle(centerTitle(frame.getSize().width, title, frame.getFontMetrics(frame.getFont())));
				}
			});

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
			frame.setIconImage(icon);

			frame.setResizable(true);
			
			frame.setLocation(dimension.width / 2 - width / 2, dimension.height / 2 - height / 2);

			/* Create the panels used for the frame */
			JPanel mainPanel = new JPanel();
			JPanel topPanel = new JPanel();
			JPanel leftPanel = new JPanel();
			JPanel middlePanel = new JPanel();

			/* Create new layouts for the panels */
			mainPanel.setLayout(new GridBagLayout());
			topPanel.setLayout(new GridBagLayout());
			leftPanel.setLayout(new GridBagLayout());
			middlePanel.setLayout(new GridBagLayout());
			GridBagConstraints constraint = new GridBagConstraints();
			
			// ------------ topPanel -------------- //
			/* Create the base constraints for the topPanel */
			constraint.gridx = 0;
			constraint.gridy = 0;
			constraint.weighty = 0.09;
			constraint.gridwidth = 1;
			constraint.insets = new Insets(0,10,0,0); // Small spacing between the buttons
			constraint.weightx = 0;
			
			/* Create the load button with the previous constraint */
			JButton loadButton = new JButton("Charger");
			topPanel.add(loadButton, constraint);
			
			/* Create the save button with the previous constraint on the next grid */
			JButton saveButton = new JButton("Enregistrer");
			constraint.gridx = 1;
			topPanel.add(saveButton, constraint);
			
			/* Modify the constraints to fit the topPanel in the mainPanel */
			constraint.fill = GridBagConstraints.BOTH;
			constraint.gridwidth = 0;
			constraint.gridx = 0;
			constraint.gridy = 0;
			constraint.insets.set(0,0,0,0);
			mainPanel.add(topPanel, constraint);
			
			// ------------------ leftPanel -------------------- //
			/* Create a text field for the user to input text in
			* and adjust the constraints to fit in the leftPanel */
			JTextField userInputField = new JTextField("");
			constraint.weightx = 1;
			constraint.gridwidth = 1;
			constraint.weighty = 0.05;
			constraint.ipady = 30;
			constraint.gridx = 0;
			constraint.gridy = 0;
			leftPanel.add(userInputField, constraint);
			
			
			/* Create a test field non editable that will list all the words
			* that we might be searching for the in userInputField
			* and adjust the constraints to fit the leftPanel */
			
			// J'ai utilisé ce site pour régler un bug avecles JList. https://stackoverflow.com/questions/16214480/adding-elements-to-a-jlist
			// Merci à l'utilisateur Robin pour ça réponse et son exemple.
			DefaultListModel<String> listFieldModel = new DefaultListModel<>();
			JList<String> listField = new JList<String>(listFieldModel);
			//fin de l'emprunt
			
			
			listField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listField.setVisibleRowCount(listField.getHeight());
			
			constraint.ipady = 0;
			constraint.weighty = 0.95;
			constraint.gridx = 0;
			constraint.gridy = 1;
			
			JScrollPane leftScroll = new JScrollPane(listField);
			leftPanel.add(leftScroll, constraint);
			
			/* Adjust the constraints to fit the leftPanel in the mainPanel. */
			constraint.gridx = 0;
			constraint.gridy = 1;
			constraint.weighty = 0.9;
			constraint.weightx = 0.3;
			mainPanel.add(leftPanel, constraint);
			
			// ------------------ mainPanel --------------------- //
			/* Add a non editable text field that will contain my dictionary (list of words)
			* and adjust it's constraints to fit the mainPanel */

			/* Add a dictionary field that will store a JList of the dictionary */
			constraint.weightx = 0;
			constraint.fill = GridBagConstraints.BOTH;
			
			// J'ai utilisé ce site pour régler un bug avecles JList. https://stackoverflow.com/questions/16214480/adding-elements-to-a-jlist
			// Merci à l'utilisateur Robin pour ça réponse et son exemple.
			DefaultListModel<String> dictionaryFieldModel = new DefaultListModel<>();
			JList<String> dictionaryField = new JList<String>(dictionaryFieldModel);
			for(String i : listDictionary) {
				dictionaryFieldModel.addElement(i);
			}
			dictionaryField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			rightPanel.setBackground(Color.white);
			
			rightPanel.add(dictionaryField, constraint);
			
			/* Add a scrollPanel that will show the dictionary in the mainPanel */
			constraint.gridx = 2;
			constraint.gridy = 1;
			constraint.weightx = 0.3;
			JScrollPane scrollPanel = new JScrollPane(rightPanel);
			scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
			mainPanel.add(scrollPanel, constraint);


			/* Add a non editable text field that will contains the definition
			* of the word being currently surveyed for and adjust it's constraint 
			* to fit in the middle of the mainPanel */
			constraint.gridx = 0;
			constraint.gridy = 0;
			constraint.weightx = 1;
			constraint.ipady = 0;
			JTextArea definitionField = new JTextArea();
			definitionField.setLineWrap(true);
			JScrollPane definitionScroll = new JScrollPane(definitionField);
			definitionScroll.setPreferredSize(new Dimension(width/3, leftPanel.getHeight()));
			middlePanel.add(definitionScroll,constraint);
		
			constraint.gridx = 1;
			constraint.gridy = 1;
			constraint.weightx = 0.4;
			definitionField.setBackground(UIManager.getColor("TextField.background"));

			mainPanel.add(middlePanel, constraint);
			
			/* Add a add and modify button and adjust it's constraint to fit
			* at the bottom of the mainPanel */
			JButton addModifyButton = new JButton("ajouter/modifier");
			constraint.gridwidth = 0;
			constraint.weighty = 0.01;
			constraint.weightx = 1;
			constraint.gridx = 0;
			constraint.gridy = 2;
			mainPanel.add(addModifyButton, constraint);
			// ------------- end ---------------- //
			
			frame.add(mainPanel);
			frame.pack();
			frame.setVisible(true);
			
			// ------------- Action Listener -------------- //
			
			/* Override the methods from class LeyListener to fit our needs */
			KeyListener keyListener = new KeyListener() {
				
				public void keyListener(KeyEvent e) {
					if (e.getID() == KeyEvent.KEY_TYPED) {
						dictionnaire.searchAllWords(dictionnaire.getAllWords(), userInputField.getText());
					}
				}
				
				//Ces méthodes ne sont pas utilisées. Cependant, il est obligatoire de les implémenter.
				@Override
				public void keyPressed(KeyEvent e) {
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					
				}

				@Override
				public void keyTyped(KeyEvent e) {
					
				}
			};
			
			/* Override the methods from class MouseListener to fit our needs */
			MouseListener mouseListener = new MouseListener(){
				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() > 0) {
						
						if(e.getSource() == dictionaryField) {
							
							userInputField.setText(dictionaryField.getSelectedValue());
							definitionField.setText(dictionnaire.searchDefinition(dictionaryField.getSelectedValue()));
							
						}else if(e.getSource() == addModifyButton) {
							
							if(definitionField.getText().isEmpty()) {
								
								dictionnaire.remove(userInputField.getText());
								dictionaryFieldModel.removeElement(userInputField.getText());
							
							}else if (!dictionnaire.searchWord(userInputField.getText())) {
								dictionnaire.addWord(userInputField.getText(), definitionField.getText());
								dictionaryFieldModel.addElement(userInputField.getText());
								
								//Merci à l'utilisateur Paul Samsotha pour son code. Voici le lien où ça a été pris
								//https://stackoverflow.com/questions/22926170/sort-defaultlistmodel-with-java-sql-time-objects
								ArrayList<String> list = new ArrayList<String>();
								
								for(int i = 0; i < dictionaryFieldModel.size(); i++) {
									list.add(dictionaryFieldModel.get(i));
								}
								dictionaryFieldModel.clear();
								
								Collections.sort(list);
								for(String i : list) {
									dictionaryFieldModel.addElement(i.trim());
								}
								// Fin d'emprunt
								
							} else{
								dictionnaire.modify(userInputField.getText(), definitionField.getText());
							}
							
							
						}else if(e.getSource() == listField) {
							
							userInputField.setText(listField.getSelectedValue());
							definitionField.setText(dictionnaire.searchDefinition(userInputField.getText()));	
							
						}else if(e.getSource() == saveButton) {
							
							try {
								new DictionaryHandler().saveDictionary(dictionnaire);
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						} else if (e.getSource() == loadButton) {

							try {
								if (createWindow(title, width, height)) {
									frame.setVisible(false);
									frame.dispose();
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
			};
			
			
			/* Override the methods from class DocumentListener to fit our needs */
			DocumentListener documentListener = new DocumentListener() {
				public void display() {
					
					if(!dictionnaire.searchWord(userInputField.getText())) {
						definitionField.setText(null);
						
					}else {

						if(dictionnaire.searchWord(userInputField.getText())) {
							definitionField.setText(dictionnaire.searchDefinition(userInputField.getText()));
						}

					}
					
					listField.removeAll();
					listFieldModel.removeAllElements();
					List<String> list = new ArrayList<String>();
					list = (ArrayList<String>)dictionnaire.searchAllWords(list, userInputField.getText());
					
					if(list == null) {

					}else {
						
						for(String i : list) {
							listFieldModel.addElement(i);
						}
					}

				}
				
				public void update(DocumentEvent e) {	

				}

				@Override
				public void changedUpdate(DocumentEvent arg0) {

				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					display();
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					display();
					if(userInputField.getText().isEmpty()) {
						listField.removeAll();
						listFieldModel.removeAllElements();
					}

				}

			};
			
			/* Adding the Listeners to the GUI */
			userInputField.addKeyListener(keyListener);
			dictionaryField.addMouseListener(mouseListener);
			addModifyButton.addMouseListener(mouseListener);
			saveButton.addMouseListener(mouseListener);
			loadButton.addMouseListener(mouseListener);
			listField.addMouseListener(mouseListener);
			userInputField.getDocument().addDocumentListener(documentListener);

			return true; // Code has been fully executed

		} catch (IOException e) {
			return false; // Code has not been fully executed
		}
	}
	
	/**
	 * <p>This method automatically align the title to the center of the window. Nothing is returned by the 
	 * method.</p>
	 * <p>This method takes a JFrame object as a parameter and modifies it.<br>
	 * This method also takes the width of the window as a parameter</p>
	 * 
	 * @param width width of the frame
	 * @param title title of the frame
	 * @param font font of the frame
	 * 
	 * @return String (title of the frame)
	 */
	private static String centerTitle(int width, String title, FontMetrics font){
		 return String.format("%" +	((width/2 - font.stringWidth(title)/2))/font.stringWidth(" ") + "s", "") + title;
	}
	
}
