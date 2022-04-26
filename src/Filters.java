import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* Holds collection of filters, which can be acted upon
* @author Kyle Reed
* @version 1.0
*/
public class Filters implements Serializable {
    
    private static final long serialVersionUID = 890247967106502984L;
    
    private static LinkedList<Filter> filters = new LinkedList<>();
    private LinkedList<Filter> filtersSave = new LinkedList<>();

    public Filters() {
        filtersSave = filters;
    }

    /**
    * adds a filter to filters
    * @param Filter filter
    */
    public static void addFilter(Filter filter) {
        filters.add(filter);
    }

    /**
    * goes through and apply the filters to the posts
    */
    public static void applyFilters() {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(filters.size());

        filters.forEach((filter) -> {
            if (filter instanceof FeedChanger) {
                Runnable filtering = () -> {
                    ((FeedChanger)filter).determineFeedAlteration();
                    latch.countDown();
                };
                executor.execute(filtering);
            }
            else {
                latch.countDown();
            }
        });
        try {
            latch.await();
            filters.forEach((filter) -> filter.filterPosts());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }

    /**
    * changes filter to the parameter filter
    * @param Filters filters
    */
    public static void changeFilter(Filters filters) {
        filters.getFiltersSave().forEach((filter) -> Filters.addFilter(filter));
    }


    /**
    * @return filterSave, the current filters
    */
    public LinkedList<Filter> getFiltersSave() {
        return filtersSave;
    }
}
