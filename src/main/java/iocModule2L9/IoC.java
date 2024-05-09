package iocModule2L9;

import exceptionModule1L3.ICommand;

import java.util.Map;
import java.util.function.Supplier;

public class IoC {

    private static Map<String, String, ICommand> store;

    public static <T> T resolve(String commandName,String dependency, Supplier<T> supplier) {
        // Здесь происходит логика разрешения зависимостей

        // При инициализации заводим зависимость IoC.Register (имя зависимости и стратегия) , возвращает объект ICommand и  метод execute регистрирует объект

        return supplier.get(); //supplier - Этот интерфейс используется в различных контекстах, где требуется предоставить значение или результат без параметров.
    }


}
