package comands;

import canvas.Viewer.IView;

public interface ICommand {

    void execute(IView view);

    boolean quitCommand();
}
