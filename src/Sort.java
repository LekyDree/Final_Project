import java.io.Serializable;
import java.util.LinkedList;

public class Sort extends Filter implements FeedChanger, Serializable  {

    private static final long serialVersionUID = 98031462017356L;

    private String keyWord;
    private transient LinkedList<Post> priority = new LinkedList<>();

    public Sort(String keyWord) {
        this.keyWord = keyWord.toLowerCase();
        
    }

    public void filterPosts() {
        priority = new LinkedList<>();
        for (Post post : postFeed) {
            if (post.getText().toLowerCase().contains(keyWord) || post.getUserName().toLowerCase().contains(keyWord)) {
                priority.add(post);
            }
        }
    }

    public void changeFeed() {
        PostFeed.removePosts(priority);
        PostFeed.addPosts(0, priority);
    }
}
