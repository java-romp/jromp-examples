package jromp.examples.variables;

import jromp.parallel.Parallel;
import jromp.parallel.var.PrivateVariable;
import jromp.parallel.var.Variable;
import jromp.parallel.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrivateVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(PrivateVariableExample.class);

    public static void main(String[] args) {
        Variables variables = Variables.create().add("privateVariable", new PrivateVariable<>(-1));

        Parallel.defaultConfig()
                .withVariables(variables)
                .block((id, vars) -> {
                    Variable<Integer> privateVar = vars.get("privateVariable");
                    privateVar.set(id);
                    logger.info("Thread {}", privateVar.value());
                })
                .join();

        logger.info("Private variable {}", variables.<Integer>get("privateVariable").value());
    }
}
