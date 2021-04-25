package app.mytest;

import app.creta.enums.Monotonicity;
import app.creta.util.MonotonicityUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 数组单调性性能测试
 *
 * @author faith.huan 2020-10-09 16:13
 */
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class Test_02_Monotonicity {

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double[] x = {4.0, 3.0, 2.0, 1.0};

        double[][] x2 = {
                {4.0, 3.0, 2.0, 1.0},
                {5.0, 4.0, 3.0, 2.0},
                {6.0, 5.0, 4.0, 3.0},
                {7.0, 6.0, 5.0, 4.0}};
        final double[] y = {4.0, 3.0, 2.0, 1.0};

    }


    @Benchmark
    public Monotonicity measureOneDim(ThreadState state) {
        return MonotonicityUtil.getMonotonicity(state.x);
    }

    @Benchmark
    public Monotonicity measureOneDimY(ThreadState state) {
        return MonotonicityUtil.getMonotonicity(state.y);
    }

    @Benchmark
    public Monotonicity measureTwoDim(ThreadState state) {
        return MonotonicityUtil.getMonotonicity(state.x2);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Test_02_Monotonicity.class.getSimpleName())
                //.threads(2)
                .forks(1)
                .build();
        new Runner(opt).run();

        /*
        3 warm up  10 measure
        Benchmark                                   Mode  Samples          Score  Score error  Units
        a.m.Test_02_Monotonicity.measureOneDim     thrpt       10  210619598.966  5685370.881  ops/s
        a.m.Test_02_Monotonicity.measureOneDimY    thrpt       10  208200448.706  9996663.314  ops/s
        a.m.Test_02_Monotonicity.measureTwoDim     thrpt       10   10284505.729   448911.258  ops/s*/
    }

}
