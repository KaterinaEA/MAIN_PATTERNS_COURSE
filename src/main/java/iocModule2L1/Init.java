package iocModule2L1;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.Retry;
import java.util.*;
import java.util.function.Function;

public class Init {

    public void init() {

        Function<Object[], Object> functionRegister = (args) -> {

            if (!(args[0] instanceof String)) {
                throw new IllegalArgumentException("First argument must be a string.");
            }

            // Создаем новый массив, исключая первый элемент
            Object[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            IoC.register((String) args[0], newArgs);

            return null;

        };

        Function<Object[], Object> supplierQueue        = (args) -> (Queue<ICommand>) new LinkedList<ICommand>();
        Function<Object[], Object> functionRetry        = (args) -> new Retry ((ICommand) args[0]);
        Function<Object[], Object> functionMove         = (args) -> new CommandMove ();
        Function<Object[], Object> biFunctionAddQueue   = (args) -> new AddQueue ((Queue<ICommand>) args[0] , (ICommand) args[1]);

        //IoC.register("IoC.registry", functionRegister);

        IoC.register("QueueCommand", supplierQueue);
        IoC.register("Retry", functionRetry);
        IoC.register("AddQueue", biFunctionAddQueue);
        IoC.register("CommandMove", functionMove);


/*        IoC.resolve("IoC.registry", "CommandMove", functionMove);
        IoC.resolve("IoC.registry","QueueCommand", supplierQueue);
        IoC.resolve("IoC.registry","Retry", functionRetry);
        IoC.resolve("IoC.registry","AddQueue", biFunctionAddQueue);*/

    }
}
