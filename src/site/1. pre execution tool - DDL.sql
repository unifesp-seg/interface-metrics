-- ----------------------------
 -- Table structure for `interface_metrics`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics`;
CREATE TABLE `interface_metrics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_type` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `entity_type` varchar(255) DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `modifiers` varchar(255) DEFAULT NULL,
  `fqn` varchar(2048) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `relation_type` varchar(255) DEFAULT NULL,
  `processed` tinyint(1) DEFAULT NULL,
  `processed_params` tinyint(1) DEFAULT NULL,
  `total_params` int(11) DEFAULT NULL,
  `total_words_method` int(11) DEFAULT NULL,
  `total_words_class` int(11) DEFAULT NULL,
  `only_primitive_types` tinyint(4) DEFAULT NULL,
  `is_static` tinyint(4) DEFAULT NULL,
  `has_type_same_package` tinyint(4) DEFAULT NULL,
  `p0_c0_w0_t0` int(11) DEFAULT NULL,
  `p0_c0_w0_t1` int(11) DEFAULT NULL,
  `p0_c0_w1_t0` int(11) DEFAULT NULL,
  `p0_c0_w1_t1` int(11) DEFAULT NULL,
  `p0_c1_w0_t0` int(11) DEFAULT NULL,
  `p0_c1_w0_t1` int(11) DEFAULT NULL,
  `p0_c1_w1_t0` int(11) DEFAULT NULL,
  `p0_c1_w1_t1` int(11) DEFAULT NULL,
  `p1_c0_w0_t0` int(11) DEFAULT NULL,
  `p1_c0_w0_t1` int(11) DEFAULT NULL,
  `p1_c0_w1_t0` int(11) DEFAULT NULL,
  `p1_c0_w1_t1` int(11) DEFAULT NULL,
  `p1_c1_w0_t0` int(11) DEFAULT NULL,
  `p1_c1_w0_t1` int(11) DEFAULT NULL,
  `p1_c1_w1_t0` int(11) DEFAULT NULL,
  `p1_c1_w1_t1` int(11) DEFAULT NULL,
  `error` tinyint(4) DEFAULT NULL,
  `result1` text DEFAULT NULL,
  `result2` text DEFAULT NULL,
  `result3` text DEFAULT NULL,
  `result4` text DEFAULT NULL,
  `exec1` text DEFAULT NULL,
  `exec2` text DEFAULT NULL,
  `exec3` text DEFAULT NULL,
  `exec4` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `entity_id` (`entity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1825438 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_pairs_inner`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_pairs_inner`;
