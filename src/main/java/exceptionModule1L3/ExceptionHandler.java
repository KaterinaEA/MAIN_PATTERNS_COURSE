package exceptionModule1L3;

import com.google.common.base.Function;
import com.google.common.collect.TreeBasedTable;
import com.google.common.base.Optional;
import lspIspModule1L2.move.Move;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

public class ExceptionHandler {

    private static Map<Class<? extends ICommand>, Map<Class<? extends Exception>, ICommand>> store;
    private static ExceptionHandler INSTANCE;


    private ExceptionHandler() {
        store = new HashMap<>();
    }

    public static ExceptionHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExceptionHandler();
        }

        return INSTANCE;
    }



    public static ICommand handle(ICommand command, Exception e) {


        System.out.println("start " + command.getClass().getName());

        try {

            ICommand handler = store.get(command.getClass()).get(e.getClass());

        } catch (Exception ex) {

            ICommand handler_default = new LogCommand(e);

            System.out.println("handler_default "+ handler_default.getClass().getName());

            return handler_default;
        }


        System.out.println("handler found");


        return store.getOrDefault(command.getClass(), Collections.emptyMap()).getOrDefault(e.getClass(), null);

    }

    public static void RegisterHandler (ICommand command, Exception e, ICommand h) {

        Map<Class<? extends Exception>, ICommand> map = new HashMap<>();

        map.put(e.getClass(), h);

        store.put(command.getClass(), map);

    }

}
