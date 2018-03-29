package intergrationTest.ConsoleViewTest;

import canvas.SimpleModel;
import canvas.figures.fillArea.BatchSingleThreadFillArea;
import canvas.figures.fillArea.IFillArea;
import canvas.figures.fillArea.ParallelBatchFillArea;
import comands.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParallelFillAreaCommandTest extends CommonModelTests {

    @Test(timeout = 1000000)
    public void consoleShouldFillCanvasInConcurrentInOneThread() throws IOException {
        model = createCanvas(4, 4);
        CreateRectangleCommand createLineCommand = new CreateRectangleCommand(1, 1, 3, 3);
        ICommand command = new FillParallelAreaCommand(4, 1, 'y', 1, 2, 2);
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
        ICommand command = new FillParallelAreaCommand(4, 1, 'y', 2, 2, 2);
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
        ICommand command = new FillParallelAreaCommand(4, 1, 'y', 4, 2, 2);
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

    @Ignore
    @Test
    public void consoleShouldFillCanvas() throws IOException {
        model = new SimpleModel(2048, 2048);
        IFillArea fillArea = new ParallelBatchFillArea(view, model, 6, 512, 128, new BatchSingleThreadFillArea(model));

        for (int i = 1; i <= 2048; i++) {
            for (int j = 1; j <= 2048; j++) {
                if (i % 2 == 1)
                    model.set(j, i, 'a');
                else if (j % 2 == 1)
                    model.set(j, i, 'a');
            }
        }
        fillArea.fill(1, 1, 'b');

        try(BufferedWriter out = new BufferedWriter(new FileWriter(new File("bigFile.txt")))){
            out.write("2048 2048");
            out.newLine();
            for (int i = 1; i <= 2048; i++) {
                for (int j = 1; j <= 2048; j++)
                    out.write(model.get(j,i));
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
