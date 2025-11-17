import java.util.Stack;

/**
 * CommandInvoker - Invoker class that executes commands and maintains history
 * Provides undo functionality by keeping track of executed commands
 */
public class CommandInvoker {
    private Stack<Command> commandHistory;
    
    public CommandInvoker() {
        this.commandHistory = new Stack<>();
    }
    
    /**
     * Executes a command and adds it to the history stack
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
    }
    
    /**
     * Undoes the last executed command
     */
    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command command = commandHistory.pop();
            command.undo();
        } else {
            System.out.println("Nothing to undo.");
        }
    }
    
    /**
     * Checks if there are commands to undo
     */
    public boolean canUndo() {
        return !commandHistory.isEmpty();
    }
    
    /**
     * Clears the command history
     */
    public void clearHistory() {
        commandHistory.clear();
    }
    
    /**
     * Gets the size of the command history
     */
    public int getHistorySize() {
        return commandHistory.size();
    }
}

