package intergrationTest.Canvas;

import canvas.SimpleCanvas;
import canvas.Viewer.ConsoleViewer;
import comands.CreateLineCommand;
import comands.CreateRectangleCommand;
import comands.FillAreaCommand;
import comands.FillConcurrentAreaCommand;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SimpleCanvasTests {

    PrintStream old;

    @Before
    public void preTest() {
        old = System.out;
    }

    @After
    public void afterTest() {
        System.setOut(old);
    }

    @Test
    public void consoleShouldPrintEmptyCanvas() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);

            view.draw(canvas);

            String expected =
                    "------" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldRePrintEmptyCanvas() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            view.draw(canvas);

            String expected =
                    "------" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();


            SimpleCanvas canvas1 = new SimpleCanvas(4, 2);
            view.draw(canvas1);

            expected =
                    "------" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());
            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintSimpleLine() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateLineCommand createLineCommand = new CreateLineCommand(1, 1, 4, 1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|xxxx|" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintSimpleLineInOnePoint() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateLineCommand createLineCommand = new CreateLineCommand(4, 4, 4, 4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|   x|" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintDiagonalLineLeftToRight() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateLineCommand createLineCommand = new CreateLineCommand(1, 1, 4, 4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|xx  |" + "\r\n" +
                            "| xx |" + "\r\n" +
                            "|  xx|" + "\r\n" +
                            "|   x|" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintDiagonalLineRightToLeft() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateLineCommand createLineCommand = new CreateLineCommand(1, 4, 4, 1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|   x|" + "\r\n" +
                            "|  xx|" + "\r\n" +
                            "| xx |" + "\r\n" +
                            "|xx  |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintRectangle() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|xxx |" + "\r\n" +
                            "|x x |" + "\r\n" +
                            "|xxx |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintRectangleInOnePoint() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 1, 1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|x   |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldPrintRectangleInOneLine() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 4, 1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);
            createLineCommand.execute(view);

            String expected =
                    "------" + "\r\n" +
                            "|xxxx|" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldFillCanvas() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        FillAreaCommand fill = new FillAreaCommand(4, 1, 'y');
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            createLineCommand.execute(view);
            System.setOut(ps);
            fill.execute(view);
            String expected =
                    "------" + "\r\n" +
                            "|xxxy|" + "\r\n" +
                            "|x xy|" + "\r\n" +
                            "|xxxy|" + "\r\n" +
                            "|yyyy|" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldFillCanvasClickOnLine() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        FillAreaCommand fill = new FillAreaCommand(3, 3, 'y');
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            createLineCommand.execute(view);
            System.setOut(ps);
            fill.execute(view);
            String expected =
                    "------" + "\r\n" +
                            "|yyy |" + "\r\n" +
                            "|y y |" + "\r\n" +
                            "|yyy |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldFillCanvasClickOnePoint() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        FillAreaCommand fill = new FillAreaCommand(2, 2, 'y');
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            createLineCommand.execute(view);
            System.setOut(ps);
            fill.execute(view);
            String expected =
                    "------" + "\r\n" +
                            "|xxx |" + "\r\n" +
                            "|xyx |" + "\r\n" +
                            "|xxx |" + "\r\n" +
                            "|    |" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void consoleShouldFillCanvasInConcurrent() throws IOException {
        SimpleCanvas canvas = new SimpleCanvas(4, 4);
        ConsoleViewer view = new ConsoleViewer();
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        FillConcurrentAreaCommand fill = new FillConcurrentAreaCommand(4, 1, 'y', 4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            createLineCommand.execute(view);
            System.setOut(ps);
            fill.execute(view);
            String expected =
                    "Warning. Created not parallel canvas. Will be used single thread implementation" + "\r\n" +
                    "------" + "\r\n" +
                            "|xxxy|" + "\r\n" +
                            "|x xy|" + "\r\n" +
                            "|xxxy|" + "\r\n" +
                            "|yyyy|" + "\r\n" +
                            "------" + "\r\n";

            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

}
