package tdd_module;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();

        double[] result = new double[3];

        result = calcQuadraticEquation(a, b, c);

    }

    public static boolean checkCoef (double[] coefs){

        boolean result = true;

        for (double coef : coefs) {
            if (Double.isNaN(coef) || Double.isInfinite(coef)) {
                result = false;
                return result;
            }
        }

        return result;

    }

    public static double[] calcQuadraticEquation(double a, double b, double c) {

        double[] coefArray = new double[] {a,b,c};
        double[] result = new double[3];
        double e = 0.000001;

        if ( !checkCoef(coefArray) ) {

            System.out.println("Нельзя задавать не числовые коэфициенты");
            return result;
        }

        if ( a <= 0 + e && a >= 0 - e ) {

            System.out.println("Коэффициент a не может быть равным 0");
            return result;
        }

        // вычисление дискриминанта
        double D = (b * b) - (4 * a * c);

        System.out.println("D = " + D);

        if (D > 0 + e) {
                // два различных корня
                double x1 = (-b + Math.sqrt(D)) / (2 * a);
                double x2 = (-b - Math.sqrt(D)) / (2 * a);

                System.out.println("x1 = " + x1);
                System.out.println("x2 = " + x2);

                result[0] = x1;
                result[1] = x2;
                result[2] = D;

                return result;
            } else if (D <= 0 + e && D >= 0 - e) {
                // один корень
                double x = -b / (2 * a);

                result[0] = x;
                result[1] = x;
                result[2] = D;


                System.out.println("x = " + x);
                return result;

            } else {
                // корней нет
                System.out.println("Корней нет");
                return result;
            }

    }
}
