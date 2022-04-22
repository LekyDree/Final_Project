import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Spam extends Filter{
    
    private HashMap<String, Integer> riskyUsers = new HashMap<>();
    private HashMap<String, LinkedList<Post>> postsWithSameUser = new HashMap<>();
    private HashMap<String, LinkedList<Post>> postsWithSameText = new HashMap<>();

    LinkedList<Post> postsToRemove;

    private int numRepeatsAllowed;

    public Spam(int numRepeatsAllowed) {
        this.numRepeatsAllowed = numRepeatsAllowed;
    }

    public void filterPosts() {
        postsToRemove = new LinkedList<>();
        LinkedList<Post> posts = PostFeed.getPostFeed();
        posts.forEach(post -> tooMuch(post));
        PostFeed.removePosts(postsToRemove);

        System.out.println(postsWithSameText);
        System.out.println(postsWithSameUser);
        System.out.println(riskyUsers);
    }

    private void tooMuch(Post post) {
        String postUser = post.getUserName();
        String postText = post.getText();
        goThroughPosts(postUser, postsWithSameUser.get(postUser), post, true);
        goThroughPosts(postText, postsWithSameText.get(postText), post, false);
    }

    private void goThroughPosts(String postInfo, LinkedList<Post> posts, Post post, boolean userPassThrough) {
        if (posts == null) {
            posts = new LinkedList<>();
            ((userPassThrough) ? postsWithSameUser : postsWithSameText).put(postInfo, posts);
            System.out.println(postInfo);
        }
        posts.add(post);

        if (posts.size() > numRepeatsAllowed) {
            Iterator it = posts.iterator();
            if (userPassThrough){
                while (it.hasNext())
                    //postsToRemove.add(spamPost);
                    riskyUsers.put(postInfo, riskyUsers.get(postInfo) + 1);
                }
            }
            else {
                for (Post spamPost : posts) {
                    postsToRemove.add(spamPost);
                    Integer numSpamPosts = riskyUsers.get(spamPost.getUserName());
                    riskyUsers.put(spamPost.getUserName(), numSpamPosts == null ? 1 : numSpamPosts + 1);
                }
            }
            
        }
    }

