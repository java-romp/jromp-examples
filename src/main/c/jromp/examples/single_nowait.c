#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int singleVar;

    #pragma omp parallel num_threads(4) shared(singleVar)
    {
        printf("1 - Thread %d\n", omp_get_thread_num());

        #pragma omp single nowait
        {
            singleVar = 1;
            printf("Single block executed by thread %d\n", omp_get_thread_num());
        } // Implicit barrier

        printf("Barrier passed\n");
        printf("Thread %d has finished\n", omp_get_thread_num());
    }

    printf("Single var: %d\n", singleVar);

    return 0;
}
