import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
* Holds the feed of post objects acted on by all filters
* @author Kyle Reed
* @version 1.0
*/
public class PostFeed {
    static private LinkedList<Post> allPosts = new LinkedList<>();

    /**
    * adds post to allPosts
    * @param Post post
    */
    static public void addPost(Post post) {
        allPosts.add(post);
    }

    /**
    * adds post to allPosts using the index and the list of posts
    * @param int index, List<Post> posts
    */
    static public void addPosts(int index, Collection<Post> posts) {
        allPosts.addAll(index, posts);
    }

    /**
    * returns allPosts
    * @return List<Post>
    */
    static public List<Post> getPostFeed() {
        return Collections.unmodifiableList(allPosts);
    }

    /**
    * removes a post from allPosts
    * @param Post post
    */
    static public void removePost(Post post) {
        allPosts.remove(post);
    }

    /**
    * removes a post from allPosts using the index and the list of posts
    * @param int index, List<Post> posts
    */
    static public void removePosts(Collection<Post> posts) {
        allPosts.removeAll(posts);
    }
}