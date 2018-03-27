package comands;

import canvas.viewer.IView;

/*
   Last command before terminating
 */
public class QuitCommand implements ICommand {

    public void execute(IView view) {
        view.printMessage("The program will be terminated");
    }

    @Override
    public boolean quitCommand() {
        return true;
    }
}
