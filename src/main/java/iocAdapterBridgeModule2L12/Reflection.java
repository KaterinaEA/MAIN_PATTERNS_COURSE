package iocAdapterBridgeModule2L12;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Получаем класс интерфейса
        Class<?> imovableInterface = Class.forName("lspIspModule1L2.move.IMovable");

/*        // Создаем новый класс с именем MovableAdapter
        Class<?> movableAdapterClass = Class.forName("MovableAdapter");

        // Заполняем конструктор класса MovableAdapter параметрами
        Constructor<?> constructor = movableAdapterClass.getDeclaredConstructor(new Class<?>[]{imovableInterface});
        constructor.setAccessible(true);

        // Создаем экземпляр класса MovableAdapter
        Object instance = constructor.newInstance(imovableInterface);

        // Переопределяем методы интерфейса в новом классе
        Map<String, Method> methods = new HashMap<>();
        for (Method method : imovableInterface.getMethods()) {
            methods.put(method.getName(), method);
        }

        // Методы интерфейса
        methods.forEach((name, method) -> {
            try {
                method.invoke(instance, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });*/
    }
}
