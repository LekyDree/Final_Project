import java.util.ArrayList;

public class BanList /*extends*/
{
    ArrayList<String> bannedWords;
    
    public BanList(ArrayList<String> bannedWords)
    {
        this.bannedWords = bannedWords;
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