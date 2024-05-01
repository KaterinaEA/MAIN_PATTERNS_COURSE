package exceptionModule1L3;

import com.google.common.base.Function;
import com.google.common.collect.TreeBasedTable;
import com.google.common.base.Optional;
import lspIspModule1L2.move.Move;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class ExceptionHandler {

/*    private IDict<
            Type,
            IDict<
                    Type,
                    Func<ICommand, Exception, ICommand>
                    >> store;

    public static ICommand Handle(ICommand c, Exception e){
        Type ct = c.GetType();
        Type et = e.GetType();

        return store[ct][et](c, e); // GetValueOrDefault(key, value)
    }*/

//    private static Map<Class<? extends ICommand>, Map<Class<? extends Exception>, Function<ICommand, ICommand>>> store;

//    private IDict<Type, IDict<Type, Func<ICommand, Exception, ICommand>>> store;

    private Map<Class<? extends ICommand>, Map<Class<? extends Exception>, ICommand>> store;

//    public static ICommand handle(ICommand c, Exception e) {
//        Class<? extends ICommand> ct = c.getClass();
//        Class<? extends Exception> et = e.getClass();
//
//        return store.getOrDefault(ct, new HashMap<>()).getOrDefault(et, (command, ex) -> null).apply(c, e);
//    }

    public ExceptionHandler() {
        store = new HashMap<>();

        Map<Class<? extends Exception>, ICommand> map = new HashMap<>();

        map.put(NullPointerException.class, new ICommand() {
            @Override
            public void execute() {
                System.out.println("asdasdasdasdad");
            }
        });

        store.put(Move.class, map);

    }

    public ICommand handle(ICommand command, Exception e) {
        return store.get(command.getClass()).get(e.getClass());
    }

//    public static ICommand handle(ICommand command, Exception exception) {
//        Class<? extends ICommand> commandClass = command.getClass();
//        Class<? extends Exception> exceptionClass = exception.getClass();
//
//        ExceptionHandler handler = handlers.get(commandClass).get(exceptionClass);
//        if (handler == null) {
//            throw new IllegalArgumentException("Handler not found for command type " + commandClass.getName() + " and exception type " + exceptionClass.getName());
//        }
//        return handler.handle(command, exception);
//    }

    //public static void RegisterHandler(Type ct, Type et, Func<ICommand, Exception, ICommand> h) {
    //    store[ct][et] = h;
    //}

}
