import java.util.LinkedList;

public class Sort extends Filter implements FeedChanger  {

    private String keyWord;
    LinkedList<Post> priority = new LinkedList<>();

    public Sort(String keyWord) {
        this.keyWord = keyWord.toLowerCase();
    }

    public void filterPosts() {
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
