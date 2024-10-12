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
