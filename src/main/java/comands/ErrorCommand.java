package comands;

import canvas.viewer.IView;

/**
 * Contains error during parsing command
 */
public class ErrorCommand implements ICommand {

    private String message;

    public ErrorCommand(Exception exception) {
        this.message = exception.getMessage();
    }

    public ErrorCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute(IView view) {
        view.showError(message);
    }

    @Override
    public boolean quitCommand() {
        return false;
    }

    public String getMessage() {
        return message;
    }
}
