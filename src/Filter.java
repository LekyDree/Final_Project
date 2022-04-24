import java.util.List;

/**
 * Author       Alex Tapiero
 * Version      1
 * Group        1
 */
    abstract public class Filter {

    protected List<Post> postFeed = PostFeed.getPostFeed();
    /**
     * Use the filter.
     */
    abstract public void filterPosts();

}
