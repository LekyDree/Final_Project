import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *  Demonstration of various controls
 */


public class GUI extends Application{

	public static ArrayList<String> words = new ArrayList<>();
	public static boolean spamPosts = false;
	public static boolean spamUsers = false;
    public static boolean defaultWordList = true;
    public static boolean priorityEnabled = false;
    private static String keyWord;

	public static void main(String[] args) {
		/* static method inherited from Application class that creates
		 * an instance of the Application class and starts the JavaFX lifecycle. */
		launch(args);
	}

	@Override public void start(Stage mainStage) throws InterruptedException {
		
		BorderPane mainPane = new BorderPane();     // make layout to hold controls
		setupControls(mainPane);  // initialize and place controls
		Scene scene = new Scene(mainPane);	        // Setup a Splash Screen
		setStage(mainStage, scene);                 // Finalize and show the stage
	}

	private void setupControls(BorderPane mainPane) {
		// Create labels

		Label filters = new Label("Filters:");
		
		// Creates Seperators
		Separator sep1 = new Separator();
		sep1.setOrientation(Orientation.HORIZONTAL);

		

		Button finish = new Button ("Done");
		finish.setVisible(false);
		

		// Create radio buttons
		RadioButton spamButton = new RadioButton("Spam");
		RadioButton maskButton = new RadioButton("Mask");
		RadioButton priButton = new RadioButton("Priority");
		

		TextField badWords = new TextField();
		badWords.setVisible(false);

        TextField priorityKeyword = new TextField();
        priorityKeyword.setVisible(false);

		// Create TextAreas
		//TextArea textarea1 = new TextArea("I am text area 1");

		ListView<String> badWord = new ListView<String>();
		badWord.setOrientation(Orientation.VERTICAL);
        badWord.setPrefWidth(350);

		// textarea1.setPrefRowCount(6);
		// textarea1.setPrefHeight(60);
		// textarea1.setWrapText(true);
		// textarea1.setPrefColumnCount(6);



		HBox textAreas = new HBox(10);
		textAreas.getChildren().addAll(badWord);
		textAreas.setPadding(new Insets(20,0,0,0));



		VBox otherControls = new VBox(80);
	
		HBox maskCont = new HBox(10);
        CheckBox defaultWords = new CheckBox("Disable default words");

        defaultWords.setVisible(false);
		maskCont.setPadding(new Insets(10, 0, 0, 0));
		maskCont.getChildren().addAll(maskButton, badWords, defaultWords, finish);

		HBox spamCont = new HBox(10);
		CheckBox checkUsers = new CheckBox("Spam Users");
		CheckBox checkPosts = new CheckBox("Spam Posts");

        
        HBox priCont = new HBox(10);
        priCont.getChildren().addAll(priButton, priorityKeyword);

		checkUsers.setVisible(false);
		checkPosts.setVisible(false);

		spamCont.setPadding(new Insets(10, 0, 0, 0));
		spamCont.getChildren().addAll(spamButton, checkUsers, checkPosts);

		VBox top = new VBox(10);
		top.setPadding(new Insets(10, 10, 10, 10));
		top.getChildren().addAll(filters, spamCont, maskCont, priCont, sep1);


		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setTop(top);
		mainPane.setBottom(textAreas);
		mainPane.setCenter(otherControls);	

		// Now setup listeners

		maskButton.setOnAction(e -> badWord(e, badWords, defaultWords, finish));
		spamButton.setOnAction(e -> spamBoxes(e, checkUsers, checkPosts));
        priButton.setOnAction(e -> priorityBox(e, priorityKeyword));

        // ArrayList of words to be masked
		//ArrayList<String> words = new ArrayList<>();

		badWords.setOnKeyPressed(e -> wordsToList(e, badWords, words, badWord));
        priorityKeyword.setOnKeyPressed(e -> addPriority(e, priorityKeyword, keyWord));

		finish.setOnAction(e -> hideWordBox(e, finish, defaultWords, badWords));

		checkUsers.selectedProperty().addListener(e -> enableUsers(spamUsers));
		checkPosts.selectedProperty().addListener(e -> enablePosts(spamPosts));

        defaultWords.selectedProperty().addListener(e -> disableDefault(defaultWordList));
        
	}

	
	// Will return user-entered words to be filtered as ArrayList of Strings
	private void badWord(ActionEvent e, TextField bw, CheckBox def, Button btn) {

		bw.setVisible(true);;
		btn.setVisible(true);
        def.setVisible(true);
		
	}
	
	private ArrayList<String> wordsToList(KeyEvent e, TextField textField, ArrayList<String> words, ListView list) {
		if (e.getCode().equals(KeyCode.ENTER) ||
				e.getCode().equals(KeyCode.TAB)) {
			
                    if (words.contains(textField.getText())) {
                        words.remove(textField.getText());
                        list.getItems().remove(textField.getText());
                    }
                    else {
                        words.add(textField.getText());
			            list.getItems().add(textField.getText());
                    }
                    
			textField.clear();
		}

		return words;
	}

    private String addPriority(KeyEvent e, TextField txt, String keyword) {
        
        if (e.getCode().equals(KeyCode.ENTER)) {
            keyWord = txt.getText();
            txt.setVisible(false);
        }
        return keyWord;
    }

    private void priorityBox(ActionEvent e, TextField txtField) {
        txtField.setVisible(true);
    }

    private void disableDefault(boolean def) {
        def = false;
    }

	private void enableUsers(boolean enabled) {
		enabled = true;
	}

	private void enablePosts(boolean enabled) {
		enabled = true;
	}

	private void hideWordBox(ActionEvent e, Button btn, CheckBox def, TextField tf) {
		btn.setVisible(false);
		tf.setVisible(false);
        def.setVisible(false); 
	}

	private void spamBoxes(ActionEvent e, CheckBox cb1, CheckBox cb2) {
		cb1.setVisible(true);
		cb2.setVisible(true);

	}

	private void setStage(Stage stage, Scene scene) {
		stage.setTitle("GUI Test");
		stage.setScene(scene);
		stage.show();		
	}

	private static ArrayList<String> getWords() {

		return words;
	}

	private static boolean getSpamPosts() {
		return spamPosts;
	}

	private static boolean getSpamUsers() {
		return spamUsers;
	}

    private static boolean getDefaultEnabled() {
        return defaultWordList;
    }

    private static String priorityWord() {
        return keyWord;
    }
}