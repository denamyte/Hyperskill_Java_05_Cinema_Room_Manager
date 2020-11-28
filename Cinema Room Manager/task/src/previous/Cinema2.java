package previous;

import java.util.Scanner;

public class Cinema2 {

    static int calculateProfit(int rows, int cols) {
        int totalSeats = rows * cols;
        if (totalSeats <= 60) {
            return totalSeats * 10;
        }
        int frontHalf = rows / 2;
        return frontHalf * cols * 10 + (rows - frontHalf) * cols * 8;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int cols = scanner.nextInt();
        System.out.println("Total income:");
        System.out.printf("$%d%n", Cinema2.calculateProfit(rows, cols));
    }
}