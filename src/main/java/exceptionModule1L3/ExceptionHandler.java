package exceptionModule1L3;

import java.util.*;

public class ExceptionHandler {

    // Один обработчик на каждую очередь: разные очереди (например, в разных тестах)
    // не должны делить между собой ни очередь, ни зарегистрированные обработчики.
    private static final Map<Queue<ICommand>, ExceptionHandler> INSTANCES = new WeakHashMap<>();

    private final Queue<ICommand> _queue;

    // Map<Key, Map<Key, Value>> map = new HashMap<>();
    private final Map<Class<? extends ICommand>, Map<Class<? extends Exception>, ICommand>> store;

    private ExceptionHandler(Queue<ICommand> queue) {

        store = new HashMap<>();
        _queue = queue;

    }

    public static synchronized ExceptionHandler getInstance(Queue<ICommand> q) {
        return INSTANCES.computeIfAbsent(q, ExceptionHandler::new);
    }

    public void handle(ICommand command, Exception e ) {


        System.out.println("start " + command.getClass().getName());

        try {

             ICommand handler = store.get(command.getClass()).get(e.getClass());

            _queue.add(handler);

        } catch (Exception ex) {

            ICommand handler_default = new LogCommand(e);

            _queue.add(handler_default);

            System.out.println("handler_default " + handler_default.getClass().getName());

        }

        System.out.println("handler found");

    }

    public void RegisterHandler (ICommand command, Exception e, ICommand h) {

        // Дополняем карту обработчиков для класса команды, а не затираем её:
        // иначе регистрация второго исключения для той же команды стирала бы первую.
        store.computeIfAbsent(command.getClass(), k -> new HashMap<>())
                .put(e.getClass(), h);

    }

}
