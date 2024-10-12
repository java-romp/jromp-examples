package jromp.examples;

import jromp.JROMP;
import jromp.construct.critical.Critical;
import jromp.operation.Operations;
import jromp.var.SharedVariable;
import jromp.var.Variables;

import static jromp.JROMP.getThreadNum;

public class CriticalExample {
    public static void main(String[] args) {
        Variables variables = Variables.create().add("criticalVar", new SharedVariable<>(0));

        JROMP.withThreads(4)
             .withVariables(variables)
             .block(vars -> {
                 System.out.printf("1 - Thread %d%n", getThreadNum());

                 Critical.enter("criticalVar", vars, v -> {
                     v.<Integer>get("criticalVar").update(Operations.add(1).get());

                     System.out.printf("Critical thread %d%n", getThreadNum());
                 });

                 System.out.printf("2 - Thread %d%n", getThreadNum());
             })
             .join();
    }
}
