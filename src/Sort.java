import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sort extends Filter  {

    private String keyWord;

    public Sort(String keyWord) {
        this.keyWord = keyWord.toLowerCase();
    }

    public void filterPosts() {
        List<Post> feed = PostFeed.getPostFeed();
        LinkedList<Post> priority = new LinkedList<>();
        for (Post post : feed) {
            if (post.getText().toLowerCase().contains(keyWord) || post.getUserName().toLowerCase().contains(keyWord)) {
                priority.add(post);
            }
        }
        PostFeed.removePosts(priority);
        PostFeed.addPosts(0, priority);
    }
}
