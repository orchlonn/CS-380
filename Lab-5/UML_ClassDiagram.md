# UML Class Diagram - Library Management System

## Overview

This document describes the UML class diagram for the Library Management System. The system is designed with extensibility in mind, using abstract classes and inheritance to allow for easy addition of new item types in the future.

## Class Diagram (Text Representation)

```
┌─────────────────────────────────────┐
│         <<abstract>>                │
│         LibraryItem                 │
├─────────────────────────────────────┤
│ - title: String                     │
│ - callNumber: String                │
│ - checkedOut: boolean               │
│ - checkedOutBy: Patron              │
├─────────────────────────────────────┤
│ + LibraryItem(String, String)       │
│ + getTitle(): String                │
│ + getCallNumber(): String           │
│ + isCheckedOut(): boolean           │
│ + getCheckedOutBy(): Patron         │
│ + checkOut(Patron): boolean         │
│ + checkIn(): boolean                │
│ + getDetails(): String <<abstract>> │
│ + getSearchableText(): String <<ab>>│
│ + toString(): String                │
└─────────────────────────────────────┘
           △
           │
     ┌─────┴──────┐
     │            │
┌────┴────┐  ┌────┴────┐
│  Book   │  │   DVD   │
├─────────┤  ├─────────┤
│- authors│  │- year:  │
│ :List   │  │  int    │
│- publica│  └─────────┘
│ tionYear│  │+ DVD()  │
│ :int    │  │+ getYear│
│- isbn:  │  │ ():int  │
│ String  │  │+ getDet │
├─────────┤  │ ails(): │
│+ Book() │  │ String  │
│+ getAuth│  │+ getSear│
│ ors():  │  │ chable  │
│ List    │  │ Text(): │
│+ getPub │  │ String  │
│ licatio │  └─────────┘
│ nYear():│
│ int     │
│+ getIsbn│
│ ():Stri │
│ ng      │
│+ getDeta│
│ ils():  │
│ String  │
│+ getSear│
│ chableT │
│ ext():  │
│ String  │
└─────────┘


┌─────────────────────────────────────┐
│            Patron                   │
├─────────────────────────────────────┤
│ - name: String                      │
│ - studentId: String                 │
│ - checkedOutItems: List<LibraryItem>│
├─────────────────────────────────────┤
│ + Patron(String, String)            │
│ + getName(): String                 │
│ + getStudentId(): String            │
│ + getCheckedOutItems(): List        │
│ + addCheckedOutItem(LibraryItem)    │
│ + removeCheckedOutItem(LibraryItem) │
│ + toString(): String                │
└─────────────────────────────────────┘
           △
           │ has many
           │
┌──────────┴──────────────────────────┐
│       LibrarySystem                 │
├─────────────────────────────────────┤
│- inventory: Map<String,LibraryItem> │
│- patrons: Map<String,Patron>        │
├─────────────────────────────────────┤
│+ LibrarySystem()                    │
│+ addItem(LibraryItem): boolean      │
│+ removeItem(String): boolean        │
│+ registerPatron(Patron): boolean    │
│+ searchItems(String): List          │
│+ checkOutItem(String, String): bool │
│+ checkInItem(String): boolean       │
│+ displayInventory(): void           │
│+ displayPatrons(): void             │
│+ getPatron(String): Patron          │
│+ getItem(String): LibraryItem       │
└─────────────────────────────────────┘
```

## Relationships

1. **Inheritance (Generalization)**

   - `Book` extends `LibraryItem` (IS-A relationship)
   - `DVD` extends `LibraryItem` (IS-A relationship)
   - Future items (e.g., Skateboard, Scooter) can also extend `LibraryItem`

2. **Association**

   - `LibraryItem` has a reference to `Patron` (checkedOutBy)
   - This is a 0..1 relationship (item can be checked out to zero or one patron)

3. **Composition**

   - `LibrarySystem` contains `Map<String, LibraryItem>` (inventory)
   - `LibrarySystem` contains `Map<String, Patron>` (patrons)
   - `Patron` contains `List<LibraryItem>` (checkedOutItems)

4. **Dependency**
   - `LibraryDriver` depends on all other classes for testing

## Design Patterns and Principles

### 1. Abstract Class Pattern

- `LibraryItem` is abstract, defining common behavior for all library items
- Forces subclasses to implement `getDetails()` and `getSearchableText()`

### 2. Open/Closed Principle

- The system is open for extension (new item types) but closed for modification
- Adding a new item type only requires creating a new class that extends `LibraryItem`
- No changes to `LibrarySystem` or other classes needed

### 3. Encapsulation

- All fields are private with public getters
- Internal state is protected from direct access

### 4. Efficient Data Structures

- `HashMap` used for O(1) lookup of items by call number
- `HashMap` used for O(1) lookup of patrons by student ID
- `List` used for maintaining order of checked out items

## Future Extensibility

To add new item types (e.g., Skateboard, Scooter):

```java
public class Skateboard extends LibraryItem {
    private String brand;
    private String size;

    // Constructor and methods...

    @Override
    public String getDetails() {
        // Implementation
    }

    @Override
    public String getSearchableText() {
        // Implementation
    }
}
```

No changes needed to:

- `LibrarySystem` (already handles `LibraryItem` polymorphically)
- `Patron` (already handles `LibraryItem` polymorphically)
- Core checkout/checkin logic

## Class Responsibilities

### LibraryItem (Abstract)

- Manages checkout/checkin state
- Tracks which patron has item
- Provides template for specific item types

### Book

- Stores book-specific data (authors, ISBN, publication year)
- Implements book-specific details and search

### DVD

- Stores DVD-specific data (year)
- Implements DVD-specific details and search

### Patron

- Represents a library user
- Tracks items checked out by patron

### LibrarySystem

- Central management system
- Handles all library operations
- Manages inventory and patron registry
- Coordinates checkout/checkin operations

### LibraryDriver

- Tests all system functionality
- Demonstrates usage scenarios
