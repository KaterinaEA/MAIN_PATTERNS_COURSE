package iocModule2L9;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;

import java.util.LinkedList;
import java.util.Queue;

public class Init {

    public void init() {

        IoC.register( "CommandMove"
                , () -> (ICommand) new CommandMove()
        );

        IoC.register( "QueueCommand"
                , LinkedList::new
        );

/*        IoC.register( "AddQueue"
                , (Queue<ICommand> q, ICommand c) -> (ICommand) new AddQueue(q,c)
        );*/

    }
}
