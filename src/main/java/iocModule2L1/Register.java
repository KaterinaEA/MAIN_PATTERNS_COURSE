package iocModule2L1;

import exceptionModule1L3.ICommand;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@RequiredArgsConstructor
public class Register {

    /**
     * Класс Register реализует механизм регистрации зависимостей.
     * <p>
     * args — массив объектов, которые могут быть использованы для разрешения зависимости.
     *        строка, которая используется для идентификации объекта
     */

    static Object[] _args;

    public Register (Object[] args) {

        _args = args;

    }

    public static void execute() {

        if (!(_args[0] instanceof String)) {
            throw new IllegalArgumentException("First argument must be a string.");
        }

        // Создаем новый массив, исключая первый элемент
        Object[] newArgs = Arrays.copyOfRange(_args, 1, _args.length);
        IoC.register((String) _args[0], newArgs);

    }
}
