package jromp.examples.variables;

import jromp.JROMP;
import jromp.operation.Operations;
import jromp.var.ReductionVariable;
import jromp.var.Variables;
import jromp.var.reduction.ReductionOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllReductionExample {
    private static final Logger logger = LoggerFactory.getLogger(AllReductionExample.class);

    private static void sumReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.sum(), 0));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.add(i));
                 }
             })
             .join();

        logger.info("Sum: {}", variables.<Integer>get("result").value());
    }

    private static void multiplicationReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.mul(), 1));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(1, 10, false, (start, end, vars) -> {
                 for (int i = start; i <= end; i++) {
                     vars.<Integer>get("result").update(Operations.multiply(i));
                 }
             })
             .join();

        logger.info("Multiplication: {}", variables.<Integer>get("result").value());
    }

    private static void bitwiseAndReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.band(), ~0));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.bitwiseAnd(i));
                 }
             })
             .join();

        logger.info("Bitwise AND: {}", variables.<Integer>get("result").value());
    }

    private static void bitwiseOrReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.bor(), 0));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.bitwiseOr(i));
                 }
             })
             .join();

        logger.info("Bitwise OR: {}", variables.<Integer>get("result").value());
    }

    private static void bitwiseXorReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.bxor(), 0));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.bitwiseXor(i));
                 }
             })
             .join();

        logger.info("Bitwise XOR: {}", variables.<Integer>get("result").value());
    }

    private static void logicalAndReduction() {
        Variables variables = Variables.create().add("result",
                                                     new ReductionVariable<>(ReductionOperations.land(), true));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     final int finalI = i;
                     vars.<Boolean>get("result").update(result -> result && finalI % 2 == 0);
                 }
             })
             .join();

        logger.info("Logical AND: {}", variables.<Boolean>get("result").value());
    }

    private static void logicalOrReduction() {
        Variables variables = Variables.create().add("result",
                                                     new ReductionVariable<>(ReductionOperations.lor(), false));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     final int finalI = i;
                     vars.<Boolean>get("result").update(result -> result || finalI % 2 == 0);
                 }
             })
             .join();

        logger.info("Logical OR: {}", variables.<Boolean>get("result").value());
    }

    private static void maxReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.max(),
                                                                                       Integer.MIN_VALUE));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.max(i));
                 }
             })
             .join();

        logger.info("Max: {}", variables.<Integer>get("result").value());
    }

    private static void minReduction() {
        Variables variables = Variables.create().add("result", new ReductionVariable<>(ReductionOperations.min(),
                                                                                       Integer.MAX_VALUE));

        JROMP.allThreads()
             .withVariables(variables)
             .parallelFor(0, 10, false, (start, end, vars) -> {
                 for (int i = start; i < end; i++) {
                     vars.<Integer>get("result").update(Operations.min(i));
                 }
             })
             .join();

        logger.info("Min: {}", variables.<Integer>get("result").value());
    }

    public static void main(String[] args) {
        sumReduction();
        multiplicationReduction();
        bitwiseAndReduction();
        bitwiseOrReduction();
        bitwiseXorReduction();
        logicalAndReduction();
        logicalOrReduction();
        maxReduction();
        minReduction();
    }
}
