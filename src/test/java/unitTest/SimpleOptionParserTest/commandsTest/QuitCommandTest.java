package unitTest.SimpleOptionParserTest.commandsTest;

import canvas.Viewer.IView;
import comands.ICommand;
import comands.QuitCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class QuitCommandTest {

    IView view;

    @Before
    public void preTest(){
        view = Mockito.mock(IView.class);
    }

    @Test
    public void quitCommandShouldReturnTrue(){
        Assert.assertTrue((new QuitCommand()).quitCommand());
    }

    @Test
    public void executeShouldCallPrintMessage(){
        ICommand command = new QuitCommand();
        command.execute(view);
        Mockito.verify(view).printMessage("The program will be terminated");
    }
}
