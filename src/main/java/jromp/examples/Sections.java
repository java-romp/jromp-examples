package jromp.examples;

import jromp.JROMP;

import static jromp.JROMP.getThreadNum;

public class Sections {
    public static void main(String[] args) {
        JROMP.allThreads()
             .sections(false,
                       vars -> System.out.println("Task 1. Running thread " + getThreadNum()),
                       vars -> System.out.println("Task 2. Running thread " + getThreadNum()),
                       vars -> System.out.println("Task 3. Running thread " + getThreadNum()),
                       vars -> System.out.println("Task 4. Running thread " + getThreadNum())
             )
             .join();
    }
}
