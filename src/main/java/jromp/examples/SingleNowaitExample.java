package jromp.examples;

import jromp.JROMP;
import jromp.var.SharedVariable;
import jromp.var.Variable;

import static jromp.JROMP.getThreadNum;

public class SingleNowaitExample {
    public static void main(String[] args) {
        Variable<Integer> singleVar = new SharedVariable<>(0);

        JROMP.withThreads(4)
             .parallel(vars -> System.out.printf("1 - Thread %d%n", getThreadNum()))
             .singleBlock(true, vars -> {
                 singleVar.set(1);
                 System.out.printf("Single block executed by thread %d%n", getThreadNum());
             })
             .parallel(vars -> System.out.printf("Thread %d has finished%n", getThreadNum()))
             .join();

        System.out.printf("Single var: %d%n", singleVar.value());
    }
}
