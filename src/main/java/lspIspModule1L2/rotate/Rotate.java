package lspIspModule1L2.rotate;

public class Rotate {
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