CREATE TABLE `interface_metrics_pairs_inner` (
  `interface_metrics_a` bigint(20) NOT NULL DEFAULT '0',
  `interface_metrics_b` bigint(20) NOT NULL DEFAULT '0',
  `search_type` varchar(14) NOT NULL,
  PRIMARY KEY (`interface_metrics_a`,`interface_metrics_b`,`search_type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
 -- Table structure for `interface_metrics_pairs`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_pairs`;
CREATE TABLE `interface_metrics_pairs` (
  `interface_metrics_a` bigint(20) NOT NULL DEFAULT '0',
  `interface_metrics_b` bigint(20) NOT NULL DEFAULT '0',
  `search_type` varchar(14) NOT NULL,
  `result` int(11) DEFAULT NULL,
  PRIMARY KEY (`interface_metrics_a`,`interface_metrics_b`,`search_type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_pairs_test`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_pairs_test`;
CREATE TABLE `interface_metrics_pairs_test` (
  `interface_metrics_a` bigint(20) NOT NULL DEFAULT '0',
  `interface_metrics_b` bigint(20) NOT NULL DEFAULT '0',
  `search_type` varchar(14) NOT NULL,
  PRIMARY KEY (`interface_metrics_a`,`interface_metrics_b`,`search_type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_params`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_params`;
CREATE TABLE `interface_metrics_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `interface_metrics_id` bigint(20) NOT NULL,
  `param` varchar(2048) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3635229 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_test`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_test`;
CREATE TABLE `interface_metrics_test` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_type` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `entity_type` varchar(255) DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `modifiers` varchar(255) DEFAULT NULL,
  `fqn` varchar(2048) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `return_type` varchar(2048) DEFAULT NULL,
  `relation_type` varchar(255) DEFAULT NULL,
  `processed` tinyint(1) DEFAULT NULL,
  `processed_params` tinyint(1) DEFAULT NULL,
  `total_params` int(11) DEFAULT NULL,
  `total_words_method` int(11) DEFAULT NULL,
  `total_words_class` int(11) DEFAULT NULL,
  `only_primitive_types` tinyint(4) DEFAULT NULL,
  `is_static` tinyint(4) DEFAULT NULL,
  `has_type_same_package` tinyint(4) DEFAULT NULL,
  `p0_c0_w0_t0` int(11) DEFAULT NULL,
  `p0_c0_w0_t1` int(11) DEFAULT NULL,
  `p0_c0_w1_t0` int(11) DEFAULT NULL,
  `p0_c0_w1_t1` int(11) DEFAULT NULL,
  `p0_c1_w0_t0` int(11) DEFAULT NULL,
  `p0_c1_w0_t1` int(11) DEFAULT NULL,
  `p0_c1_w1_t0` int(11) DEFAULT NULL,
  `p0_c1_w1_t1` int(11) DEFAULT NULL,
  `p1_c0_w0_t0` int(11) DEFAULT NULL,
  `p1_c0_w0_t1` int(11) DEFAULT NULL,
  `p1_c0_w1_t0` int(11) DEFAULT NULL,
  `p1_c0_w1_t1` int(11) DEFAULT NULL,
  `p1_c1_w0_t0` int(11) DEFAULT NULL,
  `p1_c1_w0_t1` int(11) DEFAULT NULL,
  `p1_c1_w1_t0` int(11) DEFAULT NULL,
  `p1_c1_w1_t1` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_top`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_top`;
CREATE TABLE `interface_metrics_top` (
  `class` enum('R_API_TOP3','R_API_NOT3','R_API_ALL*','R_USR_TOP3','R_USR_NOT3','R_USR_ALL*','P_API_TOP3','P_API_NOT3','P_API_ALL*','P_USR_TOP3','P_USR_NOT3','P_USR_ALL*') NOT NULL,
  `description` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL DEFAULT '',
  `total` bigint(20) NOT NULL,
  PRIMARY KEY (`class`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_types`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_types`;
CREATE TABLE `interface_metrics_types` (
  `class` int(1) NOT NULL,
  `description` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics`;
CREATE TABLE `interface_metrics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_type` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `entity_type` varchar(255) DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `modifiers` varchar(255) DEFAULT NULL,
  `fqn` varchar(2048) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `return_type` varchar(2048) DEFAULT NULL,
  `relation_type` varchar(255) DEFAULT NULL,
  `processed` tinyint(1) DEFAULT NULL,
  `processed_params` tinyint(1) DEFAULT NULL,
  `total_params` int(11) DEFAULT NULL,
  `total_words_method` int(11) DEFAULT NULL,
  `total_words_class` int(11) DEFAULT NULL,
  `only_primitive_types` tinyint(4) DEFAULT NULL,
  `is_static` tinyint(4) DEFAULT NULL,
  `has_type_same_package` tinyint(4) DEFAULT NULL,
  `p0_c0_w0_t0` int(11) DEFAULT NULL,
  `p0_c0_w0_t1` int(11) DEFAULT NULL,
  `p0_c0_w1_t0` int(11) DEFAULT NULL,
  `p0_c0_w1_t1` int(11) DEFAULT NULL,
  `p0_c1_w0_t0` int(11) DEFAULT NULL,
  `p0_c1_w0_t1` int(11) DEFAULT NULL,
  `p0_c1_w1_t0` int(11) DEFAULT NULL,
  `p0_c1_w1_t1` int(11) DEFAULT NULL,
  `p1_c0_w0_t0` int(11) DEFAULT NULL,
  `p1_c0_w0_t1` int(11) DEFAULT NULL,
  `p1_c0_w1_t0` int(11) DEFAULT NULL,
  `p1_c0_w1_t1` int(11) DEFAULT NULL,
  `p1_c1_w0_t0` int(11) DEFAULT NULL,
  `p1_c1_w0_t1` int(11) DEFAULT NULL,
  `p1_c1_w1_t0` int(11) DEFAULT NULL,
  `p1_c1_w1_t1` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `entity_id` (`entity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1825438 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_filter`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_filter`;
CREATE TABLE `interface_metrics_filter` (
  `entities_filter` varchar(255) NOT NULL,
  `total` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_inner`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_inner`;
CREATE TABLE `interface_metrics_inner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_type` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `entity_type` varchar(255) DEFAULT NULL,
  `entity_id` bigint(20) DEFAULT NULL,
  `modifiers` varchar(255) DEFAULT NULL,
  `fqn` varchar(2048) DEFAULT NULL,
  `params` varchar(2048) DEFAULT NULL,
  `return_type` varchar(2048) DEFAULT NULL,
  `relation_type` varchar(255) DEFAULT NULL,
  `processed` tinyint(1) DEFAULT NULL,
  `processed_params` tinyint(1) DEFAULT NULL,
  `total_params` int(11) DEFAULT NULL,
  `total_words_method` int(11) DEFAULT NULL,
  `total_words_class` int(11) DEFAULT NULL,
  `only_primitive_types` tinyint(4) DEFAULT NULL,
  `is_static` tinyint(4) DEFAULT NULL,
  `has_type_same_package` tinyint(4) DEFAULT NULL,
  `p0_c0_w0_t0` int(11) DEFAULT NULL,
  `p0_c0_w0_t1` int(11) DEFAULT NULL,
  `p0_c0_w1_t0` int(11) DEFAULT NULL,
  `p0_c0_w1_t1` int(11) DEFAULT NULL,
  `p0_c1_w0_t0` int(11) DEFAULT NULL,
  `p0_c1_w0_t1` int(11) DEFAULT NULL,
  `p0_c1_w1_t0` int(11) DEFAULT NULL,
  `p0_c1_w1_t1` int(11) DEFAULT NULL,
  `p1_c0_w0_t0` int(11) DEFAULT NULL,
  `p1_c0_w0_t1` int(11) DEFAULT NULL,
  `p1_c0_w1_t0` int(11) DEFAULT NULL,
  `p1_c0_w1_t1` int(11) DEFAULT NULL,
  `p1_c1_w0_t0` int(11) DEFAULT NULL,
  `p1_c1_w0_t1` int(11) DEFAULT NULL,
  `p1_c1_w1_t0` int(11) DEFAULT NULL,
  `p1_c1_w1_t1` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  KEY `entity_id` (`entity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1825438 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_pairs_clone_10`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_pairs_clone_10`;
CREATE TABLE `interface_metrics_pairs_clone_10` (
  `i` bigint(20) NOT NULL,
  `header_id_a` bigint(20) NOT NULL,
  `header_id_b` bigint(20) NOT NULL,
  `entity_id_a` bigint(20) DEFAULT NULL,
  `entity_id_b` bigint(255) DEFAULT NULL,
  `processed` tinyint(4) DEFAULT NULL,
  KEY `entity_id_a` (`entity_id_a`),
  KEY `entity_id_b` (`entity_id_b`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `interface_metrics_compare`
-- ----------------------------
DROP TABLE IF EXISTS `interface_metrics_compare`;
CREATE TABLE `interface_metrics_compare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_metrics_a` int(11) DEFAULT NULL,
  `interface_metrics_b` int(11) DEFAULT NULL,
  `exec1` tinyint(4) DEFAULT NULL,
  `exec2` tinyint(4) DEFAULT NULL,
  `exec3` tinyint(4) DEFAULT NULL,
  `exec4` tinyint(4) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17959 DEFAULT CHARSET=latin1;
