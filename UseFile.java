import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
    
public class UseFile {
    private static String file = "src/input.txt"; //we can use this as a default instruction file
    private static LinkedList<Post> feed = PostFeed.getPostFeed();

    public UseFile(String file){
        this.file = file;
    }

    public UseFile(){
    }
    
    public String getFile(){
        return file;
    }

    public void setFile(String newFile){
        file = newFile;
    }
    
    public static void openFile()
    {   
        file = GUI.getAddress();
        
        try (Reader myReader = new BufferedReader(new FileReader(file))) {//risky code so try catch. Buffered is faster

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

            myReader.close();
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}

        
	
	}

    public static void editFile()
    {
        
        //can be edited to take in a collection of words parameter additionally and convert that into a string.
        try (Writer myWriter = new BufferedWriter(new FileWriter("src/output.txt"))) 
             {
				// In the beginning both files were opened as overwrite
				myWriter.write("Listed here are some words which may find offensive");
				
				myWriter.write(System.lineSeparator());  // new line (regardless of op system!)
				myWriter.write(System.lineSeparator());

                //put a for each here to go through a list of words followed by a lineSeparator in between them
                //myWriter.write(PostFeed.getPostFeed().toString());

                ArrayList<Filter> filters = new ArrayList<>();
                for (Filter f : filters) {
                    Filter filter;
                    if (Filter.sortActive) {
                        filter = new Sort();
                    }
                    else if (Filter.maskActive) {
                        filter = new BanList(GUI.words, !GUI.defaultWords.isSelected());
                        
                    }
                    else {
                        filter = new Spam(1, GUI.checkPosts.isSelected(), GUI.checkUsers.isSelected());
                    }
                    filter.filterPosts();
                }

                for (Post post : feed) {   
                    myWriter.write(post.toString());
                    myWriter.write(System.lineSeparator());
                }
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static void makeFile(){

        try {
            File myObj = new File("src/output.txt");
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

