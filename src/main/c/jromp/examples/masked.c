#include <stdio.h>
#include <omp.h>

// Simple example with master directive

int main(int argc, char *argv[]) {
    #pragma omp parallel
    {
        #pragma omp masked filter(1)
        {
            printf("Hello from the master thread (%d)\n", omp_get_thread_num());
        }
    }

    return 0;
}
