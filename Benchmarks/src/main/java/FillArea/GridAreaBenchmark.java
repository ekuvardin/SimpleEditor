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
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/*
    Area 512*512
    Benchmark                                             Mode  Cnt  Score   Error  Units
    FillArea.GridAreaBenchmark.MultipleThreadFilling.put  avgt    5  4,813 ± 0,224  ns/op
    FillArea.GridAreaBenchmark.SingleThreadFilling.put    avgt    5  2,677 ± 0,190  ns/op

    Area 2048*2048
    Benchmark                                             Mode  Cnt  Score   Error  Units
    FillArea.GridAreaBenchmark.MultipleThreadFilling.put  avgt    5  5,078 ± 0,286  ns/op
    FillArea.GridAreaBenchmark.SingleThreadFilling.put    avgt    5  7,357 ± 1,808  ns/op
 */
public class GridAreaBenchmark {

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
        private char colour = 'a';

        @Setup(Level.Trial)
        public void benchSetup() {
            model = new SimpleModel(width, height);
            fillArea = new SingleThreadFillArea(model);

            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= width; j++) {
                    if (i % 2 == 1)
                        model.set(j, i, colour);
                    else if (j % 2 == 1)
                        model.set(j, i, colour);
                }
            }
        }

        @Setup(Level.Iteration)
        public void preSetup() {
            colour += 1;
        }

        @Benchmark
        public void put() {
            fillArea.fill(1, 1, colour);
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
        private char colour = 'a';
        private IView view = new ConsoleViewer();

        @Setup(Level.Trial)
        public void benchSetup() {
            model = new SimpleModel(width, height);
            fillArea = new ParallelBatchFillArea(view, model, 6, 128, 128, new BatchSingleThreadFillArea(model));

            for (int i = 1; i <= height; i++) {
                for (int j = 1; j <= width; j++) {
                    if (i % 2 == 1)
                        model.set(j, i, colour);
                    else if (j % 2 == 1)
                        model.set(j, i, colour);
                }
            }
        }

        @Setup(Level.Iteration)
        public void preSetup() {
            colour += 1;
        }

        @Benchmark
        public void put() throws InterruptedException {
            fillArea.fill(1, 1, colour);
        }
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(GridAreaBenchmark.class.getSimpleName())
                .warmupIterations(4)
                .measurementIterations(8)
                .forks(1)
                .timeout(TimeValue.seconds(60))
                .jvmArgs("-ea")
                  .addProfiler(GCProfiler.class)
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}