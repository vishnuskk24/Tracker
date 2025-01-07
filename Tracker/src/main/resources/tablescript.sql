        -- Drop and recreate the database
DROP DATABASE IF EXISTS tracker2;
CREATE DATABASE tracker2;
USE tracker2;

-- Create Employee table
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(255),
    username VARCHAR(60) UNIQUE,
    mail_id VARCHAR(50),
    status VARCHAR(15),
    password VARCHAR(255),
    role VARCHAR(30)
);

-- Create Activity table
CREATE TABLE Activity (
    activity_id INT PRIMARY KEY AUTO_INCREMENT,
    activity_name VARCHAR(60),
    start_date DATE,
    end_date DATE,
    assigned_by VARCHAR(20),
    no_of_times_updated INT,
    remarks VARCHAR(255),
    activity_status VARCHAR(20),
    e_id INT,
    FOREIGN KEY (e_id) REFERENCES Employee(employee_id)
);

-- Insert sample data into Employee table
INSERT INTO Employee (employee_id, employee_name, username, mail_id, status, password, role)
VALUES 
    (1, 'John Doe', 'johndoe', 'johndoe@example.com', 'Active', 'password123', 'Manager'),
    (2, 'Jane Smith', 'janesmith', 'janesmith@example.com', 'Deactive', 'password456', 'Developer'),
    (3, 'Alice Brown', 'alicebrown', 'alicebrown@example.com', 'Active', 'password789', 'Tester'),
    (4, 'Michael Green', 'michaelgreen', 'michaelgreen@example.com', 'Active', 'password123', 'Developer'),
    (5, 'Sophia White', 'sophiawhite', 'sophiawhite@example.com', 'Active', 'password456', 'HR'),
    (6, 'Liam Black', 'liamblack', 'liamblack@example.com', 'Deactive', 'password789', 'Tester'),
    (7, 'Olivia Gray', 'oliviagray', 'oliviagray@example.com', 'Active', 'password321', 'Manager'),
    (8, 'William Blue', 'williambue', 'williambue@example.com', 'Active', 'password654', 'Developer'),
    (9, 'Emma Yellow', 'emmayellow', 'emmayellow@example.com', 'Deactive', 'password987', 'Support'),
    (10, 'James Red', 'jamesred', 'jamesred@example.com', 'Active', 'password159', 'Sales');

-- Insert sample data into Activity table (with foreign key reference to Employee)
INSERT INTO Activity (activity_name, start_date, end_date, assigned_by, no_of_times_updated, remarks, activity_status, e_id)
VALUES 
    ('Project Kickoff', '2025-01-01', '2025-01-02', 'John Doe', 3, 'Initial planning phase', 'In_Progress', 1),
    ('Code Review', '2025-01-03', '2025-01-05', 'Jane Smith', 2, 'Reviewing feature implementation', 'Completed', 2),
    ('Testing', '2025-01-06', '2025-01-07', 'Alice Brown', 1, 'Bug fixing and testing', 'Not_Yet_Started', 3),
    ('System Upgrade', '2025-01-10', '2025-01-12', 'Michael Green', 4, 'Upgrading system software', 'In_Progress', 4),
    ('HR Training', '2025-01-13', '2025-01-14', 'Sophia White', 1, 'Employee onboarding training', 'Completed', 5),
    ('Bug Fixes', '2025-01-15', '2025-01-16', 'Liam Black', 5, 'Fixing critical bugs in module A', 'Not_Yet_Started', 6),
    ('Team Meeting', '2025-01-17', '2025-01-17', 'Olivia Gray', 3, 'Discussing project updates and plans', 'In_Progress', 7),
    ('API Integration', '2025-01-18', '2025-01-20', 'William Blue', 2, 'Integrating third-party API', 'In_Progress', 8),
    ('Customer Support Training', '2025-01-21', '2025-01-23', 'Emma Yellow', 1, 'Training support staff', 'Completed', 9),
    ('Sales Presentation', '2025-01-24', '2025-01-25', 'James Red', 4, 'Preparing and presenting sales pitch', 'In_Progress', 10),
    ('Database Backup', '2025-01-26', '2025-01-26', 'John Doe', 2, 'Running backup on database server', 'Completed', 1),
    ('Code Refactoring', '2025-01-28', '2025-01-29', 'Jane Smith', 6, 'Refactoring legacy code for optimization', 'In_Progress', 2),
    ('Feature Demo', '2025-01-30', '2025-01-31', 'Alice Brown', 3, 'Presenting new feature demo', 'Not_Yet_Started', 3),
    ('Client Call', '2025-02-01', '2025-02-01', 'Olivia Gray', 1, 'Discussing project scope with client', 'Completed', 7),
    ('Bug Fix Testing', '2025-02-02', '2025-02-04', 'Liam Black', 4, 'Testing after bug fixes', 'In_Progress', 6),
    ('Product Launch', '2025-02-05', '2025-02-07', 'William Blue', 2, 'Launching new product in the market', 'Completed', 8),
    ('Employee Feedback', '2025-02-08', '2025-02-09', 'Sophia White', 1, 'Collecting feedback from employees', 'Not_Yet_Started', 5),
    ('Server Maintenance', '2025-02-10', '2025-02-10', 'Michael Green', 3, 'Performing server maintenance tasks', 'Completed', 4),
    ('Monthly Report', '2025-02-12', '2025-02-12', 'James Red', 5, 'Preparing monthly sales report', 'In_Progress', 10);
