package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;

import java.util.HashMap;
import java.util.function.Function;

public class RegisterDependencyCommand implements ICommand {
    private final String _dependency;
    private final Function<Object[], Object> _dependencyResolverStrategy;

    public RegisterDependencyCommand(String dependency, Function<Object[], Object> dependencyResolverStrategy) {

        _dependency = dependency;
        _dependencyResolverStrategy = dependencyResolverStrategy;
        HashMap<String, Function<Object[], Object>> currentScope = IoC.resolve("IoC.Scope.Current");
        currentScope.put(dependency, dependencyResolverStrategy);

    }

    @Override
    public void execute() {
        HashMap<String, Function<Object[], Object>> currentScope = IoC.resolve("IoC.Scope.Current");
        currentScope.put(_dependency, _dependencyResolverStrategy);
    }

}
