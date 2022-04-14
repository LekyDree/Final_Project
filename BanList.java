import java.util.ArrayList;

public class BanList /*extends*/
{
    ArrayList<String> bannedWords;
    
    public BanList(ArrayList<String> bannedWords, boolean defaultWords)
    {
        this.bannedWords = bannedWords;
        if(defaultWords)
        {
            defaultWords();
        }
        
    }

    /**
     * add the list of default baned words
     */
    public void defaultWords()
    {
        bannedWords.add("asshole");
        bannedWords.add("bitch");
        bannedWords.add("bullshit");
        bannedWords.add("crap");
        bannedWords.add("damn");
        bannedWords.add("fuck");
        bannedWords.add("fucking");
        bannedWords.add("goddamn");
        bannedWords.add("motherfuck");
        bannedWords.add("piss");
        bannedWords.add("pee");
        bannedWords.add("poo");
        bannedWords.add("shit");
        
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
        String[] split = post.getText().split(" ");
        for (String badWord : bannedWords) {
            
            for (String  splitString : split) {
                if(splitString.equals(badWord))
                {
                    return true;
                } 
            }
            
        }
        return false;
    }
    
}