-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.31 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for db_portal
CREATE DATABASE IF NOT EXISTS `db_portal` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_portal`;

-- Dumping structure for table db_portal.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name_department` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_info_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fb95fonxsgxyx3y4i76tmf4rp` (`user_info_id`),
  CONSTRAINT `FKcg5q5wxe0me7ihs30bnoo8r4a` FOREIGN KEY (`user_info_id`) REFERENCES `ms_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.department: ~0 rows (approximately)
DELETE FROM `department`;

-- Dumping structure for table db_portal.event
CREATE TABLE IF NOT EXISTS `event` (
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  `event_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `event_year` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_name` (`event_name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.event: ~2 rows (approximately)
DELETE FROM `event`;
INSERT INTO `event` (`event_id`, `event_name`, `event_year`) VALUES
	(23, 'QCC 2024', '2024'),
	(27, 'SS 2024', '2024');

-- Dumping structure for table db_portal.file_award
CREATE TABLE IF NOT EXISTS `file_award` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name_file` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `path_file` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type_file` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_team` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdo3yk6ho05peo30f6gefebcqg` (`id_team`),
  CONSTRAINT `FKdo3yk6ho05peo30f6gefebcqg` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.file_award: ~0 rows (approximately)
DELETE FROM `file_award`;

-- Dumping structure for table db_portal.history_prestasi
CREATE TABLE IF NOT EXISTS `history_prestasi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `prestasi` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_award` bigint DEFAULT NULL,
  `id_team` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrup5s9jxocoo9jxbheyvtnvek` (`id_award`),
  KEY `FKmq14ncs0nyaamqx68y6qqcxt2` (`id_team`),
  CONSTRAINT `FKmq14ncs0nyaamqx68y6qqcxt2` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`),
  CONSTRAINT `FKrup5s9jxocoo9jxbheyvtnvek` FOREIGN KEY (`id_award`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.history_prestasi: ~0 rows (approximately)
DELETE FROM `history_prestasi`;

-- Dumping structure for table db_portal.member_team
CREATE TABLE IF NOT EXISTS `member_team` (
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `name_member` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `position` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_team` bigint DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  KEY `FKt3srduyyyl2eby6j2n24lbg0e` (`id_team`),
  CONSTRAINT `FKt3srduyyyl2eby6j2n24lbg0e` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.member_team: ~0 rows (approximately)
DELETE FROM `member_team`;

-- Dumping structure for table db_portal.ms_user
CREATE TABLE IF NOT EXISTS `ms_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nip` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g654enkj2gifxv3miea4v46w7` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.ms_user: ~0 rows (approximately)
DELETE FROM `ms_user`;
INSERT INTO `ms_user` (`id`, `email`, `nip`, `password`, `role`, `username`) VALUES
	(1, '', '', '$2a$10$DbEEvaYPCk0kZAE7fDDyXeU8qNKut19MBBHo4uoiY9VTu3Gc5Q7ei', 'ADMIN', 'admin');

-- Dumping structure for table db_portal.registration_event
CREATE TABLE IF NOT EXISTS `registration_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `file_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `improvement_title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `award_id` bigint DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2ex703j40ypg5j9so5x1m4u1` (`award_id`),
  KEY `FKon59a6qyo47mat941vgkauoj4` (`team_id`),
  CONSTRAINT `FKh2ex703j40ypg5j9so5x1m4u1` FOREIGN KEY (`award_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `FKon59a6qyo47mat941vgkauoj4` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.registration_event: ~0 rows (approximately)
DELETE FROM `registration_event`;

-- Dumping structure for table db_portal.steps
CREATE TABLE IF NOT EXISTS `steps` (
  `step_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `step_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `event_id` bigint DEFAULT NULL,
  PRIMARY KEY (`step_id`),
  KEY `FK87th8mpfbslon2cksj3m1rp14` (`event_id`),
  CONSTRAINT `FK87th8mpfbslon2cksj3m1rp14` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.steps: ~6 rows (approximately)
DELETE FROM `steps`;
INSERT INTO `steps` (`step_id`, `description`, `end_date`, `start_date`, `step_name`, `event_id`) VALUES
	(13, 'Tahap pendaftaran dawdawdwakdmawmdawmdaw', '2024-02-23', '2024-02-21', 'Pendaftaran', 23),
	(14, 'Tahap verifikasi berkas pendaftar dawdawdaw dddddddddddddddddddddddddddddddddddd', '2024-02-25', '2024-02-24', 'Verifikasi', 23),
	(15, 'Tahap Penilaian  dadawdawld,awd,aw ', '2024-02-27', '2024-02-26', 'Penilaian', 23),
	(16, 'Tahap Pengumuman  daw,dl;awdl,aw', '2024-03-01', '2024-02-28', 'Pengumuman Hasil', 23),
	(19, 'desc 1', '2024-02-22', '2024-02-22', 'step 1', 27),
	(20, 'desc 2', '2024-02-22', '2024-02-22', '2', 27);

-- Dumping structure for table db_portal.team
CREATE TABLE IF NOT EXISTS `team` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint DEFAULT NULL,
  `name_team` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `team_creation_year` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_portal.team: ~0 rows (approximately)
DELETE FROM `team`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
