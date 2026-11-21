package iocModule2L1.Scope;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InitCommand implements ICommand {
    public static final ThreadLocal<Object> currentScopes = new ThreadLocal<>();
    public static final HashMap<String, Function<Object[], Object>> rootScope = new HashMap<>();
    // Скоупы может параллельно создавать несколько потоков — обычный HashMap здесь не потокобезопасен.
    public static final ConcurrentHashMap<String, HashMap<String, Function<Object[], Object>>> dictionaryScope = new ConcurrentHashMap<>();
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
            rootScope.put("IoC.Scope.New"            , (args) -> new NewScopeCommand((String) args[0]));

            rootScope.put("IoC.register", (args) -> new RegisterDependencyCommand((String) args[0], (Function<Object[], Object>) args[1]));

            alreadyExecutesSuccessfully = true;
        }
    }
}
