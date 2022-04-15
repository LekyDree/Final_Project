import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
     * @param ArrayList<String> bannedWords
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
            ArrayList<String> checkedWords = checkPost(post);  
            if(!checkedWords.isEmpty())
            {
                //mask
                checkedWords.forEach(badWord -> post.setText((post.getText().replace(badWord, "████"))));
            }
        }
    }

    /**
     * Checks the post for banned words and then returns the banned words that were found
     * @param post
     * @return ArrayList<String> checkedWords
     */
    public ArrayList<String> checkPost(Post post)
    {
        Pattern pattern;
        ArrayList<String> checkedWords = new ArrayList<>();

        for (String badWord : bannedWords) {
            pattern = Pattern.compile("(([^A-Za-z])|^)" + badWord + "(([^A-Za-z])|$)");
            Matcher matcher = pattern.matcher(post.getText());
            while (matcher.find())
            {
                checkedWords.add(badWord);
            } 
        }
        return checkedWords;
    }
    
}