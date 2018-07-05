-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 05, 2018 at 03:49 PM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cbt`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(11) NOT NULL,
  `course_code` varchar(10) NOT NULL,
  `course_title` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `course_code`, `course_title`) VALUES
(1, 'CSC 101', 'Introduction to Computer Science'),
(2, 'CSC 103', 'System Architecture');

-- --------------------------------------------------------

--
-- Table structure for table `course_registration`
--

CREATE TABLE `course_registration` (
  `id` int(11) NOT NULL,
  `reg_number` varchar(20) NOT NULL,
  `course_code` varchar(20) NOT NULL,
  `session_name` varchar(20) NOT NULL,
  `result` int(3) NOT NULL,
  `status` enum('open','close','','') NOT NULL DEFAULT 'close'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course_registration`
--

INSERT INTO `course_registration` (`id`, `reg_number`, `course_code`, `session_name`, `result`, `status`) VALUES
(1, 'a', 'CSC 101', '2018/2019', 2, 'close'),
(4, 'a', 'CSC 103', '2018/2019', 2, 'close');

-- --------------------------------------------------------

--
-- Table structure for table `exam_question`
--

CREATE TABLE `exam_question` (
  `id` int(11) NOT NULL,
  `teacher_id` int(5) NOT NULL,
  `question` text NOT NULL,
  `a` varchar(80) NOT NULL,
  `b` varchar(80) DEFAULT NULL,
  `c` varchar(80) DEFAULT NULL,
  `d` varchar(80) DEFAULT NULL,
  `e` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `exam_question`
--

INSERT INTO `exam_question` (`id`, `teacher_id`, `question`, `a`, `b`, `c`, `d`, `e`) VALUES
(1, 1, 'If a is for apple and b is for ball, what is the value of noodles?', 'e', 'b', 'c', 'n', NULL),
(2, 1, 'Who is Nigeria\'s fourth highest goal scorer?', 'Yekini', 'Omokachi', 'Aiyebeni', 'Amuneke', NULL),
(3, 1, 'When I slap you , you will say that I have come, again, you better mind yourself', 'a', 'b', 'c', 'd', NULL),
(4, 2, '\"why sid, you put, these here\"', 'a', 'b', 'v', 'f', NULL),
(5, 2, '\'hello\'\'', 'j', 'j', 'j', 'j', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` varchar(20) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `access_level` int(1) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `middle_name` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `first_name`, `access_level`, `last_name`, `middle_name`, `password`) VALUES
('2008162972', 'amaobi', 1, 'Obikobe', 'Victor', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8'),
('a', 'Oluoma', 0, 'Aduchie', 'Precious', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8');

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` int(5) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `name`) VALUES
(1, '2018/2019');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `person_id` varchar(20) NOT NULL,
  `department` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`person_id`, `department`) VALUES
('a', 'Computer Science');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `person_id` varchar(20) NOT NULL,
  `id` int(5) NOT NULL,
  `course_id` int(5) NOT NULL,
  `session_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`person_id`, `id`, `course_id`, `session_id`) VALUES
('2008162972', 1, 1, 1),
('2008162972', 2, 2, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `course_code` (`course_code`);

--
-- Indexes for table `course_registration`
--
ALTER TABLE `course_registration`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `registration` (`reg_number`,`course_code`,`session_name`),
  ADD KEY `CR_course_code` (`course_code`),
  ADD KEY `session_name` (`session_name`);

--
-- Indexes for table `exam_question`
--
ALTER TABLE `exam_question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`teacher_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`first_name`,`last_name`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `session_name` (`name`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`person_id`),
  ADD UNIQUE KEY `reg_number` (`department`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_set` (`person_id`,`course_id`,`session_id`),
  ADD KEY `t_course_id` (`course_id`),
  ADD KEY `t_session_id` (`session_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `course_registration`
--
ALTER TABLE `course_registration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `exam_question`
--
ALTER TABLE `exam_question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course_registration`
--
ALTER TABLE `course_registration`
  ADD CONSTRAINT `CR_course_code` FOREIGN KEY (`course_code`) REFERENCES `course` (`course_code`) ON UPDATE CASCADE,
  ADD CONSTRAINT `reg_no` FOREIGN KEY (`reg_number`) REFERENCES `student` (`person_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `session_name` FOREIGN KEY (`session_name`) REFERENCES `session` (`name`) ON UPDATE CASCADE;

--
-- Constraints for table `exam_question`
--
ALTER TABLE `exam_question`
  ADD CONSTRAINT `id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `reg_number` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `teacher`
--
ALTER TABLE `teacher`
  ADD CONSTRAINT `t_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `t_person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `t_session_id` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
