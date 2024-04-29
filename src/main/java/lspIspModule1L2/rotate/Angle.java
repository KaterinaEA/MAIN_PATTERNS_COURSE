package lspIspModule1L2.rotate;

import lombok.Getter;

@Getter
public class Angle {

    int _direction;
    int _n;

    public Angle(int direction, int n) {
        _direction = direction;
        _n = n;
    }

    public static Angle plus(Angle a, Angle b) {

        return new Angle(a._direction + b._direction, a._n + b._n);
    }
}
