package jromp.examples.variables;

import jromp.JROMP;
import jromp.var.PrivateVariable;
import jromp.var.Variable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class PrivateVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(PrivateVariableExample.class);

    public static void main(String[] args) {
        Variables variables = Variables.create().add("privateVariable", new PrivateVariable<>(-1));

        JROMP.allThreads()
             .withVariables(variables)
             .parallel(vars -> {
                 Variable<Integer> privateVar = vars.get("privateVariable");
                 privateVar.set(getThreadNum());
                 logger.info("Thread {}", privateVar.value());
             })
             .join();

        logger.info("Private variable {}", variables.<Integer>get("privateVariable").value());
    }
}
