import comands.*;
import parser.IConsoleParser;
import parser.SimpleOptionParser;

import java.util.Scanner;

public class SimpleEditor {

    public static void main(String[] args) {
        IConsoleParser parser = new SimpleOptionParser();

        try(Scanner scanner = new Scanner(System.in)){
            ICommand command = null;
            do{
                command = parser.parseCommand(( scanner.nextLine()).split("\\s+"));
                command.execute();
            }while(!(command instanceof QuitCommand));
        }
    }
}
