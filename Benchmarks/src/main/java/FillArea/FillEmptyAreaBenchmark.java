package FillArea;

import canvas.Model;
import canvas.SimpleModel;
import canvas.figures.fillArea.BatchSingleThreadFillArea;
import canvas.figures.fillArea.IFillArea;
import canvas.figures.fillArea.ParallelBatchFillArea;
import canvas.figures.fillArea.SingleThreadFillArea;
import canvas.viewer.ConsoleViewer;
import canvas.viewer.IView;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;


/*
    Benchmark                                                  Mode  Cnt   Score   Error  Units
    fillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put  avgt    5   9,233 ± 3,001  ns/op
    fillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put    avgt    5  15,580 ± 5,009  ns/op
 */
public class FillEmptyAreaBenchmark {

    static int width = 2048;
    static int height = 2048;

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 4)
    @Measurement(iterations = 5)
    @Timeout(time = 3)
    @Fork(1)
    public static class SingleThreadFilling {

        private Model model;
        private IFillArea fillArea;

        @Setup(Level.Iteration)
        public void preSetup() {
            model = new SimpleModel(width, height);
            fillArea = new SingleThreadFillArea(model);
        }

        @Benchmark
        public void put() {
            fillArea.fill(1, 1, 'y');
        }
    }
/*
    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 4)
    @Measurement(iterations = 5)
    @Timeout(time = 3)
    @Fork(1)
    public static class MultipleThreadFilling {

        private Model model;
        private IFillArea fillArea;
        private IView view = new ConsoleViewer();

        @Setup(Level.Iteration)
        public void preSetup() throws InterruptedException {
            model = new SimpleModel(width, height);
            fillArea = new ParallelBatchFillArea(view, model, 1, 128, 128, new BatchSingleThreadFillArea(model));
        }

        @Benchmark
        public void put() throws InterruptedException {
            fillArea.fill(1, 1, 'y');
        }
    }
*/
    public static void main(String[] args) {

        Options opt = new OptionsBuilder()
                .include(FillEmptyAreaBenchmark.class.getSimpleName())
                .warmupIterations(4)
                .measurementIterations(5)
                .forks(1)
                .timeout(TimeValue.seconds(60))
                .jvmArgs("-ea")
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

}