import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Filters {
    
    private static LinkedList<Filter> filtersFeedChanger = new LinkedList<>();
    private static LinkedList<Filter> filtersNotFeedChanger = new LinkedList<>();

    public static void addFilter(Filter filter) {
        if (filter instanceof FeedChanger) {
            filtersFeedChanger.add(filter);
        }
        else {
            filtersNotFeedChanger.add(filter);
        }
    }

    public static void applyFilters() {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(filtersFeedChanger.size());

        filtersFeedChanger.forEach((filter) -> {
            Runnable filtering = () -> {
                filter.filterPosts();
                latch.countDown();
            };
            executor.execute(filtering);
        });
        try {
            latch.await();
            filtersNotFeedChanger.forEach((filter) -> filter.filterPosts());
            filtersFeedChanger.forEach((filter) -> ((FeedChanger)filter).changeFeed());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        
    }
}
