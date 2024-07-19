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

        IoC.resolve("IoC.Scope.New", "ScopeIdGame1");

        IoC.resolve("IoC.Scope.Current.Set", "ScopeIdGame1");

    }

    @Test
    public void testRegister() {

        Function<Object[], Object> functionMove         = (args) -> new CommandMove ();
        IoC.resolve("IoC.register", "CommandMove", functionMove);
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();

        HashMap<String, Function<Object[], Object>> currentScope1 = IoC.resolve("IoC.Scope.Current");
        HashMap<String, Function<Object[], Object>> currentScope2 = InitCommand.dictionaryScope.get("ScopeIdGame1");

        assertEquals("Проверка, что текущий Scope совпадает с заданным", currentScope1, currentScope2);

        assertEquals("Вернули объект класса CommandMove", CommandMove.class, move.getClass());

    }

    @Test(expected = Exception.class)
    public void testGetExceptionWhenNotDependency() {
        CommandMove move = IoC.resolve("CommandMove");
        move.execute();
    }



}
