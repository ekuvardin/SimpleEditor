package comands;

public class QuitCommand implements ICommand {

    public void execute() {
        System.out.println("The program will be terminated");
    }
}
