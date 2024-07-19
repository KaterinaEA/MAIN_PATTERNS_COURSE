package iocAdapterBridgeModule2L12;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        excludedFiles.add("InMemoryClass");

        String fileName = file.getName();

        if (fileName.startsWith("I") && !excludedFiles.contains(fileName)) {
            return true;
        }

        return false;
    }

    public static String getClassName(File file) throws ClassNotFoundException {

        // Путь к файлу класса
        String classFilePath = file.getPath();

        String classNameFull = classFilePath.substring(0, classFilePath.lastIndexOf('.'));

        // Находим индекс первого вхождения "\java"
        int index = classNameFull.indexOf("\\java");

        // Извлекаем подстроку после индекса "\java" до конца пути
        String className = classNameFull.substring(index+6);

        return className.replace("\\", ".");

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
        // Создаем пустой файл
        adapterFile.createNewFile();

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
        System.out.println(String.format("Адаптер сгенерирован для %s", file.getName()));
    }


}
