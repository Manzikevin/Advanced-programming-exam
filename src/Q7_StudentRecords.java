import java.sql.*;
import java.util.*;

// Q7: JDBC student records (5 subjects) with grades and name filters
// Grades: >=85 A | 75-84 B | 60-74 C | 50-59 D | below 50 F
public class Q7_StudentRecords {
    static final String URL = "jdbc:mysql://localhost:3306/school";
    static final String USER = "root";
    static final String PASS = "";

    static String grade(double avg) {
        if (avg >= 85) return "A";
        if (avg >= 75) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }

    public static void main(String[] args) {
        String sql = "SELECT name, sub1, sub2, sub3, sub4, sub5, " +
                     "(sub1+sub2+sub3+sub4+sub5)/5.0 AS avg FROM students ORDER BY avg DESC";
        Map<String, List<String>> byGrade = new LinkedHashMap<>();
        for (String g : new String[]{"A", "B", "C", "D", "F"}) byGrade.put(g, new ArrayList<>());
        List<String> startJ = new ArrayList<>(), startM = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.printf("%-10s %5s %5s %5s %5s %5s %8s %6s%n",
                    "Name", "S1", "S2", "S3", "S4", "S5", "Average", "Grade");
            while (rs.next()) {
                String name = rs.getString("name");
                double avg = rs.getDouble("avg");
                String g = grade(avg);
                System.out.printf("%-10s %5d %5d %5d %5d %5d %7.1f%% %6s%n", name,
                        rs.getInt("sub1"), rs.getInt("sub2"), rs.getInt("sub3"),
                        rs.getInt("sub4"), rs.getInt("sub5"), avg, g);
                byGrade.get(g).add(name + " (" + String.format("%.1f", avg) + "%)");
                if (name.toUpperCase().startsWith("J")) startJ.add(name);
                if (name.toUpperCase().startsWith("M")) startM.add(name);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return;
        }

        System.out.println("\n=== Students by grade ===");
        System.out.println("A (85% and above): " + byGrade.get("A"));
        System.out.println("B (75% - 84%)    : " + byGrade.get("B"));
        System.out.println("C (60% - 74%)    : " + byGrade.get("C"));
        System.out.println("D (50% - 59%)    : " + byGrade.get("D"));
        System.out.println("F (below 50%)    : " + byGrade.get("F"));

        System.out.println("\n=== Names starting with 'J' ===");
        startJ.forEach(n -> System.out.println(" - " + n));
        System.out.println("\n=== Names starting with 'M' ===");
        startM.forEach(n -> System.out.println(" - " + n));
    }
}
