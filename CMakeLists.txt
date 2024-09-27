cmake_minimum_required(VERSION 3.9)
project(jromp-examples C)

set(CMAKE_C_STANDARD 11)

set(OpenMP_LIB /usr/lib/llvm-12/lib/libomp.so)
set(OpenMP_INCLUDE /usr/lib/llvm-12/lib/clang/12.0.0/include)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fopenmp")

link_libraries(m ${OpenMP_LIB})
include_directories(${OpenMP_INCLUDE})

set(EXAMPLES_DIR src/main/c/jromp/examples)

add_executable(basic_usage_1 ${EXAMPLES_DIR}/basic_usage_1.c)
add_executable(basic_usage_2 ${EXAMPLES_DIR}/basic_usage_2.c)
add_executable(parallel_for_base ${EXAMPLES_DIR}/parallel_for_base.c)
add_executable(pi_calculation_with_reduction ${EXAMPLES_DIR}/pi_calculation_with_reduction.c)
add_executable(sections ${EXAMPLES_DIR}/sections.c)
add_executable(single ${EXAMPLES_DIR}/single.c)
add_executable(single_nowait ${EXAMPLES_DIR}/single_nowait.c)
add_executable(atomic ${EXAMPLES_DIR}/atomic.c)
add_executable(critical ${EXAMPLES_DIR}/critical.c)
add_executable(private_variable ${EXAMPLES_DIR}/private_variable.c)
add_executable(shared_variable ${EXAMPLES_DIR}/shared_variable.c)
add_executable(first_private_variable ${EXAMPLES_DIR}/first_private_variable.c)
add_executable(last_private_variable ${EXAMPLES_DIR}/last_private_variable.c)
add_executable(if ${EXAMPLES_DIR}/if.c)
add_executable(matrix_multiplication ${EXAMPLES_DIR}/matrix_multiplication.c)

add_executable(mandelbrot_original ${EXAMPLES_DIR}/mandelbrot/mandelbrot_original.c
        ${EXAMPLES_DIR}/mandelbrot/mandelbrot.h)
add_executable(mandelbrot_parallel ${EXAMPLES_DIR}/mandelbrot/mandelbrot_parallel.c
        ${EXAMPLES_DIR}/mandelbrot/mandelbrot.h)

add_executable(critical_with_atomic ${EXAMPLES_DIR}/error_prone/critical_with_atomic.c)
add_executable(masked ${EXAMPLES_DIR}/masked.c)
