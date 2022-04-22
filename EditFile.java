import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class EditFile {
    private String file;
    private boolean mode;
    
    public EditFile(String file, boolean mode){
        this.file = file;
        this.mode = mode;
    }

    public void editFile()
    {
        //can be edited to take in a collection of words parameter additionally and convert that into a string.
        try (Writer myWriter = new FileWriter(file, mode)) 
        
             {

				// In the beginning both files were opened as overwrite
				myWriter.write("Listed here are some words which may find offensive");
				
				myWriter.write(System.lineSeparator());  // new line (regardless of op system!)
				
                //put a for each here to go through a list of words followed by a lineSeparator in between them
				myWriter.write("Why did I sign up for this course?");
				
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
    }
}
