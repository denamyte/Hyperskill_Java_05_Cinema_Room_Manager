package previous;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema3 {

    private static final int SMALL_CINEMA_LIMIT = 60;
    private static final int HIGH_PRICE = 10;
    private static final int LOW_PRICE = 8;
    private final int rowAmount;
    private final int colAmount;
    private String[][] data;

    public Cinema3(int rowAmount, int colAmount) {
        this.rowAmount = rowAmount;
        this.colAmount = colAmount;
        initialize();
    }

    private void initialize() {
        data = new String[rowAmount + 1][colAmount + 1];
        for (int row = 0; row < rowAmount + 1; row++) {
            Arrays.fill(data[row], "S");
            data[row][0] = "" + row;
        }
        for (int col = 1; col <= colAmount; col++) {
            data[0][col] = "" + col;
        }
        data[0][0] = " ";
    }

    private void buyTicket(int rowNumber, int colNumber) {
        if (rowNumber > 0 && rowNumber <= rowAmount
                && colNumber > 0 && colNumber <= colAmount) {
            data[rowNumber][colNumber] = "B";
        }
    }

    private int getTicketPriceString(int rowNumber) {
        return rowAmount * colAmount <= SMALL_CINEMA_LIMIT || rowNumber <= rowAmount / 2
                ? HIGH_PRICE : LOW_PRICE;
    }

    public String render() {
        StringBuilder sb = new StringBuilder("\nCinema:\n");
        for (String[] row : data) {
            for (String col : row) {
                sb.append(col).append(' ');
            }
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rowAmount = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int colAmount = scanner.nextInt();
        Cinema3 cinema = new Cinema3(rowAmount, colAmount);

        System.out.println(cinema.render());

        System.out.println("\nEnter a row number:");
        int rowNumber = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int colNumber = scanner.nextInt();
        cinema.buyTicket(rowNumber, colNumber);

        System.out.printf("%nTicket price: $%d%n", cinema.getTicketPriceString(rowNumber));
        System.out.println(cinema.render());
    }
}