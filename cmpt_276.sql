-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Jul 22, 2021 at 09:49 PM
-- Server version: 5.7.28
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cmpt_276`
--

-- --------------------------------------------------------

--
-- Table structure for table `cmpt_276_post`
--

DROP TABLE IF EXISTS `cmpt_276_post`;
CREATE TABLE IF NOT EXISTS `cmpt_276_post` (
  `studentNumber` int(9) NOT NULL,
  `postTitle` varchar(100) COLLATE utf8_bin NOT NULL,
  `postContent` varchar(500) COLLATE utf8_bin NOT NULL,
  `postTime` datetime NOT NULL,
  `postID` int(10) NOT NULL,
  `pictureLink` varchar(1000) COLLATE utf8_bin NOT NULL,
  UNIQUE KEY `postID` (`postID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `cmpt_276_post`
--

INSERT INTO `cmpt_276_post` (`studentNumber`, `postTitle`, `postContent`, `postTime`, `postID`, `pictureLink`) VALUES
(100000001, 'Test Post 5', 'hello world', '2021-07-13 13:00:00', 9999999, 'https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg'),
(100000001, 'Test Post 4', 'hello world', '2021-07-15 10:38:51', 9999998, 'https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg'),
(100000001, 'Test Post 3', 'hello world', '2021-07-15 10:38:58', 9999997, 'https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg'),
(100000001, 'Test Post 2', 'hello world', '2021-07-15 10:39:02', 9999996, 'https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg'),
(100000001, 'Test Post 1', 'hello world', '2021-07-15 10:39:07', 9999995, 'https://dazedimg-dazedgroup.netdna-ssl.com/830/azure/dazed-prod/1150/0/1150228.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `cmpt_276_reply`
--

DROP TABLE IF EXISTS `cmpt_276_reply`;
CREATE TABLE IF NOT EXISTS `cmpt_276_reply` (
  `studentNumber` int(9) NOT NULL,
  `replyContent` varchar(500) COLLATE utf8_bin NOT NULL,
  `replyTime` datetime NOT NULL,
  `replyID` int(10) NOT NULL,
  `parentPostID` int(10) NOT NULL,
  UNIQUE KEY `replyID` (`replyID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `cmpt_276_reply`
--

INSERT INTO `cmpt_276_reply` (`studentNumber`, `replyContent`, `replyTime`, `replyID`, `parentPostID`) VALUES
(100000003, 'Yes sir! Let\'s make the world a better place!', '2021-07-22 11:00:00', 1, 9999999),
(100000002, 'I like your enthusiasm @JohnnyTest', '2021-07-22 11:34:09', 2, 9999999),
(100000003, 'Well yea... world\'s burning around us gotta do something about it and start somewhere', '2021-07-23 09:08:23', 3, 9999999);

-- --------------------------------------------------------

--
-- Table structure for table `cmpt_276_user`
--

DROP TABLE IF EXISTS `cmpt_276_user`;
CREATE TABLE IF NOT EXISTS `cmpt_276_user` (
  `studentNumber` int(9) NOT NULL,
  `firstName` varchar(45) COLLATE utf8_bin NOT NULL,
  `middleName` varchar(45) COLLATE utf8_bin NOT NULL,
  `lastName` varchar(45) COLLATE utf8_bin NOT NULL,
  `phoneNumber` varchar(30) COLLATE utf8_bin NOT NULL,
  `externalLinks` varchar(1000) COLLATE utf8_bin NOT NULL,
  `email` varchar(45) COLLATE utf8_bin NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `isAdmin` bit(1) NOT NULL,
  `dormNumber` varchar(15) COLLATE utf8_bin NOT NULL,
  `isActive` bit(1) NOT NULL,
  UNIQUE KEY `studentNumber` (`studentNumber`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `cmpt_276_user`
--

INSERT INTO `cmpt_276_user` (`studentNumber`, `firstName`, `middleName`, `lastName`, `phoneNumber`, `externalLinks`, `email`, `startDate`, `endDate`, `isAdmin`, `dormNumber`, `isActive`) VALUES
(100000001, 'Test', '', 'Administrator', '123-456-7890', 'justatestlink.com', 'test@test.com', '2021-07-01', '2021-08-01', b'1', '001', b'1'),
(100000002, 'Another', 'Test', 'User', '223-456-7890', '', 'testing@test.com', '2021-07-02', '2021-08-02', b'0', '', b'1'),
(100000003, 'Johnny', '', 'Test', '000-111-2222', 'https://www.netflix.com/ca/title/81193032', 'johnny@test.com', '2021-07-03', '2021-08-03', b'0', '', b'1'),
(100000004, 'Test', 'Inactive', 'User', '111-222-3333', '', 'inactive@test.com', '2021-06-01', '2021-07-01', b'0', '', b'0');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
