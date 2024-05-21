package tddModuleTest;

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

        double exceptedX1 = 1.0;
        double exceptedX2 = -1.0;

        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertEquals(exceptedX1, result[0], e);
        assertEquals(exceptedX2, result[1], e);

    }

    @Test
    public void
    testCompleteQuadraticEquationMultiRoot1 () {

        double a = 1.0; //1.0;
        double b = 2.0; //2.0;
        double c = 1.0; //0.999975;
        double e = 0.000001;

        double exceptedx1 = - 1.0;

        double[] result;

        result = tdd_module.Solve.calcQuadraticEquation(a, b, c);

        assertEquals(exceptedx1, result[0], e);
        assertEquals(exceptedx1, result[1], e);

    }

    @Test
    public void testAFloat() {

        double a = -Math.pow(1, -42);
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
}
