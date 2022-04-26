import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.regex.Pattern;

import java.util.regex.Matcher;

/**
* Filters out a list of banned words
* @author Holden Fellenger 
* @version 1.0
*/
public class BanList extends Filter implements Serializable
{
    private static final long serialVersionUID = 43892023963089865L;

    private ArrayList<String> bannedWords = new ArrayList<>();
        
    public BanList(boolean defaultWords, List<String> words)
    {
        if(defaultWords) defaultWords();
        bannedWords.addAll(words);
        System.out.println(bannedWords);
    }

    /**
     * add the list of default baned words
     * @param ArrayList<String> bannedWords
     */
    public void defaultWords()
    {
        try (Reader myReader = new BufferedReader(new FileReader("src/badWords.txt"))) {

			String thisLine; 
			while ((thisLine = ((BufferedReader) myReader).readLine()) != null) {
                bannedWords.add(thisLine);
			}
			System.out.println();
            myReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }

    @Override
    /** 
    *creates a concrete method for filterPosts
    *
    */
    public void filterPosts()
    {
        for (Post post : postFeed) {
            ArrayList<String> checkedWords = checkPost(post);  
            if(!checkedWords.isEmpty()) {
                checkedWords.forEach(badWord -> {
                    String asterisks = "*".repeat(badWord.length());
                    post.setText((post.getText().replace(badWord, asterisks)));
                });
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
        Matcher matcher;
        ArrayList<String> checkedWords = new ArrayList<>();

        for (String badWord : bannedWords) {
            pattern = Pattern.compile("(([^A-Za-z])|^)" + badWord + "(([^A-Za-z])|$)");
            matcher = pattern.matcher(post.getText().toLowerCase());
            while (matcher.find())
            {
                checkedWords.add(matcher.group().strip());
            } 
        }
        System.out.println(checkedWords);
        return checkedWords;
    }
}