/**
 * Represents a DVD in the library system.
 * Contains information specific to DVDs such as year and call number.
 * 
 * @author CS-380 Student
 * @version 1.0
 */
public class DVD extends LibraryItem {
    private final int year;
    
    /**
     * Constructs a DVD with the specified details.
     * 
     * @param title the title of the DVD
     * @param callNumber the call number for locating the DVD
     * @param year the year the DVD was released
     */
    public DVD(String title, String callNumber, int year) {
        super(title, callNumber);
        this.year = year;
    }
    
    /**
     * Gets the release year of the DVD.
     * 
     * @return the year
     */
    public int getYear() {
        return year;
    }
    
    @Override
    public String getDetails() {
        return String.format("DVD: '%s' (%d) | Call#: %s",
                           getTitle(), year, getCallNumber());
    }
    
    @Override
    public String getSearchableText() {
        return (getTitle() + " " + getCallNumber()).toLowerCase();
    }
}

