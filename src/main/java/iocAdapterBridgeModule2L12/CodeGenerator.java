package iocAdapterBridgeModule2L12;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Collections;

public class CodeGenerator {
    public static void main() throws Exception {
        File sourceDir = new File("src/main/java");
        List<File> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .forEach(javaFiles::add);
        }
        for (File file : javaFiles) {
            if (isInterfaceClass(file)) {
                System.out.println(file.getParentFile());
                generateAdapter(file);
            }
        }
    }

    public static boolean isInterfaceClass(File file) {

        Set<String> excludedFiles = new HashSet<>();
        excludedFiles.add("Init.java");
        excludedFiles.add("IoC.java");
        excludedFiles.add("InitCommand.java");
        excludedFiles.add("IDependencyResolver.java");
        excludedFiles.add("ICommand.java");
        excludedFiles.add("InMemoryClass.java");
        excludedFiles.add("InMemoryClassLoader.java");
        excludedFiles.add("InMemoryFileManager.java");

        String fileName = file.getName();

        if (fileName.startsWith("I") && !excludedFiles.contains(fileName)) {
            return true;
        }

        return false;
    }
    private static Logger getLogger(File file) throws ClassNotFoundException {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        return loggerContext.getLogger(CodeGenerator.getClassName(file));
    }
    public static String getClassName(File file) {

        // Путь к файлу класса
        String classFilePath = file.getPath();

        String classNameFull = classFilePath.substring(0, classFilePath.lastIndexOf('.'));

        // Находим индекс первого вхождения "\java"
        int index = classNameFull.indexOf("\\java");

        // Извлекаем подстроку после индекса "\java" до конца пути
        String className = classNameFull.substring(index+6);

        return className.replace("\\", ".");

    }

    public static List<File> SearchInterface () throws IOException {
        File sourceDir = new File("src/main/java");
        List<File> javaFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .map(Path::toFile)
                    .forEach(javaFiles::add);
        }
        return javaFiles;
    }


    public static <T> T typeOf(File file, Uobject uobject) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        String sourceCode = BuilderStringAdapter.getSourceCode(file);
        String qualifiedClassName = String.format("iocAdapterBridgeModule2L12.%sAdapter", file.getName().substring(1, file.getName().lastIndexOf('.')));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(qualifiedClassName, sourceCode));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics().forEach(d -> {
                try {
                    getLogger(file).error(String.valueOf(d));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(qualifiedClassName);

            //TODO добавить проверку на приведение типов
            return (T) clazz.getDeclaredConstructor(Uobject.class).newInstance(uobject);
        }
        return null;
    }

    private static void generateAdapter(File file) throws Exception {
        String returnStr;

        // Получаем имя исходного файла без расширения
        String baseName = file.getName().substring(1, file.getName().lastIndexOf('.'));

        // Получаем полное имя класса
        String className = getClassName(file);

        // Получаем класс интерфейса
        Class<?> sourceInterface = Class.forName(className);

        // Получаем список методов интерфейса
        Method[] methods = sourceInterface.getMethods();

        // Создаем новый файл адаптера
        File adapterFile = new File("src\\main\\java\\iocAdapterBridgeModule2L12", String.format( "%sAdapter.java", baseName));

        // Открываем файл для записи
        try (PrintWriter writer = new PrintWriter(adapterFile)) {
            // Пишем содержимое адаптера в файл
            writer.println("package iocAdapterBridgeModule2L12;");
            writer.println();
            writer.println(" import iocModule2L1.IoC;");
            writer.println();
            writer.println(String.format(" import %s;", className));
            writer.println();

            for (Method method : methods) {
                // Получаем тип возвращаемого значения
                Type returnType = method.getGenericReturnType();
                String returnTypeString = returnType.getTypeName();

                if (!returnTypeString.equals("void")) {
                    writer.println(String.format(" import %s;", returnTypeString));
                    writer.println();
                }

                }

            writer.println(String.format("public class %sAdapter implements I%s {", baseName, baseName));
            writer.println();
            writer.println("private final Uobject _obj;");
            writer.println();
            writer.println(String.format("public %sAdapter (Uobject uObj) {_obj = uObj;}", baseName));
            writer.println();

            // Перебираем каждый метод
            for (Method method : methods) {
                // Получаем тип возвращаемого значения
                Type returnType = method.getGenericReturnType();
                String returnTypeString = returnType.getTypeName();
                String commaIfParamNotNull = "";

                int idx = returnTypeString.lastIndexOf('.')+1;

                String returnTypeStringShort = returnTypeString.substring(idx);
                //System.out.println("Тип возвращаемого значения: " + returnType);

                // Получаем сигнатуру метода
                String methodSignature = method.getName();
                //System.out.println("Сигнатура метода: " + methodSignature);

                // Получаем параметры метода
                Class<?>[] parameterTypes = method.getParameterTypes();
                //System.out.println("Параметры метода: " + Arrays.toString(parameterTypes));
                List<String> paramList = new ArrayList<>();
                List<String> paramListName = new ArrayList<>();


                for (int i = 0; i < parameterTypes.length; i++) {
                    // Получаем информацию о параметре
                    Type parameterType = parameterTypes[i];
                    String parameterTypeString = parameterType.toString();
                    String parameterName = method.getParameters()[i].getName();

                    idx = parameterTypeString.lastIndexOf('.')+1;

                    String parameterTypeStringShort = parameterTypeString.substring(idx);

                    paramList.add(String.format(" %s %s ",parameterTypeStringShort, parameterName));
                    paramListName.add(parameterName);
                    //System.out.println("Тип параметра: " + parameterType + ", Наименование параметра: " + parameterName);
                }

                if (!returnTypeString.equals("void")) {
                    returnStr = "return";
                } else{
                    returnStr = "";
                }

                if (!paramListName.isEmpty()) {
                    commaIfParamNotNull = " , ";
                }

                writer.println(
                            String.format(" public %s %s(%s) {%s IoC.resolve(\"%s.%s\", _obj %s);}"
                                    , returnTypeStringShort
                                    , methodSignature
                                    , String.join(",", paramList)
                                    , returnStr
                                    , baseName
                                    , methodSignature
                                    , commaIfParamNotNull+String.join(",",paramListName)
                            ));
                writer.println();

            }
            writer.println("}");
        }
        // Выводим сообщение о том, что адаптер сгенерирован
        //System.out.println(String.format("Адаптер сгенерирован для %s", file.getName()));
    }


}
