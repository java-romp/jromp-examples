package jromp.examples.variables;

import jromp.JROMP;
import jromp.var.FirstPrivateVariable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class FirstPrivateVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(FirstPrivateVariableExample.class);

    public static void main(String[] args) {
        int val = 123456789;
        Variables variables = Variables.create().add("firstPrivateVariable", new FirstPrivateVariable<>(val));

        logger.info("Value of \"val\" before the OpenMP parallel region: {}.", val);

        JROMP.withThreads(4)
             .withVariables(variables)
             .block(vars -> {
                 logger.info("Thread {} sees \"val\" = {}, and updates it to be {}.",
                             getThreadNum(), vars.get("firstPrivateVariable").value(), getThreadNum());
                 vars.get("firstPrivateVariable").set(getThreadNum());
             })
             .join();

        // Value after the parallel region; unchanged.
        logger.info("Value of \"val\" after the OpenMP parallel region: {}.", val);
    }
}
