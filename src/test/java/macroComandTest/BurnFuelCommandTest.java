package macroComandTest;

import MacroCommandModule1L8.BurnFuelCommand;
import MacroCommandModule1L8.MoveObject;
import lspIspModule1L2.move.IMovable;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class BurnFuelCommandTest {

    @Test
    public void testBurnFuelCommand() {

        IMovable m = Mockito.mock(IMovable.class);

        MoveObject mo = new MoveObject();

        double currentFuelLevel = 100.00;
        double fuelConsumptionPerSecond = 10.00;
        double timeMillis = 3600;
        double exceptedFuelLevel = 64.00;

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelConsumptionPerSecond = fuelConsumptionPerSecond;
        mo.timeMillis = timeMillis;
        mo.fuelLevel = currentFuelLevel;

        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(m);

        burnFuelCommand.execute();

        assertEquals(exceptedFuelLevel, mo.getFuelLevel(), 0.001);

    }

}
