package canvas.Viewer;

import canvas.Canvas;

import java.util.concurrent.Executor;

public interface IView {

    void draw(Canvas canvas);

    void showError(Exception e);

    void showError(String message);

    void printMessage(String message);
}
