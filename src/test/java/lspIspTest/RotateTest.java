package lspIspTest;

import lspIspModule1L2.rotate.Angle;
import lspIspModule1L2.rotate.IRotable;
import lspIspModule1L2.rotate.Rotate;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class RotateTest {

    @Test
    public void testRotate() {
        IRotable r = Mockito.mock(IRotable.class);

        Angle directionStart = new Angle(1, 30);
        Angle n = new Angle(1, 30);

        // Настраиваем mock-объект
        when(r.getAngle()).thenReturn(directionStart);
        when(r.getAngularVelocity()).thenReturn(n);

        ArgumentCaptor<Angle> argumentCaptor = ArgumentCaptor.forClass(Angle.class);
        doNothing().when(r).setAngle(argumentCaptor.capture());

        // Создаём экземпляр класса Move
        Rotate rotate = new Rotate(r);

        // Перемещаем объект
        rotate.execute();

        Angle newAngle = argumentCaptor.getValue();
        Angle actualAngle = new Angle(2,60);

        //assertEquals(actualPosition, newPosition);

        // Дополнительная проверка координат x и y

        assertEquals(2, newAngle.get_direction());
        assertEquals(60, newAngle.get_n());

    }

    @Test(expected = Exception.class)
    public void testGetExceptionAngle() {
        IRotable r = Mockito.mock(IRotable.class);

        // Создаём экземпляр класса Move
        Rotate rotate = new Rotate(r);

        // Передаем пустой угол
        Mockito.when(r.getAngle()).thenReturn(null);

        rotate.execute();

    }

    @Test(expected = Exception.class)
    public void testGetExceptionAngularVelocity() {
        IRotable r = Mockito.mock(IRotable.class);

        // Создаём экземпляр класса Move
        Rotate rotate = new Rotate(r);

        // Передаем пустой угол
        Mockito.when(r.getAngularVelocity()).thenReturn(null);

        rotate.execute();

    }

    @Test(expected = Exception.class)
    public void testSetExceptionAngle() {

        // Создаём mock-объект для интерфейса IMovable
        IRotable r = Mockito.mock(IRotable.class);

        Rotate rotate = new Rotate(r);

        Angle start = new Angle(1, 30);

        // Настраиваем mock-объект
        when(r.getAngle()).thenReturn(null);
        when(r.getAngularVelocity()).thenReturn(start);

        // Перемещаем объект
        rotate.execute();

    }

    @Test(expected = Exception.class)
    public void testSetExceptionVelocity() {

        // Создаём mock-объект для интерфейса IMovable
        IRotable r = Mockito.mock(IRotable.class);

        Rotate rotate = new Rotate(r);

        Angle start = new Angle(1, 30);

        // Настраиваем mock-объект
        when(r.getAngle()).thenReturn(start);
        when(r.getAngularVelocity()).thenReturn(null);

        // Перемещаем объект
        rotate.execute();

    }


    @Test
    public void testGetAngle() {
        IRotable rotable = Mockito.mock(IRotable.class);

        // Given
        Angle expectedAngle = new Angle(0, 0);
        Mockito.when(rotable.getAngle()).thenReturn(expectedAngle);

        // When
        Angle actualAngle = rotable.getAngle();

        // Then
        assertEquals(expectedAngle, actualAngle);
    }

    @Test
    public void testSetAngle() {

        IRotable rotable = Mockito.mock(IRotable.class);

        // Given
        Angle newAngle = new Angle(0, 0);

        // When
        rotable.setAngle(newAngle);

        // Then
        Mockito.verify(rotable).setAngle(newAngle);
    }

    @Test
    public void testGetAngularVelocity() {

        IRotable rotable = Mockito.mock(IRotable.class);

        // Given
        Angle expectedAngularVelocity = new Angle(0, 0);
        Mockito.when(rotable.getAngularVelocity()).thenReturn(expectedAngularVelocity);

        // When
        Angle actualAngularVelocity = rotable.getAngularVelocity();

        // Then
        assertEquals(expectedAngularVelocity, actualAngularVelocity);
    }

}
