# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.19)
# Database: OLRS
# Generation Time: 2017-10-21 07:34:35 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table notice_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notice_info`;

CREATE TABLE `notice_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `notice_id` bigint(11) NOT NULL,
  `notice_uuid` varchar(255) NOT NULL,
  `officer_id` int(11) NOT NULL,
  `status` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `officer_id` (`officer_id`),
  CONSTRAINT `notice_info_ibfk_1` FOREIGN KEY (`officer_id`) REFERENCES `officer_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table officer_info
# ------------------------------------------------------------

DROP TABLE IF EXISTS `officer_info`;

CREATE TABLE `officer_info` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `officer_info` WRITE;
/*!40000 ALTER TABLE `officer_info` DISABLE KEYS */;

INSERT INTO `officer_info` (`id`, `password`, `name`)
VALUES
	(101,'123456','selina'),
	(102,'123456','helen');

/*!40000 ALTER TABLE `officer_info` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
