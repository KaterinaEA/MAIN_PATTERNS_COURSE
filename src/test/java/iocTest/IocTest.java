package iocTest;

import exceptionModule1L3.AddQueue;
import exceptionModule1L3.ICommand;
import iocModule2L1.CommandMove;
import iocModule2L1.Init;
import iocModule2L1.IoC;
import org.junit.Test;
import iocModule2L1.*;

import java.util.Queue;


public class IocTest {

    public IocTest(){

        Init init = new Init();
        init.init();

    }

    @Test
    public void testIoCresolveStrategy(){

        CommandMove move = IoC.resolve_old("CommandMove");
        move.execute();

    }

    @Test
    public void testIoCresolveStrategyWithArgs(){

        Queue<ICommand> q = IoC.resolve_old("QueueCommand");

        CommandMove move = IoC.resolve_old("CommandMove");

        AddQueue addQueue = IoC.resolve_old("AddQueue", q, move);

        addQueue.execute();

    }

    @Test(expected = Exception.class)
    public void testIoCresolveStrategyException() {

        CommandMove move = IoC.resolve_old("CommandMove1");

        move.execute();

    }

}
