CREATE DATABASE mysql_task;
USE mysql_task;
-- BANKING SCENARIO TABLES
-- Customers Table
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    city VARCHAR(50)
);
-- Accounts Table
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_type VARCHAR(20),
    balance DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
-- Data Insertion
INSERT INTO customers (name, city) VALUES
('Janani','Chennai'),
('Arun','Coimbatore'),
('Priya','Madurai'),
('Karthik','Salem');

INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1,'Savings',50000),
(1,'Current',20000),
(2,'Savings',30000),
(3,'Savings',15000),
(4,'Current',40000);
SELECT * FROM customers;

SELECT * FROM accounts;

-- Questions On Basic+ Where+ Operators(1-15) ---

-- 1.	Retrieve all accounts with balance greater than 20,000. 
SELECT * FROM accounts WHERE balance >20000;

-- 2.	Find customers who live in Chennai. 
SELECT * FROM customers WHERE city='chennai';

-- 3.	Display accounts with balance between 20,000 and 50,000.
SELECT * FROM accounts WHERE balance BETWEEN 20000 AND 50000;

-- 4.	Find customers whose names start with 'J'. 
SELECT * FROM customers WHERE name LIKE'J%';

-- 5.	Retrieve accounts of type 'Savings' or 'Current'. 
SELECT * FROM accounts WHERE account_type='Savings' OR account_type='Current';

-- 6.	Display accounts that are not 'Savings'. 
SELECT * FROM accounts WHERE account_type NOT IN('Savings');

-- 7.	Find customers whose names contain the letter 'a'. 
SELECT * FROM customers WHERE name LIKE'%a%';

-- 8.	Retrieve accounts with balance less than or equal to 30,000. 
SELECT * FROM accounts WHERE balance <=30000;

-- 9.	Find customers who are not from Madurai. 
SELECT * FROM customers WHERE city NOT IN('madurai');

-- 10.	Display accounts where balance is not between 10,000 and 40,000.
SELECT * FROM accounts WHERE balance  NOT BETWEEN 10000 AND 40000;

-- 11.	Retrieve customers whose names end with 'i'. 
SELECT * FROM customers WHERE name LIKE'%i';

-- 12.	Find accounts with balance equal to 50,000. 
SELECT * FROM accounts WHERE balance =50000;

-- 13.	Display customers whose city is either Chennai or Salem. 
SELECT * FROM customers WHERE city IN('Chennai','Salem');

-- 14.	Find accounts with balance greater than 10,000 and less than 40,000. 
SELECT * FROM accounts WHERE balance >10000 AND balance < 40000;

-- 15.	Retrieve accounts where account type is not in ('Current'). 
SELECT * FROM accounts WHERE account_type NOT IN('Current');

-- ORDER BY (16 TO 18)--

-- 16.	Display all accounts sorted by balance in descending order.  
SELECT * FROM accounts ORDER BY balance DESC;

-- 17.	List customers sorted alphabetically by name. 
SELECT * FROM customers ORDER BY name ASC;

-- 18.	Display accounts sorted by account type and then by balance (descending). 
SELECT * FROM accounts ORDER BY account_type ,balance DESC;

-- AGGREGATE FUNCTIONS (19 TO 23) --

-- 19.	Find the total balance of all accounts. 
SELECT SUM(balance) AS total_balance FROM accounts;

-- 20.	Calculate the average balance of accounts. 
SELECT AVG(balance) AS avg_balance FROM accounts;

-- 21.	Find the maximum account balance. 
SELECT MAX(balance) AS max_balance FROM accounts;

-- 22.	Find the minimum account balance. 
SELECT MIN(balance) AS min_balance FROM accounts;

-- 23.	Count the total number of customers. 
SELECT COUNT(*) AS Noof_customers FROM customers;

-- GROUP BY + HAVING (24 TO 28) --

-- 24.	Find total balance grouped by account type.
SELECT account_type , SUM(balance) FROM accounts GROUP BY account_type;

-- 25.	Find average balance for each account type. 
SELECT account_type , AVG(balance) FROM accounts GROUP BY account_type;

-- 26.	Display account types having average balance greater than 20,000. 
SELECT account_type , AVG(balance) FROM accounts GROUP BY account_type HAVING AVG(balance)>30000;

-- 27.	Count number of accounts for each customer. 
SELECT  customer_id, COUNT(*) AS total_accounts FROM accounts GROUP BY customer_id;

