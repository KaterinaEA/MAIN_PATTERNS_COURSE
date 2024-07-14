package iocAdapterBridgeModule2L12;

import lspIspModule1L2.move.Vector;

public class MovableAdapter {

    private final Uobject _obj;

    public MovableAdapter (Uobject uObj) {
        _obj = uObj;
    }

    public Vector getPosition() {
        return (Vector) _obj.getProperty("Position");
    }

    public Vector getVelocity() {
        return (Vector) _obj.getProperty("Velocity");
    }

    public void setPosition(Vector newPosition) {
        _obj.setProperty("Position", newPosition);
    }

}
