package lspIspModule1L2.move;

public class Vector {

    int _x;
    int _y;

    public Vector(int x, int y) {
        _x = x;
        _y = y;
    }

    public static Vector plus(Vector position, Vector velocity){

        return new Vector(position._x + velocity._x, position._y + velocity._y);
    }

    public int getX() {
        return _x;
    }

    public void setX(int _x) {
        this._x = _x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int _y) {
        this._y = _y;
    }
}
