package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variable;

public class SingleExample {
    public static void main(String[] args) {
        Variable<Integer> singleVar = new SharedVariable<>(0);

        // Todo: add the nowait
        Parallel.withThreads(4)
                .block((id, vars) -> System.out.printf("1 - Thread %d%n", id))
                .singleBlock((id, vars) -> {
                    singleVar.set(1);
                    System.out.printf("Single block executed by thread %d%n", id);
                })
                .block((id, vars) -> System.out.printf("2 - Thread %d%n", id))
                .join();

        System.out.printf("Single var: %d%n", singleVar.value());
    }
}
