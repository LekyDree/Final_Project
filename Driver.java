import javafx.application.Application;

public class Driver {
    
    public static void main(String[] args){

        //reading from a file
        ReadFile sesame = new ReadFile("src/instructions.txt");
        sesame.OpenFile();

        //creating a new file of a certain name
        CreateFile ship = new CreateFile("src/testOutput.txt");//only allows for one file of same name at a time
        ship.makeFile();

        EditFile pencil = new EditFile("src/testOutput.txt", false);//no check for whether something was already written to it.
        //true is append
        //false is overwrite
        pencil.editFile();

        //EditFile pencil = new EditFile("src/testOutput.txt", false, words); would take an arraylist and work with those instead

        Application.launch(GUI.class, args);
        
       // System.out.println(fileChooser.getAddress());

        
       // EditFile pencil2 = new EditFile(fileChooser.getAddress(), false);
       // pencil2.editFile();



    }
}
