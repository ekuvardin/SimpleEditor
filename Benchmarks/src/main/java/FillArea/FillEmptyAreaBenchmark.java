package FillArea;

import canvas.Canvas;
import canvas.ConcurrentCanvas;
import canvas.Figures.FillArea.ConcurrentFillArea;
import canvas.Figures.FillArea.IFillArea;
import canvas.Figures.FillArea.SingleThreadFillArea;
import canvas.SimpleCanvas;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;


public class FillEmptyAreaBenchmark {

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 4)
    @Measurement(iterations = 5)
    @Timeout(time = 3)
    @Fork(1)
    public static class SingleThreadFilling {

        private Canvas canvas;
        IFillArea singleThreadFillArea;

        @Setup(Level.Trial)
        public void setup() throws InterruptedException {
            singleThreadFillArea = new SingleThreadFillArea();
        }

        @Setup(Level.Iteration)
        public void preSetup() throws InterruptedException {
            canvas = new SimpleCanvas(4048, 4048);
        }

        @Benchmark
        public void put() throws InterruptedException {
            singleThreadFillArea.fill(canvas, 1, 1, 'y', 1);
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

        private Canvas canvas;
        IFillArea fillArea;

        @Setup(Level.Trial)
        public void setup() throws InterruptedException {
            fillArea = new ConcurrentFillArea();
        }

        @Setup(Level.Iteration)
        public void preSetup() throws InterruptedException {
            canvas = new ConcurrentCanvas(4048, 4048);
        }

        @Benchmark
        public void put() throws InterruptedException {
            fillArea.fill(canvas, 2024, 2024, 'y', 16);
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