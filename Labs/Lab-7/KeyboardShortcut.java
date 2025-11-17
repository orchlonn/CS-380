/**
 * KeyboardShortcut - Represents a keyboard shortcut in the UI
 * Demonstrates how UI elements can use Command objects
 */
public class KeyboardShortcut {
    private String keyCombo;
    private Command command;
    private CommandInvoker invoker;
    
    public KeyboardShortcut(String keyCombo, Command command, CommandInvoker invoker) {
        this.keyCombo = keyCombo;
        this.command = command;
        this.invoker = invoker;
    }
    
    /**
     * Simulates pressing the keyboard shortcut
     */
    public void press() {
        System.out.println("Keyboard: " + keyCombo + " pressed");
        invoker.executeCommand(command);
    }
    
    public String getKeyCombo() {
        return keyCombo;
    }
}

