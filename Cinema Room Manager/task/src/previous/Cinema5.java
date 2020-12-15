package previous;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Cinema5 {

    static final int SMALL_CINEMA_LIMIT = 60;
    static final int HIGH_PRICE = 10;
    static final int LOW_PRICE = 8;
    static final Map<Integer, MenuOption> OPTION_MAP = new LinkedHashMap<>() {{
        put(1, new MenuOption(1, "Show the seats", Cinema5::renderAction));
        put(2, new MenuOption(2, "Buy a ticket", Cinema5::buyTicketAction));
        put(3, new MenuOption(3, "Statistics", Cinema5::statisticsAction));
        put(0, new MenuOption(0, "Exit", Cinema5::quitAction));
    }};

    final Scanner scanner = new Scanner(System.in);

    final int rowAmount;
    final int colAmount;
    final int seatsAmount;
    final int totalIncome;
    private String[][] data;
    private int purchasedCount;
    private int currentIncome;
    private boolean exit = false;

    public Cinema5() {
        List<Integer> rowCol = inputData(
                scanner, "Enter the number of rows:", "Enter the number of seats in each row:");
        this.rowAmount = rowCol.get(0);  // Oh! Where are you, object/array/list/whatever decomposition in Java!?
        this.colAmount = rowCol.get(1);
        seatsAmount = rowAmount * colAmount;
        totalIncome = getTotalIncome();
        initializeData();
        menuCycle();
    }

    private int getTotalIncome() {
        return seatsAmount <= SMALL_CINEMA_LIMIT
                ? seatsAmount * HIGH_PRICE
                : seatsAmount * LOW_PRICE + rowAmount / 2 * colAmount * (HIGH_PRICE - LOW_PRICE);
    }

    private void initializeData() {
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
            OPTION_MAP.values().forEach(System.out::println);
            OPTION_MAP.get(scanner.nextInt()).action.accept(this);
        }
    }

    public void renderAction() {
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

    private void buyTicketAction() {
        boolean success = false;
        while (!success) {
            List<Integer> rowCol = inputData(scanner, "\nEnter a row number:", "Enter a seat number in that row:");
            int rowNumber = rowCol.get(0);
            int colNumber = rowCol.get(1);

            if (wrongCoordinates(rowNumber, colNumber)) {
                System.out.println("\nWrong input!");
                continue;
            }
            if (isAlreadyPurchased(rowNumber, colNumber)) {
                System.out.println("\nThat ticket has already been purchased!");
                continue;
            }

            success = true;
            data[rowNumber][colNumber] = "B";
            int ticketPrice = getTicketPrice(rowNumber);
            currentIncome += ticketPrice;
            purchasedCount++;
            System.out.printf("%nTicket price: $%d%n", ticketPrice);
        }
    }

    private void statisticsAction() {
        System.out.printf("%nNumber of purchased tickets: %d%n", purchasedCount);
        System.out.printf("Percentage: %.2f%%%n", 100.0 * purchasedCount / seatsAmount);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);
    }

    private void quitAction() {
        exit = true;
    }

    private boolean wrongCoordinates(int rowNumber, int colNumber) {
        return rowNumber < 1 || rowNumber > rowAmount
                || colNumber < 1 || colNumber > colAmount;
    }

    private boolean isAlreadyPurchased(int rowNumber, int colNumber) {
        return "B".equals(data[rowNumber][colNumber]);
    }

    private int getTicketPrice(int rowNumber) {
        return seatsAmount <= SMALL_CINEMA_LIMIT || rowNumber <= rowAmount / 2
                ? HIGH_PRICE : LOW_PRICE;
    }

    static List<Integer> inputData(Scanner scanner, String... questions) {
        return Arrays.stream(questions).map(question -> {
            System.out.println(question);
            return scanner.nextInt();
        }).collect(Collectors.toList());
    }

    static class MenuOption {
        final int index;
        final String text;
        final Consumer<Cinema5> action;

        MenuOption(int index, String text, Consumer<Cinema5> action) {
            this.index = index;
            this.text = text;
            this.action = action;
        }

        @Override
        public String toString() {
            return String.format("%d. %s", index, text);
        }
    }

    public static void main(String[] args) {
        new Cinema5();
    }
}