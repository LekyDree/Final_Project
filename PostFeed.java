import java.util.LinkedList;

public class PostFeed {
    static private LinkedList<Post> allPosts;


    static public void addPost(Post p) {
        allPosts.add(p);
    }

    static public void addPost(Post p, int index) {
        allPosts.add(index, p);
    }
}
