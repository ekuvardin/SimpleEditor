package comands;

import canvas.Viewer.IView;

/*
  Define command to interact with user
 */
public interface ICommand {

    /**
     * execute command
     *
     * @param view where to show results
     */
    void execute(IView view);

    /*
        is command the last before exit
     */
    boolean quitCommand();
}
