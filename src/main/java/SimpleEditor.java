import canvas.Viewer.ConsoleViewer;
import canvas.Viewer.IView;
import comands.*;
import parser.IConsoleParser;
import parser.SimpleOptionParser;

import java.util.Scanner;

public class SimpleEditor {

    public static void main(String[] args) {
        IConsoleParser parser = new SimpleOptionParser();
        ConsoleViewer view = new ConsoleViewer();

        try(Scanner scanner = new Scanner(System.in)){
            ICommand command = null;
            do{
                command = parser.parseCommand(( scanner.nextLine()).split("\\s+"));
                command.execute(view);
            }while(!(command instanceof QuitCommand));
        }
    }
}
