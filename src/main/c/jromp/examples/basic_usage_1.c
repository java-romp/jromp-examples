#include <omp.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    int threads;
    int tid;

    #pragma omp parallel private(tid)
    {
        tid = omp_get_thread_num();
        threads = omp_get_num_threads();
        printf("Hello World from thread %d of %d\n", tid, threads);
    } // Implicit barrier

    return 0;
}
