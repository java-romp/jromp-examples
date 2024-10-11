package jromp.examples.variables;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.SharedVariable;
import jromp.var.Variable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class SharedVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(SharedVariableExample.class);

    public static void main(String[] args) {
        Variables variables = Variables.create().add("sharedVariable", new SharedVariable<>(0));

        // #################################################################
        // NOTE: this code is not thread-safe (unexpected results may occur)
        // #################################################################

        JROMP.allThreads()
             .withVariables(variables)
             .block(vars -> {
                 Variable<Integer> sharedVariable = vars.get("sharedVariable");
                 sharedVariable.update(Operations.add(1));
                 logger.info("Thread {}: {}", getThreadNum(), sharedVariable.value());
             })
             .join();

        // This value could take any value between 1 and the number of threads
        logger.info("Shared variable: {}", variables.<Integer>get("sharedVariable").value());
    }
}
