import javax.swing.*;
import java.awt.*;

public class FullRouteCipherGUI extends JFrame {
    private JTextField inputField, keyField, outputField;

    public FullRouteCipherGUI() {
        setTitle("Spiral Route Cipher (Clockwise)");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel(" Text Input:"));
        inputField = new JTextField("THEQUICKBROWNFOX");
        add(inputField);

        add(new JLabel(" Key (Number of Columns):"));
        keyField = new JTextField("4");
        add(keyField);

        add(new JLabel(" Result:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton enc = new JButton("Encrypt");
        JButton dec = new JButton("Decrypt");
        btnPanel.add(enc);
        btnPanel.add(dec);
        add(btnPanel);

        enc.addActionListener(e -> outputField.setText(process(true)));
        dec.addActionListener(e -> outputField.setText(process(false)));

        setVisible(true);
    }

    private String process(boolean isEncrypt) {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
        int cols = Integer.parseInt(keyField.getText());
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] grid = new char[rows][cols];

        if (isEncrypt) {
            // ENCRYPT: Fill row-by-row, Read Spiral
            int idx = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = (idx < text.length()) ? text.charAt(idx++) : 'X';
                }
            }
            return traverseSpiral(grid, rows, cols, true);
        } else {
            // DECRYPT: Fill Spiral, Read row-by-row
            fillSpiral(grid, rows, cols, text);
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    sb.append(grid[r][c]);
                }
            }
            return sb.toString();
        }
    }

    private String traverseSpiral(char[][] grid, int rows, int cols, boolean read) {
        StringBuilder sb = new StringBuilder();
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) sb.append(grid[top][i]);
            top++;
            for (int i = top; i <= bottom; i++) sb.append(grid[i][right]);
            right--;
            if (top <= bottom) {
                for (int i = right; i >= left; i--) sb.append(grid[bottom][i]);
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top; i--) sb.append(grid[i][left]);
                left++;
            }
        }
        return sb.toString();
    }

    private void fillSpiral(char[][] grid, int rows, int cols, String text) {
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        int idx = 0;

        while (top <= bottom && left <= right && idx < text.length()) {
            for (int i = left; i <= right && idx < text.length(); i++) grid[top][i] = text.charAt(idx++);
            top++;
            for (int i = top; i <= bottom && idx < text.length(); i++) grid[i][right] = text.charAt(idx++);
            right--;
            if (top <= bottom) {
                for (int i = right; i >= left && idx < text.length(); i--) grid[bottom][i] = text.charAt(idx++);
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= top && idx < text.length(); i--) grid[i][left] = text.charAt(idx++);
                left++;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FullRouteCipherGUI::new);
    }
}