package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Viewer.IView;
import comands.ErrorCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateErrorCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new ErrorCommand(" ")).quitCommand());
    }

    @Test
    public void executeShouldCallDrawMessage() {
        ICommand command = new ErrorCommand("smt");
        command.execute(view);

        Mockito.verify(view).showError("smt");
    }

    @Test
    public void executeShouldCallDrawMessageException() {
        ICommand command = new ErrorCommand(new RuntimeException("zero"));
        command.execute(view);

        Mockito.verify(view).showError("zero");
    }
}

