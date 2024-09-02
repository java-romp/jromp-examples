package jromp.examples;

import jromp.parallel.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatrixMultiplicationWithoutParallelization {
    private static final Logger logger = LoggerFactory.getLogger(MatrixMultiplicationWithoutParallelization.class);
    private static final int N = 2000;
    private static final boolean OPTIONAL_PART = true;

    public static void main(String[] args) {
        // Print the available number of threads
        logger.info("Number of threads: {}", Runtime.getRuntime().availableProcessors());

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
        final double start = Utils.getWTime();

        // Matrix multiplication
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                c[i * N + j] = 0.0;

                for (int k = 0; k < N; k++) {
                    c[i * N + j] += a[i * N + k] * b[k * N + j];
                }
            }
        }

        // region Optional(advanced): Create another loop with reduction variable.

        if (OPTIONAL_PART) {
            double sum = 0.0;

            // Calculate the sum of all elements in the matrix
            for (int i = 0; i < N * N; i++) {
                sum += c[i];
            }

            logger.info("Total sum: {}", sum);
        }

        // endregion

        // Print the execution time
        final double end = Utils.getWTime();
        logger.info("Time: {}", end - start);

        // Free memory
        // No need to free memory in Java
    }
}
