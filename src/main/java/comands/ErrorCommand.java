package comands;

public class ErrorCommand implements ICommand {

    private String message;

    public ErrorCommand(Exception exception){
        this.message = exception.getMessage();
    }

    public ErrorCommand(String message){
        this.message = message;
    }

    @Override
    public void execute() {
        System.err.println("ERROR: " + message);
        System.err.println();
    }
}
