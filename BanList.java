import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class BanList /*extends*/
{
    ArrayList<String> bannedWords;
    
    public BanList(ArrayList<String> bannedWords, boolean defaultWords)
    {
        this.bannedWords = bannedWords;
        if(defaultWords)
        {
            defaultWords(this.bannedWords);
        }

        
        
    }

    /**
     * add the list of default baned words
     */
    public void defaultWords(ArrayList<String> bannedWords)
    {
        try (Reader myReader = new BufferedReader(new FileReader("badWords.txt"))) {

			String thisLine; 
			while ((thisLine = ((BufferedReader) myReader).readLine()) != null) {
                bannedWords.add(thisLine);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }


    public ArrayList<String> getBannedWords() {
        return this.bannedWords;
    }

    public void setBannedWords(ArrayList<String> bannedWords) {
        this.bannedWords = bannedWords;
    }

    //@oveerride
    public void filterPosts(ArrayList<Post> posts)
    {
        for (Post post : posts) 
        {
            if(checkPost(post))
            {
                //mask
                System.out.println(post.getUserName() + "'s post contained a bad word");
                

            }
        }
    }

    public boolean checkPost(Post post)
    {
        String notLetters = "^?![A-Za-z]$";
        //Pattern pattern = Pattern.compile(notLetters);
        for (String badWord : bannedWords) {
            if(post.getText().contains(notLetters + badWord + notLetters))
            {   
                return true;
            } 
        }
            
        return false;
    }
    
}