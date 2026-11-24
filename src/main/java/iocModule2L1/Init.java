package iocModule2L1;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.Retry;
import iocModule2L1.Scope.InitCommand;

import java.util.*;
import java.util.function.Function;

public class Init {

    public void init() {

        new InitCommand().execute();

        Function<Object[], Object> supplierQueue      = (args) -> (Queue<ICommand>) new LinkedList<ICommand>();
        Function<Object[], Object> functionRetry      = (args) -> new Retry ((ICommand) args[0]);
        Function<Object[], Object> functionMove       = (args) -> new CommandMove ();
        Function<Object[], Object> biFunctionAddQueue = (args) -> new AddQueue ((Queue<ICommand>) args[0] , (ICommand) args[1]);

        register("CommandMove", functionMove);
        register("QueueCommand", supplierQueue);
        register("Retry", functionRetry);
        register("AddQueue", biFunctionAddQueue);

    }

    private static void register(String key, Function<Object[], Object> strategy) {
        ICommand command = IoC.resolve("IoC.register", key, strategy);
        command.execute();
    }
}
