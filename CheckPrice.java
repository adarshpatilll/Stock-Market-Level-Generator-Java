import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class CheckPrice extends JPanel {
    private JLabel resultLabel;

    public CheckPrice() {
        setBounds(350, 300, 300, 200);
        setBackground(new Color(255, 223, 186));

        // Add a border around the entire panel
        Border border = BorderFactory.createLineBorder(new Color(51, 102, 204), 2);
        setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        setLayout(new BorderLayout(0, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(51, 102, 204));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel titleLabel = new JLabel("Check Strike Price");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputPanel.setBackground(new Color(255, 255, 255));

        // Input Field
        JTextField priceTextField = new JTextField(15);
        priceTextField.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(priceTextField);

        // Result Label
        resultLabel = new JLabel("Result : ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(resultLabel);

        priceTextField.addActionListener(e -> {
            try {
                int enteredPrice = Integer.parseInt(priceTextField.getText());
                String result = BankNiftyOperator.isSpecial(enteredPrice).equals("Special") ? "Special" : "Not Special";
                resultLabel.setText("Result : " + result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Result : Invalid Input");
            }
        });

        add(inputPanel, BorderLayout.CENTER);
    }
}
