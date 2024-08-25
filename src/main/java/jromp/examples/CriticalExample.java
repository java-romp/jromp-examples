package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.construct.critical.Critical;
import jromp.parallel.operation.Operations;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variables;

public class CriticalExample {
    public static void main(String[] args) {
        Variables variables = Variables.create().add("criticalVar", new SharedVariable<>(0));

        Parallel.withThreads(4)
                .withVariables(variables)
                .block(((id, vars) -> {
                    System.out.printf("1 - Thread %d%n", id);

                    Critical.enter("criticalVar", id, vars, (i, v) -> {
                        v.<Integer>get("criticalVar").update(Operations.add(1).get());

                        System.out.printf("Critical thread %d%n", i);
                    });

                    System.out.printf("2 - Thread %d%n", id);
                }))
                .join();
    }
}
