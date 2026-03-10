import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaesarCipherGUI extends JFrame {
    // UI Components
    private JTextField inputField, keyField, outputField;
    private JButton encryptButton;

    public CaesarCipherGUI() {
        // 1. Setup the Window (JFrame)
        setTitle("CIR 310: Caesar Cipher Tool");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // Vertical stack layout

        // 2. Create and Add Components
        add(new JLabel(" Enter Plaintext:"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel(" Enter Shift Key (Integer):"));
        keyField = new JTextField();
        add(keyField);

        encryptButton = new JButton("Run Cipher");
        add(encryptButton);

        outputField = new JTextField();
        outputField.setEditable(false); // User cannot type in the result field
        outputField.setBackground(Color.LIGHT_GRAY);
        add(outputField);

        // 3. The Button Action
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    int shift = Integer.parseInt(keyField.getText()); // Convert string to int
                    
                    // Call the encryption logic
                    String result = performEncryption(text, shift);
                    outputField.setText(result);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number for the key.");
                }
            }
        });
    }

    // Logic
    private String performEncryption(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                char encrypted = (char) ((character - base + shift) % 26 + base);
                result.append(encrypted);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new CaesarCipherGUI().setVisible(true);
        });
    }
}