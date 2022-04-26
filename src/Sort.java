import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

/**
* Puts posts containg a keyword to the top
* @author Ethan Rienzi, Alex Tapiero
* @version 1.0
*/
public class Sort extends Filter implements FeedChanger, Serializable  {

    private static final long serialVersionUID = 98031462017356L;

    private String keyWord;
    private transient HashSet<Post> priority = new HashSet<>();

    /**
    * constructor for Sort
    * @param String keyWord
    */
    public Sort(String keyWord) {
        this.keyWord = keyWord.toLowerCase();
        
    }

    /**
    *filters the posts by adding them to the top
    */
    @Override
    public void filterPosts() {
        PostFeed.removePosts(priority);
        PostFeed.addPosts(0, priority);
    }

    /**
    * determines weather or not the post contains the keyword to be added to priority to be filtered in filterPosts()
    */
    @Override
    public void determineFeedAlteration() {
        priority = new HashSet<>();
        for (Post post : postFeed) {
            if (post.getText().toLowerCase().contains(keyWord) || post.getUserName().toLowerCase().contains(keyWord)) {
                priority.add(post);
            }
        }
    }
}
