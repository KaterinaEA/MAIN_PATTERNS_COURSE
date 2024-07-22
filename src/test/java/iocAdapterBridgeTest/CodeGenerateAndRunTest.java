package iocAdapterBridgeTest;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import iocAdapterBridgeModule2L12.BuilderStringAdapter;
import iocAdapterBridgeModule2L12.InMemoryClass;
import iocAdapterBridgeModule2L12.InMemoryFileManager;
import iocAdapterBridgeModule2L12.JavaSourceFromString;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class CodeGenerateAndRunTest {

    private Logger getLogger() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        return loggerContext.getLogger(this.getClass().getName());
    }

    @Test
    public void whenStringIsCompiled_ThenCodeShouldExecute() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        File sourceDir = new File("src/main/java");
        List<File> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .forEach(javaFiles::add);
        }
        String sourceCode = null;
        for (File file : javaFiles) {
            if (file.getName().equals("IMovable.java")) {
                sourceCode = BuilderStringAdapter.getSourceCode(file);
                //System.out.println(BuilderStringAdapter.getSourceCode(file));
            }
        }

        String qualifiedClassName = "iocAdapterBridgeModule2L12.MovableAdapter";

        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(qualifiedClassName, sourceCode));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics().forEach(d -> getLogger().error(String.valueOf(d)));
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(qualifiedClassName);
            InMemoryClass instanceOfClass = (InMemoryClass) clazz.newInstance();

            //Assertions.assertInstanceOf(InMemoryClass.class, instanceOfClass);

            instanceOfClass.runCode();
        }
    }

}
