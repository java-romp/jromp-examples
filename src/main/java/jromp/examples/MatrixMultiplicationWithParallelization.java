package jromp.examples;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.ReductionVariable;
import jromp.var.SharedVariable;
import jromp.var.Variable;
import jromp.var.Variables;
import jromp.var.reduction.ReductionOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getNumThreads;
import static jromp.JROMP.getWTime;

public class MatrixMultiplicationWithParallelization {
    private static final Logger logger = LoggerFactory.getLogger(MatrixMultiplicationWithParallelization.class);
    private static final int N = 2000;
    private static final boolean OPTIONAL_PART = true;

    public static void main(String[] args) {
        // Print the available number of threads
        JROMP.allThreads()
             .singleBlock(false, variables -> {
                 logger.info("Number of threads: {}", getNumThreads());
             })
             .join();

        // Allocate memory for matrices
        double[] a = new double[N * N];
        double[] b = new double[N * N];
        double[] c = new double[N * N];

        // Initialize matrices
        for (int i = 0; i < N * N; i++) {
            a[i] = 1.0;
            b[i] = 1.0;
        }

        // Start the timer
        final double startTimer = getWTime();

        // Create the variables that are going to be used in the parallel block
        Variables variables = Variables.create();
        variables.add("a", new SharedVariable<>(a));
        variables.add("b", new SharedVariable<>(b));
        variables.add("c", new SharedVariable<>(c));

        // Matrix multiplication
        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, N, false, (start, end, vars) -> {
                 double[] aInternal = vars.<double[]>get("a").value();
                 double[] bInternal = vars.<double[]>get("b").value();
                 double[] cInternal = vars.<double[]>get("c").value();

                 for (int i = start; i < end; i++) {
                     for (int j = 0; j < N; j++) {
                         cInternal[i * N + j] = 0.0;

                         for (int k = 0; k < N; k++) {
                             cInternal[i * N + j] += aInternal[i * N + k] * bInternal[k * N + j];
                         }
                     }
                 }
             })
             .join();

        // region Optional(advanced): Create another loop with reduction variable.

        if (OPTIONAL_PART) {
            // Add a new variable to the variables object
            variables.add("sum", new ReductionVariable<>(ReductionOperations.sum(), 0.0));

            // Calculate the sum of all elements in the matrix
            JROMP.withThreads(4)
                 .withVariables(variables)
                 .parallelFor(0, N * N, false, (start, end, vars) -> {
                     double[] cInternal = vars.<double[]>get("c").value();
                     Variable<Double> sumInternal = vars.get("sum");

                     for (int i = start; i < end; i++) {
                         sumInternal.update(Operations.add(cInternal[i]));
                     }
                 })
                 .join();

            logger.info("Total sum: {}", variables.<Double>get("sum").value());
        }

        // endregion

        // Print the execution time
        final double endTimer = getWTime();
        logger.info("Time: {}", endTimer - startTimer);

        // Free memory
        // No need to free memory in Java
    }
}
