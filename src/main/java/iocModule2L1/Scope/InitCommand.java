package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;
import iocModule2L1.IoC;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class InitCommand implements ICommand {
    public static final ThreadLocal<Object> currentScopes = new ThreadLocal<>();
    public static final HashMap<String, Function<Object[], Object>> rootScope = new HashMap<>();
    private static boolean alreadyExecutesSuccessfully = false;

    public void execute() {
        if (alreadyExecutesSuccessfully) {
            return;
        }

        synchronized (rootScope) {
            rootScope.put("IoC.Scope.Current.Set", (args) -> new SetCurrentScopeCommand(args[0]));
            rootScope.put("IoC.Scope.Current.Clear", (args) -> new ClearCurrentScopeCommand());
            rootScope.put("IoC.Scope.Current", (args) -> currentScopes.get() != null ? currentScopes.get() : rootScope);
            rootScope.put("IoC.Scope.Parent", (args) -> {
                try {
                    throw new Exception("The root scope has no a parent scope.");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            rootScope.put("IoC.Scope.Create.Empty", (args) -> new HashMap<String, Function<Object[], Object>>());
            rootScope.put("IoC.Scope.Create",
                    (args) -> {
                        HashMap<String, Function<Object[], Object>> creatingScope =
                                (HashMap<String, Function<Object[], Object>>) IoC.resolve("IoC.Scope.Create.Empty");

                        if (args.length > 0) {
                            Object parentScope = args[0];
                            creatingScope.put("IoC.Scope.Parent", (params) -> parentScope);
                        } else {
                            Object parentScope = IoC.resolve("IoC.Scope.Current");
                            creatingScope.put("IoC.Scope.Parent", (params) -> parentScope);
                        }
                        return creatingScope;
                    });
            rootScope.put("IoC.register", (args) -> new RegisterDependencyCommand((String) args[0], (RegisterDependencyCommand.FuncDependencyResolverStrategy) args[1]));

            alreadyExecutesSuccessfully = true;
        }
    }
}
