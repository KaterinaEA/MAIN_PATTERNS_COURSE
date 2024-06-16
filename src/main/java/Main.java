import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import exceptionModule1L3.Retry;
import iocModule2L1.CommandMove;
import iocModule2L1.Init;
import iocModule2L1.IoC;

import java.util.Queue;


public class Main {
    public static void main(String[] args) {

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove");

        move.execute();

        Queue<ICommand> queueCommands = IoC.resolve("QueueCommand");

        Retry retry = IoC.resolve("Retry", move);

        retry.execute();

        AddQueue addQueue = IoC.resolve("AddQueue", queueCommands, move);

        addQueue.execute();

    }


}
