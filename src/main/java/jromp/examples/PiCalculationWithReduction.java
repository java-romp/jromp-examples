package jromp.examples;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.ReductionVariable;
import jromp.var.Variables;
import jromp.var.reduction.ReductionOperations;

import static jromp.JROMP.getWTime;

public class PiCalculationWithReduction {
    public static void main(String[] args) {
        int n = 100000;
        double h = 1.0 / n;
        ReductionVariable<Double> result = new ReductionVariable<>(ReductionOperations.sum(), 0d);
        Variables vars = Variables.create().add("sum", result);

        double initialTime = getWTime();

        JROMP.allThreads()
             .withVariables(vars)
             .parallelFor(1, n + 1, false, (start, end, variables) -> {
                 double x;
                 double sum = 0.0;

                 for (int i = start; i < end; i++) {
                     x = h * (i - 0.5);
                     sum += calc(x);
                 }

                 variables.<Double>get("sum").update(Operations.add(sum).get());
             })
             .join();

        double finalResult = h * result.value();
        double finalTime = getWTime();

        System.out.printf("Time: %fms%n", (finalTime - initialTime) * 1000);
        System.out.printf("PI is approximately: %.16f. Error: %.16f%n", finalResult,
                          Math.abs(finalResult - Math.PI));
    }

    private static double calc(double a) {
        return 4.0 / (1.0 + a * a);
    }
}
