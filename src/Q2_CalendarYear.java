import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

// Q2: Calendar year (UI in Q2_CalendarYear.form, logic here)
public class Q2_CalendarYear extends JFrame {
    private JPanel mainPanel;
    private JSpinner yearSpinner;
    private JButton showButton;
    private JPanel yearPanel;

    public Q2_CalendarYear() {
        super("Calendar Year");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));
        yearPanel.setLayout(new GridLayout(3, 4, 8, 8));
        showButton.addActionListener(e -> buildYear());
        buildYear();
        setSize(950, 720);
        setLocationRelativeTo(null);
    }

    // called by the generated UI code because the spinner is "Custom Create"
    private void createUIComponents() {
        yearSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 1, 9999, 1));
    }

    private void buildYear() {
        yearPanel.removeAll();
        int year = (Integer) yearSpinner.getValue();
        for (int m = 1; m <= 12; m++) yearPanel.add(monthPanel(YearMonth.of(year, m)));
        yearPanel.revalidate();
        yearPanel.repaint();
    }

    private JPanel monthPanel(YearMonth ym) {
        JPanel p = new JPanel(new GridLayout(0, 7));
        p.setBorder(BorderFactory.createTitledBorder(
                ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
        for (String d : new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"}) {
            JLabel l = new JLabel(d, SwingConstants.CENTER);
            l.setFont(l.getFont().deriveFont(Font.BOLD));
            p.add(l);
        }
        int offset = ym.atDay(1).getDayOfWeek().getValue() - 1;
        for (int i = 0; i < offset; i++) p.add(new JLabel(""));
        for (int d = 1; d <= ym.lengthOfMonth(); d++) {
            JLabel l = new JLabel(String.valueOf(d), SwingConstants.CENTER);
            if (ym.atDay(d).getDayOfWeek() == DayOfWeek.SUNDAY) l.setForeground(Color.RED);
            if (ym.atDay(d).equals(LocalDate.now())) {
                l.setOpaque(true);
                l.setBackground(new Color(255, 230, 120));
            }
            p.add(l);
        }
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q2_CalendarYear().setVisible(true));
    }
}
