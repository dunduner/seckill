/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.27 : Database - dszt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dszt` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dszt`;

/*Table structure for table `seckill` */

DROP TABLE IF EXISTS `seckill`;

CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品的id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `seckill` */

insert  into `seckill`(`seckill_id`,`name`,`number`,`start_time`,`end_time`,`create_time`) values (1,'100元秒杀红米note',396,'2017-03-14 20:57:05','2017-03-30 09:57:09','2017-03-09 09:57:47'),(2,'200元秒杀小米4',192,'2017-03-08 09:56:38','2017-03-25 09:56:42','2017-03-09 09:57:23'),(3,'300元秒杀ipad2',300,'2017-03-08 09:56:07','2017-03-13 09:56:12','2017-03-09 09:56:50'),(4,'4000元秒杀苹果6',96,'2017-03-23 09:55:32','2017-04-01 09:55:47','2017-03-09 09:56:30'),(5,'588秒杀化妆品',1000,'2017-03-29 11:01:14','2017-04-01 11:01:18','2017-03-20 11:02:17');

/*Table structure for table `success_killed` */

DROP TABLE IF EXISTS `success_killed`;

CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL COMMENT '无效0  成功1',
  `state` tinyint(4) NOT NULL DEFAULT '-1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `success_killed` */

insert  into `success_killed`(`seckill_id`,`user_phone`,`state`,`create_time`) values (1,13623865089,0,'2017-03-24 16:19:56'),(1,18888888888,0,'2017-03-24 16:42:52'),(2,13623865089,0,'2017-03-24 16:28:38'),(4,13623865089,0,'2017-03-24 16:24:40'),(4,18888888888,0,'2017-03-27 15:04:19');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
