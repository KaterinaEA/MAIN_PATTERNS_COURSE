package iocAdapterBridgeTest;

import exceptionModule1L3.ICommand;
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
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CodeGeneratorTest {

    public CodeGeneratorTest () {

        InitCommand initCommand = new InitCommand();

        initCommand.execute();

        // Регистрируем стратегию "Adapter" сами, а не полагаемся на то, что её
        // успеет зарегистрировать конструктор другого тестового класса —
        // у IoC общий rootScope, и порядок выполнения тестов JUnit не гарантирован.
        Function<Object[], Object> functionAdapter = (args) -> {
            try {
                return CodeGenerator.typeOf((File) args[0], (Uobject) args[1]);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };

        ICommand registerAdapter = IoC.resolve("IoC.register", "Adapter", functionAdapter);
        registerAdapter.execute();
    }

    @Test
    public void codeGeneratorTest() throws Exception {

        CodeGenerator.main();

        File generatedAdapter = new File("src/main/java/iocAdapterBridgeModule2L12/MovableAdapter.java");

        assertTrue("Генератор должен создать файл адаптера для IMovable", generatedAdapter.exists());
    }

    @Test
    public void strategyAdapterTest() throws Exception {

        CodeGenerator.main();

        File interfaceFile = findInterfaceFile("IMovable.java");
        Uobject obj = Mockito.mock(Uobject.class);

        Object adapter = IoC.resolve("Adapter", interfaceFile, obj);

        assertNotNull("Стратегия \"Adapter\" должна вернуть созданный адаптер", adapter);
        assertTrue("Сгенерированный адаптер должен реализовывать IMovable", adapter instanceof IMovable);
    }

    @Test
    public void builderStringAdapterTest() throws ClassNotFoundException, IOException {

        File interfaceFile = findInterfaceFile("IMovable.java");

        String sourceCode = BuilderStringAdapter.getSourceCode(interfaceFile);

        assertTrue("Сгенерированный исходник должен объявлять класс адаптера",
                sourceCode.contains("class MovableAdapter"));
        assertTrue("Сгенерированный адаптер должен реализовывать IMovable",
                sourceCode.contains("implements IMovable"));
    }

    private static File findInterfaceFile(String fileName) throws IOException {
        File sourceDir = new File("src/main/java");
        List<File> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .forEach(javaFiles::add);
        }
        for (File file : javaFiles) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        fail("Файл " + fileName + " не найден в src/main/java");
        return null;
    }

}
