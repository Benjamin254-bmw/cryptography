import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TranspositionGUI extends JFrame {
    private JTextField inputField, keyField, outputField;

    public TranspositionGUI() {
        setTitle("Columnar Transposition Cipher");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel(" Text Input:"));
        inputField = new JTextField("ATTACKATDAWN");
        add(inputField);

        add(new JLabel(" Key (Numeric length or Word):"));
        keyField = new JTextField("4"); // We'll use the length of this input
        add(keyField);

        add(new JLabel(" Result:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        JButton enc = new JButton("Encrypt");
        JButton dec = new JButton("Decrypt");
        
        enc.addActionListener(e -> outputField.setText(encrypt()));
        dec.addActionListener(e -> outputField.setText(decrypt()));

        add(enc); add(dec);
        setVisible(true);
    }

    private String encrypt() {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
    int numCols = keyField.getText().length();
    
    // Guard Clause
    if (numCols <= 1 || numCols >= text.length()) {
        JOptionPane.showMessageDialog(this, 
            "Key length must be between 2 and " + (text.length() - 1));
        return text;
    }

        int numRows = (int) Math.ceil((double) text.length() / numCols);
        char[][] grid = new char[numRows][numCols];

        // Fill grid row by row
        int charIdx = 0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (charIdx < text.length()) {
                    grid[r][c] = text.charAt(charIdx++);
                } else {
                    grid[r][c] = 'X'; // Padding
                }
            }
        }

        // Read grid column by column
        StringBuilder result = new StringBuilder();
        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                result.append(grid[r][c]);
            }
        }
        return result.toString();
    }

    private String decrypt() {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
        int numCols = keyField.getText().length();
        if (numCols == 0) return "Invalid Key";

        int numRows = text.length() / numCols;
        char[][] grid = new char[numRows][numCols];

        // Fill grid column by column
        int charIdx = 0;
        for (int c = 0; c < numCols; c++) {
            for (int r = 0; r < numRows; r++) {
                grid[r][c] = text.charAt(charIdx++);
            }
        }

        // Read grid row by row to get plaintext
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                result.append(grid[r][c]);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TranspositionGUI::new);
    }
}