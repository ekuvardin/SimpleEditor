package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Model;
import canvas.SimpleModel;
import canvas.Viewer.IView;
import comands.FillAreaCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FillAreaCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new FillAreaCommand(1, 2, 'x')).quitCommand());
    }

    @Test
    public void executeShouldCallDrawCanvas() {
        Model model = new SimpleModel(4, 4);
        ICommand command = new FillAreaCommand(1, 2, 'x');
        command.execute(view);


        Mockito.verify(view).draw(model);
    }
}

