import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Spam extends Filter{
    
    private HashMap<String, Integer> riskyUsers = new HashMap<>();
    private HashMap<String, LinkedList<Post>> postsWithSameUser = new HashMap<>();
    private HashMap<String, LinkedList<Post>> postsWithSameText = new HashMap<>();
    private int numRepeatsAllowed;

    public Spam(int numRepeatsAllowed) {
        this.numRepeatsAllowed = numRepeatsAllowed;
    }

    public void filterPosts() {
        LinkedList<Post> posts = PostFeed.getPostFeed();
        posts.forEach(post -> tooMuch(post));
        System.out.println(riskyUsers);
        System.out.println(postsWithSameUser);
        System.out.println(postsWithSameText);
    }

    private void tooMuch(Post post) {
        String postUser = post.getUserName();
        String postText = post.getText();
        goThroughPosts(postUser, postsWithSameUser.get(postText), post, true);
        goThroughPosts(postText, postsWithSameText.get(postText), post, false);
    }

    private void goThroughPosts(String postInfo, LinkedList<Post> posts, Post post, boolean userPassThrough) {
        if (posts == null) {
            posts = new LinkedList<>();
            ((userPassThrough) ? postsWithSameUser : postsWithSameText).put(postInfo, posts);
        }
        posts.add(post);

        if (posts.size() > numRepeatsAllowed) {
            if (userPassThrough){
                for (Post spamPost : posts) {
                    //PostFeed.removePost(spamPost);
                    riskyUsers.put(postInfo, riskyUsers.get(postInfo) + 1);
                }
            }
            else {
                for (Post spamPost : posts) {
                    //PostFeed.removePost(spamPost);
                    Integer numSpamPosts = riskyUsers.get(spamPost.getUserName());
                    riskyUsers.put(spamPost.getUserName(), numSpamPosts == null ? 1 : numSpamPosts + 1);
                }
            }
            
        }
    }
    
}
