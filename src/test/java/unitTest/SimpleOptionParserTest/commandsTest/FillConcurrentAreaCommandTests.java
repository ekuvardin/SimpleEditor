package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Model;
import canvas.ConcurrentModel;
import canvas.SimpleModel;
import canvas.Viewer.IView;
import comands.FillConcurrentAreaCommand;
import comands.ICommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FillConcurrentAreaCommandTests {

    IView view;

    @Before
    public void preTest() {
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnFalse() {
        Assert.assertFalse((new FillConcurrentAreaCommand(1, 2, 'x', 4)).quitCommand());
    }

    @Test
    public void executeShouldCallSingleThreadExampleWhenNotParallel() {
        Model model = new SimpleModel(4,4);
        ICommand command = new FillConcurrentAreaCommand(1, 2, 'x', 4);
        command.execute(view);

        Mockito.verify(view).printMessage("Warning. Created not parallel model. Will be used single thread implementation");
        Mockito.verify(view).draw(model);
    }

    @Test
    public void executeShouldCallParallelThreadExampleWhenIsParallel() {
        Model model = new ConcurrentModel(4,4);
        ICommand command = new FillConcurrentAreaCommand(1, 2, 'x', 4);
        command.execute(view);

        Mockito.verify(view, Mockito.never()).printMessage("Warning. Created not parallel model. Will be used single thread implementation");
        Mockito.verify(view).draw(model);
    }
}

