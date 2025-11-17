import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library patron who can check out items.
 * Each patron has a name and student ID number.
 * 
 * @author Orchlon Chinbat
 */
public class Patron {
    private final String name;
    private final String studentId;
    private final List<LibraryItem> checkedOutItems;
    
    /**
     * Constructs a Patron with the specified name and student ID.
     * 
     * @param name the patron's name
     * @param studentId the patron's student ID number
     */
    public Patron(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.checkedOutItems = new ArrayList<>();
    }
    
    /**
     * Gets the patron's name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the patron's student ID.
     * 
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }
    
    /**
     * Gets the list of items currently checked out by this patron.
     * 
     * @return a list of checked out items
     */
    public List<LibraryItem> getCheckedOutItems() {
        return new ArrayList<>(checkedOutItems);
    }
    
    /**
     * Adds an item to the patron's checked out list.
     * 
     * @param item the item to add
     */
    public void addCheckedOutItem(LibraryItem item) {
        if (!checkedOutItems.contains(item)) {
            checkedOutItems.add(item);
        }
    }
    
    /**
     * Removes an item from the patron's checked out list.
     * 
     * @param item the item to remove
     */
    public void removeCheckedOutItem(LibraryItem item) {
        checkedOutItems.remove(item);
    }
    
    @Override
    public String toString() {
        return String.format("Patron: %s (ID: %s) - %d items checked out",
                           name, studentId, checkedOutItems.size());
    }
}

