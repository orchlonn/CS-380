public class Main {
    public static void main(String[] args) {
        Document doc = new Document();
        CommandInvoker invoker = new CommandInvoker();

        doc.addText("Hello");
        System.out.println("Initial: " + doc.getText());

        invoker.execute(new CutCommand(doc));
        System.out.println("After Cut: " + doc.getText());

        invoker.undo();
        System.out.println("After Undo Cut: " + doc.getText());

        invoker.execute(new CopyCommand(doc));
        invoker.execute(new PasteCommand(doc));
        System.out.println("After Paste: " + doc.getText());

        invoker.undo();
        System.out.println("After Undo Paste: " + doc.getText());
    }
}
