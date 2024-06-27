package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;

import java.util.HashMap;

public class RegisterDependencyCommand implements ICommand {
    private String _dependency;
    private FuncDependencyResolverStrategy _dependencyResolverStrategy;

    public RegisterDependencyCommand(String dependency, FuncDependencyResolverStrategy dependencyResolverStrategy) {
        _dependency = dependency;
        _dependencyResolverStrategy = dependencyResolverStrategy;
    }

    @Override
    public void execute() {
        HashMap<String, FuncDependencyResolverStrategy> currentScope = IoC.resolve("IoC.Scope.Current");
        currentScope.put(_dependency, _dependencyResolverStrategy);
    }

    // Интерфейс FuncDependencyResolverStrategy для поддержки стратегий разрешения зависимостей
    public interface FuncDependencyResolverStrategy {
        Object resolve(String dependency, Object[] args);
    }
}
