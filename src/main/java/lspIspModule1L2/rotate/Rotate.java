package lspIspModule1L2.rotate;

import exceptionModule1L3.ICommand;

public class Rotate implements ICommand {
    IRotable _rotable;
    public Rotate(IRotable rotable) {
        _rotable = rotable;
    }

    public void execute() {
        _rotable.setAngle(
                Angle.plus(
                        _rotable.getAngle(),
                        _rotable.getAngularVelocity()
                )
        );
    }
}
