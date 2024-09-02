#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int count = 0;

    #pragma omp parallel shared(count) if(0)
    {
        #pragma omp atomic update
        count += 1;
    }

    printf("Number of threads: %d\n", count);

    return 0;
}
