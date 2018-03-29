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
    Area 2048*2048

    Benchmark                                                  Mode  Cnt   Score   Error  Units
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put  avgt    5   5,613 ± 0,189  ns/op
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put    avgt    5  12,464 ± 8,244  ns/op

    Area 512*512
    Benchmark                                                  Mode  Cnt  Score   Error  Units
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put  avgt    5  4,824 ± 0,202  ns/op
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put    avgt    5  5,453 ± 0,448  ns/op

    Also take a look at difference in calling GC and error in Area 2048*2048.

    Speculation:
    In Single thread algorithm we process a lot of objects in a big area and allocate a lot of space for it.
    In Parallel algorithm we use a lot of small areas and after finishing calculating on one of them GC can instantly reuse space allocated for it.
    In fact that when area are not big(512*512) error is also small.

    Results with GCProfiler.class, Area 2048*2048

    Benchmark                                                                          Mode  Cnt     Score    Error   Units
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put                            avgt   15    11,542 ±  3,933   ns/op
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.alloc.rate             avgt   15   104,656 ±  0,368  MB/sec
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.alloc.rate.norm        avgt   15     1,901 ±  0,651    B/op
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.churn.G1_Old_Gen       avgt   15    63,557 ± 37,833  MB/sec
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.churn.G1_Old_Gen.norm  avgt   15     1,303 ±  0,909    B/op
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.count                  avgt   15    13,000           counts
    FillArea.FillEmptyAreaBenchmark.SingleThreadFilling.put:·gc.time                   avgt   15  3049,000               ms

    Benchmark                                                                                                  Mode  Cnt    Score    Error   Units
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put                                                  avgt   15    5,218 ±  0,075   ns/op
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.alloc.rate                                   avgt   15  101,062 ±  0,207  MB/sec
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.alloc.rate.norm                              avgt   15    0,828 ±  0,013    B/op
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.CodeHeap_'non-profiled_nmethods'       avgt   15    0,097 ±  0,142  MB/sec
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.CodeHeap_'non-profiled_nmethods'.norm  avgt   15    0,001 ±  0,001    B/op
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.G1_Old_Gen                             avgt   15  105,804 ± 27,529  MB/sec
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.G1_Old_Gen.norm                        avgt   15    0,867 ±  0,226    B/op
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.G1_Survivor_Space                      avgt   15    0,088 ±  0,249  MB/sec
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.churn.G1_Survivor_Space.norm                 avgt   15    0,001 ±  0,002    B/op
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.count                                        avgt   15   16,000           counts
    FillArea.FillEmptyAreaBenchmark.MultipleThreadFilling.put:·gc.time                                         avgt   15   42,000               ms
 */
public class FillEmptyAreaBenchmark {

    static int width = 512;
    static int height = 512;

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
            fillArea = new ParallelBatchFillArea(view, model, 4, 128, 128, new BatchSingleThreadFillArea(model));
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
                .include(FillEmptyAreaBenchmark.class.getSimpleName())
                .warmupIterations(4)
                .measurementIterations(5)
                .forks(1)
                .timeout(TimeValue.seconds(60))
                .jvmArgs("-ea")
                //   .addProfiler(GCProfiler.class)
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}