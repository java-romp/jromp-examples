package jromp.examples.presentation;

import jromp.JROMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getNumThreads;
import static jromp.JROMP.getThreadNum;

@SuppressWarnings("all")
public class JROMPUsage {
    private static final Logger logger = LoggerFactory.getLogger(JROMPUsage.class);

    /*
     * Code structure:
     *   1. Specify the number of threads to use.
     *   2. Define the block(s) of code to be executed.
     *   3. Join the threads.
     */

    public static void main(String[] args) {
        JROMP.allThreads() // 1.
             // 2. (
             .parallel(variables -> {
                 logger.info("Hello World from thread {} of {}", getThreadNum(), getNumThreads());
             })
             // 2. )
             .join(); // 3.
    }
}


/*
JROMP.allThreads()
     .parallel(variables -> {
         logger.info("Hello World from thread {} of {}", getThreadNum(), getNumThreads());
     })
     .join();
 */

/*
Variable<Integer> privateVar = new PrivateVariable<>(0);
Variables vars = Variables.create()
                          .add("privateVar", privateVar);

JROMP.allThreads()
     .withVariables(vars)
     .parallel(variables -> {
         Variable<Integer> privateVarLocal = variables.get("privateVar");

         privateVarLocal.update(Operations.add(1));

         logger.info("Value of privateVar: {}", privateVarLocal.value());
     })
     .join();

logger.info("Final value of privateVar: {}", privateVar.value());
 */
