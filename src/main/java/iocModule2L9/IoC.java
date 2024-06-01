package iocModule2L9;

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
 * Метод проверяет, есть ли в карте strategy запись с ключом dependency. Если да, то метод Resolve<T> возвращает объект типа T, который хранится в карте strategy. В противном случае метод Resolve<T> генерирует исключение ArgumentException.
 */
public class IoC {

    private static final Map<String, Object> dictionaryDependency = new HashMap<>();

    private static final BiFunction<String, Object[], Object> _strategy =
            (dependency, args) -> {
                if (dictionaryDependency.containsKey(dependency)) {

                    dictionaryDependency.get(dependency);

                    return dictionaryDependency.get(dependency);

                } else {

                    throw new IllegalArgumentException("Dependency \"" + dependency + "\" is not found.");

                }
            };


    public static void register (String dependency, Function<String, Object> strategy) {

        dictionaryDependency.put(dependency, strategy);

    }

    public static <T> T resolve(String d, Object... args) {

        return (T) _strategy.apply(d, args);

    }

}
