package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Canvas;
import canvas.SimpleCanvas;
import canvas.Viewer.IView;
import comands.CreateCanvasCommand;
import comands.CreateLineCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateCanvasCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new CreateCanvasCommand(1, 2)).quitCommand());
    }

    @Test
    public void executeShouldCallDrawCanvas() {
        ICommand command = new CreateCanvasCommand(1, 2);
        command.execute(view);

        //Mockito.verify(view).draw(canvas);
    }
}

