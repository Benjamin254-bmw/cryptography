import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VigenereCipherGUI extends JFrame {
    private JTextField inputField, keyField, outputField;
    private JButton encryptBtn, decryptBtn;

    
    public VigenereCipherGUI() {
        setTitle("Vigenère Cipher Tool");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // UI Components
        add(new JLabel(" Text Input:"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel(" Key (Letters only):"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel(" Result:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        outputField.setBackground(new Color(235, 235, 235));
        add(outputField);

        encryptBtn = new JButton("Encrypt");
        decryptBtn = new JButton("Decrypt");
        add(encryptBtn);
        add(decryptBtn);

        // Logic Listeners
        encryptBtn.addActionListener(e -> process(true));
        decryptBtn.addActionListener(e -> process(false));

        setVisible(true);
    }

    private void process(boolean encrypt) {
        String text = inputField.getText().toUpperCase().replaceAll("[^A-Z]", "");
        String key = keyField.getText().toUpperCase().replaceAll("[^A-Z]", "");

        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid key!");
            return;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = key.charAt(j % key.length()) - 'A';

            if (encrypt) {
                result.append((char) ((c - 'A' + shift) % 26 + 'A'));
            } else {
                result.append((char) ((c - 'A' - shift + 26) % 26 + 'A'));
            }
            j++;
        }
        outputField.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VigenereCipherGUI::new);
    }
}