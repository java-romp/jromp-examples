#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int private_variable = -1;

    #pragma omp parallel private(private_variable)
    {
        private_variable = omp_get_thread_num();
        printf("Thread %d\n", private_variable);
    }

    printf("Private variable: %d\n", private_variable);

    return 0;
}
