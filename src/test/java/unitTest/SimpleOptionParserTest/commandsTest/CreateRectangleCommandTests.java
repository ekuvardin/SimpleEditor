package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Canvas;
import canvas.SimpleCanvas;
import canvas.Viewer.IView;
import comands.CreateLineCommand;
import comands.CreateRectangleCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateRectangleCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new CreateRectangleCommand(1, 2, 3, 4)).quitCommand());
    }

    @Test
    public void executeShouldCallDrawCanvas() {
        Canvas canvas = new SimpleCanvas(4, 4);
        ICommand command = new CreateRectangleCommand(1, 2, 3, 4);
        command.execute(view);

        Mockito.verify(view).draw(canvas);
    }
}
