package iocModule2L1;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.Retry;

import java.util.LinkedList;
import java.util.Queue;

public class Init {

    public void init() {

        IoC.register( "QueueCommand"
                , () -> new LinkedList()
        );

        IoC.register( "CommandMove"
                , () -> (ICommand) new CommandMove()
        );

        //IoC.register("Retry", (ICommand c) -> new Retry(c));


        //IoC.register("AddQueue", (Queue<ICommand> q, ICommand c) -> new AddQueue(q, c));
    }
}
