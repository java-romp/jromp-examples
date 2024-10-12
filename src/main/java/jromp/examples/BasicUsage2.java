package jromp.examples;

import jromp.JROMP;
import jromp.var.SharedVariable;
import jromp.var.Variable;

import static jromp.JROMP.getNumThreads;
import static jromp.JROMP.getThreadNum;

public class BasicUsage2 {
    public static void main(String[] args) {
        Variable<Integer> threads = new SharedVariable<>(0);

        JROMP.allThreads()
             .block(variables -> {
                 int numThreads = getNumThreads();
                 threads.set(numThreads);

                 System.out.printf("Hello World from thread %d of %d%n", getThreadNum(), numThreads);
             })
             .parallelFor(0, threads.value(), false, (start, end, variables) -> {
                 for (int i = start; i < end; i++) {
                     System.out.printf("Thread %d: %d%n", getThreadNum(), i);
                 }
             })
             .block(variables -> System.out.printf("Thread %d done%n", getThreadNum()))
             .join();
    }
}
