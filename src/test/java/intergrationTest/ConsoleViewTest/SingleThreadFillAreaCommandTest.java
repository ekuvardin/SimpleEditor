package intergrationTest.ConsoleViewTest;

import canvas.SimpleModel;
import canvas.figures.fillArea.IFillArea;
import canvas.figures.fillArea.SingleThreadFillArea;
import comands.*;
import org.junit.Test;

import java.io.IOException;

public class SingleThreadFillAreaCommandTest extends CommonModelTests {

    @Test
    public void consoleShouldPrintEmptyCanvas() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateCanvasCommand(4, 4);

        String expected =
                "------" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";
        checkResult(command, expected);
    }

    @Test
    public void consoleShouldRePrintEmptyCanvas() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateCanvasCommand(4, 4);

        String expected =
                "------" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";
        checkResult(command, expected);

        command = new CreateCanvasCommand(4, 2);

        expected =
                "------" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";
        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintSimpleLine() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateLineCommand(1, 1, 4, 1);

        String expected =
                "------" + "\r\n" +
                        "|xxxx|" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintSimpleLineInOnePoint() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateLineCommand(4, 4, 4, 4);

        String expected =
                "------" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|   x|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintDiagonalLineLeftToRight() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateLineCommand(1, 1, 4, 4);

        String expected =
                "------" + "\r\n" +
                        "|xx  |" + "\r\n" +
                        "| xx |" + "\r\n" +
                        "|  xx|" + "\r\n" +
                        "|   x|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintDiagonalLineRightToLeft() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateLineCommand(1, 4, 4, 1);

        String expected =
                "------" + "\r\n" +
                        "|   x|" + "\r\n" +
                        "|  xx|" + "\r\n" +
                        "| xx |" + "\r\n" +
                        "|xx  |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintRectangle() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateRectangleCommand(1, 1, 3, 3);

        String expected =
                "------" + "\r\n" +
                        "|xxx |" + "\r\n" +
                        "|x x |" + "\r\n" +
                        "|xxx |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintRectangleInOnePoint() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateRectangleCommand(1, 1, 1, 1);

        String expected =
                "------" + "\r\n" +
                        "|x   |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldPrintRectangleInOneLine() throws IOException {
        model = createCanvas(4, 4);
        ICommand command = new CreateRectangleCommand(1, 1, 4, 1);

        String expected =
                "------" + "\r\n" +
                        "|xxxx|" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldFillCanvas() throws IOException {
        model = createCanvas(4, 4);
        CreateRectangleCommand createRectangleCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillAreaCommand(4, 1, 'y');
        createRectangleCommand.execute(view);

        String expected =
                "------" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|x xy|" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|yyyy|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldFillCanvasClickOnLine() throws IOException {
        model = createCanvas(4, 4);
        CreateRectangleCommand createRectangleCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillAreaCommand(3, 1, 'y');
        createRectangleCommand.execute(view);

        String expected =
                "------" + "\r\n" +
                        "|yyy |" + "\r\n" +
                        "|y y |" + "\r\n" +
                        "|yyy |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldFillCanvasClickOnePoint() throws IOException {
        model = createCanvas(4, 4);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillAreaCommand(2, 2, 'y');
        createLineCommand.execute(view);

        String expected =
                "------" + "\r\n" +
                        "|xxx |" + "\r\n" +
                        "|xyx |" + "\r\n" +
                        "|xxx |" + "\r\n" +
                        "|    |" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldFillCanvas2() throws IOException {
        model = new SimpleModel(512, 512);
        IFillArea fillArea = new SingleThreadFillArea(model);
        fillArea.fill(1, 1, 'y');
        // fillArea = new ParallelBatchFillArea(view, model, 1,256,256,  new BatchSingleThreadFillArea(model));
        view.draw(model);
    }
}
