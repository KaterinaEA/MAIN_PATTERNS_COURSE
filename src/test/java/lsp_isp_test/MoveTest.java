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

        Vector positionStart = new Vector(12, 5);
        Vector velocityStart = new Vector(-7, 3);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(positionStart);
        when(m.getVelocity()).thenReturn(velocityStart);

        ArgumentCaptor<Vector> argumentCaptor = ArgumentCaptor.forClass(Vector.class);
        doNothing().when(m).setPosition(argumentCaptor.capture());

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Перемещаем объект
        move.execute();

        Vector newPosition = argumentCaptor.getValue();
        Vector actualPosition = new Vector(5,8);

        //assertEquals(actualPosition, newPosition);

        // Дополнительная проверка координат x и y

        assertEquals(5, newPosition.getX());
        assertEquals(8, newPosition.getY());

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

        Vector velocityStart = new Vector(-7, 3);

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

        Vector start = new Vector(-7, 3);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(start);
        when(m.getVelocity()).thenReturn(null);

        // Создаём экземпляр класса Move
        Move move = new Move(m);

        // Перемещаем объект
        move.execute();

    }

    @Test
    public void testGetPosition() {
        IMovable move = Mockito.mock(IMovable.class);

        // Given
        Vector expectedVector = new Vector(1, 0);
        Mockito.when(move.getPosition()).thenReturn(expectedVector);

        // When
        Vector actualVector = move.getPosition();

        // Then
        assertEquals(expectedVector, actualVector);
    }

    @Test
    public void testGetVelocity() {

        IMovable move = Mockito.mock(IMovable.class);

        // Given
        Vector expectedVelocity = new Vector(0, 1);
        Mockito.when(move.getVelocity()).thenReturn(expectedVelocity);

        // When
        Vector actualVelocity = move.getVelocity();

        // Then
        assertEquals(expectedVelocity, actualVelocity);
    }

    @Test
    public void testSetPosition() {

        IMovable move = Mockito.mock(IMovable.class);

        // Given
        Vector expectedPosition = new Vector(5, 8);
        // When
        move.setPosition(expectedPosition);

        // Then
        Mockito.verify(move).setPosition(expectedPosition);
    }

}
