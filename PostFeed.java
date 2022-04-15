import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

public class PostFeed {
    static private HashMap<Post, Integer> allPosts;


    static public void addPost(Post p) {
        allPosts.put(p, p.getText().hashCode());
    }

    static public HashMap<Post, Integer> getPostFeed() {
        return allPosts;
    }
}
