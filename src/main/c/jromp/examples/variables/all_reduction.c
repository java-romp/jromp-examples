#include <limits.h>
#include <math.h>
#include <stdio.h>
#include <omp.h>
#include <stdbool.h>

void sum_reduction() {
    int result = 0;

    #pragma omp parallel for reduction(+:result)
    for (int i = 0; i < 10; i++) {
        result += i;
    }

    printf("Sum: %d\n", result);
}

void multiplication_reduction() {
    int result = 1;

    #pragma omp parallel for reduction(*:result)
    for (int i = 1; i <= 10; i++) {
        result *= i;
    }

    printf("Multiplication: %d\n", result);
}

void bitwise_and_reduction() {
    int result = ~0;

    #pragma omp parallel for reduction(&:result)
    for (int i = 0; i < 10; i++) {
        result &= i;
    }

    printf("Bitwise AND: %d\n", result);
}

void bitwise_or_reduction() {
    int result = 0;

    #pragma omp parallel for reduction(|:result)
    for (int i = 0; i < 10; i++) {
        result |= i;
    }

    printf("Bitwise OR: %d\n", result);
}

void bitwise_xor_reduction() {
    int result = 0;

    #pragma omp parallel for reduction(^:result)
    for (int i = 0; i < 10; i++) {
        result ^= i;
    }

    printf("Bitwise XOR: %d\n", result);
}

void logical_and_reduction() {
    bool result = true;

    #pragma omp parallel for reduction(&&:result)
    for (int i = 0; i < 10; i++) {
        result = result && i % 2 == 0;
    }

    printf("Logical AND: %s\n", result ? "true" : "false");
}

void logical_or_reduction() {
    bool result = false;

    #pragma omp parallel for reduction(||:result)
    for (int i = 0; i < 10; i++) {
        result = result || i % 2 == 0;
    }

    printf("Logical OR: %s\n", result ? "true" : "false");
}

void max_reduction() {
    int result = INT_MIN;

    #pragma omp parallel for reduction(max:result)
    for (int i = 0; i < 10; i++) {
        result = (int) fmax(result, i);
    }

    printf("Max: %d\n", result);
}

void min_reduction() {
    int result = INT_MAX;

    #pragma omp parallel for reduction(min:result)
    for (int i = 0; i < 10; i++) {
        result = (int) fmin(result, i);
    }

    printf("Min: %d\n", result);
}

int main() {
    sum_reduction();
    multiplication_reduction();
    bitwise_and_reduction();
    bitwise_or_reduction();
    bitwise_xor_reduction();
    logical_and_reduction();
    logical_or_reduction();
    max_reduction();
    min_reduction();

    return 0;
}
