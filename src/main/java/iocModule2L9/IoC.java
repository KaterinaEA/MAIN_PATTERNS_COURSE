package iocModule2L9;

import exceptionModule1L3.ICommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
 * Метод проверяет, есть ли в карте strategy запись с ключом dependency. Если да, то метод Resolve<T> возвращает объект типа T, который хранится в карте strategy. В противном случае метод Resolve<T> генерирует исключение ArgumentException.
 */
public class IoC {

    //private static final Map<String, Supplier<Objects>> _strategy = new HashMap<>();

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

    public static <T> T resolve(String dependency, Object... args) {
        return (T) _strategy.apply(dependency, args);
    }

/*    private static final Function<String, Object[]> _strategy =
            (dependency, args) -> {
                if ("RegisterIocResolveDependencyStrategy".equals(dependency)) {
                    return new Register(
                            (Function<Function<String, Object[]>, Function<String, Object[]>>) args[0]
                    );
                } else {
                    throw new IllegalArgumentException("Dependency \"" + dependency + "\" is not found.");
                }
            };

    public static <T> T resolve(String register, String dependency, Function<String, Object[]> args) {
        return (T) _strategy.apply(register, dependency, args);
    }*/




    /**
     * Метод Resolve принимает три параметра: name, dependency и args.
     * <p>
     * name — строка, которая используется для идентификации объекта, который нужно разрешить.
     * dependency — строка, представляющая зависимость, которую нужно разрешить.
     * args — массив объектов, которые могут быть использованы для разрешения зависимости.
     * <p>
     * Метод проверяет, есть ли в карте strategy запись с ключом name. Если да, то метод Resolve возвращает объект типа T, который хранится в карте strategy по ключу dependency. В противном случае метод Resolve генерирует исключение IllegalArgumentException.
     */
/*    public static <T> T Resolve(String dependency, String arg1, Function<Object[], Object> resolver) {
        // Здесь происходит логика разрешения зависимостей
        // При инициализации заводим зависимость IoC.Register (имя зависимости и стратегия) , возвращает объект ICommand и  метод execute регистрирует объект

        if (strategy.containsKey(dependency)) {
            return (T) strategy.get(dependency).get(dependency).get();
        } else {
            throw new IllegalArgumentException(String.format("Dependency %s is not found", dependency));
        }
    }*/

    /**
     * Метод resolve принимает три параметра: dependency, arg1 и resolver.
     * <p>
     * dependency — строка, которая используется для идентификации объекта, который нужно разрешить.
     * arg1 — строка, которая представляет аргумент, который будет передан в функцию resolver.
     * resolver — функция, которая принимает массив объектов и возвращает объект.
     * <p>
     * Метод проверяет, соответствует ли значение параметра dependency строке «IoC.Register».
     * Если да, то метод resolve вызывает функцию resolver с аргументом arg1 и возвращает результат.
     * В противном случае метод resolve генерирует исключение IllegalArgumentException.
     */
/*    public static Object resolve(String dependency, String arg1, Function<Object[], Object> resolver) {
        if (dependency.equals("IoC.Register")) {
            return resolver.apply(new Object[] { arg1 });
        } else {
            throw new IllegalArgumentException("Dependency not found: " + dependency);
        }
    }*/



}

}
