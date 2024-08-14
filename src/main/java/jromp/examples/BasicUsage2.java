package jromp.examples;

import jromp.Constants;
import jromp.parallel.Parallel;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variable;

public class BasicUsage2 {
    public static void main(String[] args) {
        Variable<Integer> threads = new SharedVariable<>(0);

        Parallel.defaultConfig()
                .block((id, variables) -> {
                    int numThreads = variables.<Integer>get(Constants.NUM_THREADS).value();
                    threads.set(numThreads);

                    System.out.printf("Hello World from thread %d of %d%n", id, numThreads);
                })
                .parallelFor(0, threads.value(), false, (id, start, end, variables) -> {
                    for (int i = start; i < end; i++) {
                        System.out.printf("Thread %d: %d%n", id, i);
                    }
                })
                .block((id, variables) -> System.out.printf("Thread %d done%n", id))
                .join();
    }
}
