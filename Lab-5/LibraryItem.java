/**
 * Abstract base class representing a library item.
 * This class serves as a template for all types of items that can be
 * checked out from the library (books, DVDs, etc.).
 * 
 * @author CS-380 Student
 * @version 1.0
 */
public abstract class LibraryItem {
    private String title;
    private String callNumber;
    private boolean checkedOut;
    private Patron checkedOutBy;
    
    /**
     * Constructs a LibraryItem with the specified title and call number.
     * 
     * @param title the title of the library item
     * @param callNumber the call number used to locate the item
     */
    public LibraryItem(String title, String callNumber) {
        this.title = title;
        this.callNumber = callNumber;
        this.checkedOut = false;
        this.checkedOutBy = null;
    }
    
    /**
     * Gets the title of the library item.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the call number of the library item.
     * 
     * @return the call number
     */
    public String getCallNumber() {
        return callNumber;
    }
    
    /**
     * Checks if the item is currently checked out.
     * 
     * @return true if checked out, false otherwise
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }
    
    /**
     * Gets the patron who has checked out this item.
     * 
     * @return the patron who checked out the item, or null if available
     */
    public Patron getCheckedOutBy() {
        return checkedOutBy;
    }
    
    /**
     * Checks out this item to a patron.
     * 
     * @param patron the patron checking out the item
     * @return true if successful, false if already checked out
     */
    public boolean checkOut(Patron patron) {
        if (!checkedOut) {
            checkedOut = true;
            checkedOutBy = patron;
            return true;
        }
        return false;
    }
    
    /**
     * Checks in this item, making it available again.
     * 
     * @return true if successful, false if not checked out
     */
    public boolean checkIn() {
        if (checkedOut) {
            checkedOut = false;
            checkedOutBy = null;
            return true;
        }
        return false;
    }
    
    /**
     * Gets detailed information about the library item.
     * Subclasses must implement this method to provide specific details.
     * 
     * @return a string with detailed information about the item
     */
    public abstract String getDetails();
    
    /**
     * Gets searchable text for this item.
     * Subclasses should override this to include relevant searchable fields.
     * 
     * @return searchable text (lowercase)
     */
    public abstract String getSearchableText();
    
    @Override
    public String toString() {
        String status = checkedOut ? " [CHECKED OUT by " + checkedOutBy.getName() + "]" : " [AVAILABLE]";
        return getDetails() + status;
    }
}

