package iocAdapterBridgeTest;

import iocAdapterBridgeModule2L12.CodeGenerator;
import iocAdapterBridgeModule2L12.Uobject;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import lspIspModule1L2.move.IMovable;
import org.junit.Test;
import org.mockito.Mockito;

public class CodeGeneratorTest {

    public CodeGeneratorTest () {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();
    }

    @Test
    public void codeGeneratorTest() throws Exception {
        CodeGenerator.main();
    }

    @Test
    public void strategyAdapterTest() throws Exception {
        CodeGenerator.main();
        IMovable m          = Mockito.mock(IMovable.class);
        Uobject  obj        = Mockito.mock(Uobject.class);
        Object   adapter    = IoC.resolve("adapter", m.getClass(), obj);
    }

}
