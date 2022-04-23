import java.util.LinkedList;

public class Filters {
    
    private static LinkedList<Filter> filters = new LinkedList<>();

    public static void addFilter(Filter filter) {
        filters.add(filter);
    }

    public static void applyFilters() {
        filters.forEach((filter) -> filter.filterPosts());
    }
}
