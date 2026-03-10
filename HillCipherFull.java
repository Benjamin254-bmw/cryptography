import javax.swing.*;
import java.awt.*;

public class HillCipherFull extends JFrame {
    private JTextField inputField, outputField;
    // Key Matrix: {{3, 3}, {2, 5}}. Determinant = 15 - 6 = 9. 
    // Modular inverse of 9 mod 26 is 3.
    private final int[][] keyMatrix = {{3, 3}, {2, 5}};
    private final int[][] invMatrix = {{15, 17}, {20, 9}}; // Pre-calculated inverse for this specific key

    public HillCipherFull() {
        setTitle("Full Hill Cipher (2x2)");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel(" Text Input (A-Z only):", SwingConstants.CENTER));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel(" Result:", SwingConstants.CENTER));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        JButton encBtn = new JButton("Encrypt");
        JButton decBtn = new JButton("Decrypt");
        
        encBtn.addActionListener(e -> process(keyMatrix));
        decBtn.addActionListener(e -> process(invMatrix));

        add(encBtn);
        add(decBtn);

        setVisible(true);
    }

    private void process(int[][] matrix) {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
        if (text.length() % 2 != 0) text += "X";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            int p1 = text.charAt(i) - 'A';
            int p2 = text.charAt(i + 1) - 'A';

            // Matrix Multiplication: (Matrix * Vector) mod 26
            int r1 = (matrix[0][0] * p1 + matrix[0][1] * p2) % 26;
            int r2 = (matrix[1][0] * p1 + matrix[1][1] * p2) % 26;

            // Handle Java's negative modulo
            if (r1 < 0) r1 += 26;
            if (r2 < 0) r2 += 26;

            result.append((char) (r1 + 'A'));
            result.append((char) (r2 + 'A'));
        }
        outputField.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HillCipherFull::new);
    }
}