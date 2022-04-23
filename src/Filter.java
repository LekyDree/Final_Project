/**
 * Author       Alex Tapiero
 * Version      1
 * Group        1
 */
    abstract public class Filter {
    
    static boolean spamActive = GUI.spamButton.isSelected();
    static boolean sortActive = GUI.sortButton.isSelected();
    static boolean maskActive = GUI.maskButton.isSelected();

    /**
     * Use the filter.
     */
    abstract public void filterPosts();

    
}
