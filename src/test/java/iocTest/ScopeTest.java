package iocTest;

import exceptionModule1L3.ICommand;
import iocModule2L1.CommandMove;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;

public class ScopeTest {

    public ScopeTest() {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();

        ICommand newScope = IoC.resolve("IoC.Scope.New", "ScopeIdGame1");
        newScope.execute();

        ICommand setScope = IoC.resolve("IoC.Scope.Current.Set", "ScopeIdGame1");
        setScope.execute();

    }

    @After
    public void clearScope() {
        ICommand clearScope = IoC.resolve("IoC.Scope.Current.Clear");
        clearScope.execute();
    }

    @Test
    public void testRegister() {

        Function<Object[], Object> functionMove = (args) -> new CommandMove ();
        ICommand registerCommand = IoC.resolve("IoC.register", "CommandMove", functionMove);
        registerCommand.execute();

        CommandMove move = IoC.resolve("CommandMove");
        move.execute();

        HashMap<String, Function<Object[], Object>> currentScope1 = IoC.resolve("IoC.Scope.Current");
        HashMap<String, Function<Object[], Object>> currentScope2 = InitCommand.dictionaryScope.get("ScopeIdGame1");

        assertEquals("Проверка, что текущий Scope совпадает с заданным", currentScope1, currentScope2);

        assertEquals("Вернули объект класса CommandMove", CommandMove.class, move.getClass());

    }

    @Test(expected = Exception.class)
    public void testGetExceptionWhenNotDependency() {
        // Ключ заведомо нигде не регистрируется — в отличие от "CommandMove",
        // который могут зарегистрировать другие тестовые классы в общем rootScope
        // в рамках того же прогона JVM, из-за чего проверка отсутствия зависимости
        // стала бы зависеть от порядка выполнения тестов.
        IoC.resolve("ScopeTest.NonExistentDependency");
    }



}
