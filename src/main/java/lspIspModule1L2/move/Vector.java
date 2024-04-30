package lspIspModule1L2.move;

import lombok.Getter;

@Getter
public class Vector {

    int _n;
    int[] m;

    public Vector(int[] mass) {
        _n = mass.length;
        m = mass;
    }

    public static Vector plus(Vector position, Vector velocity){
        int[] result_mass = new int[position._n];

        if (position._n == velocity._n) {
            for (int i = 0; i < position._n; i++) {
                result_mass[i] = position.m[i] + velocity.m[i];
            }
        }

        return new Vector(result_mass);
    }

}
