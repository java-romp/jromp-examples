#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define N 2000
#define OPTIONAL_PART 1

int main() {
    // Print the available number of threads
    #pragma omp parallel
    {
        // Only one thread prints this message
        #pragma omp single
        {
            printf("Number of threads: %d\n", omp_get_num_threads());
        }
    }

    // Allocate memory for matrices
    double *a = malloc(N * N * sizeof(double));
    double *b = malloc(N * N * sizeof(double));
    double *c = malloc(N * N * sizeof(double));

    // Initialize matrices
    for (int i = 0; i < N * N; i++) {
        a[i] = 1.0;
        b[i] = 1.0;
    }

    // Start the timer
    const double start = omp_get_wtime();

    // Matrix multiplication
    #pragma omp parallel for shared(a, b, c)
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            c[i * N + j] = 0.0;

            for (int k = 0; k < N; k++) {
                c[i * N + j] += a[i * N + k] * b[k * N + j];
            }
        }
    }

    // This type of pragma is only used for annotation purposes, it does not affect the code execution
    #pragma region Optional(extra point): Create another loop with a reduction variable.

    if (OPTIONAL_PART) {
        double sum = 0.0;

        // Calculate the sum of all elements in the matrix
        #pragma omp parallel for shared(c) reduction(+:sum) num_threads(4)
        for (int i = 0; i < N * N; i++) {
            sum += c[i];
        }

        printf("Total sum: %f\n", sum);
    }

    #pragma endregion

    // Print the execution time
    const double end = omp_get_wtime();
    printf("Time: %f\n", end - start);

    // Free memory
    free(a);
    free(b);
    free(c);

    return 0;
}
