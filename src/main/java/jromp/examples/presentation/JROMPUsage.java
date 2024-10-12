package jromp.examples.presentation;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.PrivateVariable;
import jromp.var.Variable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

@SuppressWarnings("all")
public class JROMPUsage {
    private static final Logger logger = LoggerFactory.getLogger(JROMPUsage.class);

    public static void main(String[] args) {
        Variables vars = Variables.create();
        vars.add("privateVar", new PrivateVariable<>(0));

        JROMP.allThreads()
             .withVariables(vars)
             .block(variables -> {
                 Variable<Integer> privateVar = variables.get("privateVar");

                 privateVar.update(Operations.assign(1));

                 logger.info("Hello World from thread {} of {}", getThreadNum(), privateVar.value());
             })
             .join();

        logger.info("privateVar = {}", vars.get("privateVar").value());
    }
}

/*
package jromp.examples.presentation;

import jromp.Constants;
import jromp.JROMP;
import jromp.var.SharedVariable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class JROMPUsage {
    private static final Logger logger = LoggerFactory.getLogger(JROMPUsage.class);

    public static void main(String[] args) {
        JROMP.allThreads()
             .block(variables -> {
                 int numThreads = variables.<Integer>get(Constants.NUM_THREADS).value();
                 logger.info("Hello World from thread {} of {}", getThreadNum(), numThreads);
             })
             .join();
    }
}
 */

/*
package jromp.examples.presentation;

import jromp.Constants;
import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.PrivateVariable;
import jromp.var.Variable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

@SuppressWarnings("all")
public class JROMPUsage {
    private static final Logger logger = LoggerFactory.getLogger(JROMPUsage.class);

    public static void main(String[] args) {
        Variables vars = Variables.create();
        vars.add("privateVar", new PrivateVariable<>(0));

        JROMP.allThreads()
             .withVariables(vars)
             .block(variables -> {
                 Variable<Integer> numThreads = variables.get(Constants.NUM_THREADS);
                 Variable<Integer> variableName = variables.get("privateVar");

                 variableName.update(Operations.add(1));

                 logger.info("Hello World from thread {} of {}", getThreadNum(), numThreads.value());
             })
             .join();

        logger.info("privateVar = {}", vars.get("privateVar").value());
    }
}
 */
