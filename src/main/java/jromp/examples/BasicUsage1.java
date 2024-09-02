package jromp.examples;

import jromp.Constants;
import jromp.parallel.Parallel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicUsage1 {
    private static final Logger logger = LoggerFactory.getLogger(BasicUsage1.class);

    public static void main(String[] args) {
        Parallel.defaultConfig()
                .block((id, variables) -> {
                    int numThreads = variables.<Integer>get(Constants.NUM_THREADS).value();
                    logger.info("Hello World from thread {} of {}", id, numThreads);
                })
                .join();
    }
}
