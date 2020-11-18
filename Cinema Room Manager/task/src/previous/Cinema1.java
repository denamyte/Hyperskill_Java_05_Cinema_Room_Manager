package previous;

import java.util.Arrays;

public class Cinema1 {

    private String[][] data;

    public Cinema1() {
        initialize();
    }

    private void initialize() {
        data = new String[8][9];
        for (int row = 0; row < 8; row++) {
            Arrays.fill(data[row], "S");
            data[row][0] = "" + row;
            data[0][row + 1] = "" + (row + 1);
        }
        data[0][0] = " ";
    }

    public String render() {
        StringBuilder sb = new StringBuilder("Cinema:\n");
        for (String[] row : data) {
            for (String col : row) {
                sb.append(col).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.print(new Cinema1().render());
    }
}