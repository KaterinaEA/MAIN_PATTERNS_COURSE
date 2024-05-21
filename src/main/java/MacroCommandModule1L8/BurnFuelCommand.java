package MacroCommandModule1L8;

import exceptionModule1L3.ICommand;
import lombok.RequiredArgsConstructor;
import lspIspModule1L2.move.IMovable;

@RequiredArgsConstructor
public class BurnFuelCommand implements ICommand {

    private final IMovable m;

    @Override
    public void execute() {

        MoveObject moveObject = m.getFuelLevel();

        m.setFuel(MoveObject.burnFuel(moveObject));

    }
}
