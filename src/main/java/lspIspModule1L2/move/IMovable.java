package lspIspModule1L2.move;

import MacroCommandModule1L8.MoveObject;

public interface IMovable {

    Vector getPosition();
    Vector getVelocity();

    void setPosition(Vector newPosition);

    MoveObject getFuelLevel();
    void setFuel(MoveObject newFuel);

}
