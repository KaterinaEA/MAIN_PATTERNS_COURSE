package tdd_module_test;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestSolve {

    @Test
    public void
    testIncompleteQuadraticEquationNotRoot () {

        double a = 1.0;
        double b = 0.0;
        double c = 1.0;
        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertEquals("Для уравнения x^2+1 = 0 корней нет (возвращается пустой массив)", 0.0, result[0]);
        assertEquals("Для уравнения x^2+1 = 0 корней нет (возвращается пустой массив)", 0.0, result[1]);

    }

    @Test
    public void
    testIncompleteQuadraticEquationTwoRoots () {

        double a = 1.0;
        double b = 0.0;
        double c = -1.0;
        double e = 0.000001;

        double exceptedx1 = 1.0;
        double exceptedx2 = -1.0;

        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertEquals(exceptedx1, result[0], e);
        assertEquals(exceptedx2, result[1], e);

    }

    @Test
    public void
    testСompleteQuadraticEquationMultiRoot1 () {

        double a = 1.0; //1.0;
        double b = 2.0; //2.0;
        double c = 1.0; //0.999975;
        double e = 0.000001;

        double exceptedx1 = - 1.0; //+ e;
        //double exceptedx2 = - 1.0 - e;

        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        //assertTrue(result[0] <= exceptedx1 && result[0] >= exceptedx2);
        assertEquals(exceptedx1, result[0], e);
        assertEquals(exceptedx1, result[1], e);

    }

    @Test
    public void testAFloat() {

        double a = -0.0000000000000000000000000000000000000000001;
        double b = 1.0;
        double c = 1.0;
        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertFalse(result[0] == result[1]);

    }

    @Test
    public void testAZero() {

        double a = 0.0;
        double b = 1.0;
        double c = 1.0;
        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertEquals("A = 0", 0.0, result[0]);
        assertEquals("A = 0", 0.0, result[1]);

    }

    @Test
    public void testCheckCoef() {

        double a = Double.NaN;
        double b = Double.POSITIVE_INFINITY;
        double c = 0.0;
        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertEquals("Коэффициенты = 0", 0.0, result[0]);
        assertEquals("Коэффициенты = 0", 0.0, result[1]);

    }






    @Test
    public void testPositiveDiscriminant() {

        double a = 1.0;
        double b = 5.0;
        double c = -6.0;
        double expectedX1 = 1.0;
        double expectedX2 = -6.0;


        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertEquals("Корень x1=", expectedX1, result[0]);
        assertEquals("Корень x2=", expectedX2, result[1]);

    }

    @Test
    public void testNegativeDiscriminant() {

        double a = 1.0;
        double b = -2.0;
        double c = 5.0;
        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertTrue(result[2] < 0.0 + 0.00001);

    }

    @Test
    public void testZeroDiscriminant() {
        double a = 1.0;
        double b = -4.0;
        double c = 4.0;
        double[] result;
        double e = 0.00001;

        double expectedD = 0.0;
        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertEquals(expectedD, result[2], e);

    }









    @Test
    public void
    testQuadraticEquationDEqual0001 () {

        double a = 1.0;
        double b = 2.0;
        double c = 0.999975;
        double[] result;
        double e = 0.0001;

        double expectedD = 0.0 + e;
        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);//Main.main(null);

        assertEquals(expectedD, result[2], e);

    }
}
