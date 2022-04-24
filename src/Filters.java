import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Filters implements Serializable {
    
    private static final long serialVersionUID = 890247967106502984L;
    
    private static LinkedList<Filter> filtersFC = new LinkedList<>();
    private static LinkedList<Filter> filtersNotFC = new LinkedList<>();

    private LinkedList<Filter> filtersFCSave = new LinkedList<>();
    private LinkedList<Filter> filtersNotFCSave = new LinkedList<>();

    public Filters() {
        filtersFCSave = filtersFC;
        filtersNotFCSave = filtersNotFC;
    }

    public static void addFilter(Filter filter) {
        if (filter instanceof FeedChanger) {
            filtersFC.add(filter);
        }
        else {
            filtersNotFC.add(filter);
        }
    }

    public static void applyFilters() {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(filtersFC.size());

        filtersFC.forEach((filter) -> {
            Runnable filtering = () -> {
                filter.filterPosts();
                latch.countDown();
            };
            executor.execute(filtering);
        });
        try {
            latch.await();
            filtersNotFC.forEach((filter) -> filter.filterPosts());
            filtersFC.forEach((filter) -> ((FeedChanger)filter).changeFeed());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }

    public static void changeFilter(Filters filters) {
        filters.getFiltersFCSave().forEach((filter) -> filtersFC.add(filter));
        filters.getFiltersNotFCSave().forEach((filter) -> filtersNotFC.add(filter));
    }

    public LinkedList<Filter> getFiltersFCSave() {
        return filtersFCSave;
    }

    public LinkedList<Filter> getFiltersNotFCSave() {
        return filtersNotFCSave;
    }
}
