-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 17, 2023 lúc 05:27 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `gkjava`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `history` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `name`, `email`, `username`, `password`, `age`, `phoneNumber`, `status`, `role`, `history`) VALUES
(1, 'Nguyễn Mai Tấn Thành', 'admin@gmail.com', 'admin', 'admin', 20, '3423', 'normal', 'admin', '17-12-2023 23:15:51'),
(2, 'Phạm Thanh Tuấn', 'manager@gmail.com', 'manager', 'manager', 21, '12341241123', 'normal', 'manager', '17-12-2023 23:20:38'),
(3, 'Trần Nam Phát', 'employee@gmail.com', 'employee', 'employee', 20, '012312', 'normal', 'employee', '17-12-2023 23:21:13');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `certificate`
--

CREATE TABLE `certificate` (
  `id` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `issuer` varchar(255) NOT NULL,
  `issueDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `certificate`
--

INSERT INTO `certificate` (`id`, `studentId`, `title`, `issuer`, `issueDate`) VALUES
(1, 1, 'IELTS', 'Britis Council', '2023-12-09'),
(2, 1, 'TOEIC', 'Britis Council', '2023-12-04'),
(3, 1, 'Aptis', 'Britis Council', '2023-12-09'),
(4, 2, 'Aptis', 'Britis Council', '2023-11-03'),
(5, 2, 'Microsoft Word', 'Microsoft', '2023-12-07'),
(6, 2, 'IELTS', 'Britis Council', '2023-11-03'),
(7, 3, 'IELTS', 'Britis Council', '2023-12-06'),
(13, 1, 'Microsoft Dev', 'Microsoft', '2023-12-17'),
(14, 1, 'Microsoft Excel', 'Microsoft', '2023-12-17'),
(15, 1, 'Microsoft Word', 'Microsoft', '2023-12-12'),
(16, 2, 'Microsoft Dev', 'Microsoft', '2023-12-17'),
(17, 2, 'Microsoft Excel', 'Microsoft', '2023-12-17'),
(18, 2, 'Microsoft Word', 'Microsoft', '2023-12-12');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `dOB` varchar(255) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `students`
--

INSERT INTO `students` (`id`, `name`, `dOB`, `gender`, `phoneNumber`, `address`) VALUES
(1, 'Nguyễn Mai Tấn Thành', '13/03/2003', 'Male', '12341241123', 'Quảng Ngãi'),
(2, 'Phạm Thanh Tuấn', '23/02/2003', 'Male', '2412412412', 'Cam Ranh'),
(3, 'Trần Nam Phát', '20/01/2003', 'Male', '1412412', 'Vĩnh Long'),
(10, 'Nguyễn Văn A', '13/03/2003', 'Male', '12341241123', 'Quảng Ngãi'),
(11, 'Phạm Thanh B', '23/02/2003', 'Male', '2412412412', 'Cam Ranh'),
(12, 'Trần Nam C', '20/01/2003', 'Male', '1412412', 'Vĩnh Long');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `certificate`
--
ALTER TABLE `certificate`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `certificate`
--
ALTER TABLE `certificate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
