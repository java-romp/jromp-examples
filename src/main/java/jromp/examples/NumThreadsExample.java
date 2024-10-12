package jromp.examples;

import jromp.JROMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getNumThreads;

public class NumThreadsExample {
    private static final Logger logger = LoggerFactory.getLogger(NumThreadsExample.class);

    public static void main(String[] args) {
        logger.info("Number of threads (outside): {}", getNumThreads());

        JROMP.allThreads()
             .singleBlock(false, variables -> logger.info("Number of threads (inside): {}", getNumThreads()))
             .join();
    }
}
