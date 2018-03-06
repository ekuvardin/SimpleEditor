package FillArea;

import canvas.ConcurrentModel;
import canvas.Figures.FillArea.ConcurrentFillArea;
import canvas.Figures.FillArea.IFillArea;
import canvas.Figures.FillArea.SingleThreadFillArea;
import canvas.Model;
import canvas.SimpleModel;
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

        private Model model;

        @Setup(Level.Iteration)
        public void preSetup() throws InterruptedException {
            model = new SimpleModel(4048, 4048);
        }

        @Benchmark
        public void put() throws InterruptedException {
            SingleThreadFillArea.fill(model, 1, 1, 'y');
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
        IFillArea fillArea;

        @Setup(Level.Iteration)
        public void preSetup() throws InterruptedException {
            model = new ConcurrentModel(4048, 4048);
        }

        @Benchmark
        public void put() throws InterruptedException {
            ConcurrentFillArea.fill(model, 2024, 2024, 'y', 16);
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