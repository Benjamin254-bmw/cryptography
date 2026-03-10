import javax.swing.*;
import java.awt.*;

public class AutokeyCipherGUI extends JFrame {
    private JTextField inputField, keyField, outputField;

    public AutokeyCipherGUI() {
        setTitle("Autokey Cipher Tool");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // UI Setup
        add(new JLabel(" Text Input:"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel(" Initial Key:"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel(" Result:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        JButton encryptBtn = new JButton("Encrypt");
        JButton decryptBtn = new JButton("Decrypt");

        encryptBtn.addActionListener(e -> process(true));
        decryptBtn.addActionListener(e -> process(false));

        add(encryptBtn);
        add(decryptBtn);

        setVisible(true);
    }

    private void process(boolean isEncrypt) {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
        String key = keyField.getText().toUpperCase().replaceAll("[^A-Z]", "");

        if (text.isEmpty() || key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both text and a key.");
            return;
        }

        if (isEncrypt) {
            outputField.setText(encrypt(text, key));
        } else {
            outputField.setText(decrypt(text, key));
        }
    }

    private String encrypt(String plaintext, String key) {
        // Full Key = Key + Plaintext
        String fullKey = key + plaintext;
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            int p = plaintext.charAt(i) - 'A';
            int k = fullKey.charAt(i) - 'A';
            int c = (p + k) % 26;
            ciphertext.append((char) (c + 'A'));
        }
        return ciphertext.toString();
    }

    private String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        StringBuilder currentKey = new StringBuilder(key);

        for (int i = 0; i < ciphertext.length(); i++) {
            int c = ciphertext.charAt(i) - 'A';
            int k = currentKey.charAt(i) - 'A';

            // Decryption: (C - K + 26) mod 26
            int p = (c - k + 26) % 26;
            char decodedChar = (char) (p + 'A');

            plaintext.append(decodedChar);
            // Append the decoded character to the key for the next iteration
            currentKey.append(decodedChar);
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AutokeyCipherGUI::new);
    }
}