import javax.swing.*;
import java.util.ArrayList;

// Q6: Add at least 4 numbers. Add / Total / Reset (clears input box) / Clear (clears all) / Exit
public class Q6_AddNumbers extends JFrame {
    private JPanel mainPanel;
    private JTextField numberField;
    private JButton addButton;
    private JTextArea listArea;
    private JLabel totalLabel;
    private JButton totalButton;
    private JButton resetButton;
    private JButton clearButton;
    private JButton exitButton;

    private final ArrayList<Double> numbers = new ArrayList<>();

    public Q6_AddNumbers() {
        super("Add Numbers");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        listArea.setEditable(false);
        totalLabel.setText("Total: -");

        addButton.addActionListener(e -> {
            try {
                double v = Double.parseDouble(numberField.getText().trim());
                numbers.add(v);
                listArea.append(numberField.getText().trim() + "\n");
                numberField.setText("");
                numberField.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number.");
            }
        });
        numberField.addActionListener(e -> addButton.doClick());
        totalButton.addActionListener(e -> {
            if (numbers.size() < 4) {
                JOptionPane.showMessageDialog(this,
                        "Add at least 4 numbers first (you have " + numbers.size() + ").");
                return;
            }
            double s = 0;
            for (double d : numbers) s += d;
            totalLabel.setText("Total: " + (s == Math.rint(s) ? String.valueOf((long) s) : String.valueOf(s)));
        });
        resetButton.addActionListener(e -> {
            numberField.setText("");
            numberField.requestFocus();
        });
        clearButton.addActionListener(e -> {
            numbers.clear();
            listArea.setText("");
            totalLabel.setText("Total: -");
        });
        exitButton.addActionListener(e -> System.exit(0));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q6_AddNumbers().setVisible(true));
    }
}
