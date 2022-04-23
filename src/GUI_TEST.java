import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class GUI_TEST extends Application {

    private static Label lblMessage = new Label();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage mainStage) {
        BorderPane mainPane = new BorderPane();  // make layout to hold controls
        setupControls(mainPane);  // initialize and place controls
        Scene scene = new Scene(mainPane);  // Set up the main scene
        setStage(mainStage, scene);   // finalize and show the stage     
    }

    private void  setupControls(BorderPane mainPane) {
        Button btnHello = new Button();
        btnHello.setText("Say 'Hello World'");
        btnHello.setOnAction(e -> lblMessage.setText("Hello World"));

        // Add controls to border pane
        mainPane.setTop(btnHello);
        BorderPane.setAlignment(btnHello, Pos.CENTER);


        
        mainPane.setCenter(new Label(" "));  // cheap way to get vertical space
        mainPane.setBottom(lblMessage);
        BorderPane.setAlignment(lblMessage, Pos.CENTER);
    }

    private static void setStage(Stage mainStage, Scene scene) {
        mainStage.setWidth(499);
        mainStage.setTitle("Hello World");
        mainStage.setScene(scene);
        mainStage.show();
    }
}
