import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Spam extends Filter{
    
    private HashMap<String, Integer> riskyUsers;
    private HashMap<String, Post> posts;
    private HashMap<String, Integer> riskyPosts;

    public void filterPosts() {
        filterPosts(1);
    }

    public void filterPosts(int numRepeatsAllowed) {

        BiConsumer<Post, Integer> determineRiskLevel = (post, inte) -> {
            String postText = post.getText();
            postText.
            Integer risk = riskyPosts.get(postText);
            if (risk != null) {
                tooMuch(numRepeatsAllowed, risk, postText);
            }
            else {
                int postsSize = posts.size();
                posts.put(postText, post);
                if (posts.size() == postsSize) {
                    tooMuch(numRepeatsAllowed, 0, postText); 
                }
            }
        //};

        HashMap<Post, Integer> posts = PostFeed.getPostFeed();
        posts.forEach(determineRiskLevel);
        //Loop through all posts
        //If a user is a banned user, their post doesn't show
        //Check their username to see if their spam
        //Check        
    }

    public void tooMuch(int numRepeatsAllowed, int risk, String postText) {
        riskyPosts.put(postText, risk + 1);
        if (risk + 1 > numRepeatsAllowed) {
            posts.remove(postText);
        }
    }
}
