package tdd_module;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();

        double[] result = new double[3];

        calcQuadraticEquation(a, b, c);

    }

    public static double[] calcQuadraticEquation(double a, double b, double c) {

        // вычисление дискриминанта
            double D = (b * b) - (4 * a * c);
            double[] result = new double[3];

        System.out.println("D = " + D);


        if (D > 0) {
                // два различных корня
                double x1 = (-b + Math.sqrt(D)) / (2 * a);
                double x2 = (-b - Math.sqrt(D)) / (2 * a);

                System.out.println("x1 = " + x1);
                System.out.println("x2 = " + x2);

                result[0] = x1;
                result[1] = x2;
                result[2] = D;

                return result;
            } else if (D == 0) {
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
