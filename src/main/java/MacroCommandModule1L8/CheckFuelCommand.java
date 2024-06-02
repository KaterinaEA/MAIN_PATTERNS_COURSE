package MacroCommandModule1L8;

import exceptionModule1L3.ICommand;
import lombok.RequiredArgsConstructor;
import lspIspModule1L2.move.IMovable;

@RequiredArgsConstructor
public class CheckFuelCommand implements ICommand {

    private final IMovable m;

    @Override
    public void execute() {

        try {

            MoveObject moveObject = m.getFuelLevel(); // метод для получения текущего уровня топлива

            if (moveObject.fuelLevel < moveObject.minFuelLevel) {

                throw new CommandException("Fuel must be greater than minFuelLevel");

            }

            System.out.println("Checking if fuel is available");

        } catch (CommandException e) {

                throw new RuntimeException(e.getMessage());

        }
    }
}
