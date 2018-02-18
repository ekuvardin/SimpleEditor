package intergrationTest.ConsoleViewTest;

import canvas.Canvas;
import canvas.SimpleCanvas;
import canvas.Viewer.ConsoleViewer;
import comands.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class CommonCanvasTests {

    PrintStream old;
    Canvas canvas;
    ConsoleViewer view= new ConsoleViewer();;

    @Before
    public void preTest() {
        old = System.out;
    }

    @After
    public void afterTest() {
        System.setOut(old);
    }

    public abstract Canvas createCanvas(int x, int y);

    @Test
    public void consoleShouldPrintEmptyCanvas() throws IOException {
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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
        canvas = createCanvas(4, 4);
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

    protected void checkResult(ICommand command, String expected) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);

            command.execute(view);

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

}
