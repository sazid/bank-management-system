-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 06, 2018 at 01:48 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `m6`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `accountNumber` varchar(32) NOT NULL,
  `balance`       double      NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`accountNumber`, `balance`)
VALUES ('0', 23.41),
       ('0001', 10200),
       ('0002', 123),
       ('0003', 6500.34),
       ('123', 999),
       ('1291', 3.14159);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `username`      varchar(32) NOT NULL,
  `phoneNumber`   varchar(15) NOT NULL,
  `accountNumber` varchar(32) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`username`, `phoneNumber`, `accountNumber`)
VALUES ('keya', '+8000', '1291'),
       ('saima', '+8801333333333', '0003'),
       ('sazid', '+8801111111111', '0001');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `username`    varchar(32) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `role`        varchar(20) NOT NULL,
  `salary`      double      NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`username`, `phoneNumber`, `role`, `salary`)
VALUES ('admin', '+880123456789', 'manager', 1000000),
       ('hello', '+8888888', 'general', 10212);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status`   varchar(32) NOT NULL,
  `name`     text        NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`, `status`, `name`)
VALUES ('admin', 'admin', 'employee', 'Admin'),
       ('hello', 'hello', 'employee', 'Hello World'),
       ('keya', 'password', 'customer', 'Keya Zaman'),
       ('saima', 'password', 'customer', 'Saima Nazifa'),
       ('sazid', 'password', 'customer', 'Mohammed Sazid Al Rashid');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `accountNumber` varchar(32) NOT NULL,
  `type`          varchar(20) NOT NULL,
  `amount`        double      NOT NULL,
  `date`          date        NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountNumber`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `customer_accountnumber_unique` (`accountNumber`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`username`) USING BTREE;

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD UNIQUE KEY `transaction_accountNumber` (`accountNumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
