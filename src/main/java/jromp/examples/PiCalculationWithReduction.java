package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.operation.Operations;
import jromp.parallel.var.ReductionVariable;
import jromp.parallel.var.Variables;
import jromp.parallel.var.reduction.Sum;

public class PiCalculationWithReduction {
    public static void main(String[] args) {
        int n = 100000;
        double h = 1.0 / n;
        ReductionVariable<Double> result = new ReductionVariable<>(new Sum<>(), 0d);
        Variables vars = Variables.create().add("sum", result);

        long initialTime = System.nanoTime();

        Parallel.defaultConfig()
                .withVariables(vars)
                .parallelFor(1, n + 1, false, (id, start, end, variables) -> {
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
        long finalTime = System.nanoTime();

        System.out.printf("Time: %fms%n", (finalTime - initialTime) / 1e6);
        System.out.printf("PI is approximately: %.16f. Error: %.16f%n", finalResult,
                          Math.abs(finalResult - Math.PI));
    }

    private static double calc(double a) {
        return 4.0 / (1.0 + a * a);
    }
}
