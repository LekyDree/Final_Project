import javafx.application.Application;

/**
* Runs program
* @author Ethan Rienzi
* @version 1.0
*/
public class Driver {
    
    public static void main(String[] args){

        Application.launch(GUI.class, args);
        
        UseFile.openFile();
        UseFile.makeFile();
        UseFile.editFile();
    }
}
