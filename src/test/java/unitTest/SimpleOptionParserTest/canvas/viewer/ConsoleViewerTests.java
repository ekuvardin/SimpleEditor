package unitTest.SimpleOptionParserTest.canvas.viewer;

import canvas.Canvas;
import canvas.Viewer.ConsoleViewer;
import comands.ICommand;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ConsoleViewerTests {

    PrintStream oldOut;
    PrintStream oldErr;
    Canvas canvas;
    ConsoleViewer view= new ConsoleViewer();;

    @Before
    public void preTest() {
        oldOut = System.out;
        oldErr = System.err;
        canvas = Mockito.mock(Canvas.class);
    }

    @After
    public void afterTest() {
        System.setOut(oldOut);
        System.setErr(oldErr);
    }

    @Test
    public void drawShouldPrintCanvas() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Mockito.when(canvas.getxLength()).thenReturn(2);
        Mockito.when(canvas.getyLength()).thenReturn(1);

        Mockito.when(canvas.get(1,1)).thenReturn('e');
        Mockito.when(canvas.get(2,1)).thenReturn('y');

        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);

            view.draw(canvas);

            String expected =
                    "----"+ "\r\n" +
                            "|ey|"+ "\r\n" +
                            "----"+ "\r\n";
            Assert.assertEquals(expected, baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void printMessageShouldPrintMessage() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setOut(ps);

            view.printMessage("test");

            Assert.assertEquals("test"+"\r\n", baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void showErrorShouldPrintErrorMessage() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setErr(ps);

            view.showError("test");

            Assert.assertEquals("ERROR: test"+"\r\n"+"\r\n", baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }

    @Test
    public void showErrorShouldPrintError() throws IOException {
        RuntimeException exception = new RuntimeException();
        StackTraceElement[] array = new  StackTraceElement[]{new StackTraceElement("testclass", "testname", "file",2)};
        exception.setStackTrace(array);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(PrintStream ps = new PrintStream(baos)) {
            System.setErr(ps);

            view.showError(exception);

            Assert.assertEquals("ERROR: [testclass.testname(file:2)]"+"\r\n"+"\r\n", baos.toString());

            System.out.flush();
            baos.reset();
        } finally {
            baos.close();
        }
    }


    private void checkResult(ICommand command, String expected) throws IOException {
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
