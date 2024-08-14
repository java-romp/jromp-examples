package jromp.examples;

import jromp.parallel.Parallel;
import jromp.parallel.var.PrivateVariable;
import jromp.parallel.var.Variable;
import jromp.parallel.var.Variables;

public class PrivateVariableExample {
    public static void main(String[] args) {
        Variable<Integer> privateVariable = new PrivateVariable<>(0);
        privateVariable.set(-1);
        Variables variables = Variables.create().add("privateVariable", privateVariable);

        Parallel.defaultConfig()
                .block(variables, (id, vars) -> {
                    Variable<Integer> privateVar = vars.get("privateVariable");
                    privateVar.set(id);
                    System.out.printf("Thread %d\n", privateVar.value());
                })
                .join();

        System.out.printf("Private variable %d%n", privateVariable.value());
    }
}
