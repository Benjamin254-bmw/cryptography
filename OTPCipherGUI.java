import javax.swing.*;
import java.awt.*;

public class OTPCipherGUI extends JFrame {
    private JTextField inputField, keyField, outputField;
    private JButton processButton;

    public OTPCipherGUI() {
        setTitle("CIR 310: One-Time Pad (XOR) Tool");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel(" Enter Bitstring (e.g., 1010101):"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel(" Enter Key (e.g., 1110001):"));
        keyField = new JTextField();
        add(keyField);

        processButton = new JButton("Encode / Decode");
        add(processButton);

        outputField = new JTextField();
        outputField.setEditable(false);
        outputField.setBackground(new Color(230, 230, 230));
        add(outputField);

        // Logic for XOR operation
        processButton.addActionListener(e -> {
            String bits = inputField.getText();
            String key = keyField.getText();

            if (bits.length() != key.length()) {
                JOptionPane.showMessageDialog(this, "Error: Key and Message must be the same length!");
                return;
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < bits.length(); i++) {
                int b = Character.getNumericValue(bits.charAt(i));
                int k = Character.getNumericValue(key.charAt(i));
                // XOR operator ^
                result.append(b ^ k);
            }
            outputField.setText(result.toString());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OTPCipherGUI().setVisible(true));
    }
}