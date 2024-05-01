package lspIspModule1L2.move;

import exceptionModule1L3.ICommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Move implements ICommand {
    private final IMovable m;

    @Override
    public void execute() {
       // try {
            m.setPosition(Vector.plus(m.getPosition(),
                    m.getVelocity()
            ));

            throw new NullPointerException();
       // } catch (Exception e) {
       //     System.out.println("Move failed");
            //e.printStackTrace();
       // }

    }
}
