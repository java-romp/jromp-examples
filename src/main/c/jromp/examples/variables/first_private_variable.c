#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int val = 123456789;

    printf("Value of \"val\" before the OpenMP parallel region: %d.\n", val);

    #pragma omp parallel num_threads(4) firstprivate(val)
    {
        printf("Thread %d sees \"val\" = %d, and updates it to be %d.\n", omp_get_thread_num(), val,
               omp_get_thread_num());
        val = omp_get_thread_num();
    }

    // Value after the parallel region; unchanged.
    printf("Value of \"val\" after the OpenMP parallel region: %d.\n", val);

    return 0;
}
