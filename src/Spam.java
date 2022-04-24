import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Spam extends Filter implements FeedChanger, Serializable{
    
    private static final long serialVersionUID = 98024176423187L;

    private transient HashMap<String, LinkedList<Post>> postsWithSameUser = new HashMap<>();
    private transient HashMap<String, LinkedList<Post>> postsWithSameText = new HashMap<>();

    private int numRepeatsAllowed;
    private boolean deleteSpamUsersEnabled;
    private boolean deleteSpamTextEnabled;

    private transient HashSet<Post> postsToRemove = new HashSet<>();

    public Spam(int numRepeatsAllowed, boolean deleteSpamTextEnabled, boolean deleteSpamUsersEnabled) {
        this.numRepeatsAllowed = numRepeatsAllowed;
        this.deleteSpamTextEnabled = deleteSpamTextEnabled;
        this.deleteSpamUsersEnabled = deleteSpamUsersEnabled;
    }

    public void filterPosts() {
        postsWithSameText = new HashMap<>();
        postsWithSameUser = new HashMap<>();
        postsToRemove = new HashSet<>();
        postFeed.forEach(post -> collectSpam(post));
        if (deleteSpamTextEnabled) deleteSpamText();
        if (deleteSpamUsersEnabled) deleteSpamUsers();
    }

    private void collectSpam(Post post) {
        String postUser = post.getUserName();
        String postText = post.getText();
        goThroughPosts(postUser, postsWithSameUser.get(postUser), post, true);
        goThroughPosts(postText, postsWithSameText.get(postText), post, false);
    }

    private void goThroughPosts(String postInfo, LinkedList<Post> posts, Post post, boolean userPassThrough) {
        if (posts == null) {
            posts = new LinkedList<>();
            ((userPassThrough) ? postsWithSameUser : postsWithSameText).put(postInfo, posts);
        }
        posts.add(post);        
    }

    private void deleteSpamText() {
        for (LinkedList<Post> posts : postsWithSameText.values()) {
            if (posts.size() > numRepeatsAllowed) {
                postsToRemove.addAll(posts);
            }
        }
    }

    private void deleteSpamUsers() {
        Comparator<List<Post>> higherRepeat = (posts1, posts2) -> posts1.size() - posts2.size();

        postsWithSameUser.forEach((user, posts) -> {
            Collection<List<Post>> userPostRepeats = posts.stream().collect(Collectors.groupingBy(Post::getText)).values();
            if (userPostRepeats.stream().max(higherRepeat).get().size() > numRepeatsAllowed) {
                postsToRemove.addAll(posts);
            }
        });
    }

    public void changeFeed() {
        PostFeed.removePosts(postsToRemove);
    }
}
