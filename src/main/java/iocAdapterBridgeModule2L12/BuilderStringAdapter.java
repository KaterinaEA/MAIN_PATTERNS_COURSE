package iocAdapterBridgeModule2L12;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BuilderStringAdapter {

    public static String getSourceCode(File file) throws ClassNotFoundException {
        String returnStr;
        String baseName = file.getName().substring(1, file.getName().lastIndexOf('.'));
        String packageName = "iocAdapterBridgeModule2L12";
        String className = CodeGenerator.getClassName(file);
        Class<?> sourceInterface = Class.forName(className);
        Method[] methods = sourceInterface.getMethods();

        StringBuilder code = new StringBuilder();


        code.append("package ").append(packageName).append(";\n\n");
        code.append("import iocModule2L1.IoC;\n");
        code.append(String.format("import %s;\n", className));

        for (Method method : methods) {

            Type returnType = method.getGenericReturnType();
            String returnTypeString = returnType.getTypeName();

            if (returnTypeString != "void") {
                code.append(String.format(" import %s;\n", returnTypeString));
            }

        }

        code.append(String.format("public class %sAdapter implements I%s {\n", baseName, baseName));
        code.append("private final Uobject _obj;\n");
        code.append(String.format("public %sAdapter (Uobject uObj) {_obj = uObj;}\n", baseName));

        // Перебираем каждый метод
        for (Method method : methods) {
            // Получаем тип возвращаемого значения
            Type returnType = method.getGenericReturnType();
            String returnTypeString = returnType.getTypeName();
            String commaIfParamNotNull = "";

            Integer idx = returnTypeString.lastIndexOf('.')+1;

            String returnTypeStringShort = returnTypeString.substring(idx);

            String methodSignature = method.getName();

            Class<?>[] parameterTypes = method.getParameterTypes();

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

            if (returnTypeString != "void") {
                returnStr = "return";
            } else{
                returnStr = "";
            }

            if (!paramListName.isEmpty()) {
                commaIfParamNotNull = " , ";
            }

            code.append(
                    String.format(" public %s %s(%s) {%s IoC.resolve(\"%s.%s\", _obj %s);}\n\n"
                            , returnTypeStringShort
                            , methodSignature
                            , String.join(",", paramList)
                            , returnStr
                            , baseName
                            , methodSignature
                            , commaIfParamNotNull+String.join(",",paramListName)
                    ));

        }

        return code.toString();
    }
}
