package jromp.examples;

import jromp.parallel.Parallel;

public class Sections {
    public static void main(String[] args) {
        Parallel.defaultConfig()
                .sections(false,
                          (i, vars) -> System.out.println("Task 1. Running thread " + i),
                          (i, vars) -> System.out.println("Task 2. Running thread " + i),
                          (i, vars) -> System.out.println("Task 3. Running thread " + i),
                          (i, vars) -> System.out.println("Task 4. Running thread " + i)
                )
                .join();
    }
}
