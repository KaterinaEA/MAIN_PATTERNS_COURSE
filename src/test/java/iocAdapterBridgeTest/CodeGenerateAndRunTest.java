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
import java.util.List;
import java.util.function.Function;


import static iocAdapterBridgeModule2L12.CodeGenerator.getClassName;
import static iocAdapterBridgeModule2L12.CodeGenerator.isInterfaceClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CodeGenerateAndRunTest {

    public CodeGenerateAndRunTest (){
        InitCommand initCommand = new InitCommand();
        initCommand.execute();

        Function<Object[], Object> functionVector        = (args) -> new Vector((int[]) args[0]);
        Function<Object[], Object> functionAngle         = (args) -> new Angle((int) args[0], (int) args[1]);

        register("Vector", functionVector);
        register("Angle", functionAngle);

        Function<Object[], Object> functionAdapter = (args) -> {
            try {
                return CodeGenerator.typeOf((File) args[0], (Uobject) args[1]);
            } catch (ReflectiveOperationException e) {
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

        Spaceship spaceship = new Spaceship();

        int[] positionMass = {5, 7};
        Vector position = IoC.resolve("Vector", positionMass);
        spaceship.setProperty("position", position);

        Function<Object[], Object> functionMovableGetPosition = (args) -> spaceship.getProperty("position");
        register("Movable.getPosition", functionMovableGetPosition);

        List<File> javaFiles = CodeGenerator.SearchInterface();

        boolean movableAdapterChecked = false;

        for (File file : javaFiles) {
            if (isInterfaceClass(file) && getClassName(file).equals("lspIspModule1L2.move.IMovable")) {

                IMovable movableAdapter = IoC.resolve("Adapter", file, spaceship);

                assertNotNull("Сгенерированный адаптер должен быть создан", movableAdapter);
                assertArrayEquals("Сгенерированный адаптер должен вернуть значение через IoC-стратегию",
                        position.getM(), movableAdapter.getPosition().getM());

                movableAdapterChecked = true;
            }
        }

        assertEquals("Интерфейс IMovable должен быть найден и адаптер проверен", true, movableAdapterChecked);
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

        Function<Object[], Object> functionMovableSetPosition         = (args) -> {

            Vector currentVector = (Vector) spaceship.getProperty("position");
            Vector newVector = Vector.plus(currentVector, (Vector) args[1]);

            spaceship.setProperty("position", newVector);
            return null;
        };

        Function<Object[], Object> functionRotableSetAngle         = (args) -> {

            Angle currentAngle = (Angle) spaceship.getProperty("angle");
            Angle newAngle = Angle.plus(currentAngle, (Angle) args[1]);

            spaceship.setProperty("angle", newAngle);
            return null;
        };

        register("Movable.getPosition", functionMovableGetPosition);
        register("Movable.setPosition", functionMovableSetPosition);

        register("Rotable.getAngle", functionRotableGetAngle);
        register("Rotable.setAngle", functionRotableSetAngle);

        List<File> javaFiles = CodeGenerator.SearchInterface();

        Vector expectedPositionAfterMove = Vector.plus(vector, newVelocity);
        Angle rotationDelta = IoC.resolve("Angle", 1, 5);
        Angle expectedAngleAfterRotate = Angle.plus(angle, rotationDelta);

        boolean movableAdapterChecked = false;
        boolean rotableAdapterChecked = false;

        for (File file : javaFiles) {
            if (isInterfaceClass(file)) {

                if (getClassName(file).equals("lspIspModule1L2.move.IMovable")) {

                    IMovable movableAdapter = IoC.resolve("Adapter", file, spaceship);
                    movableAdapter.setPosition(newVelocity);
                    Vector actualPosition = movableAdapter.getPosition();

                    assertArrayEquals("Положение после setPosition должно смещаться на величину скорости",
                            expectedPositionAfterMove.getM(), actualPosition.getM());

                    movableAdapterChecked = true;

                } else if (getClassName(file).equals("lspIspModule1L2.rotate.IRotable")) {

                    IRotable rotableAdapter = IoC.resolve("Adapter", file, spaceship);
                    rotableAdapter.setAngle(rotationDelta);
                    Angle actualAngle = rotableAdapter.getAngle();

                    assertEquals("Направление угла после поворота должно обновиться",
                            expectedAngleAfterRotate.get_direction(), actualAngle.get_direction());
                    assertEquals("Величина угла после поворота должна обновиться",
                            expectedAngleAfterRotate.get_n(), actualAngle.get_n());

                    rotableAdapterChecked = true;

                }

            }

        }

        assertEquals("Интерфейс IMovable должен быть найден и адаптер проверен", true, movableAdapterChecked);
        assertEquals("Интерфейс IRotable должен быть найден и адаптер проверен", true, rotableAdapterChecked);
    }

}
