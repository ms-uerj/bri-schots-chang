-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt


--
-- Create schema bri
--

CREATE DATABASE IF NOT EXISTS bri;
USE bri;

--
-- Definition of table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL auto_increment,
  `FK_RECORD_ID` int(11) default NULL,
  `SCORE` text,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4820 DEFAULT CHARSET=latin1;


--
-- Definition of table `query`
--

DROP TABLE IF EXISTS `query`;
CREATE TABLE `query` (
  `id` int(11) NOT NULL,
  `QUESTION` text,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


--
-- Definition of table `query_evaluation`
--

DROP TABLE IF EXISTS `query_evaluation`;
CREATE TABLE `query_evaluation` (
  `Query_id` int(11) NOT NULL,
  `evaluations_id` int(11) NOT NULL,
  UNIQUE KEY `evaluations_id` (`evaluations_id`),
  KEY `FK511B9753132FB1ED` (`evaluations_id`),
  KEY `FK511B97537EF82EC` (`Query_id`),
  CONSTRAINT `FK511B9753132FB1ED` FOREIGN KEY (`evaluations_id`) REFERENCES `evaluation` (`id`),
  CONSTRAINT `FK511B97537EF82EC` FOREIGN KEY (`Query_id`) REFERENCES `query` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


--
-- Definition of table `record`
--

DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `RECORD_ID` int(11) NOT NULL,
  `ABSTRACT` text,
  `DATA` text,
  `TITLE` text,
	FULLTEXT (ABSTRACT, DATA, TITLE),  
  PRIMARY KEY  (`RECORD_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE record ADD FULLTEXT INDEX (ABSTRACT);
ALTER TABLE record ADD FULLTEXT INDEX (TITLE);
ALTER TABLE record ADD FULLTEXT INDEX (DATA);
