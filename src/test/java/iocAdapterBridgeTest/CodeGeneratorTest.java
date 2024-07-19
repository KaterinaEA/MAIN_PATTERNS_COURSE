package iocAdapterBridgeTest;

import iocAdapterBridgeModule2L12.BuilderStringAdapter;
import iocAdapterBridgeModule2L12.CodeGenerator;
import iocAdapterBridgeModule2L12.Uobject;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import lspIspModule1L2.move.IMovable;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @Test
    public void builderStringAdapterTest() throws ClassNotFoundException, IOException {
        File sourceDir = new File("src/main/java");
        List<File> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .forEach(javaFiles::add);
        }
        for (File file : javaFiles) {
            if (file.getName().equals("IMovable.java")) {
                System.out.println(file.getParentFile());
                System.out.println(BuilderStringAdapter.getSourceCode(file));
            }
        }
    }

}
