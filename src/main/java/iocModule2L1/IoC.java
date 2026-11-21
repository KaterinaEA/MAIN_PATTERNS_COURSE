package iocModule2L1;

import iocModule2L1.Scope.DependencyResolver;
import iocModule2L1.Scope.InitCommand;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * IoC-контейнер. Единственная точка входа для всех операций — resolve(key, args):
 * регистрация зависимости, работа со скоупами и получение самих зависимостей
 * выполняются одним и тем же методом, различается только переданный ключ.
 * <p>
 * Геттеры (например, "IoC.Scope.Current") возвращают значение сразу.
 * Мутирующие операции (регистрация зависимости, создание/смена скоупа)
 * возвращают команду, которую вызывающий код должен выполнить через execute().
 */
public class IoC {

    static BiFunction<String, Object[], Object> strategy =
            (d, args) -> {

                Object scope = InitCommand.currentScopes.get() != null ? InitCommand.currentScopes.get() : InitCommand.rootScope;
                DependencyResolver dependencyResolver = new DependencyResolver((HashMap<String, Function<Object[], Object>>) scope);

                return dependencyResolver.resolve(d, args);
            };

    public static <T> T resolve(String d, Object... args) {

        return (T) strategy.apply(d, args);

    }

}
