CREATE DATABASE IF NOT EXISTS school;
USE school;
DROP TABLE IF EXISTS students;
CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  sub1 INT, sub2 INT, sub3 INT, sub4 INT, sub5 INT   -- five subjects, each /100
);
INSERT INTO students (name, sub1, sub2, sub3, sub4, sub5) VALUES
('John',   92, 88, 90, 85, 95),
('Jane',   78, 80, 76, 82, 79),
('Joseph', 65, 70, 62, 68, 71),
('Mary',   55, 58, 52, 60, 54),
('Michael',88, 91, 84, 90, 87),
('Mugisha',72, 66, 70, 74, 68),
('Alice',  45, 50, 48, 52, 40),
('Eric',   80, 77, 79, 81, 76),
('Grace',  58, 51, 55, 57, 53),
('Peter',  62, 64, 66, 61, 69);
