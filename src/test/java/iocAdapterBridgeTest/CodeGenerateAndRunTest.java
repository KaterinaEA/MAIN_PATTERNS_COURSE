package iocAdapterBridgeTest;


import exceptionModule1L3.ICommand;
import iocAdapterBridgeModule2L12.*;
import iocModule2L1.IoC;
import iocModule2L1.Scope.InitCommand;
import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Vector;
import lspIspModule1L2.rotate.Angle;
import lspIspModule1L2.rotate.IRotable;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Function;


import static iocAdapterBridgeModule2L12.CodeGenerator.getClassName;
import static iocAdapterBridgeModule2L12.CodeGenerator.isInterfaceClass;


public class CodeGenerateAndRunTest {

    public CodeGenerateAndRunTest (){
        InitCommand initCommand = new InitCommand();
        initCommand.execute();

        Function<Object[], Object> functionVector        = (args) -> new Vector((int[]) args[0]);
        Function<Object[], Object> functionAngle         = (args) -> new Angle((int) args[0], (int) args[1]);

        register("Vector", functionVector);
        register("Angle", functionAngle);

        Function<Object[], Object> functionAdapter = (args) -> {

            // TODO переделать
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

        register("Adapter", functionAdapter);
    }

    private static void register(String key, Function<Object[], Object> strategy) {
        ICommand command = IoC.resolve("IoC.register", key, strategy);
        command.execute();
    }

    @Test
    public void whenStringIsCompiled_ThenCodeShouldExecute() throws IOException {

        Uobject uobject = new Spaceship();

        List<File> javaFiles = CodeGenerator.SearchInterface();

        for (File file : javaFiles) {
            if (isInterfaceClass(file)) {


                //System.out.println(getSourceCode(file));
                //InMemoryClass adapter = IoC.resolve("Adapter", file, uobject);

                //adapter.runCode();

            }

        }
    }

    @Test
    public void whenStringIsCompiled_ThenCodeShouldGetSetProperty() throws IOException {

        Spaceship spaceship = new Spaceship();

        int[] initVelocityMass = {0,0};
        Vector vector = IoC.resolve("Vector", initVelocityMass );
        int[] newVelocityMass = {1,1};
        Vector newVelocity = IoC.resolve("Vector", newVelocityMass );

        int direction = 1; int n = 10;
        Angle angle = IoC.resolve("Angle", direction, n );

        spaceship.setProperty("position", vector);
        spaceship.setProperty("angle", angle);

        Function<Object[], Object> functionMovableGetPosition   = (args) -> spaceship.getProperty("position");
        Function<Object[], Object> functionRotableGetAngle      = (args) -> spaceship.getProperty("angle");

        register("Movable.getPosition", functionMovableGetPosition);
        register("Rotable.getAngle", functionRotableGetAngle);

        // TODO понять можно ли такие ссыли на экземпляр spaceship в лямбде делать
        Function<Object[], Object> functionMovableSetPosition         = (args) -> {

            Vector currentVector = (Vector) spaceship.getProperty("position");
            Vector newVector = Vector.plus(currentVector, (Vector) args[1]);

            spaceship.setProperty("position", newVector);
            return null;
        };

        Function<Object[], Object> functionRotableSetAngle         = (args) -> {

            Angle currentAngle = (Angle) spaceship.getProperty("angle");
            Angle newAngle = Angle.plus(currentAngle, (Angle) args[1]);

            spaceship.setProperty("position", newAngle);
            return null;
        };

        register("Movable.getPosition", functionMovableGetPosition);
        register("Movable.setPosition", functionMovableSetPosition);

        register("Rotable.getAngle", functionRotableGetAngle);
        register("Rotable.setAngle", functionRotableSetAngle);

        List<File> javaFiles = CodeGenerator.SearchInterface();

        for (File file : javaFiles) {
            if (isInterfaceClass(file)) {

                // TODO переделать
                if (getClassName(file).equals("lspIspModule1L2.move.IMovable")) {

                    IMovable movableAdapter = IoC.resolve("Adapter", file, spaceship);
                    movableAdapter.setPosition(newVelocity);
                    Vector exceptedVector = movableAdapter.getPosition();
                    exceptedVector.equals(vector);

                } else if (getClassName(file).equals("lspIspModule1L2.rotate.IRotable")) {

                    IRotable rotableAdapter = IoC.resolve("Adapter", file, spaceship);
                    Angle exceptedAngle = rotableAdapter.getAngle();
                    exceptedAngle.equals(angle);

                }

            }

        }
    }

}