-- 28.	Display customers having more than one account. 
SELECT  customer_id, COUNT(*) AS total_accounts FROM accounts GROUP BY customer_id HAVING COUNT(*)>1;

-- JOINS (29 TO 35)

-- 29.	Retrieve customer names along with their account balances. 
SELECT c.name,a.balance FROM customers c INNER JOIN accounts a ON c.customer_id=a.account_id;

-- 30.	Display all customers and their accounts (including customers without accounts). 
SELECT c.name,a.account_id,a.account_type,a.balance FROM customers c LEFT JOIN accounts a ON c.customer_id=a.account_id;

-- 31.	Display all accounts and corresponding customer details. 
SELECT a.account_id, a.account_type, a.balance,c.customer_id, c.name, c.city FROM accounts a JOIN customers c
ON a.customer_id = c.customer_id;

-- 32.	Retrieve customer names and account types where balance is greater than 20,000. 
SELECT c.name,a.account_type,a.balance FROM customers c  JOIN accounts a ON c.customer_id=a.account_id WHERE a.balance>20000;

-- 33.	List customers with their total balance using JOIN. 
SELECT c.name, SUM(a.balance) AS total_balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, c.name;

-- 34.	Display customer names and balances sorted by balance. 
SELECT c.name,a.balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id ORDER BY balance;

-- 35.	Count number of accounts for each city using JOIN. 
SELECT c.city,COUNT(a.account_id) FROM customers c JOIN accounts a ON c.customer_id = a.customer_id GROUP BY c.city;

-- SUBQUERIES (36 TO 40) --

-- 36.	Find accounts with balance greater than average balance. 
SELECT account_id,balance FROM accounts WHERE balance>(SELECT AVG(balance) FROM accounts);

-- 37.	Retrieve customers who have accounts. 
SELECT  name FROM customers WHERE customer_id IN (SELECT customer_id FROM accounts);

-- 38.	Find customers who do not have any accounts. 
SELECT  name FROM customers WHERE customer_id NOT IN (SELECT customer_id FROM accounts);

-- 39.	Display account(s) with the maximum balance. 
SELECT * FROM accounts WHERE balance =(SELECT MAX(balance) FROM accounts);

-- 40.	Find customers whose total balance is greater than 40,000. 
SELECT name FROM customers WHERE customer_id IN (SELECT customer_id FROM accounts GROUP BY customer_id
HAVING SUM(balance) > 40000);

-- RAILWAY RESERVATION SCENARIO
-- Trains Table
CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50)
);
-- Bookings Table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_name VARCHAR(50),
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);
-- DATA INSERTION ----
INSERT INTO trains (train_name, source, destination) VALUES
('Express1','Chennai','Madurai'),
('Express2','Coimbatore','Salem'),
('Express3','Madurai','Chennai');

INSERT INTO bookings (train_id, passenger_name, fare, status) VALUES
(1,'Janani',500,'Confirmed'),
(1,'Arun',500,'Waiting'),
(2,'Priya',300,'Confirmed'),
(3,'Karthik',450,'Cancelled'),
(2,'Meena',300,'Confirmed');
SELECT * FROM trains;
SELECT * FROM bookings;

-- QUESTIONS ON RAIL WAY RESERVATION --
-- BASIC + WHERE + OPERATORS (41 TO 45) ---

-- 41.	Retrieve all bookings with fare greater than 400. 
SELECT * FROM bookings WHERE fare>400;

-- 42.	Find bookings where status is not 'Confirmed'. 
SELECT * FROM bookings WHERE status NOT IN('Confirmed');

-- 43.	Display trains starting from Chennai. 
SELECT * FROM trains WHERE source='Chennai';

-- 44.	Retrieve bookings with fare between 300 and 500. 
SELECT * FROM bookings WHERE fare BETWEEN 300 AND 500;

-- 45.	Find passengers whose names start with 'A'. 
SELECT * FROM bookings WHERE passenger_name LIKE'A%';

-- JOINS + GROUP BY + ORDER BY (46 TO 48) --

-- 46.	Retrieve train names along with passenger names.
SELECT t.train_name,b.passenger_name FROM trains t JOIN bookings b ON t.train_id=b.train_id;

-- 47.	Count number of bookings for each train. 
SELECT t.train_name, COUNT(b.booking_id) AS total_bookings FROM trains t JOIN bookings b
ON t.train_id = b.train_id GROUP BY t.train_id, t.train_name;

