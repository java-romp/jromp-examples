package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.builder.SectionBuilder;
import jromp.parallel.var.Variables;

public class Sections {
    public static void main(String[] args) {
        Parallel.defaultConfig()
                .sections(false,
                          SectionBuilder.create()
                                        .task((i, vars) -> System.out.println("Task 1. Running thread " + i))
                                        .variables(Variables.create())
                                        .add()
                                        .task((i, vars) -> System.out.println("Task 2. Running thread " + i))
                                        .variables(Variables.create())
                                        .add()
                                        .task((i, vars) -> System.out.println("Task 3. Running thread " + i))
                                        .variables(Variables.create())
                                        .add()
                                        .task((i, vars) -> System.out.println("Task 4. Running thread " + i))
                                        .variables(Variables.create())
                                        .add()
                )
                .join();
    }
}
