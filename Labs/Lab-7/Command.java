/**
 * Command interface - defines the contract for all command objects
 * Provides execute() and undo() methods
 */
public interface Command {
    /**
     * Executes the command
     */
    void execute();
    
    /**
     * Undoes the command
     */
    void undo();
}

