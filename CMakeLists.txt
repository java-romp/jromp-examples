cmake_minimum_required(VERSION 3.9)
project(jromp-examples C)

set(CMAKE_C_STANDARD 11)

find_package(OpenMP REQUIRED)
if (OPENMP_FOUND)
    message(STATUS "OpenMP found")
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} ${OpenMP_C_FLAGS}")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS}")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} ${OpenMP_EXE_LINKER_FLAGS}")

    message(STATUS "CMAKE_C_FLAGS:${CMAKE_C_FLAGS}")
else ()
    message(FATAL_ERROR "OpenMP not found")
endif ()

link_libraries(m)

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
add_executable(if ${EXAMPLES_DIR}/if.c)
add_executable(matrix_multiplication ${EXAMPLES_DIR}/matrix_multiplication.c)

add_executable(mandelbrot_original ${EXAMPLES_DIR}/mandelbrot/mandelbrot_original.c
        ${EXAMPLES_DIR}/mandelbrot/mandelbrot.h)
add_executable(mandelbrot_parallel ${EXAMPLES_DIR}/mandelbrot/mandelbrot_parallel.c
        ${EXAMPLES_DIR}/mandelbrot/mandelbrot.h)

add_executable(critical_with_atomic ${EXAMPLES_DIR}/error_prone/critical_with_atomic.c)
add_executable(masked ${EXAMPLES_DIR}/masked.c)

add_executable(private_variable ${EXAMPLES_DIR}/variables/private_variable.c)
add_executable(shared_variable ${EXAMPLES_DIR}/variables/shared_variable.c)
add_executable(first_private_variable ${EXAMPLES_DIR}/variables/first_private_variable.c)
add_executable(last_private_variable ${EXAMPLES_DIR}/variables/last_private_variable.c)
add_executable(all_reduction ${EXAMPLES_DIR}/variables/all_reduction.c)
