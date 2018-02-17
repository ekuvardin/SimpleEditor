package comands;

import canvas.Viewer.IView;

public class QuitCommand implements ICommand {

    public void execute(IView view) {
        view.printMessage("The program will be terminated");
    }
}
