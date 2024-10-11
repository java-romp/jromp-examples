package jromp.examples;

import jromp.JROMP;
import jromp.construct.atomic.Atomic;
import jromp.operation.Operations;
import jromp.var.SharedVariable;
import jromp.var.Variables;

public class AtomicExample {
    public static void main(String[] args) {
        Variables variables = Variables.create().add("count", new SharedVariable<>(0));

        JROMP.allThreads()
             .withVariables(variables)
             .block(vars -> Atomic.update("count", Operations.add(1), vars))
             .join();

        System.out.printf("Number of threads: %d%n", variables.<Integer>get("count").value());
    }
}
