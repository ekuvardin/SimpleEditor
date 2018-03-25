package FillArea;

import canvas.Figures.FillArea.*;
import canvas.Model;
import canvas.SimpleModel;
import canvas.Viewer.ConsoleViewer;
import canvas.Viewer.IView;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;


/*

Benchmark                                                  Mode  Cnt   Score   Error  Units
FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put  avgt    5   9,233 ± 3,001  ns/op
FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put    avgt    5  15,580 ± 5,009  ns/op
 */
public class FillEmptyAreaBenchmark {

    static int width = 1024;
    static int height = 1024;

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
        public void preSetup() throws InterruptedException {
            model = new SimpleModel(width, height);
            fillArea = new SingleThreadFillArea(model);
        }

        @Benchmark
        public void put() throws InterruptedException {
            fillArea.fill(1, 1, new Boundary(1, width, 1, height), 'y');
        }
    }

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
            fillArea = new ParallelBatchFillArea(view, model, 4, 64, 64, new BatchSingleThreadFillArea(model));
        }

        @Benchmark
        public void put() throws InterruptedException {
            fillArea.fill(1, 1, new Boundary(1, width, 1, height), 'y');
        }
    }

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