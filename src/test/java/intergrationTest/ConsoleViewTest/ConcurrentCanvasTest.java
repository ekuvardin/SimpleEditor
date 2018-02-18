package intergrationTest.ConsoleViewTest;

import canvas.Canvas;
import canvas.ConcurrentCanvas;
import comands.CreateRectangleCommand;
import comands.FillConcurrentAreaCommand;
import comands.ICommand;
import org.junit.Test;

import java.io.IOException;

public class ConcurrentCanvasTest extends CommonCanvasTests {

    @Override
    public Canvas createCanvas(int x, int y) {
        return new ConcurrentCanvas(x, y);
    }


    @Test(timeout = 10000)
    public void consoleShouldFillCanvasInConcurrentInOneThread() throws IOException {
        canvas = createCanvas(4, 4);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillConcurrentAreaCommand(4, 1, 'y', 1);
        createLineCommand.execute(view);

        String expected =
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
