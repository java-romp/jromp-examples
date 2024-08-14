#include <omp.h>
#include <stdio.h>
#include <math.h>

double calc(double a);

int main(int argc, char *argv[]) {
    const int n = 100000;
    const double pi25dt = 3.141592653589793238462643;
    const double h = 1.0 / n;
    double result = 0.0;
    double x;

    const double initial_time = omp_get_wtime();

    #pragma omp parallel for private(x) reduction(+:result)
    for (int i = 1; i <= n; i++) {
        x = h * (i - 0.5);
        result += calc(x);
    } // Implicit barrier

    result = h * result;
    const double final_time = omp_get_wtime();

    printf("Time: %fms\n", (final_time - initial_time) * 1000);
    printf("PI is approximately: %.16f. Error: %.16f\n", result, fabs(result - pi25dt));

    return 0;
}

double calc(const double a) {
    return 4.0 / (1.0 + a * a);
}
