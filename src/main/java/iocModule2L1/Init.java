package iocModule2L1;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.Retry;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Init {

    public void init() {

        Supplier<Object> supplierQueue = () -> (Queue<ICommand>) new LinkedList<ICommand>();
        Supplier<Object> functionMove  = CommandMove::new;
        Function<ICommand, ICommand> functionRetry  = Retry::new;
        BiFunction<Queue<ICommand>, ICommand, ICommand> biFunctionAddQueue = AddQueue::new;

        IoC.register("QueueCommand", supplierQueue);
        IoC.register("Retry", functionRetry);
        IoC.register("AddQueue", biFunctionAddQueue);
        IoC.register("CommandMove", functionMove);

    }
}
