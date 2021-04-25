package app.mytest;

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
 * 测试字符串性能
 *
 * @author faith.huan 2020-10-09 16:13
 */
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class Test_03_String {

    @State(Scope.Thread)
    public static class ThreadState {
        String format = "数据%s不具备单调性";
        String no = "不具备单调性";
    }


    @Benchmark
    public String format(ThreadState state) {
        return String.format(state.format, "123");
    }

    @Benchmark
    public String append(ThreadState state) {
        return "数据" + "123" + state.no;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Test_03_String.class.getSimpleName())
                //.threads(2)
                .forks(1)
                .build();

        new Runner(opt).run(); //Monotonicity
    }

}
