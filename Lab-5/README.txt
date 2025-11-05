================================================================================
                    LIBRARY MANAGEMENT SYSTEM - LAB 5
                          CS-380 Assignment
================================================================================

DESCRIPTION:
This project implements a library management system that allows library staff
to manage books, DVDs, and potentially other items in the future. The system
supports adding/removing items, checking items in and out, searching for items,
and tracking patron information.

================================================================================
FILES INCLUDED:
================================================================================

1. LibraryItem.java         - Abstract base class for all library items
2. Book.java                - Represents a book with authors, ISBN, etc.
3. DVD.java                 - Represents a DVD with title, year, call number
4. Patron.java              - Represents a library patron (user)
5. LibrarySystem.java       - Main system managing inventory and operations
6. LibraryDriver.java       - Driver class with comprehensive test scenarios
7. UML_ClassDiagram.md      - UML class diagram documentation
8. README.txt               - This file

================================================================================
DESIGN FEATURES:
================================================================================

1. ABSTRACTION & EXTENSIBILITY:
   - Abstract LibraryItem class allows for easy addition of new item types
   - New items (skateboards, scooters, etc.) can be added without modifying
     existing code - just extend LibraryItem
   
2. EFFICIENT DATA STRUCTURES:
   - HashMap for O(1) item lookup by call number
   - HashMap for O(1) patron lookup by student ID
   - ArrayList for maintaining ordered lists of checked out items

3. OBJECT-ORIENTED DESIGN:
   - Inheritance: Book and DVD extend LibraryItem
   - Encapsulation: All fields are private with public accessors
   - Polymorphism: LibrarySystem handles all LibraryItem types uniformly

4. CODE QUALITY:
   - Comprehensive Javadoc comments on all classes and methods
   - Proper Java naming conventions
   - Clean, formatted code with no dead code
   - Immutable fields marked as final

================================================================================
HOW TO COMPILE:
================================================================================

From the Lab-5 directory:
    javac *.java

================================================================================
HOW TO RUN:
================================================================================

    java LibraryDriver

The driver will execute a comprehensive test suite demonstrating:
  - Patron registration
  - Adding books and DVDs to inventory
  - Searching for items by title or author
  - Checking out and checking in items
  - Preventing checkout of already checked-out items
  - Removing items from inventory
  - Display of inventory and patron information

================================================================================
SAMPLE OUTPUT:
================================================================================

The test driver produces detailed output showing:
  - Each operation being performed
  - Success/failure messages
  - Current state of inventory
  - Patron checkout information
  - Search results

All test scenarios are hardcoded and require no user input.

================================================================================
SYSTEM CAPABILITIES:
================================================================================

1. Add new books with:
   - Title, author(s), publication year, ISBN, call number

2. Add new DVDs with:
   - Title, year, call number

3. Register patrons with:
   - Name, student ID

4. Search for items by:
   - Title (partial match)
   - Author name (partial match)

5. Check out items:
   - Associate item with patron
   - Track checkout status

6. Check in items:
   - Return items to available status
   - Remove from patron's checkout list

7. Remove items:
   - Only if not currently checked out
   - Permanently delete from inventory

8. Display information:
   - Complete inventory with status
   - Patron information with their checked out items

================================================================================
FUTURE EXTENSIBILITY EXAMPLE:
================================================================================

To add a new item type (e.g., Skateboard):

    public class Skateboard extends LibraryItem {
        private String brand;
        private String size;
        
        public Skateboard(String title, String callNumber, 
                         String brand, String size) {
            super(title, callNumber);
            this.brand = brand;
            this.size = size;
        }
        
        @Override
        public String getDetails() {
            return String.format("Skateboard: '%s' | Brand: %s | Size: %s | Call#: %s",
                               getTitle(), brand, size, getCallNumber());
        }
        
        @Override
        public String getSearchableText() {
            return (getTitle() + " " + brand + " " + getCallNumber()).toLowerCase();
        }
    }

No changes needed to LibrarySystem, Patron, or any other classes!

================================================================================
AUTHOR: CS-380 Student
VERSION: 1.0
DATE: November 2025
================================================================================

