package iocModule2L1;

import iocModule2L1.Scope.DependencyResolver;
import iocModule2L1.Scope.InitCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Класс Ioc реализует механизм разрешения зависимостей.
 * <p>
 * Метод Resolve<T> принимает два параметра: dependency и args.
 * <p>
 * dependency — строка, которая используется для идентификации объекта, который нужно разрешить.
 * args — массив объектов, которые могут быть использованы для разрешения зависимости.
 * <p>
 * Метод проверяет, есть ли в карте strategy запись с ключом dependency. Если да, то метод Resolve<R> возвращает объект типа R, который хранится в карте strategy. В противном случае метод Resolve<R> генерирует исключение ArgumentException.
 */
public class IoC {
    public static final Map<String, Function<Object[], Object>> dictionaryDependency = new HashMap<>();

    public static void register(String dependency, Function<Object[], Object> strategy) {
        dictionaryDependency.put(dependency, strategy);
    }

    public static <T> T resolve_old(String d, Object... args) {

        if (dictionaryDependency.containsKey(d)) {

                Object resolvedStrategy = dictionaryDependency.get(d);
                Function<Object[], Object> functionArray = (Function<Object[], Object>) resolvedStrategy;
                return (T) functionArray.apply(args);

        }
        throw new IllegalArgumentException(String.format("Dependency %s is not found.", d));
    }

    static BiFunction<String, Object[], Object> strategy =
            (d, args) -> {

                Object scope = InitCommand.currentScopes.get() != null ? InitCommand.currentScopes.get() : InitCommand.rootScope;
                DependencyResolver dependencyResolver = new DependencyResolver((HashMap<String, Function<Object[], Object>>)scope);

                return dependencyResolver.resolve(d, args);
            };

    public static <T> T resolve (String d, Object... args) {

            return (T) strategy.apply(d, args);

    }
/*
            (args) -> {
                    if ("Update Ioc Resolve Dependency Strategy".equals(args[0])) {
                    Function<Function<String, Object>, Function<String, Object>> updateStrategy =
        (Function<Function<String, Object>, Function<String, Object>>) args[1];
        return new UpdateIocResolveDependencyStrategyCommand[]{new UpdateIocResolveDependencyStrategyCommand((Function<Function<String, Object[]>, Function<String, Object>>) updateStrategy)};
        } else {
        throw new IllegalArgumentException(String.format("Dependency %s is not found.", args[0]));
        }*/

}
