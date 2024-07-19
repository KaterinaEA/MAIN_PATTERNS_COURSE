package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.function.Function;

public class InitCommand implements ICommand {
    public static final ThreadLocal<Object> currentScopes = new ThreadLocal<>();
    public static final HashMap<String, Function<Object[], Object>> rootScope = new HashMap<>();
    public static HashMap<String, HashMap<String, Function<Object[], Object>>> dictionaryScope = new HashMap<>();
    private static boolean alreadyExecutesSuccessfully = false;

    public void execute() {
        if (alreadyExecutesSuccessfully) {
            return;
        }

        dictionaryScope.put("ScopeIdRoot", rootScope);

        synchronized (rootScope) {

            rootScope.put("IoC.Scope.Current.Set"   , (args) -> new SetCurrentScopeCommand(args[0]));
            rootScope.put("IoC.Scope.Current.Clear" , (args) -> new ClearCurrentScopeCommand());
            rootScope.put("IoC.Scope.Current"       , (args) -> currentScopes.get() != null ? currentScopes.get() : rootScope);
            rootScope.put("IoC.Scope.New"           , (args) -> {

                HashMap<String, Function<Object[], Object>> newScope = new HashMap<>();
                HashMap<String, HashMap<String, Function<Object[], Object>>> dictiScope = InitCommand.dictionaryScope;
                dictiScope.put((String) args[0], newScope);
                return newScope;

            });

            rootScope.put("IoC.register", (args) -> new RegisterDependencyCommand((String) args[0], (Function<Object[], Object>) args[1]));

            alreadyExecutesSuccessfully = true;
        }
    }
}
