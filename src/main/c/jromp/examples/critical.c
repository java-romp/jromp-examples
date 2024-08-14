#include <stdio.h>
#include <omp.h>

int main(int argc, char *argv[]) {
    int criticalVar = 0;

    #pragma omp parallel num_threads(4) shared(criticalVar)
	{
		printf("1 - Thread %d\n", omp_get_thread_num());

		#pragma omp critical
		{
			criticalVar += 1;
			printf("Critical thread %d\n", omp_get_thread_num());
		}

		printf("2 - Thread %d\n", omp_get_thread_num());
	}

    return 0;
}
