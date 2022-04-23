import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.LinkedList;

public class CreateFile {
    private String fileName;
    LinkedList<Post> feed;

    public CreateFile(String fileName){
        this.fileName = fileName;
    }

    public void makeFile(){

        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
              myObj.createNewFile();
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } 
          catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    
    
   
}