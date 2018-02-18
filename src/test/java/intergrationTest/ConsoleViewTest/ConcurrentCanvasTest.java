package intergrationTest.ConsoleViewTest;

import canvas.Canvas;
import canvas.ConcurrentCanvas;
import comands.CreateLineCommand;
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
    }

    @Test(timeout = 100000)
    public void consoleShouldFillCanvasInConcurrentInSeveralThreads() throws IOException {
        canvas = createCanvas(8, 8);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillConcurrentAreaCommand(4, 1, 'y', 4);
        createLineCommand.execute(view);

        String expected =
                "----------" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|x xyyyyy|" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "----------" + "\r\n";

        checkResult(command, expected);
    }

    @Test(timeout = 10000)
    public void consoleShouldFillCanvasInConcurrentInSeveralThreadsOneMoreExample() throws IOException {
        canvas = createCanvas(8, 8);
        CreateRectangleCommand createRectangleCommand = new CreateRectangleCommand(1, 1, 3, 3);
        CreateLineCommand createLineCommand = new CreateLineCommand(6, 2, 6, 8);
        CreateLineCommand createLineCommand2 = new CreateLineCommand(4, 5, 5, 5);
        ICommand command =  new FillConcurrentAreaCommand(4, 1, 'y', 4);
        createRectangleCommand.execute(view);
        createLineCommand.execute(view);
        createLineCommand2.execute(view);

        String expected =
                "----------" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|x xyyxyy|" + "\r\n" +
                        "|xxxyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyxxxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "----------" + "\r\n";

        checkResult(command, expected);
    }
}
