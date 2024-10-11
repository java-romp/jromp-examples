package jromp.examples;

import jromp.Constants;
import jromp.JROMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class BasicUsage1 {
    private static final Logger logger = LoggerFactory.getLogger(BasicUsage1.class);

    public static void main(String[] args) {
        JROMP.allThreads()
             .block(variables -> {
                    int numThreads = variables.<Integer>get(Constants.NUM_THREADS).value();
                    logger.info("Hello World from thread {} of {}", getThreadNum(), numThreads);
                })
             .join();
    }
}
