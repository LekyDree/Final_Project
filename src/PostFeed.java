import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PostFeed {
    static private LinkedList<Post> allPosts = new LinkedList<>();


    static public void addPost(Post post) {
        allPosts.add(post);
    }

    static public void addPosts(int index, List<Post> posts) {
        allPosts.addAll(index, posts);
    }

    static public List<Post> getPostFeed() {
        return Collections.unmodifiableList(allPosts);
    }

    static public void removePost(Post post) {
        allPosts.remove(post);
    }

    static public void removePosts(Collection<Post> posts) {
        allPosts.removeAll(posts);
    }
}