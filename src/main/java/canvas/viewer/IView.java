package canvas.viewer;

import canvas.Model;

/**
 * Describe what view to the user
 */
public interface IView {

    /**
     * draw existing model
     *
     * @param model model
     */
    void draw(Model model);

    /**
     * Display exception
     *
     * @param e exception
     */
    void showError(Exception e);

    /**
     * Display message error
     *
     * @param message message error
     */
    void showError(String message);

    /**
     * Print message
     *
     * @param message message to print
     */
    void printMessage(String message);
}
