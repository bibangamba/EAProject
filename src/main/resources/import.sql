-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 14, 2024 at 09:54 PM
-- Server version: 8.3.0
-- PHP Version: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


-- Inserting data into location_type table
INSERT INTO location_type (id, created_on, updated_on, created_by, type, updated_by) VALUES
                                                                                         (1, NOW(), NOW(), 'admin', 'CLASSROOM', 'admin'),
                                                                                         (2, NOW(), NOW(), 'admin', 'LAB', 'admin'),
                                                                                         (3, NOW(), NOW(), 'admin', 'OFFICE', 'admin');

-- Inserting data into location table
INSERT INTO location (id, capacity, type_id, created_on, updated_on, created_by, name, updated_by) VALUES
                                                                                                       (1, 30, 1, NOW(), NOW(), 'admin', 'Room 101', 'admin'),
                                                                                                       (2, 20, 2, NOW(), NOW(), 'admin', 'Lab 1', 'admin'),
                                                                                                       (3, 5, 3, NOW(), NOW(), 'admin', 'Office 1', 'admin');

-- Inserting data into person table
INSERT INTO person (id, birthdate, created_on, updated_on, created_by, email_address, first_name, last_name, updated_by, gender_type) VALUES
                                                                                                                                          (1, '1980-01-01', NOW(), NOW(), 'admin', 'john.doe@example.com', 'John', 'Doe', 'admin', 'MALE'),
                                                                                                                                          (2, '1985-05-15', NOW(), NOW(), 'admin', 'jane.smith@example.com', 'Jane', 'Smith', 'admin', 'FEMALE'),
                                                                                                                                          (3, '1990-09-25', NOW(), NOW(), 'admin', 'bob.jones@example.com', 'Bob', 'Jones', 'admin', 'MALE'),
                                                                                                                                          (4, '1992-11-30', NOW(), NOW(), 'admin', 'alice.brown@example.com', 'Alice', 'Brown', 'admin', 'FEMALE'),
                                                                                                                                          (5, '1988-07-22', NOW(), NOW(), 'admin', 'chris.green@example.com', 'Chris', 'Green', 'admin', 'MALE'),
                                                                                                                                          (6, '1995-03-18', NOW(), NOW(), 'admin', 'dave.white@example.com', 'Dave', 'White', 'admin', 'MALE'),
                                                                                                                                          (7, '1993-12-12', NOW(), NOW(), 'admin', 'eva.black@example.com', 'Eva', 'Black', 'admin', 'FEMALE');

-- Inserting data into faculty table
INSERT INTO faculty (id, salutation) VALUES
                                         (6, 'Dr.'),
                                         (7, 'Prof.');

-- Inserting data into faculty_hobby table
INSERT INTO faculty_hobby (faculty_id, hobbies) VALUES
                                                    (6, 'Reading'),
                                                    (7, 'Hiking');

-- Inserting data into student table
INSERT INTO student (id, entry, faculty_adviserid, alternateid, applicantid, studentid) VALUES
                                                                                            (1, '2023-01-01', 6, 'ALT001', 'APP001', 'STU001'),
                                                                                            (2, '2023-01-01', 7, 'ALT002', 'APP002', 'STU002'),
                                                                                            (3, '2023-01-01', 6, 'ALT003', 'APP003', 'STU003'),
                                                                                            (4, '2023-01-01', 7, 'ALT004', 'APP004', 'STU004'),
                                                                                            (5, '2023-01-01', 6, 'ALT005', 'APP005', 'STU005');

