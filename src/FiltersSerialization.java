import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* Saves and loads user filter preferences
* @author Kyle Reed
* @version 1.0
*/
public class FiltersSerialization {
    
    private static String FILE_NAME = "src/";

    /**
    * Loads user filter preferences
    * @param filterName, string of filter name
    */
    public static void deserializeFilters(String filterName) {
        try {
            FileInputStream fileInput = new FileInputStream(FILE_NAME + filterName + ".ser");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            Filters.changeFilter((Filters)objectInput.readObject());
            fileInput.close();
            objectInput.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * Saves user filter preferences
    * @param filterName, string of filter name
    */
    public static void serializeFilters(String filterName) {
        Filters filters = new Filters();

        try {
            FileOutputStream fileOutput = new FileOutputStream(FILE_NAME + filterName + ".ser");
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(filters);
            fileOutput.close();
            objectOutput.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
