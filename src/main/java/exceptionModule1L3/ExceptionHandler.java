package exceptionModule1L3;

import java.util.*;

public class ExceptionHandler {


    private static ExceptionHandler INSTANCE;

    private final Queue<ICommand> _queue;

    // Map<Key, Map<Key, Value>> map = new HashMap<>();
    private final Map<Class<? extends ICommand>, Map<Class<? extends Exception>, ICommand>> store;

    private ExceptionHandler(Queue<ICommand> queue) {

        store = new HashMap<>();
        _queue = queue;

    }

    public static ExceptionHandler getInstance(Queue<ICommand> q) {
        if(INSTANCE == null) {
            INSTANCE = new ExceptionHandler(q);
        }

        return INSTANCE;
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

        Map<Class<? extends Exception>, ICommand> map = new HashMap<>();

        map.put(e.getClass(), h);

        store.put(command.getClass(), map);

    }

}
