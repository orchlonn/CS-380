import java.util.Arrays;
import java.util.List;

/**
 * Driver class to test the library management system.
 * Demonstrates adding items, registering patrons, checking out/in items,
 * searching, and removing items.
 * 
 * @author Orchlon Chinbat
 */
public class LibraryDriver {
    
    /**
     * Main method to run test scenarios.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        
        System.out.println("========================================");
        System.out.println("   Library Management System - Test");
        System.out.println("========================================\n");
        
        // Test 1: Register Patrons
        System.out.println("--- TEST 1: Registering Patrons ---");
        Patron patron1 = new Patron("Alice Johnson", "S12345");
        Patron patron2 = new Patron("Bob Smith", "S67890");
        Patron patron3 = new Patron("Carol Davis", "S54321");
        
        library.registerPatron(patron1);
        library.registerPatron(patron2);
        library.registerPatron(patron3);
        System.out.println();
        
        // Test 2: Add Books to Inventory
        System.out.println("--- TEST 2: Adding Books ---");
        Book book1 = new Book(
            "Introduction to Algorithms",
            "QA76.9.A43 C58 2009",
            Arrays.asList("Thomas H. Cormen", "Charles E. Leiserson", 
                         "Ronald L. Rivest", "Clifford Stein"),
            2009,
            "978-0262033848"
        );
        
        Book book2 = new Book(
            "Clean Code",
            "QA76.76.D47 M37 2008",
            Arrays.asList("Robert C. Martin"),
            2008,
            "978-0132350884"
        );
        
        Book book3 = new Book(
            "Design Patterns",
            "QA76.64 .G36 1995",
            Arrays.asList("Erich Gamma", "Richard Helm", 
                         "Ralph Johnson", "John Vlissides"),
            1995,
            "978-0201633610"
        );
        
        Book book4 = new Book(
            "The Pragmatic Programmer",
            "QA76.6 .H868 2020",
            Arrays.asList("David Thomas", "Andrew Hunt"),
            2020,
            "978-0135957059"
        );
        
        library.addItem(book1);
        library.addItem(book2);
        library.addItem(book3);
        library.addItem(book4);
        System.out.println();
        
        // Test 3: Add DVDs to Inventory
        System.out.println("--- TEST 3: Adding DVDs ---");
        DVD dvd1 = new DVD("The Social Network", "DVD-791.43 S678 2010", 2010);
        DVD dvd2 = new DVD("Inception", "DVD-791.43 I536 2010", 2010);
        DVD dvd3 = new DVD("The Matrix", "DVD-791.43 M425 1999", 1999);
        
        library.addItem(dvd1);
        library.addItem(dvd2);
        library.addItem(dvd3);
        System.out.println();
        
        // Test 4: Display Inventory
        System.out.println("--- TEST 4: Display Full Inventory ---");
        library.displayInventory();
        System.out.println();
        
        // Test 5: Search Functionality
        System.out.println("--- TEST 5: Search by Title ---");
        System.out.println("Searching for 'code':");
        List<LibraryItem> searchResults = library.searchItems("code");
        for (LibraryItem item : searchResults) {
            System.out.println("  Found: " + item.getDetails());
        }
        System.out.println();
        
        System.out.println("Searching for 'Martin' (author):");
        searchResults = library.searchItems("Martin");
        for (LibraryItem item : searchResults) {
            System.out.println("  Found: " + item.getDetails());
        }
        System.out.println();
        
        System.out.println("Searching for 'Matrix' (DVD):");
        searchResults = library.searchItems("Matrix");
        for (LibraryItem item : searchResults) {
            System.out.println("  Found: " + item.getDetails());
        }
        System.out.println();
        
        // Test 6: Check Out Items
        System.out.println("--- TEST 6: Checking Out Items ---");
        library.checkOutItem("QA76.9.A43 C58 2009", "S12345"); // Alice checks out "Introduction to Algorithms"
        library.checkOutItem("DVD-791.43 I536 2010", "S12345"); // Alice checks out "Inception"
        library.checkOutItem("QA76.76.D47 M37 2008", "S67890"); // Bob checks out "Clean Code"
        System.out.println();
        
        // Test 7: Try to Check Out Already Checked Out Item
        System.out.println("--- TEST 7: Attempting to Check Out Already Checked Out Item ---");
        library.checkOutItem("QA76.9.A43 C58 2009", "S54321"); // Carol tries to check out Alice's book
        System.out.println();
        
        // Test 8: Display Updated Inventory
        System.out.println("--- TEST 8: Display Updated Inventory (after checkouts) ---");
        library.displayInventory();
        System.out.println();
        
        // Test 9: Display Patron Information
        System.out.println("--- TEST 9: Display Patron Information ---");
        library.displayPatrons();
        System.out.println();
        
        // Test 10: Check In Items
        System.out.println("--- TEST 10: Checking In Items ---");
        library.checkInItem("QA76.9.A43 C58 2009"); // Return "Introduction to Algorithms"
        System.out.println();
        
        // Test 11: Check Out Previously Returned Item
        System.out.println("--- TEST 11: Check Out Previously Returned Item ---");
        library.checkOutItem("QA76.9.A43 C58 2009", "S54321"); // Carol can now check it out
        System.out.println();
        
        // Test 12: Try to Remove Checked Out Item
        System.out.println("--- TEST 12: Attempting to Remove Checked Out Item ---");
        library.removeItem("QA76.9.A43 C58 2009"); // Try to remove Carol's book
        System.out.println();
        
        // Test 13: Remove Available Item
        System.out.println("--- TEST 13: Removing Available Item ---");
        library.removeItem("QA76.6 .H868 2020"); // Remove "The Pragmatic Programmer"
        System.out.println();
        
        // Test 14: Final Inventory Display
        System.out.println("--- TEST 14: Final Inventory Display ---");
        library.displayInventory();
        System.out.println();
        
        // Test 15: Final Patron Display
        System.out.println("--- TEST 15: Final Patron Display ---");
        library.displayPatrons();
        System.out.println();
        
        System.out.println("========================================");
        System.out.println("   All Tests Completed Successfully!");
        System.out.println("========================================");
    }
}

