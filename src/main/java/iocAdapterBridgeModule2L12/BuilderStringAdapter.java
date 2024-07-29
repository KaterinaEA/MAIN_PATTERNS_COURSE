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
        code.append("import iocModule2L1.IoC;\n\n");
        code.append(String.format("import %s;\n\n", className));

        for (Method method : methods) {

            Type returnType = method.getGenericReturnType();
            String returnTypeString = returnType.getTypeName();

            if (!returnTypeString.equals("void")) {
                code.append(String.format("import %s;\n\n", returnTypeString));
            }


        }

        code.append(String.format("public class %sAdapter implements I%s, InMemoryClass {\n\n", baseName, baseName));
        code.append("public final Uobject _obj;\n\n");
        code.append(String.format("public %sAdapter (Uobject uObj) {_obj = uObj;}\n\n", baseName));

        // Перебираем каждый метод
        for (Method method : methods) {
            // Получаем тип возвращаемого значения
            Type returnType = method.getGenericReturnType();
            String returnTypeString = returnType.getTypeName();
            String commaIfParamNotNull = "";

            int idx = returnTypeString.lastIndexOf('.')+1;

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

            if (!returnTypeString.equals("void")) {
                returnStr = "return";
            } else{
                returnStr = "";
            }

            if (!paramListName.isEmpty()) {
                commaIfParamNotNull = " , ";
            }

            code.append(
                    String.format(" public %s %s(%s) {%s IoC.resolve(\"%s.%s\", _obj %s);}\n\n"
                    //String.format(" public %s %s(%s) {%s IoC.resolve(\"%s.%s\");}\n\n"
                            , returnTypeStringShort
                            , methodSignature
                            , String.join(",", paramList)
                            , returnStr
                            , baseName
                            , methodSignature
                            , commaIfParamNotNull+String.join(",",paramListName)
                    ));

        }

        code.append("   @Override\n");
        code.append("     public void runCode() {\n");
        code.append( String.format("        System.out.println(\" %sAdapter code is running...\");\n", className));
        code.append("    }\n");
        code.append("}\n" );

        return code.toString();
    }
}
