#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int shared_variable = 0;

    // NOTE: this code is not thread-safe

    #pragma omp parallel shared(shared_variable)
    {
        shared_variable += 1;
        printf("Thread %d: %d\n", omp_get_thread_num(), shared_variable);
    }

    // This value could take any value between 1 and the number of threads
    printf("Shared variable: %d\n", shared_variable);

    return 0;
}
