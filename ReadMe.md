# Java Lab: Swing Applications & JDBC

A collection of seven Java programs covering object-oriented programming (abstract classes), desktop GUI development with **Java Swing**, and database access with **JDBC**. Built with IntelliJ IDEA on Linux; runs on Windows and macOS too.

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![MariaDB](https://img.shields.io/badge/Database-MariaDB%2FMySQL-003545?logo=mariadb)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA-black?logo=intellijidea)

---

## Table of Contents

1. [Projects Overview](#projects-overview)
2. [Tech Stack](#tech-stack)
3. [Prerequisites & Installation (Linux)](#prerequisites--installation-linux)
4. [Getting the Project](#getting-the-project)
5. [Opening & Running in IntelliJ IDEA](#opening--running-in-intellij-idea)
6. [Database Setup (Q7)](#database-setup-q7)
7. [Running from the Terminal](#running-from-the-terminal)
8. [Project Structure](#project-structure)
9. [Business Rules & Assumptions](#business-rules--assumptions)
10. [Troubleshooting](#troubleshooting)
11. [Author](#author)

---

## Projects Overview

| # | File | Description | Type |
|---|------|-------------|------|
| Q1 | `Q1_AbstractDemo.java` | Abstract class with one abstract method `add` and one concrete method `multiply` | Console |
| Q2 | `Q2_CalendarYear.java` | Displays a full 12-month calendar for any year | Swing |
| Q3 | `Q3_BankingSystem.java` | Banking system with loan eligibility check and 6-month interest calculation | Swing |
| Q4 | `Q4_ScientificCalculator.java` | Scientific calculator (trigonometry, logarithms, powers, factorial, etc.) | Swing |
| Q5 | `Q5_StudentTranscript.java` | Generates a student transcript with grades, GPA and pass/fail remark | Swing |
| Q6 | `Q6_AddNumbers.java` | Adds at least four numbers with Add, Total, Reset, Clear and Exit buttons | Swing |
| Q7 | `Q7_StudentRecords.java` + `Q7_schema.sql` | Reads student records (5 subjects) from a database, assigns grades A to F and filters by name | JDBC |

### Feature Highlights

- **Q2:** Year selector, Sundays in red, today's date highlighted.
- **Q3:** Loan is approved only if the account balance is at least 10,000 RWF and the loan is at most 60,000 RWF. Shows interest, total to repay and monthly instalment.
- **Q4:** sin / cos / tan (in degrees), log, ln, square root, x², 1/x, x^y, n!, π, e, backspace and clear.
- **Q5:** Editable marks table, validation (0 to 100), per-subject grade, overall grade, GPA out of 4.0.
- **Q6:** Input validation, minimum of four numbers before totalling.
- **Q7:** Grade bands, ordered results and `J` / `M` name filters.

---

## Tech Stack

- **Language:** Java 17 or newer
- **GUI:** Java Swing (`javax.swing`, `java.awt`)
- **Database:** MariaDB or MySQL
- **JDBC Driver:** MySQL Connector/J 8.4.0
- **IDE:** IntelliJ IDEA (Community or Ultimate)

---

## Prerequisites & Installation (Linux)

The commands below target **Ubuntu / Debian**. For other distributions, use the equivalent package manager.

### 1. Install Git

```bash
sudo apt update
sudo apt install -y git
git --version
```

### 2. Install the JDK

```bash
sudo apt install -y default-jdk
java -version
javac -version
```

Both commands should print a version of 17 or higher. If your repository ships an older version, install `openjdk-17-jdk` instead.

### 3. Install IntelliJ IDEA

**Option A: Snap (easiest)**

```bash
sudo snap install intellij-idea-community --classic
```

**Option B: Manual download**

1. Download IntelliJ IDEA Community from <https://www.jetbrains.com/idea/download/?section=linux>.
2. Extract it:
   ```bash
   sudo tar -xzf ideaIC-*.tar.gz -C /opt/
   ```
3. Launch it with `/opt/idea-IC-*/bin/idea.sh`.

### 4. Install MariaDB (database server for Q7)

```bash
sudo apt install -y mariadb-server
sudo systemctl enable --now mariadb
sudo systemctl status mariadb
```

### 5. Download the MySQL JDBC Driver

```bash
mkdir -p lib
curl -L -o lib/mysql-connector-j-8.4.0.jar \
  https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar
```

### Windows / macOS (quick notes)

- **Windows:** install the JDK from <https://adoptium.net>, MariaDB from <https://mariadb.org/download>, and IntelliJ from JetBrains.
- **macOS:** `brew install openjdk mariadb git` and `brew install --cask intellij-idea-ce`.

---

## Getting the Project

```bash
git clone https://github.com/Manzikevin/java-lab.git
cd java-lab
```

Or download the ZIP from GitHub (**Code → Download ZIP**) and extract it.

---

## Opening & Running in IntelliJ IDEA

1. Open IntelliJ and choose **File → Open**, then select the `java-lab` folder.
2. Set the SDK: **File → Project Structure → Project → SDK**, and choose your JDK (17 or higher).
3. Mark the source folder (if the `.java` files are in the project root, mark the root as **Sources**): right-click the folder, then **Mark Directory as → Sources Root**.
4. Add the JDBC driver (needed for Q7 only):
    - **File → Project Structure → Libraries → + → From Maven**
    - Enter `com.mysql:mysql-connector-j:8.4.0` and click **OK → Apply**.
    - Alternatively choose **+ → Java** and select `lib/mysql-connector-j-8.4.0.jar`.
5. To run any program, open its file, then click the green ▶ next to `main`, or right-click the file and choose **Run**.

> **Tip:** IntelliJ also ships a drag-and-drop designer for Swing (**New → Swing UI Designer → GUI Form**), the equivalent of Eclipse's WindowBuilder.

---

## Database Setup (Q7)

### 1. Create the database and sample data

```bash
sudo mysql < Q7_schema.sql
```

This creates the `school` database, the `students` table and 10 sample students (including names that start with `J` and `M`).

### 2. Create a database user

MariaDB's `root` account often cannot log in from Java with an empty password, so create a dedicated user:

```bash
sudo mysql -e "CREATE USER 'goodman'@'localhost' IDENTIFIED BY 'pass123'; \
GRANT ALL ON school.* TO 'goodman'@'localhost'; FLUSH PRIVILEGES;"
```

### 3. Configure the connection

Open `Q7_StudentRecords.java` and update these constants to match your setup:

```java
static final String URL  = "jdbc:mysql://localhost:3306/school";
static final String USER = "goodman";
static final String PASS = "pass123";
```

### 4. Run

Run `Q7_StudentRecords` from IntelliJ. Expected output: a table of all students with averages and grades, then students grouped by grade, then the `J` and `M` name lists.

### Grading scale

| Average | Grade |
|---------|-------|
| 85% and above | **A** |
| 75% – 84% | **B** |
| 60% – 74% | **C** |
| 50% – 59% | **D** |
| Below 50% | **F** |

---

## Running from the Terminal

From the project root:

```bash
mkdir -p out
javac -cp "lib/*" -d out *.java
```

Run any program:

```bash
java -cp out Q1_AbstractDemo
java -cp out Q2_CalendarYear
java -cp out Q3_BankingSystem
java -cp out Q4_ScientificCalculator
java -cp out Q5_StudentTranscript
java -cp out Q6_AddNumbers
java -cp "out:lib/*" Q7_StudentRecords
```

On Windows, replace `:` with `;` in the classpath (`"out;lib/*"`).

---

## Project Structure

```
java-lab/
├── Q1_AbstractDemo.java
├── Q2_CalendarYear.java
├── Q3_BankingSystem.java
├── Q4_ScientificCalculator.java
├── Q5_StudentTranscript.java
├── Q6_AddNumbers.java
├── Q7_StudentRecords.java
├── Q7_schema.sql
├── lib/
│   └── mysql-connector-j-8.4.0.jar
└── README.md
```

---

## Business Rules & Assumptions

- **Q3 (Loan):**
    - Minimum account balance: **10,000 RWF**
    - Maximum loan: **60,000 RWF**
    - Repayment period: **6 months**
    - Interest: **5% flat** on the loan amount, so a 60,000 RWF loan pays 3,000 RWF interest (63,000 RWF total, 10,500 RWF per month).
- **Q6:** *Reset* clears the input box only; *Clear* removes all stored numbers.
- **Q7:** Average is the mean of five subject marks (each out of 100). 85% exactly counts as **A**.

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `No suitable driver found for jdbc:mysql://...` | The MySQL Connector/J jar is missing from the classpath. Add it as a library (see [Opening & Running in IntelliJ IDEA](#opening--running-in-intellij-idea), step 4). |
| `Communications link failure` | The database server is not running. Start it with `sudo systemctl start mariadb`. |
| `Unknown database 'school'` | Run `sudo mysql < Q7_schema.sql`. |
| `Access denied for user 'root'@'localhost'` | Create a dedicated user (see [Database Setup](#database-setup-q7), step 2) and update `USER` / `PASS`. |
| `Column count of mysql.proc is wrong ... use mysql_upgrade` | Run `sudo mariadb-upgrade` (or `sudo mysql_upgrade`), then `sudo systemctl restart mariadb`. |
| `javac: command not found` | Install the JDK: `sudo apt install default-jdk`. |
| Swing window does not appear over SSH or a headless server | Run on a machine with a graphical desktop. Swing needs a display. |

---

## Author

**Goodman**
GitHub: [@Manzikevin](https://github.com/Manzikevin)

---

## License

This project was created for educational purposes as a coursework submission.