package iocModule2L9;

import exceptionModule1L3.ICommand;

import java.util.function.Function;

public class Register implements ICommand {

    public Register( Function<String, Object> _strategy ) {


    }

    @Override
    public void execute() {

        private static Function<String, Object[], Object> _strategy =
                (dependency, args) -> {
                    if ("IoC.Register".equals(dependency)) {
                        return new Register(
                                (Function<Function<String, Object[] >, Function<String, Object[] >>) args[0]
                        );
                    } else {
                        throw new IllegalArgumentException("Dependency \"" + dependency + "\" is not found.");
                    }
                };

    }
}
