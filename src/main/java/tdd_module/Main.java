package tdd_module;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();

        double[] result = new double[3];

        if (0.0000000000000000000000000000000000000001 > 0.0) {
            System.out.println("Больше 0");
        } else {
            System.out.println("Меньше или равно 0");
        }

        //result = Solve.calcQuadraticEquation(a, b, c);

    }


}
