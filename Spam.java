import java.security.KeyStore.Entry;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Spam extends Filter{
    
    private HashMap<String, LinkedList<Post>> postsWithSameUser = new HashMap<>();
    private HashMap<String, LinkedList<Post>> postsWithSameText = new HashMap<>();
    LinkedList<Post> allPosts;

    private int numRepeatsAllowed;
    private boolean deleteSpamUsersEnabled;
    private boolean deleteSpamTextEnabled;

    public Spam(int numRepeatsAllowed, boolean deleteSpamTextEnabled, boolean deleteSpamUsersEnabled) {
        this.numRepeatsAllowed = numRepeatsAllowed;
        this.deleteSpamTextEnabled = deleteSpamTextEnabled;
        this.deleteSpamUsersEnabled = deleteSpamUsersEnabled;
    }

    public void filterPosts() {
        allPosts = PostFeed.getPostFeed();
        allPosts.forEach(post -> collectSpam(post));
        if (deleteSpamTextEnabled) deleteSpamText();
        if (deleteSpamUsersEnabled) deleteSpamUsers();

        System.out.println(postsWithSameUser);
        System.out.println(postsWithSameText);
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
                PostFeed.removePosts(posts);
            }
        }
    }

    private void deleteSpamUsers() {
        postsWithSameUser.forEach((user, posts) -> {
            Collection<List<Post>> posteys = posts.stream().collect(Collectors.groupingBy(Post::getText)).values();
            if (posteys.stream().max((posts1, posts2) -> posts1.size() - posts2.size()).get().size() > numRepeatsAllowed) {
                PostFeed.removePosts(posts);
            }
        });
    }
}
