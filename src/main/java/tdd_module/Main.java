package tdd_module;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        double a = scan.nextDouble();
        double b = scan.nextDouble();
        double c = scan.nextDouble();

        double[] result = new double[3];

        result = Solve.calcQuadraticEquation(a, b, c);

    }


}
