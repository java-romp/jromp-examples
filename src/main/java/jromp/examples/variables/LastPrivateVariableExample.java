package jromp.examples.variables;

import jromp.JROMP;
import jromp.var.LastPrivateVariable;
import jromp.var.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jromp.JROMP.getThreadNum;

public class LastPrivateVariableExample {
    private static final Logger logger = LoggerFactory.getLogger(LastPrivateVariableExample.class);

    public static void main(String[] args) {
        int val = 123456789;
        Variables variables = Variables.create().add("lastPrivateVariable", new LastPrivateVariable<>(val));

        logger.info("Value of \"val\" before the OpenMP parallel region: {}.", val);

        JROMP.withThreads(4)
             .withVariables(variables)
             .parallel(vars -> vars.get("lastPrivateVariable").set(getThreadNum()))
             .join();

        Integer lastValue = (Integer) variables.get("lastPrivateVariable").value();
        logger.info(
                "Value of \"val\" after the OpenMP parallel region: {}. Thread {} was therefore the last one to modify it.",
                lastValue, lastValue);
    }
}
