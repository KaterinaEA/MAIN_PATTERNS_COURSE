package lsp_isp_test;

import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class MoveTest {

    @Test
    public void testMove() {

        // Создаём mock-объект для интерфейса IMovable
        IMovable m = Mockito.mock(IMovable.class);

        int[] start_position_mass = {1, 2, 3};
        int[] start_velocity_mass = {1, 2, 3};

        Vector positionStart = new Vector(start_position_mass);
        Vector velocityStart = new Vector(start_velocity_mass);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(positionStart);
        when(m.getVelocity()).thenReturn(velocityStart);

        ArgumentCaptor<Vector> argumentCaptor = ArgumentCaptor.forClass(Vector.class);
        doNothing().when(m).setPosition(argumentCaptor.capture());

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Перемещаем объект
        move.execute();

        int[] actual_position_mass = {2, 4, 6};

        Vector newPosition = argumentCaptor.getValue();
        Vector actualPosition = new Vector(actual_position_mass);

        newPosition.equals(actualPosition);

        //verify(actualPosition).equals(Mockito.refEq(newPosition));

        //assertEquals(actualPosition, newPosition);

        // Дополнительная проверка координат x и y

        //assertEquals(5, newPosition.getX());
        //assertEquals(8, newPosition.getY());

    }

    @Test(expected = Exception.class)
    public void testGetExceptionPosition() {
        IMovable m = Mockito.mock(IMovable.class);

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Передаем пустой вектор
        Mockito.when(m.getPosition()).thenReturn(null);

        move.execute();

    }

    @Test(expected = Exception.class)
    public void testGetExceptionVelocity() {
        IMovable m = Mockito.mock(IMovable.class);

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Передаем пустой вектор
        Mockito.when(m.getVelocity()).thenReturn(null);

        move.execute();

    }

    @Test(expected = Exception.class)
    public void testExceptionPositionMove() {

        // Создаём mock-объект для интерфейса IMovable
        IMovable m = Mockito.mock(IMovable.class);

        int[] actual_velocity_mass = {2, 4, 6};

        Vector velocityStart = new Vector(actual_velocity_mass);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(null);
        when(m.getVelocity()).thenReturn(velocityStart);

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Перемещаем объект
        move.execute();

    }

    @Test(expected = Exception.class)
    public void testExceptionVelocityMove() {

        // Создаём mock-объект для интерфейса IMovable
        IMovable m = Mockito.mock(IMovable.class);

        int[] actual_velocity_mass = {2, 4, 6};
        Vector start = new Vector(actual_velocity_mass);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(start);
        when(m.getVelocity()).thenReturn(null);

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Перемещаем объект
        move.execute();

    }

}
