package intergrationTest.ConsoleViewTest;

import canvas.Canvas;
import canvas.SimpleCanvas;
import comands.CreateRectangleCommand;
import comands.FillConcurrentAreaCommand;
import comands.ICommand;
import org.junit.Test;

import java.io.IOException;

public class SimpleCanvasTest extends CommonCanvasTests {

    @Override
    public Canvas createCanvas(int x, int y) {
        return new SimpleCanvas(x, y);
    }


    @Test
    public void consoleShouldFillCanvasInConcurrent() throws IOException {
        canvas = createCanvas(4, 4);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillConcurrentAreaCommand(4, 1, 'y', 4);
        createLineCommand.execute(view);

        String expected =
                "Warning. Created not parallel canvas. Will be used single thread implementation" + "\r\n" +
                        "------" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|x xy|" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|yyyy|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);

        canvas = createCanvas(4, 4);
    }
}
