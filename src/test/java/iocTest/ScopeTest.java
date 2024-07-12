package iocTest;

import iocModule2L1.CommandMove;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.Test;

import java.util.HashMap;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;

public class ScopeTest {

    public ScopeTest() {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();

        HashMap<String, Function<Object[], Object>> iocScope = IoC.resolve("IoC.Scope.Create");

        IoC.resolve("IoC.Scope.Current.Set", iocScope);

    }

    @Test
    public void testScope() {

        Function<Object[], Object> functionMove         = (args) -> new CommandMove ();
        IoC.resolve("IoC.register", "CommandMove", functionMove);
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();

        assertEquals("Вернул объект класса CommandMove", CommandMove.class, move.getClass());

    }

}
