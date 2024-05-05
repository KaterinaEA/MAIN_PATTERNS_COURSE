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

    private ExceptionHandler() {

    }

    public static ICommand handle(ICommand command, Exception e) {
        return store.getOrDefault(command.getClass(), Collections.emptyMap()).getOrDefault(e.getClass(), null);
        //return store.get(command.getClass()).get(e.getClass());
    }

    public static void RegisterHandler (ICommand command, Exception e, ICommand h) {

        store = new HashMap<>();

        Map<Class<? extends Exception>, ICommand> map = new HashMap<>();

        map.put(e.getClass(), h);

        store.put(command.getClass(), map);

    }

}
