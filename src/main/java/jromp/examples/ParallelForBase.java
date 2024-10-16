package jromp.examples;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.PrivateVariable;
import jromp.var.SharedVariable;
import jromp.var.Variable;
import jromp.var.Variables;

import java.util.Random;

import static jromp.JROMP.getNumThreads;
import static jromp.JROMP.getThreadNum;

public class ParallelForBase {
    // A simple random number generator.
    private static final Random random = new Random();

    public static int rand(int bound) {
        return random.nextInt(bound);
    }

    public static void main(String[] args) {
        final int N = 4;
        final int M = 4;

        int i;
        int j = 0;
        int n;
        int m;
        int sum = 0;
        int[] a = new int[M];
        int[] c = new int[N];
        int[][] b = new int[M][N];

        m = M;
        n = N;

        // Initialize the arrays.
        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                b[i][j] = rand(100);
            }
        }

        for (i = 0; i < N; i++) {
            c[i] = rand(100);
        }

        // Declare the variables
        Variables vars = Variables.create();
        vars.add("a", new SharedVariable<>(a))
            .add("b", new SharedVariable<>(b))
            .add("c", new SharedVariable<>(c))
            .add("m", new SharedVariable<>(m))
            .add("n", new SharedVariable<>(n))
            // Private variables
            .add("i", new PrivateVariable<>(i))
            .add("j", new PrivateVariable<>(j))
            .add("sum", new PrivateVariable<>(sum));

        // Parallel for loop
        JROMP.withThreads(4) //! Should match the value of N and M
             .withVariables(vars)
             .parallelFor(0, m, false, (start, end, variables) -> {
                 for (int k = start; k < end; k++) {
                     int id = getThreadNum();
                     Variable<Integer> sumInternal = variables.get("sum");
                     sumInternal.set(0);

                     for (int j1 = 0; j1 < n; j1++) {
                         int firstOperand = variables.<int[][]>get("b").value()[id][j1];
                         int secondOperand = variables.<int[]>get("c").value()[j1];

                         sumInternal.update(Operations.add(firstOperand * secondOperand).get());
                     }

                     variables.<int[]>get("a").value()[id] = sumInternal.value();
                     System.out.printf("Thread %d of %d, calculates the iteration i=%d%n", id, getNumThreads(), id);
                 }
             })
             .join();

        for (i = 0; i < M; i++) {
            System.out.printf("a[%d] = %d\n", i, a[i]);
        }
    }
}
