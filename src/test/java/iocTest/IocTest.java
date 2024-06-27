package iocTest;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import iocModule2L1.CommandMove;
import iocModule2L1.Init;
import iocModule2L1.IoC;
import org.junit.Test;

import java.util.Queue;


public class IocTest {

    public IocTest(){

        Init init = new Init();
        init.init();

    }

    @Test
    public void testIoCResolveStrategy(){

        CommandMove move = IoC.resolve("CommandMove");
        move.execute();

    }

    @Test
    public void testIoCResolveStrategyWithArgs(){

        Queue<ICommand> q = IoC.resolve("QueueCommand");

        CommandMove move = IoC.resolve("CommandMove");

        AddQueue addQueue = IoC.resolve("AddQueue", q, move);

        addQueue.execute();

    }

    @Test(expected = Exception.class)
    public void testIoCResolveStrategyException() {

        CommandMove move = IoC.resolve("CommandMove1");

        move.execute();

    }

}
