import java.util.List;

/**
 * Superclass for all filter objects
 * @author       Alex Tapiero
 * @version      1
 */
    abstract public class Filter {

    protected List<Post> postFeed = PostFeed.getPostFeed();
    /**
     * Use the filter.
     */
    abstract public void filterPosts();

}
