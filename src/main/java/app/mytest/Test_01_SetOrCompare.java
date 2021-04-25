package app.mytest;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 测试直接赋值与比较赋值哪个效率高
 *
 * @author faith.huan 2020-10-09 16:13
 */
public class Test_01_SetOrCompare {

    @State(Scope.Thread)
    public static class ThreadState {
        volatile int x = 1;
    }


    @Benchmark
    public void set(ThreadState state) {
        state.x = 1;
    }

    @Benchmark
    public void compare(ThreadState state) {
        if (state.x != 1) {
            state.x = 1;
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Test_01_SetOrCompare.class.getSimpleName())
                //.threads(2)
                .forks(1)
                .build();

        new Runner(opt).run(); //Monotonicity
    }

}
