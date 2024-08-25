package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variable;

public class SingleNowaitExample {
    public static void main(String[] args) {
        Variable<Integer> singleVar = new SharedVariable<>(0);

        Parallel.withThreads(4)
                .block((id, vars) -> System.out.printf("Block executed by thread %d%n", id))
                .singleBlock(true, (id, vars) -> {
                    singleVar.set(1);
                    System.out.printf("Single block executed by thread %d%n", id);
                })
                .block((id, vars) -> System.out.printf("Thread %d has finished%n", id))
                .join();

        System.out.printf("Single var: %d%n", singleVar.value());
    }
}
