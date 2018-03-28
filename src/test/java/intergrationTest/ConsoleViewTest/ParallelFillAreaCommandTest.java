package intergrationTest.ConsoleViewTest;

import comands.*;
import org.junit.Test;

import java.io.IOException;

public class ParallelFillAreaCommandTest extends CommonModelTests {

    @Test(timeout = 1000000)
    public void consoleShouldFillCanvasInConcurrentInOneThread() throws IOException {
        model = createCanvas(4, 4);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillParallelAreaCommand(4, 1, 'y', 1 , 2 , 2);
        createLineCommand.execute(view);

        String expected =
                        "------" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|x xy|" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|yyyy|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);
    }

    @Test(timeout = 100000)
    public void consoleShouldFillCanvasInConcurrentInSeveralThreads() throws IOException {
        model = createCanvas(8, 8);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command =  new FillParallelAreaCommand(4, 1, 'y', 2 , 2 , 2);
        createLineCommand.execute(view);

        String expected =
                "----------" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|x xyyyyy|" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "|yyyyyyyy|" + "\r\n" +
                        "----------" + "\r\n";

        checkResult(command, expected);
    }

    @Test(timeout = 10000)
    public void consoleShouldFillCanvasInConcurrentInSeveralThreadsOneMoreExample() throws IOException {
        model = createCanvas(8, 8);
        CreateRectangleCommand createRectangleCommand = new CreateRectangleCommand(1, 1, 3, 3);
        CreateLineCommand createLineCommand = new CreateLineCommand(6, 2, 6, 8);
        CreateLineCommand createLineCommand2 = new CreateLineCommand(4, 5, 5, 5);
        ICommand command =  new FillParallelAreaCommand(4, 1, 'y', 4, 2, 2);
        createRectangleCommand.execute(view);
        createLineCommand.execute(view);
        createLineCommand2.execute(view);

        String expected =
                "----------" + "\r\n" +
                        "|xxxyyyyy|" + "\r\n" +
                        "|x xyyxyy|" + "\r\n" +
                        "|xxxyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyxxxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "|yyyyyxyy|" + "\r\n" +
                        "----------" + "\r\n";

        checkResult(command, expected);
    }

    @Test
    public void consoleShouldFillCanvas() throws IOException {
        model = createCanvas(256, 256);
        ICommand command =  new FillParallelAreaCommand(1, 1, 'y',4,64,64);
       // fillArea = new ParallelBatchFillArea(view, model, 1,256,256,  new BatchSingleThreadFillArea(model));
        command.execute(view);

     /*   String expected =
                "------" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|x xy|" + "\r\n" +
                        "|xxxy|" + "\r\n" +
                        "|yyyy|" + "\r\n" +
                        "------" + "\r\n";

        checkResult(command, expected);*/
    }

}
