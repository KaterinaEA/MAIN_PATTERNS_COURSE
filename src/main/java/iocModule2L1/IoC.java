package iocModule2L1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    private static final Map<String, Object> dictionaryDependency = new HashMap<>();

    public static void register(String dependency, Object strategy) {
        dictionaryDependency.put(dependency, strategy);
    }

    public static <R> R resolve(String d, Object... args) {

        if (dictionaryDependency.containsKey(d)) {

                Object resolvedStrategy = dictionaryDependency.get(d);
                Function<Object[], R> functionArray = (Function<Object[], R>) resolvedStrategy;
                return functionArray.apply(args);

        }
        throw new IllegalArgumentException("Dependency " + d + " is not found.");
    }
}
