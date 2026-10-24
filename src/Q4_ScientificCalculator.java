import javax.swing.*;
import java.awt.*;

// Q4: Scientific calculator (trig in degrees). The button layout comes from the .form file;
// if the form gives no buttons, they are created in code so the calculator always works.
public class Q4_ScientificCalculator extends JFrame {
    private JPanel mainPanel;
    private JTextField display;
    private JPanel buttonPanel;

    private static final String[] KEYS = {
            "sin", "cos", "tan", "log", "ln",
            "√", "x²", "1/x", "x^y", "n!",
            "π", "e", "(-)", "C", "⌫",
            "7", "8", "9", "/", "%",
            "4", "5", "6", "*", "-",
            "1", "2", "3", "+", "=",
            "0", ".", "eˣ", "10ˣ", "abs"
    };

    private double first = 0;
    private String op = "";
    private boolean newEntry = true;

    public Q4_ScientificCalculator() {
        super("Scientific Calculator");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        display.setEditable(false);
        display.setText("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 26));

        if (buttonPanel.getComponentCount() == 0) {          // fallback if the form has no buttons
            buttonPanel.setLayout(new GridLayout(7, 5, 4, 4));
            for (String k : KEYS) buttonPanel.add(new JButton(k));
        }
        for (Component c : buttonPanel.getComponents()) {
            if (c instanceof JButton) {
                final JButton b = (JButton) c;
                b.setFont(new Font("SansSerif", Font.PLAIN, 16));
                b.addActionListener(e -> press(b.getText().trim()));
            }
        }
        setSize(430, 520);
        setMinimumSize(new Dimension(380, 460));
        setLocationRelativeTo(null);
    }

    private double cur() {
        try { return Double.parseDouble(display.getText()); }
        catch (NumberFormatException ex) { return 0; }
    }

    private void show(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) { display.setText("Error"); newEntry = true; return; }
        if (Math.abs(v) < 1e-12) v = 0;                       // clean up sin(180) = 1.2e-16 etc.
        v = new java.math.BigDecimal(v).round(new java.math.MathContext(12)).doubleValue();
        display.setText(v == Math.rint(v) && Math.abs(v) < 1e15 ? String.valueOf((long) v) : String.valueOf(v));
        newEntry = true;
    }

    private double calc(double a, String o, double b) {
        switch (o) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "x^y": return Math.pow(a, b);
            case "%": return a % b;
            default: return b;
        }
    }

    private void press(String k) {
        if (display.getText().equals("Error") && !k.equals("C")) return;
        switch (k) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                display.setText(newEntry || display.getText().equals("0") ? k : display.getText() + k);
                newEntry = false; break;
            case ".":
                if (newEntry) { display.setText("0."); newEntry = false; }
                else if (!display.getText().contains(".")) display.setText(display.getText() + ".");
                break;
            case "C": display.setText("0"); first = 0; op = ""; newEntry = true; break;
            case "⌫":
                if (newEntry) break;
                String t = display.getText();
                display.setText(t.length() > 1 ? t.substring(0, t.length() - 1) : "0"); break;
            case "+": case "-": case "*": case "/": case "x^y": case "%":
                if (!op.isEmpty() && !newEntry) {             // chain: 2 + 3 + 4
                    first = calc(first, op, cur());
                    show(first);
                    if (display.getText().equals("Error")) { op = ""; break; }
                } else {
                    first = cur();
                }
                op = k; newEntry = true; break;
            case "=":
                if (op.isEmpty()) break;
                double r = calc(first, op, cur());
                op = ""; show(r); break;
            case "sin": show(Math.sin(Math.toRadians(cur()))); break;
            case "cos": show(Math.cos(Math.toRadians(cur()))); break;
            case "tan": show(Math.tan(Math.toRadians(cur()))); break;
            case "log": show(Math.log10(cur())); break;
            case "ln":  show(Math.log(cur())); break;
            case "√":   show(Math.sqrt(cur())); break;
            case "x²":  show(cur() * cur()); break;
            case "1/x": show(1 / cur()); break;
            case "eˣ":  show(Math.exp(cur())); break;
            case "10ˣ": show(Math.pow(10, cur())); break;
            case "abs": show(Math.abs(cur())); break;
            case "(-)": show(-cur()); newEntry = false; break;
            case "π":   show(Math.PI); break;
            case "e":   show(Math.E); break;
            case "n!":
                double n = cur();
                if (n < 0 || n != Math.rint(n) || n > 170) { show(Double.NaN); break; }
                double f = 1;
                for (int i = 2; i <= n; i++) f *= i;
                show(f); break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q4_ScientificCalculator().setVisible(true));
    }
}