-- 48.	Display train names and total fare collected for each train. 
SELECT t.train_name, SUM(b.fare) AS total_fare FROM trains t JOIN bookings b
ON t.train_id = b.train_id GROUP BY t.train_id, t.train_name;

-- SUBQUERIES (49 TO 50)--

-- 49.	Find bookings with fare equal to the highest fare.
SELECT * FROM bookings WHERE fare=(SELECT MAX(fare) FROM bookings);

-- 50.	Retrieve trains that have more than one booking. 
SELECT * FROM trains WHERE train_id IN(SELECT train_id FROM bookings GROUP BY 
train_id HAVING COUNT(booking_id)>1);

-- EMPLOYEE MANAGEMENT --

CREATE TABLE Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);
INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18');
SELECT * FROM employee;

-- QUESTIONS (1 TO 15) --

-- 1.	Find the total number of employees in each department. 
SELECT department,COUNT(*) FROM employee GROUP BY department;

-- 2.	Find the average salary of employees in each department. 
SELECT department,AVG(salary) FROM employee GROUP BY department;

-- 3.	Display departments having more than one employee. 
SELECT department,COUNT(*) FROM employee GROUP BY department HAVING COUNT(*)>1;

-- 4.	Find the highest salary in each department. 
SELECT department,MAX(salary) FROM employee GROUP BY department;

-- 5.	Find the lowest salary in each department. 
SELECT department,MIN(salary) FROM employee GROUP BY department;

-- 6.	Find departments whose average salary is greater than 50,000. 
SELECT department,AVG(salary) FROM employee GROUP BY department HAVING AVG(salary)>50000;

-- 7.	Calculate the total salary expenditure for each department. 
SELECT department,SUM(salary) FROM employee GROUP BY department;

-- 8.	Display all employees sorted by salary in descending order. 
SELECT * FROM employee ORDER BY salary DESC;

-- 9.	Display employees sorted first by department and then by salary in descending order. 
SELECT * FROM employee ORDER BY department ,salary DESC;

-- 10.	Find cities that have more than one employee. 
SELECT city,COUNT(emp_name) AS noof_employees FROM employee GROUP BY city HAVING COUNT(emp_name)>1;

-- 11.	Find the total salary paid in each city. 
SELECT city,SUM(salary) AS total_salary FROM employee GROUP BY city;

-- 12.	Display departments ordered by total salary expenditure from highest to lowest. 
SELECT department,SUM(salary) FROM employee  GROUP BY department ORDER BY SUM(salary) DESC ;

-- 13.	Find the number of employees in each department whose salary is greater than 50,000. 
SELECT department ,COUNT(*) FROM employee WHERE salary >50000 GROUP BY department;

-- 14.	Find the difference between the highest and lowest salary in each department. 
SELECT department,MAX(salary),MIN(salary),MAX(salary)-MIN(salary) AS difference FROM employee GROUP BY department;

-- 15.	Display the top 3 highest-paid employees.
SELECT * FROM employee ORDER BY salary DESC LIMIT 3;

-- CUSTOMER AND ORDER MANAGEMENT----
-- CUSTOMER TABLE

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(50),
    city VARCHAR(30)
);
-- ORDERS TABLE

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    order_date DATE,
    FOREIGN KEY(customer_id) REFERENCES Customers(customer_id)
);
-- SAMPLE DATA INSERTION ---
INSERT INTO Orders (order_id, customer_id, amount, order_date) VALUES
(101, 1, 2500.00, '2026-01-10'),
(102, 1, 1500.00, '2026-01-15'),
(103, 2, 3000.00, '2026-01-20'),
(104, 2, 1200.00, '2026-02-05'),
(105, 3, 4500.00, '2026-02-10'),
(106, 4, 1800.00, '2026-02-15'),
(107, 3, 2000.00, '2026-03-01');

SELECT * FROM customers;
SELECT * FROM orders;
-- QUESTIONS (16 TO 30)--

-- 16.	Find the total order amount for each customer. 
SELECT c.name, SUM(o.amount) AS total_amount FROM customers c JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.name;

-- 17. Find customers who have placed more than 3 orders
SELECT c.name, COUNT(o.order_id) AS total_orders FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name HAVING COUNT(o.order_id) >3;

-- 18. Find the average order amount for each customer
SELECT c.name, AVG(o.amount) AS average_amount FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name;

-- 19. Find the highest order amount placed by each customer
SELECT c.name, MAX(o.amount) AS highest_order FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name;

