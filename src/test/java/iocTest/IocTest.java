package iocTest;

import iocModule2L9.CommandMove;
import iocModule2L9.Init;
import iocModule2L9.IoC;
import org.junit.Test;


public class IocTest {

    @Test
    public void testIoCResolveStrategy(){

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove");

        move.execute();

    }

    @Test(expected = Exception.class)
    public void testIoCResolveStrategyException() {

        Init init = new Init();

        init.init();

        CommandMove move = IoC.resolve("CommandMove1");

        move.execute();

    }

}
