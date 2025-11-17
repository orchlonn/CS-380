/**
 * ToolbarButton - Represents a toolbar button in the UI
 * Demonstrates how UI elements can use Command objects
 */
public class ToolbarButton {
    private String icon;
    private Command command;
    private CommandInvoker invoker;
    
    public ToolbarButton(String icon, Command command, CommandInvoker invoker) {
        this.icon = icon;
        this.command = command;
        this.invoker = invoker;
    }
    
    /**
     * Simulates clicking the toolbar button
     */
    public void click() {
        System.out.println("Toolbar: [" + icon + "] button clicked");
        invoker.executeCommand(command);
    }
    
    public String getIcon() {
        return icon;
    }
}

