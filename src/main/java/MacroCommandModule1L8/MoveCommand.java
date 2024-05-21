package MacroCommandModule1L8;

import exceptionModule1L3.ICommand;
import lombok.RequiredArgsConstructor;
import lspIspModule1L2.move.IMovable;
import lspIspModule1L2.move.Vector;

@RequiredArgsConstructor
public class MoveCommand implements ICommand {

    private final IMovable m;

    @Override
    public void execute() {
        System.out.println("Move command");

        m.setPosition(Vector.plus(m.getPosition(),
                m.getVelocity()
        ));

    }
}
