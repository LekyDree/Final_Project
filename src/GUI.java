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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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

	private static String address;

	// Visual elements that must be globally accessible to determine whether they are selected
	private static RadioButton spamButton = new RadioButton("Spam");
	private static RadioButton maskButton = new RadioButton("Mask");
	private static RadioButton sortButton = new RadioButton("Sort");
	private static ToggleGroup filterToggleGroup = new ToggleGroup();
	private static CheckBox defaultWordsCheck = new CheckBox("Disable default words");
	private static CheckBox usersCheck = new CheckBox("Spam Users");
	private static CheckBox postsCheck = new CheckBox("Spam Posts");

	private static TextField badWordsTxt = new TextField();
	private static ListView<String> badWordLst = new ListView<String>();
	private static TextField sortKeywordTxt = new TextField();
	private static Label keywordLbl = new Label("Enter a keyword");
	private static Button apply = new Button("Apply");

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

		apply.setVisible(false);

		Button fileSelect = new Button("Select File");

		Label chosenFile = new Label();
		chosenFile.setVisible(false);
		
		badWordsTxt.setVisible(false);
		defaultWordsCheck.setVisible(false);

		maskCont.setPadding(new Insets(2, 0, 0, 0));
		maskCont.getChildren().addAll(maskButton, badWordsTxt, defaultWordsCheck);
		

		
		// Setup spam filter controls
		HBox spamCont = new HBox(10);
		usersCheck.setVisible(false);
		postsCheck.setVisible(false);

		spamCont.setPadding(new Insets(10, 0, 0, 0));
		spamCont.getChildren().addAll(spamButton, usersCheck, postsCheck);


		// Setup sort filter controls
		HBox priCont = new HBox(10);
        
        sortKeywordTxt.setVisible(false);
		keywordLbl.setVisible(false);

		priCont.setPadding(new Insets(10, 0, 0, 0));
		priCont.getChildren().addAll(sortButton, sortKeywordTxt, keywordLbl);
		


		// Setup other visual elements
		HBox textAreas = new HBox(10);
		badWordLst.setOrientation(Orientation.VERTICAL);
        badWordLst.setPrefWidth(350);
		badWordLst.setPrefHeight(200);
		badWordLst.setVisible(false);

		textAreas.getChildren().addAll(badWordLst, apply);
		textAreas.setPadding(new Insets(20,0,0,0));

		VBox otherControls = new VBox(80);
		VBox top = new VBox(10);
		HBox fileControls = new HBox(10);
		fileControls.setPadding(new Insets(0, 0, 0, 120));
		fileControls.getChildren().setAll(fileSelect, chosenFile);

		top.setPadding(new Insets(10, 10, 10, 10));
		top.getChildren().addAll(fileControls, filters, spamCont, priCont, maskCont, sep1);

		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setTop(top);
		mainPane.setBottom(textAreas);
		mainPane.setCenter(otherControls);	

		maskButton.setToggleGroup(filterToggleGroup);
		spamButton.setToggleGroup(filterToggleGroup);
		sortButton.setToggleGroup(filterToggleGroup);

		filterToggleGroup.selectedToggleProperty().addListener((change, oldRadioButton, newRadioButton) -> {
			toggleFilterSettings(oldRadioButton, false);
		});

		maskButton.setOnAction(e -> {
			apply.setVisible(true);
			toggleMaskSettings(true);
		});

		spamButton.setOnAction(e -> {
			apply.setVisible(true);
			toggleSpamSettings(true);
		});

		sortButton.setOnAction(e -> {
			apply.setVisible(true);
			toggleSortSettings(true);
		});

		apply.setOnAction(e -> {
			toggleMaskSettings(false);
			toggleSpamSettings(false);
			toggleSortSettings(false);
			apply.setVisible(false);
			createFilter();
		});

		fileSelect.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(mainStage);
			address = selectedFile.getAbsolutePath(); //added so we can work with the address

			
			chosenFile.setText(selectedFile.getName());
			chosenFile.setVisible(true);
		});
	}


	private void toggleFilterSettings(Toggle button, boolean toggleOn) {
		if (button.equals(maskButton)) {
			toggleMaskSettings(toggleOn);
		}
		else if (button.equals(spamButton)) {
			toggleSpamSettings(toggleOn);
		}
		else if (button.equals(sortButton)) {
			toggleSortSettings(toggleOn);
		}
	}

	private void toggleMaskSettings(boolean toggleOn) {
		badWordsTxt.setVisible(toggleOn);
        defaultWordsCheck.setVisible(toggleOn);
		badWordLst.setVisible(toggleOn);
		if (toggleOn) logger.log(Level.INFO, "MASKING ENABLED");
	}

	private void toggleSpamSettings(boolean toggleOn) {
		postsCheck.setVisible(toggleOn);
		usersCheck.setVisible(toggleOn);
		if (toggleOn) logger.log(Level.INFO, "SPAM FILTER ENABLED");
	}

	private void toggleSortSettings(boolean toggleOn) {
		sortKeywordTxt.setVisible(toggleOn);
		keywordLbl.setVisible(toggleOn);
		if (toggleOn) logger.log(Level.INFO, "SORTING ENABLED");
	}


	private void createFilter() {
		Filter filter;
        if (sortButton.isSelected()) {
            filter = new Sort(sortKeywordTxt.getText());
			sortButton.setSelected(false);
        }
        else if (maskButton.isSelected()) {
            filter = new BanList(!defaultWordsCheck.isSelected());
			((BanList)filter).wordsToList(badWordsTxt, badWordLst);
			maskButton.setSelected(false);
        }
        else {
            filter = new Spam(1, postsCheck.isSelected(), usersCheck.isSelected());
			spamButton.setSelected(false);
        }
        Filters.addFilter(filter);
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
}