-- 20. Display customers sorted by their total purchase amount
SELECT c.name, SUM(o.amount) AS total_purchase FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name ORDER BY total_purchase DESC;

-- 21. Find customers whose total purchase amount exceeds 10,000
SELECT c.name, SUM(o.amount) AS total_purchase FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name HAVING SUM(o.amount) > 10000;

-- 22. Display customer names along with the total number of orders placed
SELECT c.name, COUNT(o.order_id) AS total_orders FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name;

-- 23. Find the customer who spent the highest amount
SELECT c.name, SUM(o.amount) AS total_spent FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name ORDER BY total_spent DESC
LIMIT 1;

-- 24. Find the customer who placed the maximum number of orders
SELECT c.name, COUNT(o.order_id) AS total_orders FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name
ORDER BY total_orders DESC LIMIT 1;

-- 25. Find customers whose average order amount is greater than 2,000
SELECT c.name, AVG(o.amount) AS average_amount FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name HAVING AVG(o.amount) > 2000;

-- 26. Display the top 5 customers based on total purchase amount
SELECT c.name, SUM(o.amount) AS total_purchase FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name
ORDER BY total_purchase DESC LIMIT 5;

-- 27. Find the minimum order amount for each customer
SELECT c.name, MIN(o.amount) AS minimum_order FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name;

-- 28. Find customers who have placed orders worth more than 5,000 in total
SELECT c.name, SUM(o.amount) AS total_purchase FROM customers c JOIN orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name HAVING SUM(o.amount) > 5000;

-- 29. Display customer-wise total orders and total purchase amount
SELECT c.name, COUNT(o.order_id) AS total_orders, SUM(o.amount) AS total_purchase
FROM customers c JOIN orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name;

-- 30. Find customers who placed more than 2 orders and spent more than 8,000
SELECT c.name, COUNT(o.order_id) AS total_orders, SUM(o.amount) AS total_purchase FROM customers c
JOIN orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.name
HAVING COUNT(o.order_id) > 2 AND SUM(o.amount) > 8000;
   
  -- STUDENTS TABLE --
  
CREATE TABLE Students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT
);
INSERT INTO Students (student_name, department, marks) VALUES
('Arun', 'CSE', 85),
('Priya', 'CSE', 92),
('Karthik', 'ECE', 78),
('Janani', 'ECE', 88),
('Meena', 'EEE', 72),
('Ravi', 'CSE', 65),
('Divya', 'EEE', 95),
('Suresh', 'MECH', 80),
('Anjali', 'MECH', 68),
('Vijay', 'CSE', 75);
SELECT * FROM students;

-- QUESTIONS -- 

-- 31. Find the average marks scored by students in each department
SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department;

-- 32. Find departments whose average marks are above 75
SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department
HAVING AVG(marks) > 75;

-- 33. Find the highest mark scored in each department
SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department;

-- 34. Find the total number of students in each department
SELECT department, COUNT(student_id) AS total_students FROM Students GROUP BY department;

-- 35. Find departments having more than 5 students
SELECT department, COUNT(student_id) AS total_students FROM Students GROUP BY department
HAVING COUNT(student_id) > 5;

-- 36. Display departments sorted by average marks in descending order
SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department
ORDER BY average_marks DESC;

-- 37. Find the top 3 departments based on average marks
SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department
ORDER BY average_marks DESC LIMIT 3;

-- 38. Find departments whose average marks are between 70 and 90
SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department
HAVING AVG(marks) BETWEEN 70 AND 90;

-- 39. Find the total marks scored by students in each department
SELECT department, SUM(marks) AS total_marks FROM Students GROUP BY department;

-- 40. Display departments sorted by the total number of students
SELECT department, COUNT(student_id) AS total_students FROM Students GROUP BY department
ORDER BY total_students DESC;

-- 41. Find the lowest mark scored in each department
SELECT department, MIN(marks) AS lowest_marks FROM Students GROUP BY department;

-- 42. Find departments where the highest mark is greater than 90
SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department
HAVING MAX(marks) > 90;

-- 43. Find the number of students scoring above 80 in each department
SELECT department, COUNT(student_id) AS students_above_80 FROM Students WHERE marks > 80
GROUP BY department;

-- 44. Find departments where more than 3 students scored above 75
SELECT department, COUNT(student_id) AS students_above_75 FROM Students WHERE marks >75
GROUP BY department HAVING COUNT(student_id) > 3;

-- 45. Display departments ordered by highest mark in descending order
SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department
ORDER BY highest_marks DESC;
use mysql_task;













