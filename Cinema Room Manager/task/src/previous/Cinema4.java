package previous;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Cinema4 {

    static final int SMALL_CINEMA_LIMIT = 60;
    static final int HIGH_PRICE = 10;
    static final int LOW_PRICE = 8;
    static final Map<Integer, MenuOption> OPTION_MAP = new LinkedHashMap<>() {{
        put(1, new MenuOption(1, "Show the seats", Cinema4::renderAction));
        put(2, new MenuOption(2, "Buy a ticket", Cinema4::buyTicketAction));
        put(0, new MenuOption(0, "Exit", Cinema4::quitAction));
    }};

    final Scanner scanner = new Scanner(System.in);
    final int rowAmount;
    final int colAmount;
    private String[][] data;
    private boolean exit = false;

    public Cinema4() {
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
        List<Integer> rowCol = inputData(scanner, "\nEnter a row number:", "Enter a seat number in that row:");
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

    private void quitAction() {
        exit = true;
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
        final Consumer<Cinema4> action;

        MenuOption(int index, String text, Consumer<Cinema4> action) {
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
        new Cinema4();
    }
}