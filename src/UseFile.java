import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
    
    
/**
* Performs operations on input and output files
* @author Gabe Dolce, Ethan Rienzi, Holden Fellenger
* @version 1.0
*/
public class UseFile {
    private static String file = "src/input.txt"; //we can use this as a default instruction file
    private static List<Post> feed = PostFeed.getPostFeed();
    private static String destinationFile = "src/output.txt";

    public UseFile(String file){
        this.file = file;
    }

    public UseFile(){
    }
    
    /**
    returns a file address
    @return the file address of a the file selected
     */
    public String getFile(){
        return file;
    }

    /**
    sets the file address to a new name
    @param the new file name
    */
    public void setFile(String newFile){
        file = newFile;
    }
    
    /**
     *opens a file and reads it. Additionally sets variables equal to the information inside the text.
     */
    public static void openFile()
    {   
        file = GUI.getAddress();
        
        try (Reader myReader = new BufferedReader(new FileReader(file))) {

			String thisLine; 
			while ((thisLine = ((BufferedReader) myReader).readLine()) != null) {
                int colIndex = thisLine.indexOf(":");
                if (colIndex != -1)
                {
                    String userName = thisLine.substring(0, colIndex);
                    String postText = thisLine.substring(colIndex);
                    Post currPost = new Post(postText, userName);
                    PostFeed.addPost(currPost);
                }
                else
                {
                    throw new FormatException();
                }
			}
			System.out.println();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
	}

/**
 * edits a file according to the specifications of the filters given
 */
    public static void editFile()
    {
        
        //can be edited to take in a collection of words parameter additionally and convert that into a string.
        try (Writer myWriter = new BufferedWriter(new FileWriter(destinationFile))) 
             {
				myWriter.write(System.lineSeparator());  // new line (regardless of op system!)
				myWriter.write(System.lineSeparator());

                //put a for each here to go through a list of words followed by a lineSeparator in between them
                //myWriter.write(PostFeed.getPostFeed().toString());

                Filters.applyFilters();

                for (Post post : feed) {   
                    myWriter.write(post.toString());
                    myWriter.write(System.lineSeparator());
                }
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
    }

/**
 * Creates a new file to output to
 */
    public static void makeFile(){
        try {
            File myObj = new File(destinationFile);
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

