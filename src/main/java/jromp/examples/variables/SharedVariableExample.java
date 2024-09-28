package jromp.examples.variables;

import jromp.parallel.Parallel;
import jromp.parallel.operation.Operations;
import jromp.parallel.var.SharedVariable;
import jromp.parallel.var.Variable;
import jromp.parallel.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(SharedVariableExample.class);

    public static void main(String[] args) {
        Variables variables = Variables.create().add("sharedVariable", new SharedVariable<>(0));

        // #################################################################
        // NOTE: this code is not thread-safe (unexpected results may occur)
        // #################################################################

        Parallel.defaultConfig()
                .withVariables(variables)
                .block((id, vars) -> {
                    Variable<Integer> sharedVariable = vars.get("sharedVariable");
                    sharedVariable.update(Operations.add(1));
                    logger.info("Thread {}: {}", id, sharedVariable.value());
                })
                .join();

        // This value could take any value between 1 and the number of threads
        logger.info("Shared variable: {}", variables.<Integer>get("sharedVariable").value());
    }
}
