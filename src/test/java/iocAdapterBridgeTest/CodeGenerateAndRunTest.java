package iocAdapterBridgeTest;


import iocAdapterBridgeModule2L12.*;
import iocModule2L1.CommandMove;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;


import static iocAdapterBridgeModule2L12.CodeGenerator.isInterfaceClass;


public class CodeGenerateAndRunTest {

    public CodeGenerateAndRunTest (){
        InitCommand initCommand = new InitCommand();
        initCommand.execute();

        Function<Object[], Object> functionMovableGetPosition         = (args) -> new CommandMove();
        IoC.resolve("IoC.register", "Movable.getPosition", functionMovableGetPosition);

        Function<Object[], Object> functionAdapter = (args) -> {

            try {
                return CodeGenerator.typeOf((File) args[0], (Uobject) args[1]);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        };

        IoC.resolve("IoC.register", "Adapter", functionAdapter);
    }

    @Test
    public void whenStringIsCompiled_ThenCodeShouldExecute() throws IOException {

        Uobject uobject = Mockito.mock(Uobject.class);

        IoC.resolve("Movable.getPosition", uobject );

        List<File> javaFiles = CodeGenerator.SearchInterface();

        for (File file : javaFiles) {
            if (isInterfaceClass(file)) {

                InMemoryClass adapter = IoC.resolve("Adapter", file, uobject);

                adapter.runCode();
            }

        }
    }

}
