package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Model;
import canvas.SimpleModel;
import canvas.Viewer.IView;
import comands.CreateLineCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CreateLineCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new CreateLineCommand(1, 2, 3, 4)).quitCommand());
    }

    @Test
    public void executeShouldCallDrawCanvas() {
        Model model = new SimpleModel(4, 4);
        ICommand command = new CreateLineCommand(1, 2, 3, 4);
        command.execute(view);

        Mockito.verify(view).draw(model);
    }
}

