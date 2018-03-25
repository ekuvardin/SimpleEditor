package intergrationTest.ConsoleViewTest;

import canvas.Model;
import canvas.SimpleModel;
import canvas.Viewer.ConsoleViewer;
import comands.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class CommonModelTests {

    PrintStream old;
    Model model;
    ConsoleViewer view= new ConsoleViewer();;

    @Before
    public void preTest() {
        old = System.out;
    }

    @After
    public void afterTest() {
        System.setOut(old);
    }

    protected Model createCanvas(int x, int y) {
        return new SimpleModel(x, y);
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
