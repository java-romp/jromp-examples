#include <omp.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    int x = 1;

    #pragma omp parallel num_threads(2)
    {
        #pragma omp critical
        x++; // different threads can update x at the
        #pragma omp atomic
        x++; // same time!!!
    }

    printf("%d", x); // prints 2 or 3

    return 0;
}
