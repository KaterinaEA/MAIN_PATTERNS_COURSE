package iocTest;

import exceptionModule1L3.ICommand;
import iocModule2L1.CommandMove;
import iocModule2L1.Init;
import iocModule2L1.IoC;
import org.junit.Test;

import java.util.Queue;


public class IocTest {

    @Test
    public void testIoCResolveStrategy(){

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove");

        move.execute();

    }

    @Test
    public void testIoCResolveStrategyWithArgs(){

        Init init = new Init();

        init.init();

        Queue<ICommand> q = IoC.resolve("QueueCommand");

        CommandMove move = IoC.resolve("CommandMove");

        //AddQueue addQueue = IoC.resolve("AddQueue", q, move);

        //addQueue.execute();

    }

    @Test(expected = Exception.class)
    public void testIoCResolveStrategyException() {

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove1");

        move.execute();

    }

}
