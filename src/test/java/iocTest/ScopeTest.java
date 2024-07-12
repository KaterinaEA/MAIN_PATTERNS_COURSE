package iocTest;

import iocModule2L1.CommandMove;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.Test;

import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;

public class ScopeTest {

    public ScopeTest() {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();

        IoC.resolve("IoC.Scope.New", "ScopeId_1");

        IoC.resolve("IoC.Scope.Current.Set", "ScopeId_2");

    }

    @Test
    public void testRegister() {

        Function<Object[], Object> functionMove         = (args) -> new CommandMove ();
        IoC.resolve("IoC.register", "CommandMove", functionMove);
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();

        assertEquals("Вернул объект класса CommandMove", CommandMove.class, move.getClass());

    }

    @Test(expected = Exception.class)
    public void testGetExceptionWhenNotDependency() {
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();
    }

}
