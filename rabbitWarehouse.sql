-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.4.6-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 테이블 rabbit2me.logistics_category 구조 내보내기
CREATE TABLE IF NOT EXISTS `logistics_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 rabbit2me.logistics_member 구조 내보내기
CREATE TABLE IF NOT EXISTS `logistics_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` text DEFAULT '0',
  `passwd` text DEFAULT '0',
  `name` varchar(50) DEFAULT '0',
  `level` varchar(50) DEFAULT '0',
  `ip` text DEFAULT '0',
  `regidate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 rabbit2me.logistics_store 구조 내보내기
CREATE TABLE IF NOT EXISTS `logistics_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryID` int(11) DEFAULT 0,
  `memberID` int(11) DEFAULT NULL,
  `subject` text DEFAULT NULL,
  `barcode` text DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `cnt` int(11) DEFAULT NULL,
  `filename` text DEFAULT NULL,
  `filesize` bigint(20) DEFAULT NULL,
  `image` mediumblob DEFAULT NULL,
  `ip` text DEFAULT NULL,
  `regidate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='// 창고 내용\r\n';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 rabbit2me.logistics_store_flow 구조 내보내기
CREATE TABLE IF NOT EXISTS `logistics_store_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specific` varchar(50) DEFAULT NULL,
  `cnt` int(11) DEFAULT NULL,
  `fID` int(11) DEFAULT NULL,
  `regidate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 뷰 rabbit2me.logistics_store_view 구조 내보내기
-- VIEW 종속성 오류를 극복하기 위해 임시 테이블을 생성합니다.
CREATE TABLE `logistics_store_view` (
	`id` INT(11) NOT NULL,
	`category` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`memberID` INT(11) NULL,
	`subject` TEXT NULL COLLATE 'utf8_general_ci',
	`barcode` TEXT NULL COLLATE 'utf8_general_ci',
	`author` VARCHAR(50) NULL COLLATE 'utf8_general_ci',
	`price` INT(11) NULL,
	`cnt` INT(11) NULL,
	`filename` TEXT NULL COLLATE 'utf8_general_ci',
	`filesize` BIGINT(20) NULL,
	`image` MEDIUMBLOB NULL,
	`ip` TEXT NULL COLLATE 'utf8_general_ci',
	`regidate` DATETIME NULL
) ENGINE=MyISAM;

-- 뷰 rabbit2me.logistics_store_view 구조 내보내기
-- 임시 테이블을 제거하고 최종 VIEW 구조를 생성
DROP TABLE IF EXISTS `logistics_store_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `logistics_store_view` AS select logistics_store.id as 'id', logistics_category.category as 'category', logistics_store.memberID, subject, barcode, author, price, cnt, filename, filesize, image, ip, regidate from logistics_store
join logistics_category ON logistics_category.id = logistics_store.categoryID order by logistics_store.regidate asc ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
