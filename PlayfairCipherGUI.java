import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PlayfairCipherGUI extends JFrame {
    private JTextField inputField, outputField;
    private JComboBox<String> keySelector;
    private char[][] matrix = new char[5][5];

    public PlayfairCipherGUI() {
        setTitle("Playfair Cipher Tool");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Key Selection
        JPanel keyPanel = new JPanel(new FlowLayout());
        keyPanel.add(new JLabel("Select Key:"));
        keySelector = new JComboBox<>(new String[] { "Tony", "Jane", "John" });
        keyPanel.add(keySelector);
        add(keyPanel);

        // Input
        add(new JLabel(" Input Text:", SwingConstants.CENTER));
        inputField = new JTextField();
        add(inputField);

        // Result
        add(new JLabel(" Output (Encrypted):", SwingConstants.CENTER));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        // Action Button
        JButton encryptBtn = new JButton("Encrypt Message");
        encryptBtn.addActionListener(e -> encrypt());
        add(encryptBtn);

        setVisible(true);
    }

    private void generateMatrix(String key) {
        String alphabet = (key + "ABCDEFGHIKLMNOPQRSTUVWXYZ").toUpperCase().replace("J", "I");
        StringBuilder sb = new StringBuilder();
        alphabet.chars().distinct().forEach(c -> sb.append((char) c));

        for (int i = 0; i < 25; i++) {
            matrix[i / 5][i % 5] = sb.charAt(i);
        }
    }

    private String formatText(String text) {
        text = text.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder(text);
        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }
        if (sb.length() % 2 != 0)
            sb.append('X');
        return sb.toString();
    }

    private Point getPosition(char c) {
        for (int r = 0; r < 5; r++)
            for (int col = 0; col < 5; col++)
                if (matrix[r][col] == c)
                    return new Point(col, r);
        return null;
    }

    private void encrypt() {
        generateMatrix(keySelector.getSelectedItem().toString());
        String text = formatText(inputField.getText());
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            Point p1 = getPosition(text.charAt(i));
            Point p2 = getPosition(text.charAt(i + 1));

            if (p1.y == p2.y) { // Same Row
                result.append(matrix[p1.y][(p1.x + 1) % 5]);
                result.append(matrix[p2.y][(p2.x + 1) % 5]);
            } else if (p1.x == p2.x) { // Same Column
                result.append(matrix[(p1.y + 1) % 5][p1.x]);
                result.append(matrix[(p2.y + 1) % 5][p2.x]);
            } else { // Rectangle
                result.append(matrix[p1.y][p2.x]);
                result.append(matrix[p2.y][p1.x]);
            }
        }
        outputField.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayfairCipherGUI::new);
    }
}