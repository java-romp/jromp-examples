#include <omp.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define N 4
#define M 4

int main() {
    int i;
    int j;
    int n_threads;
    int tid;
    int n;
    int m;
    int sum;
    int a[M];
    int c[N];
    int b[M][N];

    srand(time(NULL));
    m = M;
    n = N;

    for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            b[i][j] = rand() % 100;
        }
    }
    for (i = 0; i < N; i++) {
        c[i] = rand() % 100;
    }

    #pragma omp parallel for private(i, j, sum, tid) shared(a, b, c, m, n, n_threads)
    for (i = 0; i < m; i++) {
        // Get ID of thread
        tid = omp_get_thread_num();
        // Get number of threads
        n_threads = omp_get_num_threads();
        sum = 0;

        for (j = 0; j < n; j++) {
            sum += b[i][j] * c[j];
        }

        a[i] = sum;
        printf("Thread %d of %d, calculates the iteration i=%d\n", tid, n_threads, i);
    } // Implicit barrier

    for (i = 0; i < M; i++) {
        printf("a[%d] = %d\n", i, a[i]);
    }

    return 0;
}
