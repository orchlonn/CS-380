import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book in the library system.
 * Contains information specific to books such as authors, ISBN, and publication year.
 * 
 * @author Orchlon Chinbat
 */
public class Book extends LibraryItem {
    private List<String> authors;
    private int publicationYear;
    private String isbn;
    
    /**
     * Constructs a Book with the specified details.
     * 
     * @param title the title of the book
     * @param callNumber the call number for locating the book
     * @param authors the list of authors
     * @param publicationYear the year the book was published
     * @param isbn the ISBN number
     */
    public Book(String title, String callNumber, List<String> authors, 
                int publicationYear, String isbn) {
        super(title, callNumber);
        this.authors = new ArrayList<>(authors);
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }
    
    /**
     * Gets the list of authors.
     * 
     * @return a list of author names
     */
    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }
    
    /**
     * Gets the publication year.
     * 
     * @return the publication year
     */
    public int getPublicationYear() {
        return publicationYear;
    }
    
    /**
     * Gets the ISBN number.
     * 
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Gets the authors as a comma-separated string.
     * 
     * @return formatted author names
     */
    private String getAuthorsString() {
        return String.join(", ", authors);
    }
    
    @Override
    public String getDetails() {
        return String.format("Book: '%s' by %s (%d) | ISBN: %s | Call#: %s",
                           getTitle(), getAuthorsString(), publicationYear, 
                           isbn, getCallNumber());
    }
    
    @Override
    public String getSearchableText() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle().toLowerCase()).append(" ");
        for (String author : authors) {
            sb.append(author.toLowerCase()).append(" ");
        }
        sb.append(isbn.toLowerCase()).append(" ");
        sb.append(getCallNumber().toLowerCase());
        return sb.toString();
    }
}

