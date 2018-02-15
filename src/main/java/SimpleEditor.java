import comands.OptionParserImpl;
import comands.ICommand;
import comands.QuitCommand;

import java.util.Scanner;

public class SimpleEditor {

    public static void main(String[] args) {
        OptionParserImpl parser = new OptionParserImpl();

        try(Scanner scanner = new Scanner(System.in)){
            ICommand command = null;
            do{
                command = parser.parseCommand(( scanner.nextLine()).split("\\s+"));
                command.execute();
            }while(!(command instanceof QuitCommand));
        }
    }
}
