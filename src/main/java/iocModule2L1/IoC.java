package iocModule2L1;

import java.util.HashMap;
import java.util.Map;
import exceptionModule1L3.ICommand;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private static final Map<String, Object> dictionaryDependency = new HashMap<>();

    public static void register(String dependency, Object strategy) {
        dictionaryDependency.put(dependency, strategy);
    }

    public static <R> R resolve(String d, Object... args) {
        if (dictionaryDependency.containsKey(d)) {
            Object resolvedStrategy = dictionaryDependency.get(d);
            if (resolvedStrategy instanceof Function || resolvedStrategy instanceof BiFunction<?,?,?>) {
                    //Function<Object[],R> functionArray = (Function<Object[],R>) resolvedStrategy;
                    //return functionArray.apply(args);
                    if (args.length > 1) { // Проверяем, что args является массивом объектов
                        BiFunction<Object, Object, R> bifunction = (BiFunction<Object, Object, R>) resolvedStrategy;
                        return bifunction.apply(args[0], args[1]);
                    } else {
                        // Если args является одиночным объектом
                        Function<Object,R> function = (Function<Object,R>) resolvedStrategy;
                        return function.apply(args[0]);
                    }
            } else if (resolvedStrategy instanceof Supplier) {
                Supplier<Object> resolveNotArgs = (Supplier<Object>) dictionaryDependency.get(d);
                return (R) resolveNotArgs.get();
            }
        }
        throw new IllegalArgumentException("Dependency " + d + " is not found.");
    }
}

/*public class IoC {

    private static final Map<String, Supplier> dictionaryDependency = new HashMap<>();

    public static void register(String dependency, Supplier<Object> strategy) {
        dictionaryDependency.put(dependency, strategy);
    }

    public static <T> T resolve(String d, Object... args) {
        if (dictionaryDependency.containsKey(d)) {
            return (T) dictionaryDependency.get(d).get();
        }
        throw new IllegalArgumentException("Dependency \"" + d + "\" is not found.");
    }
}*/

/*public class IoC {

    private static final Map<String, Supplier> dictionaryDependency = new HashMap<>();

    public static void register(String dependency, Supplier<Object> strategy) {
        dictionaryDependency.put(dependency, strategy);
    }

    public static <T> T resolve(String d, Supplier<T> supplier, Object... args) {
        if (dictionaryDependency.containsKey(d)) {
            Supplier<T> resolvedSupplier = dictionaryDependency.get(d);
            if (resolvedSupplier != null) {
                // Здесь мы используем args, если они были переданы
                return resolvedSupplier.get(args);
            }
        }
        throw new IllegalArgumentException("Dependency \"" + d + "\" is not found.");
    }
}*/
