/**
 * MenuItem - Represents a menu item in the UI
 * Demonstrates how UI elements can use Command objects
 */
public class MenuItem {
    private String label;
    private Command command;
    private CommandInvoker invoker;
    
    public MenuItem(String label, Command command, CommandInvoker invoker) {
        this.label = label;
        this.command = command;
        this.invoker = invoker;
    }
    
    /**
     * Simulates clicking the menu item
     */
    public void click() {
        System.out.println("Menu: " + label + " clicked");
        invoker.executeCommand(command);
    }
    
    public String getLabel() {
        return label;
    }
}

