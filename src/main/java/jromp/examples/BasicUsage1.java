package jromp.examples;

import jromp.JROMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getNumThreads;
import static jromp.JROMP.getThreadNum;

public class BasicUsage1 {
    private static final Logger logger = LoggerFactory.getLogger(BasicUsage1.class);

    public static void main(String[] args) {
        JROMP.allThreads()
             .block(variables -> {
                 logger.info("Hello World from thread {} of {}", getThreadNum(), getNumThreads());
             })
             .join();
    }
}
