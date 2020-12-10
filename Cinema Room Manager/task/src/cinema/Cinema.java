package cinema;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cinema {

    private static final int SMALL_CINEMA_LIMIT = 60;
    private static final int HIGH_PRICE = 10;
    private static final int LOW_PRICE = 8;
    private final Scanner scanner;
    private final int rowAmount;
    private final int colAmount;
    private String[][] data;
    boolean exit = false;

    private Map<Integer, Runnable> actions = Map.of(
            1, this::render,
            2, this::buyTicket,
            0, () -> exit = true
    );

    public Cinema() {
        scanner = new Scanner(System.in);
        List<Integer> rowCol = inputData(
                scanner, "Enter the number of rows:", "Enter the number of seats in each row:");
        this.rowAmount = rowCol.get(0);
        this.colAmount = rowCol.get(1);
        initialize();
        menuCycle();
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

    public void menuCycle() {
        while (!exit) {
            System.out.println();
            int actionIndex = showMenuOptionsAndTakeTheAction(scanner, MENU_OPTIONS);
            actions.get(actionIndex).run();
        }
    }

    public void render() {
        StringBuilder sb = new StringBuilder("\nCinema:\n");
        for (String[] row : data) {
            for (String col : row) {
                sb.append(col).append(' ');
            }
            sb.append('\n');
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb.toString());
    }

    private void buyTicket() {
        List<Integer> rowCol = inputData(scanner, "Enter a row number:", "Enter a seat number in that row:");
        buyTicket(rowCol.get(0), rowCol.get(1));
        System.out.printf("Ticket price: $%d%n", getTicketPrice(rowCol.get(0)));
    }

    private void buyTicket(int rowNumber, int colNumber) {
        if (rowNumber > 0 && rowNumber <= rowAmount
                && colNumber > 0 && colNumber <= colAmount) {
            data[rowNumber][colNumber] = "B";
        }
    }

    private int getTicketPrice(int rowNumber) {
        return rowAmount * colAmount <= SMALL_CINEMA_LIMIT || rowNumber <= rowAmount / 2
                ? HIGH_PRICE : LOW_PRICE;
    }

    static List<Integer> inputData(Scanner scanner, String... questions) {
        return Arrays.stream(questions).map(question -> {
            System.out.println(question);
            return scanner.nextInt();
        }).collect(Collectors.toList());
    }

    static int showMenuOptionsAndTakeTheAction(Scanner scanner, MenuOption... menuOptions) {
        for (MenuOption option : menuOptions) {
            System.out.println(option);
        }
        return scanner.nextInt();
    }

    static class MenuOption {
        final int index;
        final String text;

        MenuOption(int index, String text) {
            this.index = index;
            this.text = text;
        }

        @Override
        public String toString() {
            return String.format("%d. %s", index, text);
        }
    }

    static final MenuOption[] MENU_OPTIONS = new MenuOption[] {
            new MenuOption(1, "Show the seats"),
            new MenuOption(2, "Buy a ticket"),
            new MenuOption(0, "Exit"),
    };


    public static void main(String[] args) {
        Cinema cinema = new Cinema();

//
//        System.out.println(cinema.render());
//
//        System.out.println("\nEnter a row number:");
//        int rowNumber = scanner.nextInt();
//        System.out.println("Enter a seat number in that row:");
//        int colNumber = scanner.nextInt();
//        cinema.buyTicket(rowNumber, colNumber);

//        System.out.printf("%nTicket price: $%d%n", cinema.getTicketPriceString(rowNumber));
//        System.out.println(cinema.render());
    }
}