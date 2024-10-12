#include <stdio.h>
#include <omp.h>

int main() {
    printf("Number of threads (outside): %d\n", omp_get_num_threads());

    #pragma omp parallel
    {
        #pragma omp single
        {
            printf("Number of threads (inside): %d\n", omp_get_num_threads());
        }
    }

    return 0;
}
