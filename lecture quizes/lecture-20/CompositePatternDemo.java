/**
 * Composite Design Pattern - Complete Example
 * 
 * The Composite pattern lets you compose objects into tree structures
 * to represent part-whole hierarchies. It lets clients treat individual
 * objects and compositions of objects uniformly.
 * 
 * This example models a file system with files and folders.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Component interface - declares operations common to both
 * simple (leaf) and complex (composite) objects.
 */
interface FileSystemComponent {
    String getName();
    int getSize();
    void display(String indent);
}

/**
 * Leaf class - represents a file (no children).
 * A file is a terminal node in the tree structure.
 */
class File implements FileSystemComponent {
    private final String name;
    private final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "[FILE] " + name + " (" + size + " KB)");
    }
}

/**
 * Another Leaf class - represents a shortcut/link.
 * Demonstrates that you can have multiple types of leaf nodes.
 */
class Shortcut implements FileSystemComponent {
    private final String name;
    private final String target;

    public Shortcut(String name, String target) {
        this.name = name;
        this.target = target;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return 1; // Shortcuts are typically very small
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "[LINK] " + name + " -> " + target);
    }
}

/**
 * Composite class - represents a folder (can contain files and other folders).
 * Delegates operations to its children and computes aggregate results.
 */
class Folder implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Composite behavior - size is the sum of all children's sizes.
     */
    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : children) {
            totalSize += component.getSize();
        }
        return totalSize;
    }

    /**
     * Composite-specific methods for managing children.
     */
    public void add(FileSystemComponent component) {
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    public List<FileSystemComponent> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Composite behavior - delegates to all children.
     */
    @Override
    public void display(String indent) {
        System.out.println(indent + "[FOLDER] " + name + "/ (Total: " + getSize() + " KB)");
        for (FileSystemComponent component : children) {
            component.display(indent + "  ");
        }
    }
}

/**
 * Client code - demonstrates the Composite pattern.
 */
public class CompositePatternDemo {
    public static void main(String[] args) {
        System.out.println("=== Composite Design Pattern Demo ===");
        System.out.println("Modeling a File System\n");

        // Create leaf objects (files and shortcuts)
        File file1 = new File("Document.txt", 50);
        File file2 = new File("Photo.jpg", 2048);
        File file3 = new File("Music.mp3", 5120);
        File file4 = new File("Video.mp4", 10240);
        File file5 = new File("README.md", 10);
        Shortcut shortcut1 = new Shortcut("MyDoc", "Document.txt");

        // Create composite objects (folders)
        Folder root = new Folder("root");
        Folder documents = new Folder("Documents");
        Folder media = new Folder("Media");
        Folder pictures = new Folder("Pictures");
        Folder music = new Folder("Music");

        // Build the tree structure
        // Documents folder contains files
        documents.add(file1);
        documents.add(file5);
        documents.add(shortcut1);

        // Pictures folder contains a file
        pictures.add(file2);

        // Music folder contains a file
        music.add(file3);

        // Media folder contains Pictures and Music folders, plus a video file
        media.add(pictures);
        media.add(music);
        media.add(file4);

        // Root folder contains Documents and Media folders
        root.add(documents);
        root.add(media);

        // Display the entire tree structure
        System.out.println("Initial File System:");
        root.display("");

        // Demonstrate uniform treatment - both leaves and composites
        // respond to the same interface
        System.out.println("\n--- Uniform Treatment Demo ---");
        FileSystemComponent[] components = {file1, documents, media, root};
        for (FileSystemComponent component : components) {
            System.out.println("- " + component.getName() + ": " + 
                             component.getSize() + " KB");
        }

        // Demonstrate dynamic modification
        System.out.println("\n--- Dynamic Modification Demo ---");
        System.out.println("Adding a new file to Documents folder...");
        File newFile = new File("NewReport.pdf", 150);
        documents.add(newFile);
        
        System.out.println("\nModified File System:");
        root.display("");

        System.out.println("\nRemoving Music folder from Media...");
        media.remove(music);
        
        System.out.println("\nFinal File System:");
        root.display("");

        // Demonstrate composite aggregation
        System.out.println("\n--- Aggregation Demo ---");
        System.out.println("Total size of root folder: " + root.getSize() + " KB");
        System.out.println("Total size of media folder: " + media.getSize() + " KB");
        System.out.println("Total size of documents folder: " + documents.getSize() + " KB");
    }
}

