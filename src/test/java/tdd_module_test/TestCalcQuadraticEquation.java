package tdd_module_test;

import org.junit.Test;

import static junit.framework.Assert.*;

public class TestCalcQuadraticEquation {
    @Test
    public void testPositiveDiscriminant() {
        double a = 1;
        double b = 5;
        double c = 1;
        double e = 0.00001;

        //double expectedX1 = 1;
        //double expectedX2 = -1;
        double[] result;

        result = tdd_module.Main.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertTrue(result[0] < 0 - e);
        assertTrue(result[1] < 0 - e);
        //assertEquals(expectedX1, result[0], e);
        //assertEquals(expectedX2, result[1], e);
    }

    @Test
    public void testNegativeDiscriminant() {
        double a = 1;
        double b = -2;
        double c = 5;
        double[] result;

        result = tdd_module.Main.calcQuadraticEquation(a, b,c);//Main.main(null);

        assertTrue(result[2] < 0 + 0.00001);

    }

    @Test
    public void testZeroDiscriminant() {
        double a = 1;
        double b = -4;
        double c = 4;
        double[] result;
        double e = 0.00001;

        double expectedD = 0.0;
        result = tdd_module.Main.calcQuadraticEquation(a, b,c);//Main.main(null);

        assertEquals(expectedD, result[2], e);

    }

    @Test
    public void testZeroCoef() {
        double a = 0;
        double b = 0;
        double c = 0;
        double[] result;
        double e = 0.00001;

        result = tdd_module.Main.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertTrue( Double.isNaN(result[2]) );

    }
}
