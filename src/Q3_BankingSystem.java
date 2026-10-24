import javax.swing.*;

// Q3: Banking system - loan rule: balance >= 10,000 RWF, loan <= 60,000 RWF,
// 6 months, 5% flat interest on the loan.
public class Q3_BankingSystem extends JFrame {
    private static final double MIN_BALANCE = 10_000;
    private static final double MAX_LOAN = 60_000;
    private static final double RATE = 0.05;
    private static final int MONTHS = 6;

    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField balanceField;
    private JTextField loanField;
    private JButton applyButton;
    private JButton clearButton;
    private JTextArea resultArea;

    public Q3_BankingSystem() {
        super("Banking System - Loan");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        resultArea.setEditable(false);
        resultArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
        applyButton.addActionListener(e -> applyLoan());
        clearButton.addActionListener(e -> {
            nameField.setText("");
            balanceField.setText("");
            loanField.setText("");
            resultArea.setText("");
        });
        pack();
        setLocationRelativeTo(null);
    }

    private void applyLoan() {
        double balance, loan;
        try {
            balance = Double.parseDouble(balanceField.getText().trim());
            loan = Double.parseDouble(loanField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (balance < MIN_BALANCE) {
            resultArea.setText("Loan DENIED: balance must be at least 10,000 RWF.");
            return;
        }
        if (loan <= 0 || loan > MAX_LOAN) {
            resultArea.setText("Loan DENIED: loan must be between 1 and 60,000 RWF.");
            return;
        }
        double interest = loan * RATE;
        double total = loan + interest;
        String who = nameField.getText().trim().isEmpty() ? "customer" : nameField.getText().trim();
        resultArea.setText(String.format(
                "Loan APPROVED for %s%n" +
                "Loan amount      : %,.0f RWF%n" +
                "Period           : %d months%n" +
                "Interest rate    : %.0f%%%n" +
                "Interest to pay  : %,.0f RWF%n" +
                "Total to repay   : %,.0f RWF%n" +
                "Monthly payment  : %,.0f RWF",
                who, loan, MONTHS, RATE * 100, interest, total, total / MONTHS));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q3_BankingSystem().setVisible(true));
    }
}
