package macroComandTest;

import MacroCommandModule1L8.*;
import exceptionModule1L3.ICommand;
import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Move;
import lspIspModule1L2.move.Vector;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class MacroCommandTest {

    @Test
    public void testMacroCommand() {

        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = new MoveObject();

        int[] start_position_mass = {1, 2, 3};
        int[] start_velocity_mass = {1, 2, 3};

        Vector positionStart = new Vector(start_position_mass);
        Vector velocityStart = new Vector(start_velocity_mass);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(positionStart);
        when(m.getVelocity()).thenReturn(velocityStart);

        Move move = new Move(m);

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = 100.00;
        mo.minFuelLevel = 10.00;

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        double currentFuelLevel = 100.00;
        double fuelConsumptionPerSecond = 10.00;
        double timeMillis = 3600;

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = currentFuelLevel;
        mo.fuelConsumptionPerSecond = fuelConsumptionPerSecond;
        mo.timeMillis = timeMillis;

        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(m);

        List<ICommand> commands = new ArrayList<>();

        commands.add(checkFuelCommand);
        commands.add(move);
        commands.add(burnFuelCommand);

        MacroCommandMove macroCommandMove = new MacroCommandMove(commands);

        macroCommandMove.execute();

    }

    @Test(expected = Exception.class)
    public void testMacroCommandException() {

        IMovable m = Mockito.mock(IMovable.class);
        MoveObject mo = new MoveObject();

        int[] start_position_mass = {1, 2, 3};
        int[] start_velocity_mass = {1, 2, 3};

        Vector positionStart = new Vector(start_position_mass);
        Vector velocityStart = new Vector(start_velocity_mass);

        // Настраиваем mock-объект
        when(m.getPosition()).thenReturn(positionStart);
        when(m.getVelocity()).thenReturn(velocityStart);

        Move move = new Move(m);

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = 10.00;
        mo.minFuelLevel = 100.00;

        CheckFuelCommand checkFuelCommand = new CheckFuelCommand(m);

        double currentFuelLevel = 10.00;
        double fuelConsumptionPerSecond = 10.00;
        double timeMillis = 3600;

        when(m.getFuelLevel()).thenReturn(mo);

        mo.fuelLevel = currentFuelLevel;
        mo.fuelConsumptionPerSecond = fuelConsumptionPerSecond;
        mo.timeMillis = timeMillis;

        BurnFuelCommand burnFuelCommand = new BurnFuelCommand(m);

        List<ICommand> commands = new ArrayList<>();

        commands.add(checkFuelCommand);
        commands.add(move);
        commands.add(burnFuelCommand);

        MacroCommandMove macroCommandMove = new MacroCommandMove(commands);

        macroCommandMove.execute();

    }

}
