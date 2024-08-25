package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.construct.atomic.Atomic;
import jromp.parallel.operation.Operations;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variables;

public class AtomicExample {
    public static void main(String[] args) {
        Variables variables = Variables.create().add("count", new SharedVariable<>(0));

        Parallel.defaultConfig()
                .withVariables(variables)
                .block((id, vars) -> Atomic.update("count", Operations.add(1), vars))
                .join();

        System.out.printf("Number of threads: %d%n", variables.<Integer>get("count").value());
    }
}
