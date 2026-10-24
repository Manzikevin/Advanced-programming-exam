import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Q5: Student transcript (UI in Q5_StudentTranscript.form)
public class Q5_StudentTranscript extends JFrame {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField regField;
    private JTextField programField;
    private JTable table;
    private JButton generateButton;
    private JButton clearButton;
    private JTextArea transcriptArea;

    private final DefaultTableModel model =
            new DefaultTableModel(new Object[]{"Subject", "Credits", "Marks (/100)"}, 0);

    public Q5_StudentTranscript() {
        super("Student Transcript");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        for (String s : new String[]{"Mathematics", "Programming", "Databases", "Networking", "English"})
            model.addRow(new Object[]{s, 3, ""});
        table.setModel(model);
        transcriptArea.setEditable(false);
        transcriptArea.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
        generateButton.addActionListener(e -> generate());
        clearButton.addActionListener(e -> {
            nameField.setText("");
            regField.setText("");
            programField.setText("");
            for (int i = 0; i < model.getRowCount(); i++) model.setValueAt("", i, 2);
            transcriptArea.setText("");
        });
        pack();
        setLocationRelativeTo(null);
    }

    private static String grade(double m) {
        if (m >= 85) return "A";
        if (m >= 75) return "B";
        if (m >= 60) return "C";
        if (m >= 50) return "D";
        return "F";
    }

    private static double points(String g) {
        switch (g) {
            case "A": return 4;
            case "B": return 3;
            case "C": return 2;
            case "D": return 1;
            default: return 0;
        }
    }

    private void generate() {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();
        StringBuilder sb = new StringBuilder();
        sb.append("==================== STUDENT TRANSCRIPT ====================\n");
        sb.append(String.format("Name   : %s%nReg No : %s%nProgram: %s%n",
                nameField.getText(), regField.getText(), programField.getText()));
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("%-16s %7s %7s %6s %7s%n", "Subject", "Credits", "Marks", "Grade", "Points"));
        double totalMarks = 0, totalCredits = 0, totalPoints = 0;
        int n = model.getRowCount();
        for (int i = 0; i < n; i++) {
            double marks, credits;
            try {
                marks = Double.parseDouble(model.getValueAt(i, 2).toString().trim());
                credits = Double.parseDouble(model.getValueAt(i, 1).toString().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid marks and credits for row " + (i + 1));
                return;
            }
            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be 0-100 (row " + (i + 1) + ")");
                return;
            }
            String g = grade(marks);
            sb.append(String.format("%-16s %7.0f %7.1f %6s %7.1f%n",
                    model.getValueAt(i, 0), credits, marks, g, points(g)));
            totalMarks += marks;
            totalCredits += credits;
            totalPoints += points(g) * credits;
        }
        double avg = totalMarks / n;
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("Total marks  : %.1f / %d%nAverage      : %.2f%%%nOverall grade: %s%nGPA          : %.2f / 4.00%nRemark       : %s%n",
                totalMarks, n * 100, avg, grade(avg), totalPoints / totalCredits, avg >= 50 ? "PASS" : "FAIL"));
        transcriptArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q5_StudentTranscript().setVisible(true));
    }
}
