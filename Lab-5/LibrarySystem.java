import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main library management system that handles inventory and patron operations.
 * Uses efficient data structures (HashMap) for fast lookup of items and patrons.
 * 
 * @author CS-380 Student
 * @version 1.0
 */
public class LibrarySystem {
    private final Map<String, LibraryItem> inventory;
    private final Map<String, Patron> patrons;
    
    /**
     * Constructs a new LibrarySystem with empty inventory and patron list.
     */
    public LibrarySystem() {
        this.inventory = new HashMap<>();
        this.patrons = new HashMap<>();
    }
    
    /**
     * Adds a new library item to the inventory.
     * 
     * @param item the item to add
     * @return true if successfully added, false if call number already exists
     */
    public boolean addItem(LibraryItem item) {
        if (inventory.containsKey(item.getCallNumber())) {
            System.out.println("Error: Item with call number " + 
                             item.getCallNumber() + " already exists.");
            return false;
        }
        inventory.put(item.getCallNumber(), item);
        System.out.println("Successfully added: " + item.getDetails());
        return true;
    }
    
    /**
     * Removes a library item from the inventory.
     * 
     * @param callNumber the call number of the item to remove
     * @return true if successfully removed, false if not found or checked out
     */
    public boolean removeItem(String callNumber) {
        LibraryItem item = inventory.get(callNumber);
        if (item == null) {
            System.out.println("Error: Item not found with call number: " + callNumber);
            return false;
        }
        if (item.isCheckedOut()) {
            System.out.println("Error: Cannot remove item that is currently checked out.");
            return false;
        }
        inventory.remove(callNumber);
        System.out.println("Successfully removed: " + item.getDetails());
        return true;
    }
    
    /**
     * Registers a new patron in the system.
     * 
     * @param patron the patron to register
     * @return true if successfully registered, false if student ID already exists
     */
    public boolean registerPatron(Patron patron) {
        if (patrons.containsKey(patron.getStudentId())) {
            System.out.println("Error: Patron with ID " + 
                             patron.getStudentId() + " already exists.");
            return false;
        }
        patrons.put(patron.getStudentId(), patron);
        System.out.println("Successfully registered: " + patron);
        return true;
    }
    
    /**
     * Searches for library items by title or author name.
     * 
     * @param searchTerm the term to search for
     * @return a list of matching items
     */
    public List<LibraryItem> searchItems(String searchTerm) {
        List<LibraryItem> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        
        for (LibraryItem item : inventory.values()) {
            if (item.getSearchableText().contains(lowerSearchTerm)) {
                results.add(item);
            }
        }
        
        return results;
    }
    
    /**
     * Checks out an item to a patron.
     * 
     * @param callNumber the call number of the item to check out
     * @param studentId the student ID of the patron
     * @return true if successful, false otherwise
     */
    public boolean checkOutItem(String callNumber, String studentId) {
        LibraryItem item = inventory.get(callNumber);
        Patron patron = patrons.get(studentId);
        
        if (item == null) {
            System.out.println("Error: Item not found with call number: " + callNumber);
            return false;
        }
        
        if (patron == null) {
            System.out.println("Error: Patron not found with ID: " + studentId);
            return false;
        }
        
        if (item.checkOut(patron)) {
            patron.addCheckedOutItem(item);
            System.out.println("Successfully checked out:");
            System.out.println("  Item: " + item.getDetails());
            System.out.println("  To: " + patron.getName() + " (ID: " + studentId + ")");
            return true;
        } else {
            System.out.println("Error: Item is already checked out.");
            return false;
        }
    }
    
    /**
     * Checks in an item, making it available again.
     * 
     * @param callNumber the call number of the item to check in
     * @return true if successful, false otherwise
     */
    public boolean checkInItem(String callNumber) {
        LibraryItem item = inventory.get(callNumber);
        
        if (item == null) {
            System.out.println("Error: Item not found with call number: " + callNumber);
            return false;
        }
        
        if (!item.isCheckedOut()) {
            System.out.println("Error: Item is not currently checked out.");
            return false;
        }
        
        Patron patron = item.getCheckedOutBy();
        if (item.checkIn()) {
            patron.removeCheckedOutItem(item);
            System.out.println("Successfully checked in: " + item.getDetails());
            return true;
        }
        
        return false;
    }
    
    /**
     * Displays all items in the inventory.
     */
    public void displayInventory() {
        System.out.println("\n=== Library Inventory ===");
        if (inventory.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            for (LibraryItem item : inventory.values()) {
                System.out.println(item);
            }
        }
        System.out.println("Total items: " + inventory.size());
    }
    
    /**
     * Displays all registered patrons.
     */
    public void displayPatrons() {
        System.out.println("\n=== Registered Patrons ===");
        if (patrons.isEmpty()) {
            System.out.println("No registered patrons.");
        } else {
            for (Patron patron : patrons.values()) {
                System.out.println(patron);
                List<LibraryItem> items = patron.getCheckedOutItems();
                if (!items.isEmpty()) {
                    System.out.println("  Checked out items:");
                    for (LibraryItem item : items) {
                        System.out.println("    - " + item.getDetails());
                    }
                }
            }
        }
        System.out.println("Total patrons: " + patrons.size());
    }
    
    /**
     * Gets a patron by student ID.
     * 
     * @param studentId the student ID
     * @return the patron, or null if not found
     */
    public Patron getPatron(String studentId) {
        return patrons.get(studentId);
    }
    
    /**
     * Gets an item by call number.
     * 
     * @param callNumber the call number
     * @return the item, or null if not found
     */
    public LibraryItem getItem(String callNumber) {
        return inventory.get(callNumber);
    }
}

