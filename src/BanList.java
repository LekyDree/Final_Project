import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.regex.Pattern;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;

public class BanList extends Filter implements Serializable
{
    private static final long serialVersionUID = 43892023963089865L;

    private ArrayList<String> bannedWords = new ArrayList<>();
        
    public BanList(boolean defaultWords)
    {
        if(defaultWords) defaultWords();
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
    public void filterPosts()
    {
        for (Post post : postFeed) {
            ArrayList<String> checkedWords = checkPost(post);  
            if(!checkedWords.isEmpty()) {
                //mask
                checkedWords.forEach(badWord -> post.setText((post.getText().replace(badWord, "----"))));
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

    /**
	 * Collects user-entered words to be censored, adds them to arrayList of strings and displays them in list
	 * If a word is entered twice, it will be removed from the list and collection of strings
	 * @param e
	 * @return
	 */
	public void wordsToList(TextField badWordsTxt, ListView<String> badWordLst) {
		String whiteSpace = "\\s+";
		Pattern blank = Pattern.compile(whiteSpace);
		if (badWordsTxt.getText() != "" && !blank.matcher(badWordsTxt.getText()).matches()) {
			if (bannedWords.contains(badWordsTxt.getText())) {
				bannedWords.remove(badWordsTxt.getText());
				badWordLst.getItems().remove(badWordsTxt.getText());
			}
			else {
				bannedWords.add(badWordsTxt.getText());
				badWordLst.getItems().add(badWordsTxt.getText());
			}
		}
        if (bannedWords.contains(badWordsTxt.getText())) {
			badWordsTxt.clear();
		}
	}
    
}