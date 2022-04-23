import javafx.application.Application;

public class Driver {
    
    public static void main(String[] args){


        Application.launch(GUI.class, args); //this super important
        
       
        UseFile.openFile();
        UseFile.makeFile();
        UseFile.editFile();
  
        

    }
}
