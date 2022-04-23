/**
 * Final project GUI
 * @author Ethan Rienzi
 * @version 1.0
 */

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;



public class GUI extends Application{
	final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	public static ArrayList<String> words = new ArrayList<>();
    public static String keyWord;
	public static String address;

	// Visual elements that must be globally accessible to determine whether they are selected
	static RadioButton spamButton = new RadioButton("Spam");
	static RadioButton maskButton = new RadioButton("Mask");
	static RadioButton sortButton = new RadioButton("Sort");
	static CheckBox defaultWords = new CheckBox("Disable default words");
	static CheckBox checkUsers = new CheckBox("Spam Users");
	static CheckBox checkPosts = new CheckBox("Spam Posts");
	static boolean keywordEntered = false;

	public static void main(String[] args) {
		/* static method inherited from Application class that creates
		 * an instance of the Application class and starts the JavaFX lifecycle. */
		
		launch(args);
	}

	@Override public void start(Stage mainStage) throws InterruptedException {
		
		BorderPane mainPane = new BorderPane();     // make layout to hold controls
		setupControls(mainPane, mainStage);  // initialize and place controls
		Scene scene = new Scene(mainPane);	        // Setup a Splash Screen
		setStage(mainStage, scene);                 // Finalize and show the stage
	}

	private void setupControls(BorderPane mainPane, Stage mainStage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		
		
		// Create labels

		Label filters = new Label("Filters:");
		
		// Creates Seperators
		Separator sep1 = new Separator();
		sep1.setOrientation(Orientation.HORIZONTAL);
		

		// Set up mask filter controls
		HBox maskCont = new HBox(10);

		Button finish = new Button ("Done");
		finish.setVisible(false);

		Button apply = new Button("Apply");

		Button fileSelect = new Button("Select File");

		Label chosenFile = new Label();
		chosenFile.setVisible(false);
		
		TextField badWords = new TextField();
		badWords.setVisible(false);
		defaultWords.setVisible(false);

		maskCont.setPadding(new Insets(10, 0, 0, 0));
		maskCont.getChildren().addAll(maskButton, badWords, defaultWords, finish);
		

		
		// Setup spam filter controls
		HBox spamCont = new HBox(10);
		checkUsers.setVisible(false);
		checkPosts.setVisible(false);

		spamCont.setPadding(new Insets(10, 0, 0, 0));
		spamCont.getChildren().addAll(spamButton, checkUsers, checkPosts);


		// Setup sort filter controls
		HBox priCont = new HBox(10);
        
        TextField sortKeyword = new TextField();
        sortKeyword.setVisible(false);
		Label keywordLbl = new Label("Enter a keyword");
		keywordLbl.setVisible(false);
		priCont.getChildren().addAll(sortButton, sortKeyword, keywordLbl);
		


		// Setup other visual elements
		HBox textAreas = new HBox(10);
		ListView<String> badWord = new ListView<String>();
		badWord.setOrientation(Orientation.VERTICAL);
        badWord.setPrefWidth(350);
		badWord.setPrefHeight(200);
		badWord.setVisible(false);

		textAreas.getChildren().addAll(badWord, apply);
		textAreas.setPadding(new Insets(20,0,0,0));

		VBox otherControls = new VBox(80);
		VBox top = new VBox(10);
		HBox fileControls = new HBox(10);
		fileControls.setPadding(new Insets(0, 0, 0, 120));
		fileControls.getChildren().setAll(fileSelect, chosenFile);

		top.setPadding(new Insets(10, 10, 10, 10));
		top.getChildren().addAll(fileControls, filters, spamCont, maskCont, priCont, sep1);


		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setTop(top);
		mainPane.setBottom(textAreas);
		mainPane.setCenter(otherControls);	

		// Setup listeners

		maskButton.setOnAction(e -> badWord(e, badWords, defaultWords, finish, badWord));
		spamButton.setOnAction(e -> spamBoxes(e, checkUsers, checkPosts));
        sortButton.setOnAction(e -> sortBox(e, sortKeyword, keywordLbl));

		badWords.setOnKeyPressed(e -> wordsToList(e, badWords, words, badWord));
        sortKeyword.setOnKeyPressed(e -> addSort(e, sortKeyword, keyWord, keywordLbl));

		finish.setOnAction(e -> hideWordBox(e, finish, defaultWords, badWords));

		apply.setOnAction(e -> {
			
			});

		fileSelect.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(mainStage);
			address = selectedFile.getAbsolutePath(); //added so we can work with the address

			
			chosenFile.setText(selectedFile.getName());
			chosenFile.setVisible(true);
		});
	}

	
	/**
	 * When mask radiobutton enabled, makes corresponding elements visible to user
	 * @param e
	 * @param bw
	 * @param def
	 * @param btn
	 */
	private void badWord(ActionEvent e, TextField bw, CheckBox def, Button btn, ListView list) {

		bw.setVisible(true);;
		btn.setVisible(true);
        def.setVisible(true);
		list.setVisible(true);

		logger.log(Level.INFO, "MASKING ENABLED");
		
	}
	
	/**
	 * Collects user-entered words to be censored, adds them to arrayList of strings and displays them in list
	 * If a word is entered twice, it will be removed from the list and collection of strings
	 * @param e
	 * @param textField
	 * @param words
	 * @param list
	 * @return
	 */
	private void wordsToList(KeyEvent e, TextField textField, ArrayList<String> words, ListView list) {
		String whiteSpace = "\\s+";
		Pattern blank = Pattern.compile(whiteSpace);

		if (e.getCode().equals(KeyCode.ENTER) ||
				e.getCode().equals(KeyCode.TAB)) {
					if (textField.getText() != "" && !blank.matcher(textField.getText()).matches()) {
						if (words.contains(textField.getText())) {
							words.remove(textField.getText());
							list.getItems().remove(textField.getText());
						}
						else {
							words.add(textField.getText());
							list.getItems().add(textField.getText());
						}
					}
                    if (words.contains(textField.getText())) {
                    
			textField.clear();
			}
		}
	}
	

	/**
	 * Collects keyword to be sorted
	 * @param e
	 * @param txt
	 * @param keyword
	 * @param lbl
	 * @return
	 */
    private void addSort(KeyEvent e, TextField txt, String keyword, Label lbl) {
        
        if (e.getCode().equals(KeyCode.ENTER)) {
            keyWord = txt.getText();
            txt.setVisible(false);
			lbl.setVisible(false);
			keywordEntered = true;
        }
    }

	/**
	 * When sort radio button pressed, makes related elements visible
	 * @param e
	 * @param txtField
	 * @param lbl
	 */
    private void sortBox(ActionEvent e, TextField txtField, Label lbl) {
        txtField.setVisible(true);
		lbl.setVisible(true);

		logger.log(Level.INFO, "SORTING ENABLED");

    }


	/**
	 * When spam users checkbox enabled, sets corresponding boolean to true, allowing for this filter to be enabled
	 * @param enabled
	 */
	private void enableUsers(boolean enabled) {
		enabled = true;
	}

	/**
	 * When spam posts checkbox enabled, sets corresponding boolean to true, allowing for this filter to be enabled
	 * @param enabled
	 */
	private void enablePosts(boolean enabled) {
		enabled = true;
	}

	/**
	 * When "Done" button pressed, hides elements corresponding to mask filter
	 * @param e
	 * @param btn
	 * @param def
	 * @param tf
	 */
	private void hideWordBox(ActionEvent e, Button btn, CheckBox def, TextField tf) {
		btn.setVisible(false);
		tf.setVisible(false);
        def.setVisible(false); 
	}

	/**
	 * When spam radiobutton enabled, makes corresponding elements visible to user
	 * @param e
	 * @param cb1
	 * @param cb2
	 */
	private void spamBoxes(ActionEvent e, CheckBox cb1, CheckBox cb2) {
		cb1.setVisible(true);
		cb2.setVisible(true);

		logger.log(Level.INFO, "SPAM FILTER ENABLED");

	}


	/**
	 * Shows GUI
	 * @param stage
	 * @param scene
	 */
	private void setStage(Stage stage, Scene scene) {
		stage.setTitle("GUI Test");
		stage.setScene(scene);
		stage.show();		
	}

	public static String getAddress() {
		if (address != null) {
			return address;
		}
		return "src/instructions.txt";
	}

	//public List<Filter> getFilters() {
		
	//}
}