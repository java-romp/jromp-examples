package jromp.examples.variables;

import jromp.parallel.Parallel;
import jromp.parallel.var.FirstPrivateVariable;
import jromp.parallel.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstPrivateVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(FirstPrivateVariableExample.class);

    public static void main(String[] args) {
        int val = 123456789;
        Variables variables = Variables.create().add("firstPrivateVariable", new FirstPrivateVariable<>(val));

        logger.info("Value of \"val\" before the OpenMP parallel region: {}.", val);

        Parallel.withThreads(4)
                .withVariables(variables)
                .block((id, vars) -> {
                    logger.info("Thread {} sees \"val\" = {}, and updates it to be {}.",
                                id, vars.get("firstPrivateVariable").value(), id);
                    vars.get("firstPrivateVariable").set(id);
                })
                .join();

        // Value after the parallel region; unchanged.
        logger.info("Value of \"val\" after the OpenMP parallel region: {}.", val);
    }
}