-- Inserting data into course table
INSERT INTO course (id, credits, created_on, updated_on, course_code, course_description, course_name, created_by, department, updated_by) VALUES
                                                                                                                                               (1, 3.0, NOW(), NOW(), 'CS101', 'Introduction to Computer Science', 'Intro to CS', 'admin', 'Computer Science', 'admin'),
                                                                                                                                               (2, 4.0, NOW(), NOW(), 'MATH201', 'Calculus II', 'Calculus 2', 'admin', 'Mathematics', 'admin'),
                                                                                                                                               (3, 3.5, NOW(), NOW(), 'PHYS101', 'Introduction to Physics', 'Intro to Physics', 'admin', 'Physics', 'admin'),
                                                                                                                                               (4, 4.0, NOW(), NOW(), 'CHEM101', 'General Chemistry', 'Gen Chem', 'admin', 'Chemistry', 'admin'),
                                                                                                                                               (5, 2.0, NOW(), NOW(), 'HIST101', 'World History', 'World History', 'admin', 'History', 'admin');

-- Inserting data into course_offering table
INSERT INTO course_offering (id, capacity, courseid, credits, facultyid, created_on, updated_on, created_by, room, updated_by, course_offering_type) VALUES
                                                                                                                                                         (1, 25, 1, 3.0, 6, NOW(), NOW(), 'admin', 'Room 101', 'admin', 'FULL_TIME'),
                                                                                                                                                         (2, 20, 2, 4.0, 7, NOW(), NOW(), 'admin', 'Room 102', 'admin', 'PART_TIME'),
                                                                                                                                                         (3, 15, 3, 3.5, 6, NOW(), NOW(), 'admin', 'Room 103', 'admin', 'FULL_TIME'),
                                                                                                                                                         (4, 25, 4, 4.0, 7, NOW(), NOW(), 'admin', 'Room 104', 'admin', 'PART_TIME'),
                                                                                                                                                         (5, 30, 5, 2.0, 6, NOW(), NOW(), 'admin', 'Room 105', 'admin', 'FULL_TIME');

-- Inserting data into course_prerequisite table
INSERT INTO course_prerequisite (course_id, prerequisite_id) VALUES
                                                                 (2, 1),
                                                                 (3, 2),
                                                                 (4, 3);

-- Inserting data into course_registration table
INSERT INTO course_registration (id, course_offering_id, student_id) VALUES
                                                                         (1, 1, 1),
                                                                         (2, 2, 2),
                                                                         (3, 3, 3),
                                                                         (4, 4, 4),
                                                                         (5, 5, 5),
                                                                         (6, 1, 2),
                                                                         (7, 2, 3),
                                                                         (8, 3, 4),
                                                                         (9, 4, 5),
                                                                         (10, 5, 1),
                                                                         (11, 1, 3),
                                                                         (12, 2, 4),
                                                                         (13, 3, 5),
                                                                         (14, 4, 1),
                                                                         (15, 5, 2),
                                                                         (16, 1, 4),
                                                                         (17, 2, 5),
                                                                         (18, 3, 1),
                                                                         (19, 4, 2),
                                                                         (20, 5, 3),
                                                                         (21, 1, 5),
                                                                         (22, 2, 1),
                                                                         (23, 3, 2),
                                                                         (24, 4, 3),
                                                                         (25, 5, 4);

-- Inserting data into person_account table
INSERT INTO person_account (id, password, username, role) VALUES
                                                              (1, 'password1', 'john_doe', 'FACULTY'),
                                                              (2, 'password2', 'jane_smith', 'STUDENT'),
                                                              (3, 'password3', 'bob_jones', 'STUDENT'),
                                                              (4, 'password4', 'alice_brown', 'STUDENT'),
                                                              (5, 'password5', 'chris_green', 'STUDENT'),
                                                              (6, 'password6', 'dave_white', 'STUDENT'),
                                                              (7, 'password7', 'eva_black', 'STUDENT');

-- Inserting data into attendance_record table
INSERT INTO attendance_record (id, location_id, student_id, scan_date_time) VALUES
                                                                                (1, 1, 1, NOW()),
                                                                                (2, 2, 2, NOW()),
                                                                                (3, 3, 3, NOW()),
                                                                                (4, 1, 4, NOW()),
                                                                                (5, 2, 5, NOW()),
                                                                                (7, 1, 2, NOW()),
                                                                                (8, 2, 3, NOW()),
                                                                                (9, 3, 4, NOW()),
                                                                                (10, 1, 5, NOW());

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
