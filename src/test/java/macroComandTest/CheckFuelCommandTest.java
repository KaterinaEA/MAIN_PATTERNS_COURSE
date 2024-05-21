package macroComandTest;

import MacroCommandModule1L8.CheckFuelCommand;
import MacroCommandModule1L8.MoveObject;
import lspIspModule1L2.move.IMovable;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class CheckFuelCommandTest {

    @Test
    public void testPositiveCheckFuel() {

        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = new MoveObject();

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = 100.00;
        mo.minFuelLevel = 10.00;

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        try {
            checkFuelCommand.execute();
        } catch (Exception e) {
            fail("Exception in checkFuelCommand");
        }

    }

    @Test(expected = Exception.class)
    public void testNegativeCheckFuel() {

        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = Mockito.mock(MoveObject.class);

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = 5.00;
        mo.minFuelLevel = 10.00;

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        checkFuelCommand.execute();

    }

    @Test(expected = Exception.class)
    public void testGetFuelLevelException() {
        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = Mockito.mock(MoveObject.class);

        when(m.getFuelLevel()).thenReturn(mo);

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        // Передаем пустое кол-во топлива
        Mockito.when(mo.getMinFuelLevel()).thenReturn(null);

        checkFuelCommand.execute();

    }

    @Test(expected = Exception.class)
    public void testGetMinFuelLevelException() {
        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = Mockito.mock(MoveObject.class);

        when(m.getFuelLevel()).thenReturn(mo);

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        // Передаем пустое значение минимального кол-ва топлива
        Mockito.when(mo.getMinFuelLevel()).thenReturn(null);

        checkFuelCommand.execute();

    }

}
