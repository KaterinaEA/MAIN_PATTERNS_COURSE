package iocTest;

import iocModule2L1.CommandMove;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.Test;

import java.util.HashMap;
import java.util.function.Function;

public class ScopeTest {

    public ScopeTest() {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();

        HashMap<String, Function<Object[], Object>> iocScope = IoC.resolve("IoC.Scope.Create");

    }

    @Test
    public void testScope() {
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();
    }

}
