package unitTest.SimpleOptionParserTest;

import comands.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.SimpleOptionParser;


public class SimpleOptionParserTests {

    SimpleOptionParser simpleOptionParser;
    String[] args;

    @Before
    public void preTest(){
        simpleOptionParser = new SimpleOptionParser();
    }

    @Test
    public void SimpleOptionParserShouldReturnErrorCommandWhenCommandNotFound(){
        args = new String[] {"zero"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof ErrorCommand);
        Assert.assertEquals("Unknown command " + args[0], ((ErrorCommand)command).getMessage());
    }

    @Test
    public void SimpleOptionParserShouldReturnErrorCommandWhenEmptyCommand(){
        args = new String[]{};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof ErrorCommand);
        Assert.assertEquals("Empty command", ((ErrorCommand)command).getMessage());
    }

    @Test
    public void SimpleOptionParserShouldReturnQuitCommand(){
        args = new String[] {"Q"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof QuitCommand);
    }

    @Test
    public void SimpleOptionParserShouldReturnFillAreaCommand(){
        args = new String[] {"B", "1", "2", "x"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof FillAreaCommand);

        FillAreaCommand fill = (FillAreaCommand) command;

        Assert.assertEquals(1,fill.getX());
        Assert.assertEquals(2, fill.getY());
        Assert.assertEquals('x', fill.getColour());
    }

    @Test
    public void SimpleOptionParserShouldReturnFillConcurrentAreaCommand(){
        args = new String[] {"BN", "1", "2", "x", "4"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof FillParallelAreaCommand);

        FillParallelAreaCommand fill = (FillParallelAreaCommand) command;

        Assert.assertEquals(1,fill.getX());
        Assert.assertEquals(2, fill.getY());
        Assert.assertEquals('x', fill.getColour());
        Assert.assertEquals(4, fill.getThreadCount());
    }

    @Test
    public void SimpleOptionParserShouldReturnCreateCanvasCommand(){
        args = new String[] {"C", "20", "2"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof CreateCanvasCommand);

        CreateCanvasCommand canvas = (CreateCanvasCommand) command;

        Assert.assertEquals(20,canvas.getWidth());
        Assert.assertEquals(2, canvas.getHeight());
    }

    @Test
    public void SimpleOptionParserShouldReturnCreateLineCommand(){
        args = new String[] {"L", "1", "2", "20", "4"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof CreateLineCommand);

        CreateLineCommand line = (CreateLineCommand) command;

        Assert.assertEquals(1,line.getX1());
        Assert.assertEquals(2, line.getY1());
        Assert.assertEquals(20, line.getX2());
        Assert.assertEquals(4, line.getY2());
    }

    @Test
    public void SimpleOptionParserShouldReturnCreateCreateRectangleCommand(){
        args = new String[] {"R", "1", "1", "3", "3"};

        ICommand command = simpleOptionParser.parseCommand(args);
        Assert.assertNotNull(command);
        Assert.assertTrue(command instanceof CreateRectangleCommand);

        CreateRectangleCommand rect = (CreateRectangleCommand) command;

        Assert.assertEquals(1,rect.getX1());
        Assert.assertEquals(1, rect.getY1());
        Assert.assertEquals(3, rect.getX2());
        Assert.assertEquals(3, rect.getY2());
    }
}
