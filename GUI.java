import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File; //added this line here because File had an issue other

//returns the address of the txt file for the user by displaying the files on the PC to the user.
public class GUI extends Application {
    private static String address;

//    public GUI()
//    {
//        this.start();
//    }
  // public static void main(String[] args) {
    //   launch(args);

   // }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        FileChooser fileChooser = new FileChooser();
        
        //should limit results to text files only
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Button button = new Button("Select File");
        

         button.setOnAction(e -> {
             File selectedFile = fileChooser.showOpenDialog(primaryStage);
             address = selectedFile.toString(); //added so we can work with the address
         });
        

        VBox vBox = new VBox(button);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getAddress(){
        return address;
    }
}