/*
 Navicat Premium Data Transfer

 Source Server         : wartajazz
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : us-cdbr-iron-east-05.cleardb.net:3306
 Source Schema         : heroku_9eaf89231528699

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 26/05/2020 14:45:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for artist
-- ----------------------------
DROP TABLE IF EXISTS `artist`;
CREATE TABLE `artist`  (
  `artist_id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `country_of_origin` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `artist_detail` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date_create` datetime(0) NOT NULL,
  `date_modify` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`artist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of artist
-- ----------------------------
INSERT INTO `artist` VALUES (1, 'Tulus', 'Indonesia', NULL, '2019-10-30 17:40:35', NULL);
INSERT INTO `artist` VALUES (2, 'Tompi', 'Indonesia', NULL, '2019-10-30 17:40:35', NULL);
INSERT INTO `artist` VALUES (3, 'Yura Yunita', 'Indonesia', NULL, '2019-10-30 17:40:35', NULL);
INSERT INTO `artist` VALUES (4, 'Acid jazz', 'Indonesia', NULL, '2019-10-30 17:40:35', NULL);
INSERT INTO `artist` VALUES (5, 'Maliq & D', 'Indonesia', NULL, '2019-10-30 17:40:35', NULL);
INSERT INTO `artist` VALUES (6, 'Nadiene Adrianna', 'Indonesia', NULL, '2019-10-30 21:06:23', NULL);
INSERT INTO `artist` VALUES (7, 'Rudy Ermawan', 'Indonesia', NULL, '2019-10-30 21:06:41', NULL);
INSERT INTO `artist` VALUES (8, 'Idang Rasjidi', 'Indonesia', NULL, '2019-10-30 21:07:11', NULL);
INSERT INTO `artist` VALUES (9, 'Cut Nyak Deviana', 'Indonesia', NULL, '2019-10-30 21:07:42', NULL);
INSERT INTO `artist` VALUES (10, 'Nita Aartsen Trio Feat', 'Indonesia', NULL, '2019-10-30 21:08:05', NULL);
INSERT INTO `artist` VALUES (11, 'Dian Pratiwi', 'Indonesia', NULL, '2019-10-30 21:08:26', NULL);
INSERT INTO `artist` VALUES (12, 'Marcel & I/D Project', 'Indonesia', NULL, '2019-11-04 09:52:49', NULL);
INSERT INTO `artist` VALUES (13, 'Tigga', 'Indonesia', NULL, '2019-11-04 09:52:49', NULL);
INSERT INTO `artist` VALUES (14, 'Sastrani', 'Indonesia', NULL, '2019-11-04 09:52:49', NULL);
INSERT INTO `artist` VALUES (15, 'Dannis maks Trio', 'Indonesia', NULL, '2019-11-04 09:52:49', NULL);

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events`  (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date_start` datetime(0) NOT NULL,
  `date_end` datetime(0) NOT NULL,
  `date_create` datetime(0) NOT NULL,
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `htm` int(11) NULL DEFAULT NULL,
  `poster` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date_starto` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`event_id`) USING BTREE,
  INDEX `user_id`(`author`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 139 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of events
-- ----------------------------
INSERT INTO `events` VALUES (123, 'Jazz Gunung Ijen', 'Gunung Ijen', '2019-10-22 23:55:09', '2019-10-22 23:55:09', '2019-10-22 23:55:09', 'admin', 500000, '1572720328-jazzgunung-1024x1024.jpg', '2019-10-22 23:55:09');
INSERT INTO `events` VALUES (124, 'Jakarta Fusion Republic', 'Jakarta', '2019-10-30 20:44:10', '2019-10-30 20:44:10', '2019-10-30 20:44:10', 'admin', 550000, '1572720656-poster-Emerald-Bex-2019.jpeg', '2019-10-30 20:44:10');
INSERT INTO `events` VALUES (125, 'Bistar Jazz Traffic', 'Surabaya', '2019-09-14 08:00:00', '2019-09-15 12:00:00', '2019-09-10 20:46:08', 'admin', 600000, '1572720886-Poster-JTF2019-Rilis1-415x260.jpg', '2019-09-14 08:00:00');
INSERT INTO `events` VALUES (126, 'Jazz Kampoeng Djawi', 'Jawi', '2019-07-20 09:03:20', '2019-07-20 21:03:29', '2019-07-17 21:03:36', 'admin', 350000, '1572721155-jazz-kampoeng-djawi-03.jpg', '2019-07-20 09:03:20');
INSERT INTO `events` VALUES (127, 'Fort Oranje Culture Jazz', 'Maluku Utara', '2019-02-22 12:05:24', '2019-02-23 21:05:36', '2019-02-16 21:05:43', 'admin', 300000, NULL, '2019-02-22 12:05:24');
INSERT INTO `events` VALUES (128, 'Bandini Jazz World Music', 'Mataram', '2019-08-31 09:00:00', '2019-08-31 22:00:00', '2019-08-28 21:18:03', 'admin', 400000, NULL, '2019-08-31 09:00:00');
INSERT INTO `events` VALUES (129, 'Prambanan Jazz', 'Bali', '2019-07-07 08:00:00', '2019-07-07 23:00:00', '2019-07-03 21:19:48', 'admin', 600000, '1572721573-yanni-prambanan-jazz-2019.jpg', '2019-07-07 08:00:00');
INSERT INTO `events` VALUES (130, 'San Keuw Jong', 'Singkawang', '2019-02-11 15:00:00', '2019-02-11 23:00:00', '2019-02-08 21:22:17', 'admin', 400000, NULL, '2019-02-11 15:00:00');
INSERT INTO `events` VALUES (137, 'Jazz Soap Land', 'DKI Jakarta', '2019-11-05 17:00:00', '2019-11-05 21:00:00', '2019-11-05 10:20:55', 'admin', 300000, 'ahy.png', '2019-11-05 17:00:00');
INSERT INTO `events` VALUES (138, 'Jazz Jeding', 'Tangerang', '2019-11-02 14:00:00', '2019-11-02 18:00:00', '2019-11-02 02:08:38', 'admin', 275000, NULL, '2019-11-02 14:00:00');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `url` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `title` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `tags` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `thumbnail` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `author` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  `counter` int(11) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('70hQ6', 'asdasd', '<p>asd</p>', 'Profile,Birthday,Interview', '1571624373-2.PNG', 'admin', '2019-10-21 09:19:33', NULL, 0);
INSERT INTO `news` VALUES ('F8ago', '4th Maratua Jazz and Dive Fiesta persembahkan Bertha, Denny Frust dan Harry Toledo', '<p>\r\n\r\n</p><p>Hamparan laut tosca dan ragam biota laut nan menawan merupakan pemandangan yang akan memanjakan mata para pengunjung gelaran unik dan satu-satunya di dunia yaitu festival jazz di tengah laut yang merupakan Jazz Festival di Pulau terdepan Indonesia yang berbatas laut dengan Malaysia dan Filipina, Maratua Jazz &amp; Dive Fiesta yang telah memasuki gelaran tahun keempat, akan digelar pada tanggal 13-15 September 2019 bertempat di Desa Ekowisata Bohesilian, Pulau Maratua, Kepulauan Derawan, Berau Kalimantan Timur.</p><p>Sejumlah musisi hingga berita ini diturunkan yang telah memastikan kehadirannya antara lain penyanyi yang juga <em>vocal coach master</em>&nbsp;Indonesia <strong>Bertha</strong>, president <em>Indonesian Bass Family IBF</em>&nbsp;<strong>Harry Toledo</strong>, plus ex vokalis <strong>Monkey Boots</strong>&nbsp;yang dijuluki <em>The Prince of SKA Indonesia</em>&nbsp;<strong>Denny Frust</strong>, demikian yang disampaikan <strong>Agus Setiawan Basuni</strong>&nbsp;dari <em>WartaJazz</em>&nbsp;selaku founder festival.</p><p>Selain itu hadir pula grup dari Tanjung Redep yaitu <strong>Echon Project</strong>&nbsp;yang dipimpin <strong>Erson Sutanto</strong>&nbsp;yang juga Pendiri Lapau Kreasi Seni Tradisi Bulu Pattung Berau. Turut hadir pula <strong>Hamdani</strong>, violinist sekaligus drummer/komposer asal Berau yang pernah mencicipi panggung nasional dan Internasional<strong>. </strong>Tak hanya jazz, sebuah kelompok reggae <strong>Red Forest band </strong>bakal memeriahkan.</p><p>Gemerlap bintang merupakan salah satu yang dapat anda nikmati di Maratua. Merujuk tanggal penyelenggaraan MJDF tahun ini bertepatan pula dengan waktu munculnya bulan purnama.</p><p><strong>Hadirkan program baru Sunrise Concert</strong></p><p>Acara MJDF tahun ini dijadwalkan menambah program baru yaitu Sunrise Concert  yang digelar seusai shalat subuh sambal menantikan terbitnya mentari di ufuk timur yang menyembul dari tepi laut.</p><p>Sementara co-founder <strong>Juhriansyah</strong>&nbsp;yang akrab disapa Ryan dari <em>Yayasan Berau Lestari</em>&nbsp;menyatakan, “Beragam aktivitas dapat dilakukan seperti Meet &amp; Greet yang akan diselenggarakan di Pratasaba Resort dan Konser Jazz yang digelar di Desa Ekowisata dalam rangka mengembangkan spirit kebersamaan membangun pariwisata dengan pokdarwis Bohe Silian”.</p>\r\n\r\n\r\n\r\n<p>“Acara Maratua Jazz and Dive Fiesta merupakan upaya untuk mempromosikan destinasi wisata Kabupaten Berau khususnya Pulau Maratua di Kepulauan Derawan”, demikian disampaikan <strong>Masrani</strong>&nbsp;Kepala Dinas Kebudayaan dan Pariwisata Kabupaten Berau. “Kami mengundang kawan-kawan pecinta diving dan penikmat jazz untuk datang ke Pulau Maratua yang dijuluki <em>The Maldives</em>-nya Indonesia” tambahnya.</p><p>Even tahunan yang baru saja di promosikan di acara Sharq Taronalari Music Festival, Samarqand Uzbekistan minggu lalu ini – selain sejumlah agenda promosi nasional dan internasional lainnya, merupakan kerjasama antara WartaJazz – An Ecosystem of Jazz in Indonesia dengan Yayasan Berau Lestari.</p><p>Selain kegiatan musik, Maratua Jazz &amp; Dive Fiesta kali ini juga menghadirkan ragam kegiatan tambahan seperti Festival Kuliner bertajuk MJDF Food Festival dengan sajian khas yang melibatkan komunitas masyarakat &nbsp;Pulau Maratua.</p><p>Sejak tahun lalu MJDF juga menghadirkan pameran Fotografi yang kali ini menampilkan karya <strong>Basuki Rachmad</strong>, seorang diver yang juga penggemar musik jazz yang merekam keindahan panorama alam dan bawah laut Berau.</p><p>Dalam acara MJDF ini juga dijadwalkan sebagai bagian dari rangkaian kegiatan Aksi Bersih-bersih yang akan melibatkan panitia, artis, aparat desa/kecamatan dan masyarakat Pulau Maratua bersama-sama untuk menjaga kebersihan lingkungan.</p><p>Panitia yang juga mendorong pengurangan penggunaan tas kresek/plastik dengan slogan #StopUsingPlasticBag dan untuk mempromosikan menggunakan #JazzPulauTerdepan #JazzDive</p><p>Kegiatan Maratua Jazz &amp; Dive Fiesta didukung penuh oleh Kementrian Pariwisata RI, Dinas Pariwisata Provinsi Kalimantan Timur, Pemerintah Kabupaten Berau, Pratasaba Resort, dan sejumlah mitra lainnya seperti Forum Komunitas Maritim Berau FKMB, Green Nirvana Resort Hotel Derawan Indah, Hotel Bumi Segah Berau, Maratua Ecodive Centre, Move Entertainment.</p><p>Dengan media partner Good News From Indonesia, KPFM, Kaltimpost, RRI Pro2 Samarinda, JanganTulalit.com, Kaltim Kece dan Exotic Kaltim.</p>\r\n\r\n<br><p></p>', 'News,Festival,Review', '1571627664-ahy.png', 'admin', '2019-10-21 09:40:39', '2019-10-21 10:14:24', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', 'Super User');
INSERT INTO `role` VALUES ('1', 'Admin');
INSERT INTO `role` VALUES ('2', 'Mobile User');
INSERT INTO `role` VALUES ('3', 'Google User');
INSERT INTO `role` VALUES ('4', 'Facebook User');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `event_id` int(11) NOT NULL,
  `show_time` time(0) NOT NULL,
  `artist_id` int(11) NOT NULL,
  `date_create` datetime(0) NOT NULL,
  `date_modify` datetime(0) NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  INDEX `event_id`(`event_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `artist_id`(`artist_id`) USING BTREE,
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (123, '18:00:00', 5, '2019-10-22 23:00:00', NULL, 1);
INSERT INTO `schedule` VALUES (124, '11:00:00', 10, '2019-10-30 21:13:00', NULL, 1);
INSERT INTO `schedule` VALUES (125, '08:00:00', 6, '2019-09-10 21:13:33', NULL, 1);
INSERT INTO `schedule` VALUES (126, '09:00:00', 7, '2019-07-17 21:15:40', NULL, 1);
INSERT INTO `schedule` VALUES (127, '12:00:00', 9, '2019-02-16 21:16:16', NULL, 1);
INSERT INTO `schedule` VALUES (128, '15:00:00', 1, '2019-08-28 21:23:01', NULL, 1);
INSERT INTO `schedule` VALUES (129, '08:00:00', 7, '2019-07-03 21:23:48', NULL, 1);
INSERT INTO `schedule` VALUES (130, '15:00:00', 12, '2019-02-08 21:24:23', NULL, 1);
INSERT INTO `schedule` VALUES (123, '19:00:00', 6, '2019-10-22 23:00:00', NULL, 1);
INSERT INTO `schedule` VALUES (123, '20:00:00', 9, '2019-10-22 23:00:00', NULL, 1);
INSERT INTO `schedule` VALUES (123, '21:00:00', 1, '2019-10-22 23:00:00', NULL, 1);
INSERT INTO `schedule` VALUES (124, '09:00:00', 7, '2019-10-30 21:13:00', NULL, 1);
INSERT INTO `schedule` VALUES (124, '10:00:00', 2, '2019-10-30 21:13:00', NULL, 1);
INSERT INTO `schedule` VALUES (124, '08:00:00', 10, '2019-10-30 21:13:00', NULL, 1);
INSERT INTO `schedule` VALUES (125, '09:00:00', 3, '2019-09-10 21:13:33', NULL, 1);
INSERT INTO `schedule` VALUES (125, '10:00:00', 15, '2019-09-10 21:13:33', NULL, 1);
INSERT INTO `schedule` VALUES (125, '11:00:00', 13, '2019-09-10 21:13:33', NULL, 1);
INSERT INTO `schedule` VALUES (126, '10:00:00', 5, '2019-07-17 21:15:40', NULL, 1);
INSERT INTO `schedule` VALUES (126, '11:00:00', 1, '2019-07-17 21:15:40', NULL, 1);
INSERT INTO `schedule` VALUES (126, '12:00:00', 10, '2019-07-17 21:15:40', NULL, 1);
INSERT INTO `schedule` VALUES (127, '14:00:00', 13, '2019-02-16 21:16:16', NULL, 1);
INSERT INTO `schedule` VALUES (127, '15:00:00', 3, '2019-02-16 21:16:16', NULL, 1);
INSERT INTO `schedule` VALUES (127, '16:00:00', 11, '2019-02-16 21:16:16', NULL, 1);
INSERT INTO `schedule` VALUES (128, '16:00:00', 10, '2019-08-28 21:23:01', NULL, 1);
INSERT INTO `schedule` VALUES (128, '17:00:00', 6, '2019-08-28 21:23:01', NULL, 1);
INSERT INTO `schedule` VALUES (128, '18:00:00', 7, '2019-08-28 21:23:01', NULL, 1);
INSERT INTO `schedule` VALUES (129, '09:00:00', 4, '2019-07-03 21:23:48', NULL, 1);
INSERT INTO `schedule` VALUES (129, '10:00:00', 9, '2019-07-03 21:23:48', NULL, 1);
INSERT INTO `schedule` VALUES (129, '11:00:00', 2, '2019-07-03 21:23:48', NULL, 1);
INSERT INTO `schedule` VALUES (130, '16:00:00', 3, '2019-02-08 21:24:23', NULL, 1);
INSERT INTO `schedule` VALUES (130, '17:00:00', 8, '2019-02-08 21:24:23', NULL, 1);
INSERT INTO `schedule` VALUES (130, '18:00:00', 1, '2019-02-08 21:24:23', NULL, 1);
INSERT INTO `schedule` VALUES (138, '14:00:00', 3, '2019-11-02 02:08:38', NULL, 1);
INSERT INTO `schedule` VALUES (138, '15:00:00', 6, '2019-11-02 02:08:38', NULL, 1);
INSERT INTO `schedule` VALUES (138, '16:00:00', 11, '2019-11-02 02:08:38', NULL, 1);
INSERT INTO `schedule` VALUES (137, '17:00:00', 3, '2019-11-05 10:20:55', NULL, 1);
INSERT INTO `schedule` VALUES (137, '18:30:00', 6, '2019-11-05 10:20:55', NULL, 1);
INSERT INTO `schedule` VALUES (137, '19:00:00', 1, '2019-11-05 10:20:55', NULL, 1);
INSERT INTO `schedule` VALUES (137, '20:30:00', 4, '2019-11-05 10:20:55', NULL, 1);

-- ----------------------------
-- Table structure for user_bio
-- ----------------------------
DROP TABLE IF EXISTS `user_bio`;
CREATE TABLE `user_bio`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fullname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone_number` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `join_date` datetime(0) NOT NULL,
  `thumbnail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  UNIQUE INDEX `username1`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_bio
-- ----------------------------
INSERT INTO `user_bio` VALUES ('admin', 'Bibie Hadi', '6282257746611', 'Malang\r\n', '2018-12-05 00:00:00', NULL, NULL);
INSERT INTO `user_bio` VALUES ('arunseto', 'Arunseto', '-', '-', '2019-03-03 12:48:00', 'https://lh3.googleusercontent.com/-VeXPqUKdqcw/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rdaMkAPDiNy6C0-8tI_iN6hbo-uqQ/s96-c/photo.jpg', '117977263470804846305');
INSERT INTO `user_bio` VALUES ('baguskr', 'Bagus Kr', '123456789012', 'Malang', '2019-01-11 14:18:25', NULL, NULL);
INSERT INTO `user_bio` VALUES ('bibie', 'Bibie', '6282257746611', '213131', '2018-12-08 05:39:46', NULL, NULL);
INSERT INTO `user_bio` VALUES ('bimprakosoo', 'Bimo', '081331398067', 'Malang', '2019-08-03 12:46:19', NULL, NULL);
INSERT INTO `user_bio` VALUES ('irfan', 'Irfan', '-', '-', '2019-03-03 13:16:36', 'https://lh4.googleusercontent.com/-Xp_H-ULOLw0/AAAAAAAAAAI/AAAAAAAAACc/pdDoaIqefrk/s96-c/photo.jpg', '114710866507447458854');
INSERT INTO `user_bio` VALUES ('onyxzed', 'OnyxzeD', '-', '-', '2019-03-03 12:27:28', 'https://lh3.googleusercontent.com/-Vxrk1ka8dsA/AAAAAAAAAAI/AAAAAAAAAB0/MXu6YM78mAQ/s96-c/photo.jpg', '103154997531299775616');
INSERT INTO `user_bio` VALUES ('yuuto', 'Hizkia Luke Susanto doe', '1234567890', 'Kyoto', '2019-10-30 23:48:02', NULL, NULL);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `username`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', 'bibiehadikusuma@stiki.ac.id', '$2y$10$JwYxtSWi/05w6ib7wBA3seideNCBXQPqyhtJNjqh9c5WhFjH3jbtW', '0', 0, '123');
INSERT INTO `users` VALUES (2, 'bibie', 'bibie.hadi@gmail.com', '$2y$10$03D7TghoowJevSSkkPR.Aen7NCDyu60MnnNIr0iNZp2kScWAWy55u', '2', 0, '456');
INSERT INTO `users` VALUES (3, 'baguskr', 'bagus@gmail.com', '$2y$10$tKWwYPvXxEEb43/GTsDJX./zatOI1W7fe3uKRGl4t6TABFlFmtJKq', '2', 0, '789');
INSERT INTO `users` VALUES (28, 'onyxzed', 'onyxzed@gmail.com', '$2y$10$D9WxDaMOid.KITdHSUKJFO1VUjVX5GHwaQuZbyNyPKhl.MNo3qc6a', '3', 0, 'dlHo1RHbEpk:APA91bF505Zbyr5PFRE6Orrvk5lX7khNG0-OAJtVZRF6kMpA3_BqFD3zqK76Gn52ugUMjGvbMq_k4dhfkNykCyWucP84MwaD_4L-lTVx4MGB5t4-mWlDXqPanMYraGvsV-5Nwo4Q6ZIu');
INSERT INTO `users` VALUES (29, 'arunseto', 'arunstop@gmail.com', '$2y$10$G0gcqSlCbrZyHrD837hZcuiEbdpxcGzxmRCPT8BvddlTgOBf0mZWi', '3', 0, '654');
INSERT INTO `users` VALUES (32, 'irfan', 'irfanzid11@gmail.com', '$2y$10$PHH3BI/jLzbLnY3KyLYcOeusHn..h5t.6LWDUprJh7gzYcY4f1cPG', '3', 0, '321');
INSERT INTO `users` VALUES (36, 'bimprakosoo', 'omibalola@gmail.com', '$2y$10$.8E1/zWS.vPG80aFVeSx/eczIL78ko/Z.kC1Y08oR0cXyP60Tjr8y', '2', 0, '741');
INSERT INTO `users` VALUES (39, 'yuuto', 'onyxzed@gmail.com', '$2y$10$JY7q3lyjnjLpIllPhkLa3eyzOpnDifJVnxULAH.rL3dhVonZHuJFa', '2', 1, 'dlHo1RHbEpk:APA91bF505Zbyr5PFRE6Orrvk5lX7khNG0-OAJtVZRF6kMpA3_BqFD3zqK76Gn52ugUMjGvbMq_k4dhfkNykCyWucP84MwaD_4L-lTVx4MGB5t4-mWlDXqPanMYraGvsV-5Nwo4Q6ZIu');

SET FOREIGN_KEY_CHECKS = 1;
