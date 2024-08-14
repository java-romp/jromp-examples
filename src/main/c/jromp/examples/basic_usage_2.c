#include <omp.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    int num_threads;
    int tid;

    #pragma omp parallel private(tid) shared(num_threads)
    {
        tid = omp_get_thread_num();
        num_threads = omp_get_num_threads();
        printf("Hello World from thread %d of %d\n", tid, num_threads);
        int i;

        #pragma omp for private(i)
        for (i = 0; i < num_threads; i++) {
            printf("Thread %d: i = %d\n", tid, i);
        }

        printf("Thread %d done\n", tid);
    } // Implicit barrier

    return 0;
}
