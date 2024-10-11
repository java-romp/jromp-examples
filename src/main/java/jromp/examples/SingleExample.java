package jromp.examples;

import jromp.JROMP;
import jromp.var.SharedVariable;
import jromp.var.Variable;

import static jromp.JROMP.getThreadNum;

public class SingleExample {
    public static void main(String[] args) {
        Variable<Integer> singleVar = new SharedVariable<>(0);

        JROMP.withThreads(4)
             .block(vars -> System.out.printf("1 - Thread %d%n", getThreadNum()))
             .singleBlock(false, vars -> {
                    singleVar.set(1);
                    System.out.printf("Single block executed by thread %d%n", getThreadNum());
                })
             .block(vars -> System.out.printf("Thread %d has finished%n", getThreadNum()))
             .join();

        System.out.printf("Single var: %d%n", singleVar.value());
    }
}
