/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : hawaii
Target Host     : localhost:3306
Target Database : hawaii
Date: 2016-09-27 21:31:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for advertcomprelation
-- ----------------------------
DROP TABLE IF EXISTS `advertcomprelation`;
CREATE TABLE `advertcomprelation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPID` int(11) NOT NULL,
  `ADVERTID` int(11) NOT NULL,
  `SYSTYPE` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advertcomprelation
-- ----------------------------

-- ----------------------------
-- Table structure for advertinfo
-- ----------------------------
DROP TABLE IF EXISTS `advertinfo`;
CREATE TABLE `advertinfo` (
  `ADVERTID` int(11) NOT NULL AUTO_INCREMENT,
  `ADVERTNAME` varchar(100) NOT NULL COMMENT '广告文件名称',
  `PATH` varchar(100) NOT NULL COMMENT '广告图片地址',
  `CREATETIME` datetime NOT NULL COMMENT '上传时间',
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ADVERTID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='广告文件信息表';

-- ----------------------------
-- Records of advertinfo
-- ----------------------------

-- ----------------------------
-- Table structure for allotedseatinfo
-- ----------------------------
DROP TABLE IF EXISTS `allotedseatinfo`;
CREATE TABLE `allotedseatinfo` (
  `TABLENO` int(11) NOT NULL,
  `SEATNO` int(11) NOT NULL,
  `COMPID` int(11) NOT NULL DEFAULT '0',
  `MEMID` int(11) NOT NULL DEFAULT '0',
  `CREATETIME` datetime NOT NULL,
  PRIMARY KEY (`TABLENO`,`SEATNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这个是已经分出去的座位，都会记录在这里，主要是防止一个座位被重复分的问题。tableno,seatno双主键，';

-- ----------------------------
-- Records of allotedseatinfo
-- ----------------------------

-- ----------------------------
-- Table structure for cardtable
-- ----------------------------
DROP TABLE IF EXISTS `cardtable`;
CREATE TABLE `cardtable` (
  `TABLENO` int(11) NOT NULL COMMENT '人为设置的牌桌号码。',
  `COMPID` int(11) NOT NULL COMMENT '占用的比赛ID',
  `COMPNAME` varchar(90) DEFAULT NULL COMMENT '比赛名称',
  `TABLESTATE` tinyint(4) NOT NULL COMMENT '牌桌状态：1、使用中；0、空闲中；-1、禁用',
  `UUIDDECIMAL` bigint(20) DEFAULT NULL COMMENT '员工NFC卡TAGID的十进制数字',
  `ADDRESS` varchar(60) DEFAULT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`TABLENO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='牌桌资源表';

-- ----------------------------
-- Records of cardtable
-- ----------------------------

-- ----------------------------
-- Table structure for competitioninfo
-- ----------------------------
DROP TABLE IF EXISTS `competitioninfo`;
CREATE TABLE `competitioninfo` (
  `COMPID` int(11) NOT NULL AUTO_INCREMENT,
  `ROUNDTEMPID` int(11) NOT NULL,
  `COMPNAME` varchar(90) NOT NULL COMMENT '比赛名称',
  `COMPSTATE` int(11) NOT NULL COMMENT '-1、已删除；0、未开放；1、正在报名，比赛未开始；2、正在报名，比赛已开始；3、停止报名，比赛未开始，（该状态出现在比赛晋级导入的时候）；4、停止报名，比赛进行中；5、比赛已结束',
  `COMPPAUSE` int(11) NOT NULL COMMENT '比赛暂停：0、未暂停（进行中）；1、自动暂停（比赛盲注休息）；2、人为暂停（人为点击暂停比赛）',
  `COMPTYPE` int(11) NOT NULL COMMENT '比赛类型：1、晋级淘汰赛；0、单场淘汰赛',
  `SYSTYPE` tinyint(4) NOT NULL DEFAULT '1',
  `REGFEE` int(11) NOT NULL COMMENT '参赛费用',
  `SERVICFEE` int(11) NOT NULL COMMENT '服务费',
  `BEGINCHIP` int(11) NOT NULL COMMENT '会员报名比赛所得到的筹码量',
  `AMOUNTUNIT` int(11) NOT NULL COMMENT '货币类型：1、人民币；2、美元',
  `AWORD` int(11) NOT NULL COMMENT '是否带入奖励模板：1、带入；0、不带入',
  `STOPREGRANK` int(11) NOT NULL COMMENT '报名截止的盲注级别，在改盲注开始时，截止报名',
  `LEASTPRIZE` int(11) NOT NULL DEFAULT '0',
  `SUBPLAYERCOUNT` tinyint(4) NOT NULL DEFAULT '0' COMMENT '需要减掉的人数',
  `TOTALPLAYER` int(11) NOT NULL DEFAULT '0',
  `SYNCDATA` int(11) NOT NULL DEFAULT '0' COMMENT '是否同步数据：1、同步；0、不同步',
  `TABLETYPE` int(11) NOT NULL COMMENT '桌子类型：10、十人桌；9、九人桌；6、六人桌',
  `ASSIGNSEAT` int(11) NOT NULL COMMENT '是否使用分桌系统：1、使用；0、不使用',
  `REENTRY` int(11) NOT NULL DEFAULT '0' COMMENT '是否能再次报名：1、可以多次报名；0、不可多次报名',
  `STARTTIME` datetime NOT NULL COMMENT '比赛开始时间：精确到秒',
  `STARTREGTIME` datetime DEFAULT NULL,
  `CREATETIME` datetime NOT NULL COMMENT '比赛创建时间',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '比赛更新时间',
  `ENDTIME` datetime DEFAULT NULL COMMENT '比赛结束时间',
  `PAUSETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`COMPID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of competitioninfo
-- ----------------------------

-- ----------------------------
-- Table structure for compimportlog
-- ----------------------------
DROP TABLE IF EXISTS `compimportlog`;
CREATE TABLE `compimportlog` (
  `SOURCECOMPID` int(11) NOT NULL,
  `DESTCOMPID` int(11) NOT NULL,
  `IMPORTTIME` datetime NOT NULL,
  `EMPUUID` varchar(20) NOT NULL,
  PRIMARY KEY (`SOURCECOMPID`),
  UNIQUE KEY `INDEX_SOURCECOMPID` (`SOURCECOMPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='比赛进阶导入记录表';

-- ----------------------------
-- Records of compimportlog
-- ----------------------------

-- ----------------------------
-- Table structure for compmovedseatlog
-- ----------------------------
DROP TABLE IF EXISTS `compmovedseatlog`;
CREATE TABLE `compmovedseatlog` (
  `LOGID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPID` int(11) NOT NULL COMMENT '比赛ID',
  `MEMID` int(11) NOT NULL COMMENT '会员ID',
  `OLDTABLENO` int(11) NOT NULL COMMENT '原桌号',
  `OLDSEATNO` tinyint(4) NOT NULL COMMENT '原座位号',
  `EMPUUID` varchar(20) NOT NULL COMMENT '操作人ID',
  `NEWTABLENO` int(11) NOT NULL COMMENT '新桌号',
  `NEWSEATNO` tinyint(4) NOT NULL COMMENT '新座位号',
  `OPTYPE` smallint(6) NOT NULL COMMENT '类型：1、爆桌；2、平衡',
  `CREATETIME` bigint(20) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`LOGID`)
) ENGINE=InnoDB AUTO_INCREMENT=1605 DEFAULT CHARSET=utf8 COMMENT='暂时主要记录比赛中平衡，爆桌两种操作的日志';

-- ----------------------------
-- Records of compmovedseatlog
-- ----------------------------

-- ----------------------------
-- Table structure for comprunningprize
-- ----------------------------
DROP TABLE IF EXISTS `comprunningprize`;
CREATE TABLE `comprunningprize` (
  `RANKING` int(11) NOT NULL COMMENT '比赛名次',
  `COMPID` int(11) NOT NULL,
  `MEMID` int(11) NOT NULL,
  `PERCENT` float NOT NULL COMMENT '奖励百分比',
  `AMOUNTINT` int(11) NOT NULL COMMENT '奖金金额',
  `AMOUNT` double NOT NULL,
  `TABLENO` int(11) NOT NULL,
  `SEATNO` tinyint(4) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `UPDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`RANKING`,`COMPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开启奖励的比赛，运行中产生的奖励信息';

-- ----------------------------
-- Records of comprunningprize
-- ----------------------------

-- ----------------------------
-- Table structure for comprunninground
-- ----------------------------
DROP TABLE IF EXISTS `comprunninground`;
CREATE TABLE `comprunninground` (
  `COMPID` int(11) NOT NULL,
  `ROUNDTEMPID` int(11) NOT NULL,
  `CURBIGBLIND` int(11) NOT NULL COMMENT '当前盲注大盲',
  `CURSMALLBLIND` int(11) NOT NULL COMMENT '当前盲注小盲',
  `CURBEFORECHIP` int(11) NOT NULL COMMENT '当前盲注，前注',
  `CURRANK` smallint(6) NOT NULL COMMENT '当前盲注级别',
  `CURTYPE` tinyint(4) NOT NULL COMMENT '当前盲注类型：1、计时盲注；0、休息间隔',
  `STEPLEN` int(11) NOT NULL COMMENT '盲注步进，单位秒',
  `CURSTARTTIME` bigint(20) NOT NULL COMMENT '当前盲注生效时间',
  `RESTARTTIME` bigint(20) NOT NULL COMMENT '比赛从暂停中恢复的时间',
  `SYSTYPE` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`COMPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='比赛开始之后，才会把运行盲注信息放到这个表里，这个表里永远都是当前盲注信息和下一级别盲注信息';

-- ----------------------------
-- Records of comprunninground
-- ----------------------------

-- ----------------------------
-- Table structure for emp_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `emp_role_relation`;
CREATE TABLE `emp_role_relation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEID` int(11) NOT NULL,
  `EMPID` int(11) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色员工关系，员工的必须要附属于一个角色下才有操作权限';

-- ----------------------------
-- Records of emp_role_relation
-- ----------------------------
INSERT INTO `emp_role_relation` VALUES ('1', '1', '1', '1');
INSERT INTO `emp_role_relation` VALUES ('2', '1', '2', '1');
INSERT INTO `emp_role_relation` VALUES ('3', '1', '3', '1');
INSERT INTO `emp_role_relation` VALUES ('9', '3', '11', '1');
INSERT INTO `emp_role_relation` VALUES ('10', '3', '12', '1');
INSERT INTO `emp_role_relation` VALUES ('11', '2', '13', '1');
INSERT INTO `emp_role_relation` VALUES ('12', '2', '14', '1');
INSERT INTO `emp_role_relation` VALUES ('13', '2', '15', '1');
INSERT INTO `emp_role_relation` VALUES ('14', '2', '16', '1');
INSERT INTO `emp_role_relation` VALUES ('15', '2', '18', '1');
INSERT INTO `emp_role_relation` VALUES ('16', '2', '17', '1');
INSERT INTO `emp_role_relation` VALUES ('17', '4', '19', '1');
INSERT INTO `emp_role_relation` VALUES ('18', '4', '21', '1');
INSERT INTO `emp_role_relation` VALUES ('19', '4', '23', '1');
INSERT INTO `emp_role_relation` VALUES ('20', '4', '24', '1');
INSERT INTO `emp_role_relation` VALUES ('21', '4', '25', '1');
INSERT INTO `emp_role_relation` VALUES ('22', '4', '26', '1');
INSERT INTO `emp_role_relation` VALUES ('23', '1', '27', '1');
INSERT INTO `emp_role_relation` VALUES ('24', '4', '28', '1');
INSERT INTO `emp_role_relation` VALUES ('25', '4', '29', '1');
INSERT INTO `emp_role_relation` VALUES ('26', '1', '30', '1');
INSERT INTO `emp_role_relation` VALUES ('27', '4', '31', '1');
INSERT INTO `emp_role_relation` VALUES ('28', '4', '32', '1');
INSERT INTO `emp_role_relation` VALUES ('29', '4', '33', '1');
INSERT INTO `emp_role_relation` VALUES ('30', '4', '34', '1');
INSERT INTO `emp_role_relation` VALUES ('31', '1', '35', '1');
INSERT INTO `emp_role_relation` VALUES ('32', '1', '36', '1');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `EMPID` int(11) NOT NULL AUTO_INCREMENT,
  `EMPUUID` varchar(20) NOT NULL COMMENT '员工绑定的nfc卡的tagid',
  `EMPNFCID` bigint(20) NOT NULL COMMENT 'NFCID卡tagid的十进制数字值',
  `EMPNAME` varchar(12) NOT NULL,
  `EMPSTATE` tinyint(4) NOT NULL COMMENT '员工状态：1、有效；-1、删除',
  `EMPMOBILE` varchar(15) DEFAULT NULL,
  `EMPIMAGE` varchar(100) DEFAULT NULL,
  `SYSTYPE` tinyint(4) NOT NULL COMMENT '系统分类：1、WPT赛事系统',
  `EMPIDENTNO` varchar(30) DEFAULT NULL,
  `EMPIDENTTYPE` tinyint(4) DEFAULT NULL COMMENT '身份证件类型：1、身份证；2、护照',
  `PASSWORD` varchar(16) DEFAULT NULL,
  `CREATETIME` datetime NOT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `DELTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`EMPID`),
  UNIQUE KEY `INDEX_EMPUUID` (`EMPUUID`),
  UNIQUE KEY `INDEX_EMPNFCID` (`EMPNFCID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='员工表，系统的使用者';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '044F52DA493480', '1130306560069379', '刘文彬', '1', '13100001113', null, '1', '00', '1', null, '2015-04-20 14:19:15', null, null);

-- ----------------------------
-- Table structure for initednfc
-- ----------------------------
DROP TABLE IF EXISTS `initednfc`;
CREATE TABLE `initednfc` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NFCID` bigint(20) NOT NULL,
  `NFCSTATE` tinyint(4) NOT NULL COMMENT '1、有效；-1、已删除',
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_INITNFCID` (`NFCID`)
) ENGINE=InnoDB AUTO_INCREMENT=2460 DEFAULT CHARSET=utf8 COMMENT='初始化的会员卡，之后会员使用的卡，必须在这个表里';

-- ----------------------------
-- Records of initednfc
-- ----------------------------
INSERT INTO `initednfc` VALUES ('1', '1130306560069379', '1', '1');

-- ----------------------------
-- Table structure for locktable
-- ----------------------------
DROP TABLE IF EXISTS `locktable`;
CREATE TABLE `locktable` (
  `LOCKTYPE` tinyint(4) NOT NULL COMMENT '主键，锁类型：0、奖励；1、座位；',
  `LOCKSTATE` tinyint(4) NOT NULL COMMENT '这个字段的值，暂时没意义',
  PRIMARY KEY (`LOCKTYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需要定义的锁，都在这个表里，不同的锁，对应不同的记录';

-- ----------------------------
-- Records of locktable
-- ----------------------------
INSERT INTO `locktable` VALUES ('0', '0');
INSERT INTO `locktable` VALUES ('1', '0');

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UUIDDECIMAL` bigint(20) NOT NULL,
  `USERNAME` varchar(30) NOT NULL,
  `LOGINTIME` datetime NOT NULL,
  `IP` bigint(20) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Employee每次登录这里都会多一条日志记录';

-- ----------------------------
-- Records of loginlog
-- ----------------------------

-- ----------------------------
-- Table structure for memberinfo
-- ----------------------------
DROP TABLE IF EXISTS `memberinfo`;
CREATE TABLE `memberinfo` (
  `MEMID` int(11) NOT NULL AUTO_INCREMENT,
  `NFCID` bigint(20) NOT NULL,
  `CARDNO` varchar(8) NOT NULL COMMENT '会员卡号',
  `MEMNAME` varchar(30) NOT NULL,
  `MEMSTATE` tinyint(4) NOT NULL COMMENT '会员状态：1、有效；-1、删除',
  `MEMIMAGE` varchar(100) DEFAULT NULL COMMENT '会员照片路径',
  `MEMMOBILE` varchar(15) NOT NULL,
  `MEMSEX` tinyint(4) NOT NULL COMMENT '会员性别：1、男；0、女',
  `MEMIDENTNO` varchar(30) NOT NULL,
  `CHIP` int(11) NOT NULL DEFAULT '0',
  `BALANCE` int(11) NOT NULL DEFAULT '0',
  `SYSTYPE` tinyint(4) NOT NULL COMMENT '1、wpt',
  `CREATETIME` datetime NOT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `DELTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`MEMID`),
  UNIQUE KEY `INDEX_MEMNFCID` (`NFCID`),
  UNIQUE KEY `INDEX_MEMCARDNO` (`CARDNO`),
  UNIQUE KEY `INDEX_MEMIDENTNO` (`MEMIDENTNO`)
) ENGINE=InnoDB AUTO_INCREMENT=1125 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of memberinfo
-- ----------------------------

-- ----------------------------
-- Table structure for memcompinfo
-- ----------------------------
DROP TABLE IF EXISTS `memcompinfo`;
CREATE TABLE `memcompinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MEMID` int(11) NOT NULL,
  `COMPID` int(11) NOT NULL,
  `MCSTATE` int(11) NOT NULL COMMENT '参赛中的状态：-1、删除；0、已淘汰；1、已报名；2、晋级',
  `TABLENO` int(11) NOT NULL,
  `SEATNO` tinyint(4) NOT NULL,
  `CHIP` int(11) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  `REGTIME` datetime NOT NULL,
  `OUTTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2918 DEFAULT CHARSET=utf8 COMMENT='会员参加的比赛信息，中间表';

-- ----------------------------
-- Records of memcompinfo
-- ----------------------------

-- ----------------------------
-- Table structure for msgcmd
-- ----------------------------
DROP TABLE IF EXISTS `msgcmd`;
CREATE TABLE `msgcmd` (
  `MSGID` int(11) NOT NULL,
  `MSGNAME` varchar(30) NOT NULL,
  `MSGDESC` varchar(90) DEFAULT NULL,
  `PUSHCLASS` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MSGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TCP的消息号';

-- ----------------------------
-- Records of msgcmd
-- ----------------------------
INSERT INTO `msgcmd` VALUES ('101', 'SCREENSHOW', '大屏幕信息显示消息', 'com.evsward.server.thread.ScreenShowMsgPushServiceImpl');
INSERT INTO `msgcmd` VALUES ('501', 'PADDEVIMAN', 'PAD端大屏幕设备列表消息', 'com.evsward.server.thread.DevManMsgPushServiceImpl');
INSERT INTO `msgcmd` VALUES ('502', 'PADCOMPLIST', 'PAD端比赛列表消息', 'com.evsward.server.thread.CompListMsgPushServiceImpl');
INSERT INTO `msgcmd` VALUES ('503', 'PADCOMPSEAT', 'PAD端比赛管理--座位信息消息', 'com.evsward.server.thread.CompManSeatMsgPushServiceImpl');
INSERT INTO `msgcmd` VALUES ('504', 'PADCOMPPROC', 'PAD端比赛管理--进程信息消息', 'com.evsward.server.thread.CompManProcMshPushServiceImpl');

-- ----------------------------
-- Table structure for msgfuncrelation
-- ----------------------------
DROP TABLE IF EXISTS `msgfuncrelation`;
CREATE TABLE `msgfuncrelation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FUNCID` int(11) NOT NULL,
  `MSGID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='消息类型和出发消息推送功能关系表';

-- ----------------------------
-- Records of msgfuncrelation
-- ----------------------------
INSERT INTO `msgfuncrelation` VALUES ('1', '1', '503');
INSERT INTO `msgfuncrelation` VALUES ('2', '1', '504');
INSERT INTO `msgfuncrelation` VALUES ('3', '1', '101');
INSERT INTO `msgfuncrelation` VALUES ('5', '10', '502');
INSERT INTO `msgfuncrelation` VALUES ('6', '10', '504');
INSERT INTO `msgfuncrelation` VALUES ('7', '10', '101');
INSERT INTO `msgfuncrelation` VALUES ('9', '31', '503');
INSERT INTO `msgfuncrelation` VALUES ('10', '31', '504');
INSERT INTO `msgfuncrelation` VALUES ('11', '31', '101');
INSERT INTO `msgfuncrelation` VALUES ('12', '32', '503');
INSERT INTO `msgfuncrelation` VALUES ('13', '33', '503');
INSERT INTO `msgfuncrelation` VALUES ('14', '34', '502');
INSERT INTO `msgfuncrelation` VALUES ('15', '34', '504');
INSERT INTO `msgfuncrelation` VALUES ('16', '34', '101');
INSERT INTO `msgfuncrelation` VALUES ('18', '35', '502');
INSERT INTO `msgfuncrelation` VALUES ('19', '35', '504');
INSERT INTO `msgfuncrelation` VALUES ('20', '35', '101');
INSERT INTO `msgfuncrelation` VALUES ('22', '36', '504');
INSERT INTO `msgfuncrelation` VALUES ('23', '36', '101');
INSERT INTO `msgfuncrelation` VALUES ('25', '37', '504');
INSERT INTO `msgfuncrelation` VALUES ('26', '37', '101');
INSERT INTO `msgfuncrelation` VALUES ('27', '38', '504');
INSERT INTO `msgfuncrelation` VALUES ('28', '38', '101');
INSERT INTO `msgfuncrelation` VALUES ('29', '39', '502');
INSERT INTO `msgfuncrelation` VALUES ('30', '39', '503');
INSERT INTO `msgfuncrelation` VALUES ('31', '39', '504');
INSERT INTO `msgfuncrelation` VALUES ('32', '39', '101');
INSERT INTO `msgfuncrelation` VALUES ('34', '40', '502');
INSERT INTO `msgfuncrelation` VALUES ('35', '40', '503');
INSERT INTO `msgfuncrelation` VALUES ('36', '40', '504');
INSERT INTO `msgfuncrelation` VALUES ('37', '40', '101');
INSERT INTO `msgfuncrelation` VALUES ('38', '41', '503');
INSERT INTO `msgfuncrelation` VALUES ('39', '42', '101');
INSERT INTO `msgfuncrelation` VALUES ('41', '101', '101');
INSERT INTO `msgfuncrelation` VALUES ('42', '212', '501');
INSERT INTO `msgfuncrelation` VALUES ('43', '212', '101');
INSERT INTO `msgfuncrelation` VALUES ('44', '214', '501');
INSERT INTO `msgfuncrelation` VALUES ('45', '30', '502');
INSERT INTO `msgfuncrelation` VALUES ('47', '30', '101');
INSERT INTO `msgfuncrelation` VALUES ('48', '44', '502');
INSERT INTO `msgfuncrelation` VALUES ('49', '44', '101');
INSERT INTO `msgfuncrelation` VALUES ('50', '44', '503');
INSERT INTO `msgfuncrelation` VALUES ('51', '44', '504');
INSERT INTO `msgfuncrelation` VALUES ('52', '215', '501');

-- ----------------------------
-- Table structure for msgpushfunction
-- ----------------------------
DROP TABLE IF EXISTS `msgpushfunction`;
CREATE TABLE `msgpushfunction` (
  `FUNCID` int(11) NOT NULL,
  `FUNCNAME` varchar(30) NOT NULL,
  `FUNCDESC` varchar(50) NOT NULL,
  PRIMARY KEY (`FUNCID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息推送触发功能';

-- ----------------------------
-- Records of msgpushfunction
-- ----------------------------
INSERT INTO `msgpushfunction` VALUES ('1', 'REG', '选手报名比赛');
INSERT INTO `msgpushfunction` VALUES ('10', 'COMPTHREADSERV', '比赛后台线程维护');
INSERT INTO `msgpushfunction` VALUES ('30', 'CREATECOMP', '新建比赛');
INSERT INTO `msgpushfunction` VALUES ('31', 'OUTMEM', '淘汰选手');
INSERT INTO `msgpushfunction` VALUES ('32', 'BALANCE', '平衡选手');
INSERT INTO `msgpushfunction` VALUES ('33', 'BURST', '爆桌');
INSERT INTO `msgpushfunction` VALUES ('34', 'GOFORWARDBLIND', '比赛管理--进程--进一级盲注');
INSERT INTO `msgpushfunction` VALUES ('35', 'GOBACKBLIND', '比赛管理--进程--退一级盲注');
INSERT INTO `msgpushfunction` VALUES ('36', 'EDITREGEDPLAYER', '编辑报名玩家数量');
INSERT INTO `msgpushfunction` VALUES ('37', 'JUMPBLINDTIME', '比赛管理--进程--调整时间');
INSERT INTO `msgpushfunction` VALUES ('38', 'PAUSECOMP', '暂停(开始)比赛');
INSERT INTO `msgpushfunction` VALUES ('39', 'ENDCOMP', '结束比赛');
INSERT INTO `msgpushfunction` VALUES ('40', 'OPENTREG', '比赛开启报名');
INSERT INTO `msgpushfunction` VALUES ('41', 'RELEASETABLE', '比赛释放牌桌');
INSERT INTO `msgpushfunction` VALUES ('42', 'COMPBINDADVERT', '比赛绑定广告');
INSERT INTO `msgpushfunction` VALUES ('44', 'DELCOMP', '删除比赛');
INSERT INTO `msgpushfunction` VALUES ('101', 'ENTRANCECHECK', '入场安检');
INSERT INTO `msgpushfunction` VALUES ('212', 'DEVIEDIT', '大屏幕设备编辑');
INSERT INTO `msgpushfunction` VALUES ('213', 'DEVIDEL', '大屏幕设备删除');
INSERT INTO `msgpushfunction` VALUES ('214', 'DEVIREG', '大屏幕设备注册');
INSERT INTO `msgpushfunction` VALUES ('215', 'DEVIOFFLINE', '大屏幕设备断线');

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `PRIVID` int(11) NOT NULL AUTO_INCREMENT,
  `PRIVNAME` varchar(60) NOT NULL COMMENT '模块名称',
  `PRIVPATH` varchar(100) DEFAULT NULL COMMENT '模块的访问路径',
  `PRIVSTATE` tinyint(4) NOT NULL COMMENT '模块状态：1、有效；-1、已删除',
  `PRIVPARENTID` int(11) NOT NULL COMMENT '父模块ID，最顶层模块是0',
  `PRIVNAMESHOW` varchar(60) NOT NULL COMMENT '前段显示的模块名称',
  `PRIVDESC` varchar(150) DEFAULT NULL,
  `PRIVUUID` varchar(40) NOT NULL COMMENT '创建模块的nfc卡号',
  `PRIVDEPTH` tinyint(4) NOT NULL COMMENT '模块级别：0、最顶级，一次后推，级别逐步降低',
  `SYSTYPE` int(11) NOT NULL COMMENT '1、wpt',
  `CREATETIME` datetime NOT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `DELTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`PRIVID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='权限功能资源表，所有课分配的权限模块资源集合';

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('1', 'privilegeAddMember', null, '1', '0', '新建会员', null, '0', '0', '1', '2015-04-09 13:43:14', null, null);
INSERT INTO `privilege` VALUES ('2', 'privilegeSearchMember', null, '1', '0', '会员信息查询', null, '0', '0', '1', '2015-04-09 13:43:12', null, null);
INSERT INTO `privilege` VALUES ('3', 'privilegeTVManager', null, '1', '0', '大屏幕设备', null, '0', '0', '1', '2015-04-09 13:43:10', null, null);
INSERT INTO `privilege` VALUES ('4', 'privilegeInitNFCCard', null, '1', '0', '初始化NFC', null, '0', '0', '1', '2015-04-09 13:43:07', null, null);
INSERT INTO `privilege` VALUES ('5', 'privilegeMemEnter', null, '1', '0', '会员报名', null, '0', '0', '1', '2015-06-19 11:43:46', null, null);
INSERT INTO `privilege` VALUES ('6', 'privilegeVIPEnter', null, '1', '0', 'VIP报名', null, '0', '0', '1', '2015-06-19 11:45:15', null, null);
INSERT INTO `privilege` VALUES ('7', 'privilegeEntranceCheck', null, '1', '0', '入场安检', null, '0', '0', '1', '2015-06-19 11:47:32', null, null);
INSERT INTO `privilege` VALUES ('8', 'privilegeCompetitionList', null, '1', '0', '比赛列表', null, '0', '0', '1', '2015-06-19 11:47:58', null, null);
INSERT INTO `privilege` VALUES ('9', 'privilegeCompetitionAdvancedManage', null, '1', '0', '进阶管理', null, '0', '0', '1', '2015-06-19 11:48:24', null, null);
INSERT INTO `privilege` VALUES ('10', 'privilegeTableResManage', null, '1', '0', '牌桌管理', null, '0', '0', '1', '2015-06-19 11:48:50', null, null);
INSERT INTO `privilege` VALUES ('11', 'privilegeRoundTempManager', null, '1', '0', '盲注模板管理', null, '0', '0', '1', '2015-08-05 13:39:44', null, null);
INSERT INTO `privilege` VALUES ('12', 'privilegePrizeTempManager', null, '1', '0', '奖励模板管理', null, '0', '0', '1', '2015-08-05 13:44:21', null, null);
INSERT INTO `privilege` VALUES ('13', 'privilegeRoleManager', null, '1', '0', '角色管理', null, '0', '0', '1', '2015-08-05 13:46:24', null, null);
INSERT INTO `privilege` VALUES ('14', 'privilegeEmployeeManager', null, '1', '0', '员工管理', null, '0', '0', '1', '2015-08-05 13:47:13', null, null);
INSERT INTO `privilege` VALUES ('15', 'privilegeFuncManager', null, '1', '0', '功能管理', null, '0', '0', '1', '2015-08-05 13:48:54', null, null);

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize` (
  `PRIZEID` int(11) NOT NULL AUTO_INCREMENT,
  `PRIZETEMPID` int(11) NOT NULL,
  `ALLMIN` smallint(6) NOT NULL,
  `ALLMAX` int(11) NOT NULL,
  `PERCENT` float NOT NULL,
  `RANKNO` int(11) NOT NULL,
  `RANKNUM` int(11) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  `CREATETIME` datetime NOT NULL,
  PRIMARY KEY (`PRIZEID`)
) ENGINE=InnoDB AUTO_INCREMENT=1585 DEFAULT CHARSET=utf8 COMMENT='奖励模板字典表';

-- ----------------------------
-- Records of prize
-- ----------------------------
INSERT INTO `prize` VALUES ('115', '1', '2', '10', '1', '1', '1', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('116', '1', '11', '20', '0.6', '1', '2', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('117', '1', '11', '20', '0.4', '2', '2', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('118', '1', '21', '30', '0.48', '1', '3', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('119', '1', '21', '30', '0.32', '2', '3', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('120', '1', '21', '30', '0.2', '3', '3', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('121', '1', '31', '40', '0.4', '1', '4', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('122', '1', '31', '40', '0.28', '2', '4', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('123', '1', '31', '40', '0.18', '3', '4', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('124', '1', '31', '40', '0.14', '4', '4', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('125', '1', '41', '50', '0.36', '1', '5', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('126', '1', '41', '50', '0.25', '2', '5', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('127', '1', '41', '50', '0.16', '3', '5', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('128', '1', '41', '50', '0.13', '4', '5', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('129', '1', '41', '50', '0.1', '5', '5', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('130', '1', '51', '60', '0.34', '1', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('131', '1', '51', '60', '0.23', '2', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('132', '1', '51', '60', '0.15', '3', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('133', '1', '51', '60', '0.12', '4', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('134', '1', '51', '60', '0.09', '5', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('135', '1', '51', '60', '0.07', '6', '6', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('136', '1', '61', '87', '0.3', '1', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('137', '1', '61', '87', '0.21', '2', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('138', '1', '61', '87', '0.135', '3', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('139', '1', '61', '87', '0.1', '4', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('140', '1', '61', '87', '0.075', '5', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('141', '1', '61', '87', '0.06', '6', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('142', '1', '61', '87', '0.05', '7', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('143', '1', '61', '87', '0.04', '8', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('144', '1', '61', '87', '0.03', '9', '9', '1', '2015-05-07 16:26:34');
INSERT INTO `prize` VALUES ('145', '1', '88', '112', '0.2815', '1', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('146', '1', '88', '112', '0.1971', '2', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('147', '1', '88', '112', '0.1267', '3', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('148', '1', '88', '112', '0.0939', '4', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('149', '1', '88', '112', '0.0704', '5', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('150', '1', '88', '112', '0.0563', '6', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('151', '1', '88', '112', '0.0469', '7', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('152', '1', '88', '112', '0.0375', '8', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('153', '1', '88', '112', '0.0282', '9', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('154', '1', '88', '112', '0.0205', '10', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('155', '1', '88', '112', '0.0205', '11', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('156', '1', '88', '112', '0.0205', '12', '12', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('157', '1', '113', '137', '0.268', '1', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('158', '1', '113', '137', '0.1877', '2', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('159', '1', '113', '137', '0.1207', '3', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('160', '1', '113', '137', '0.0894', '4', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('161', '1', '113', '137', '0.067', '5', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('162', '1', '113', '137', '0.0536', '6', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('163', '1', '113', '137', '0.0446', '7', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('164', '1', '113', '137', '0.0357', '8', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('165', '1', '113', '137', '0.0268', '9', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('166', '1', '113', '137', '0.0195', '10', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('167', '1', '113', '137', '0.0195', '11', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('168', '1', '113', '137', '0.0195', '12', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('169', '1', '113', '137', '0.016', '13', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('170', '1', '113', '137', '0.016', '14', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('171', '1', '113', '137', '0.016', '15', '15', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('172', '1', '138', '162', '0.2575', '1', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('173', '1', '138', '162', '0.1803', '2', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('174', '1', '138', '162', '0.116', '3', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('175', '1', '138', '162', '0.0859', '4', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('176', '1', '138', '162', '0.0644', '5', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('177', '1', '138', '162', '0.0515', '6', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('178', '1', '138', '162', '0.0428', '7', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('179', '1', '138', '162', '0.0343', '8', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('180', '1', '138', '162', '0.0257', '9', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('181', '1', '138', '162', '0.0187', '10', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('182', '1', '138', '162', '0.0187', '11', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('183', '1', '138', '162', '0.0187', '12', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('184', '1', '138', '162', '0.0154', '13', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('185', '1', '138', '162', '0.0154', '14', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('186', '1', '138', '162', '0.0154', '15', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('187', '1', '138', '162', '0.0131', '16', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('188', '1', '138', '162', '0.0131', '17', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('189', '1', '138', '162', '0.0131', '18', '18', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('190', '1', '163', '187', '0.2487', '1', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('191', '1', '163', '187', '0.1743', '2', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('192', '1', '163', '187', '0.1121', '3', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('193', '1', '163', '187', '0.083', '4', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('194', '1', '163', '187', '0.0623', '5', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('195', '1', '163', '187', '0.0498', '6', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('196', '1', '163', '187', '0.0414', '7', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('197', '1', '163', '187', '0.0332', '8', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('198', '1', '163', '187', '0.0248', '9', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('199', '1', '163', '187', '0.0181', '10', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('200', '1', '163', '187', '0.0181', '11', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('201', '1', '163', '187', '0.0181', '12', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('202', '1', '163', '187', '0.0149', '13', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('203', '1', '163', '187', '0.0149', '14', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('204', '1', '163', '187', '0.0149', '15', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('205', '1', '163', '187', '0.0127', '16', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('206', '1', '163', '187', '0.0127', '17', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('207', '1', '163', '187', '0.0127', '18', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('208', '1', '163', '187', '0.0111', '19', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('209', '1', '163', '187', '0.0111', '20', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('210', '1', '163', '187', '0.0111', '21', '21', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('211', '1', '188', '212', '0.2415', '1', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('212', '1', '188', '212', '0.1693', '2', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('213', '1', '188', '212', '0.1088', '3', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('214', '1', '188', '212', '0.0806', '4', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('215', '1', '188', '212', '0.0605', '5', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('216', '1', '188', '212', '0.0484', '6', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('217', '1', '188', '212', '0.0402', '7', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('218', '1', '188', '212', '0.0322', '8', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('219', '1', '188', '212', '0.0241', '9', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('220', '1', '188', '212', '0.0176', '10', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('221', '1', '188', '212', '0.0176', '11', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('222', '1', '188', '212', '0.0176', '12', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('223', '1', '188', '212', '0.0145', '13', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('224', '1', '188', '212', '0.0145', '14', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('225', '1', '188', '212', '0.0145', '15', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('226', '1', '188', '212', '0.0123', '16', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('227', '1', '188', '212', '0.0123', '17', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('228', '1', '188', '212', '0.0123', '18', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('229', '1', '188', '212', '0.0108', '19', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('230', '1', '188', '212', '0.0108', '20', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('231', '1', '188', '212', '0.0108', '21', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('232', '1', '188', '212', '0.0096', '22', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('233', '1', '188', '212', '0.0096', '23', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('234', '1', '188', '212', '0.0096', '24', '24', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('235', '1', '213', '262', '0.2353', '1', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('236', '1', '213', '262', '0.1651', '2', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('237', '1', '213', '262', '0.1062', '3', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('238', '1', '213', '262', '0.0786', '4', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('239', '1', '213', '262', '0.059', '5', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('240', '1', '213', '262', '0.0472', '6', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('241', '1', '213', '262', '0.0392', '7', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('242', '1', '213', '262', '0.0314', '8', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('243', '1', '213', '262', '0.0235', '9', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('244', '1', '213', '262', '0.0172', '10', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('245', '1', '213', '262', '0.0172', '11', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('246', '1', '213', '262', '0.0172', '12', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('247', '1', '213', '262', '0.0141', '13', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('248', '1', '213', '262', '0.0141', '14', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('249', '1', '213', '262', '0.0141', '15', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('250', '1', '213', '262', '0.012', '16', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('251', '1', '213', '262', '0.012', '17', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('252', '1', '213', '262', '0.012', '18', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('253', '1', '213', '262', '0.0105', '19', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('254', '1', '213', '262', '0.0105', '20', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('255', '1', '213', '262', '0.0105', '21', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('256', '1', '213', '262', '0.0094', '22', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('257', '1', '213', '262', '0.0094', '23', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('258', '1', '213', '262', '0.0094', '24', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('259', '1', '213', '262', '0.0083', '25', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('260', '1', '213', '262', '0.0083', '26', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('261', '1', '213', '262', '0.0083', '27', '27', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('262', '1', '263', '337', '0.2209', '1', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('263', '1', '263', '337', '0.155', '2', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('264', '1', '263', '337', '0.0997', '3', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('265', '1', '263', '337', '0.0738', '4', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('266', '1', '263', '337', '0.0554', '5', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('267', '1', '263', '337', '0.0443', '6', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('268', '1', '263', '337', '0.0368', '7', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('269', '1', '263', '337', '0.0295', '8', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('270', '1', '263', '337', '0.0221', '9', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('271', '1', '263', '337', '0.0161', '10', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('272', '1', '263', '337', '0.0161', '11', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('273', '1', '263', '337', '0.0161', '12', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('274', '1', '263', '337', '0.0132', '13', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('275', '1', '263', '337', '0.0132', '14', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('276', '1', '263', '337', '0.0132', '15', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('277', '1', '263', '337', '0.0113', '16', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('278', '1', '263', '337', '0.0113', '17', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('279', '1', '263', '337', '0.0113', '18', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('280', '1', '263', '337', '0.0099', '19', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('281', '1', '263', '337', '0.0099', '20', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('282', '1', '263', '337', '0.0099', '21', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('283', '1', '263', '337', '0.0088', '22', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('284', '1', '263', '337', '0.0088', '23', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('285', '1', '263', '337', '0.0088', '24', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('286', '1', '263', '337', '0.0078', '25', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('287', '1', '263', '337', '0.0078', '26', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('288', '1', '263', '337', '0.0078', '27', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('289', '1', '263', '337', '0.0068', '28', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('290', '1', '263', '337', '0.0068', '29', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('291', '1', '263', '337', '0.0068', '30', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('292', '1', '263', '337', '0.0068', '31', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('293', '1', '263', '337', '0.0068', '32', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('294', '1', '263', '337', '0.0068', '33', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('295', '1', '263', '337', '0.0068', '34', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('296', '1', '263', '337', '0.0068', '35', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('297', '1', '263', '337', '0.0068', '36', '36', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('298', '1', '338', '412', '0.21', '1', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('299', '1', '338', '412', '0.147', '2', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('300', '1', '338', '412', '0.0945', '3', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('301', '1', '338', '412', '0.07', '4', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('302', '1', '338', '412', '0.0525', '5', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('303', '1', '338', '412', '0.042', '6', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('304', '1', '338', '412', '0.035', '7', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('305', '1', '338', '412', '0.028', '8', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('306', '1', '338', '412', '0.021', '9', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('307', '1', '338', '412', '0.0153', '10', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('308', '1', '338', '412', '0.0153', '11', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('309', '1', '338', '412', '0.0153', '12', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('310', '1', '338', '412', '0.0125', '13', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('311', '1', '338', '412', '0.0125', '14', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('312', '1', '338', '412', '0.0125', '15', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('313', '1', '338', '412', '0.0107', '16', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('314', '1', '338', '412', '0.0107', '17', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('315', '1', '338', '412', '0.0107', '18', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('316', '1', '338', '412', '0.0094', '19', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('317', '1', '338', '412', '0.0094', '20', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('318', '1', '338', '412', '0.0094', '21', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('319', '1', '338', '412', '0.0084', '22', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('320', '1', '338', '412', '0.0084', '23', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('321', '1', '338', '412', '0.0084', '24', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('322', '1', '338', '412', '0.0074', '25', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('323', '1', '338', '412', '0.0074', '26', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('324', '1', '338', '412', '0.0074', '27', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('325', '1', '338', '412', '0.0065', '28', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('326', '1', '338', '412', '0.0065', '29', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('327', '1', '338', '412', '0.0065', '30', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('328', '1', '338', '412', '0.0065', '31', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('329', '1', '338', '412', '0.0065', '32', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('330', '1', '338', '412', '0.0065', '33', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('331', '1', '338', '412', '0.0065', '34', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('332', '1', '338', '412', '0.0065', '35', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('333', '1', '338', '412', '0.0065', '36', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('334', '1', '338', '412', '0.0056', '37', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('335', '1', '338', '412', '0.0056', '38', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('336', '1', '338', '412', '0.0056', '39', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('337', '1', '338', '412', '0.0056', '40', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('338', '1', '338', '412', '0.0056', '41', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('339', '1', '338', '412', '0.0056', '42', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('340', '1', '338', '412', '0.0056', '43', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('341', '1', '338', '412', '0.0056', '44', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('342', '1', '338', '412', '0.0056', '45', '45', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('343', '1', '413', '487', '0.201', '1', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('344', '1', '413', '487', '0.1409', '2', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('345', '1', '413', '487', '0.0906', '3', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('346', '1', '413', '487', '0.067', '4', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('347', '1', '413', '487', '0.0503', '5', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('348', '1', '413', '487', '0.0403', '6', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('349', '1', '413', '487', '0.0336', '7', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('350', '1', '413', '487', '0.0268', '8', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('351', '1', '413', '487', '0.0201', '9', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('352', '1', '413', '487', '0.0147', '10', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('353', '1', '413', '487', '0.0147', '11', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('354', '1', '413', '487', '0.0147', '12', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('355', '1', '413', '487', '0.012', '13', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('356', '1', '413', '487', '0.012', '14', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('357', '1', '413', '487', '0.012', '15', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('358', '1', '413', '487', '0.0103', '16', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('359', '1', '413', '487', '0.0103', '17', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('360', '1', '413', '487', '0.0103', '18', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('361', '1', '413', '487', '0.009', '19', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('362', '1', '413', '487', '0.009', '20', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('363', '1', '413', '487', '0.009', '21', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('364', '1', '413', '487', '0.0081', '22', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('365', '1', '413', '487', '0.0081', '23', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('366', '1', '413', '487', '0.0081', '24', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('367', '1', '413', '487', '0.0071', '25', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('368', '1', '413', '487', '0.0071', '26', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('369', '1', '413', '487', '0.0071', '27', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('370', '1', '413', '487', '0.0062', '28', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('371', '1', '413', '487', '0.0062', '29', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('372', '1', '413', '487', '0.0062', '30', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('373', '1', '413', '487', '0.0062', '31', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('374', '1', '413', '487', '0.0062', '32', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('375', '1', '413', '487', '0.0062', '33', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('376', '1', '413', '487', '0.0062', '34', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('377', '1', '413', '487', '0.0062', '35', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('378', '1', '413', '487', '0.0062', '36', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('379', '1', '413', '487', '0.0054', '37', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('380', '1', '413', '487', '0.0054', '38', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('381', '1', '413', '487', '0.0054', '39', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('382', '1', '413', '487', '0.0054', '40', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('383', '1', '413', '487', '0.0054', '41', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('384', '1', '413', '487', '0.0054', '42', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('385', '1', '413', '487', '0.0054', '43', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('386', '1', '413', '487', '0.0054', '44', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('387', '1', '413', '487', '0.0054', '45', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('388', '1', '413', '487', '0.0046', '46', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('389', '1', '413', '487', '0.0046', '47', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('390', '1', '413', '487', '0.0046', '48', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('391', '1', '413', '487', '0.0046', '49', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('392', '1', '413', '487', '0.0046', '50', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('393', '1', '413', '487', '0.0046', '51', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('394', '1', '413', '487', '0.0046', '52', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('395', '1', '413', '487', '0.0046', '53', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('396', '1', '413', '487', '0.0046', '54', '54', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('397', '1', '488', '562', '0.1943', '1', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('398', '1', '488', '562', '0.1362', '2', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('399', '1', '488', '562', '0.0876', '3', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('400', '1', '488', '562', '0.0648', '4', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('401', '1', '488', '562', '0.0486', '5', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('402', '1', '488', '562', '0.039', '6', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('403', '1', '488', '562', '0.0325', '7', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('404', '1', '488', '562', '0.0259', '8', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('405', '1', '488', '562', '0.0195', '9', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('406', '1', '488', '562', '0.0143', '10', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('407', '1', '488', '562', '0.0143', '11', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('408', '1', '488', '562', '0.0143', '12', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('409', '1', '488', '562', '0.0116', '13', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('410', '1', '488', '562', '0.0116', '14', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('411', '1', '488', '562', '0.0116', '15', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('412', '1', '488', '562', '0.01', '16', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('413', '1', '488', '562', '0.01', '17', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('414', '1', '488', '562', '0.01', '18', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('415', '1', '488', '562', '0.0087', '19', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('416', '1', '488', '562', '0.0087', '20', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('417', '1', '488', '562', '0.0087', '21', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('418', '1', '488', '562', '0.0078', '22', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('419', '1', '488', '562', '0.0078', '23', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('420', '1', '488', '562', '0.0078', '24', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('421', '1', '488', '562', '0.0069', '25', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('422', '1', '488', '562', '0.0069', '26', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('423', '1', '488', '562', '0.0069', '27', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('424', '1', '488', '562', '0.006', '28', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('425', '1', '488', '562', '0.006', '29', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('426', '1', '488', '562', '0.006', '30', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('427', '1', '488', '562', '0.006', '31', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('428', '1', '488', '562', '0.006', '32', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('429', '1', '488', '562', '0.006', '33', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('430', '1', '488', '562', '0.006', '34', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('431', '1', '488', '562', '0.006', '35', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('432', '1', '488', '562', '0.006', '36', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('433', '1', '488', '562', '0.0052', '37', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('434', '1', '488', '562', '0.0052', '38', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('435', '1', '488', '562', '0.0052', '39', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('436', '1', '488', '562', '0.0052', '40', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('437', '1', '488', '562', '0.0052', '41', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('438', '1', '488', '562', '0.0052', '42', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('439', '1', '488', '562', '0.0052', '43', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('440', '1', '488', '562', '0.0052', '44', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('441', '1', '488', '562', '0.0052', '45', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('442', '1', '488', '562', '0.0044', '46', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('443', '1', '488', '562', '0.0044', '47', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('444', '1', '488', '562', '0.0044', '48', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('445', '1', '488', '562', '0.0044', '49', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('446', '1', '488', '562', '0.0044', '50', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('447', '1', '488', '562', '0.0044', '51', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('448', '1', '488', '562', '0.0044', '52', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('449', '1', '488', '562', '0.0044', '53', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('450', '1', '488', '562', '0.0044', '54', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('451', '1', '488', '562', '0.0037', '55', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('452', '1', '488', '562', '0.0037', '56', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('453', '1', '488', '562', '0.0037', '57', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('454', '1', '488', '562', '0.0037', '58', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('455', '1', '488', '562', '0.0037', '59', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('456', '1', '488', '562', '0.0037', '60', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('457', '1', '488', '562', '0.0037', '61', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('458', '1', '488', '562', '0.0037', '62', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('459', '1', '488', '562', '0.0037', '63', '63', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('460', '1', '563', '637', '0.189', '1', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('461', '1', '563', '637', '0.1325', '2', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('462', '1', '563', '637', '0.0852', '3', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('463', '1', '563', '637', '0.0631', '4', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('464', '1', '563', '637', '0.0473', '5', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('465', '1', '563', '637', '0.0379', '6', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('466', '1', '563', '637', '0.0316', '7', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('467', '1', '563', '637', '0.0252', '8', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('468', '1', '563', '637', '0.0189', '9', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('469', '1', '563', '637', '0.0139', '10', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('470', '1', '563', '637', '0.0139', '11', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('471', '1', '563', '637', '0.0139', '12', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('472', '1', '563', '637', '0.0113', '13', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('473', '1', '563', '637', '0.0113', '14', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('474', '1', '563', '637', '0.0113', '15', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('475', '1', '563', '637', '0.0097', '16', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('476', '1', '563', '637', '0.0097', '17', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('477', '1', '563', '637', '0.0097', '18', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('478', '1', '563', '637', '0.0085', '19', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('479', '1', '563', '637', '0.0085', '20', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('480', '1', '563', '637', '0.0085', '21', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('481', '1', '563', '637', '0.0076', '22', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('482', '1', '563', '637', '0.0076', '23', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('483', '1', '563', '637', '0.0076', '24', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('484', '1', '563', '637', '0.0067', '25', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('485', '1', '563', '637', '0.0067', '26', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('486', '1', '563', '637', '0.0067', '27', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('487', '1', '563', '637', '0.0058', '28', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('488', '1', '563', '637', '0.0058', '29', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('489', '1', '563', '637', '0.0058', '30', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('490', '1', '563', '637', '0.0058', '31', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('491', '1', '563', '637', '0.0058', '32', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('492', '1', '563', '637', '0.0058', '33', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('493', '1', '563', '637', '0.0058', '34', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('494', '1', '563', '637', '0.0058', '35', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('495', '1', '563', '637', '0.0058', '36', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('496', '1', '563', '637', '0.005', '37', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('497', '1', '563', '637', '0.005', '38', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('498', '1', '563', '637', '0.005', '39', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('499', '1', '563', '637', '0.005', '40', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('500', '1', '563', '637', '0.005', '41', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('501', '1', '563', '637', '0.005', '42', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('502', '1', '563', '637', '0.005', '43', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('503', '1', '563', '637', '0.005', '44', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('504', '1', '563', '637', '0.005', '45', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('505', '1', '563', '637', '0.0043', '46', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('506', '1', '563', '637', '0.0043', '47', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('507', '1', '563', '637', '0.0043', '48', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('508', '1', '563', '637', '0.0043', '49', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('509', '1', '563', '637', '0.0043', '50', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('510', '1', '563', '637', '0.0043', '51', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('511', '1', '563', '637', '0.0043', '52', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('512', '1', '563', '637', '0.0043', '53', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('513', '1', '563', '637', '0.0043', '54', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('514', '1', '563', '637', '0.0036', '55', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('515', '1', '563', '637', '0.0036', '56', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('516', '1', '563', '637', '0.0036', '57', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('517', '1', '563', '637', '0.0036', '58', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('518', '1', '563', '637', '0.0036', '59', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('519', '1', '563', '637', '0.0036', '60', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('520', '1', '563', '637', '0.0036', '61', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('521', '1', '563', '637', '0.0036', '62', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('522', '1', '563', '637', '0.0036', '63', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('523', '1', '563', '637', '0.0031', '64', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('524', '1', '563', '637', '0.0031', '65', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('525', '1', '563', '637', '0.0031', '66', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('526', '1', '563', '637', '0.0031', '67', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('527', '1', '563', '637', '0.0031', '68', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('528', '1', '563', '637', '0.0031', '69', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('529', '1', '563', '637', '0.0031', '70', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('530', '1', '563', '637', '0.0031', '71', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('531', '1', '563', '637', '0.0031', '72', '72', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('532', '1', '638', '712', '0.1847', '1', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('533', '1', '638', '712', '0.1295', '2', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('534', '1', '638', '712', '0.0835', '3', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('535', '1', '638', '712', '0.0617', '4', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('536', '1', '638', '712', '0.0462', '5', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('537', '1', '638', '712', '0.0373', '6', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('538', '1', '638', '712', '0.0309', '7', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('539', '1', '638', '712', '0.0246', '8', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('540', '1', '638', '712', '0.0185', '9', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('541', '1', '638', '712', '0.0136', '10', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('542', '1', '638', '712', '0.0136', '11', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('543', '1', '638', '712', '0.0136', '12', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('544', '1', '638', '712', '0.011', '13', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('545', '1', '638', '712', '0.011', '14', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('546', '1', '638', '712', '0.011', '15', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('547', '1', '638', '712', '0.0095', '16', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('548', '1', '638', '712', '0.0095', '17', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('549', '1', '638', '712', '0.0095', '18', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('550', '1', '638', '712', '0.0083', '19', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('551', '1', '638', '712', '0.0083', '20', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('552', '1', '638', '712', '0.0083', '21', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('553', '1', '638', '712', '0.0074', '22', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('554', '1', '638', '712', '0.0074', '23', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('555', '1', '638', '712', '0.0074', '24', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('556', '1', '638', '712', '0.0065', '25', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('557', '1', '638', '712', '0.0065', '26', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('558', '1', '638', '712', '0.0065', '27', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('559', '1', '638', '712', '0.0057', '28', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('560', '1', '638', '712', '0.0057', '29', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('561', '1', '638', '712', '0.0057', '30', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('562', '1', '638', '712', '0.0057', '31', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('563', '1', '638', '712', '0.0057', '32', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('564', '1', '638', '712', '0.0057', '33', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('565', '1', '638', '712', '0.0057', '34', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('566', '1', '638', '712', '0.0057', '35', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('567', '1', '638', '712', '0.0057', '36', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('568', '1', '638', '712', '0.0049', '37', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('569', '1', '638', '712', '0.0049', '38', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('570', '1', '638', '712', '0.0049', '39', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('571', '1', '638', '712', '0.0049', '40', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('572', '1', '638', '712', '0.0049', '41', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('573', '1', '638', '712', '0.0049', '42', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('574', '1', '638', '712', '0.0049', '43', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('575', '1', '638', '712', '0.0049', '44', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('576', '1', '638', '712', '0.0049', '45', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('577', '1', '638', '712', '0.0042', '46', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('578', '1', '638', '712', '0.0042', '47', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('579', '1', '638', '712', '0.0042', '48', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('580', '1', '638', '712', '0.0042', '49', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('581', '1', '638', '712', '0.0042', '50', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('582', '1', '638', '712', '0.0042', '51', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('583', '1', '638', '712', '0.0042', '52', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('584', '1', '638', '712', '0.0042', '53', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('585', '1', '638', '712', '0.0042', '54', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('586', '1', '638', '712', '0.0035', '55', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('587', '1', '638', '712', '0.0035', '56', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('588', '1', '638', '712', '0.0035', '57', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('589', '1', '638', '712', '0.0035', '58', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('590', '1', '638', '712', '0.0035', '59', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('591', '1', '638', '712', '0.0035', '60', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('592', '1', '638', '712', '0.0035', '61', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('593', '1', '638', '712', '0.0035', '62', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('594', '1', '638', '712', '0.0035', '63', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('595', '1', '638', '712', '0.003', '64', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('596', '1', '638', '712', '0.003', '65', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('597', '1', '638', '712', '0.003', '66', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('598', '1', '638', '712', '0.003', '67', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('599', '1', '638', '712', '0.003', '68', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('600', '1', '638', '712', '0.003', '69', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('601', '1', '638', '712', '0.003', '70', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('602', '1', '638', '712', '0.003', '71', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('603', '1', '638', '712', '0.003', '72', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('604', '1', '638', '712', '0.0025', '73', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('605', '1', '638', '712', '0.0025', '74', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('606', '1', '638', '712', '0.0025', '75', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('607', '1', '638', '712', '0.0025', '76', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('608', '1', '638', '712', '0.0025', '77', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('609', '1', '638', '712', '0.0025', '78', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('610', '1', '638', '712', '0.0025', '79', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('611', '1', '638', '712', '0.0025', '80', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('612', '1', '638', '712', '0.0025', '81', '81', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('613', '1', '713', '787', '0.181', '1', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('614', '1', '713', '787', '0.1269', '2', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('615', '1', '713', '787', '0.0818', '3', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('616', '1', '713', '787', '0.0605', '4', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('617', '1', '713', '787', '0.0453', '5', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('618', '1', '713', '787', '0.0366', '6', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('619', '1', '713', '787', '0.0303', '7', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('620', '1', '713', '787', '0.0241', '8', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('621', '1', '713', '787', '0.0181', '9', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('622', '1', '713', '787', '0.0134', '10', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('623', '1', '713', '787', '0.0134', '11', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('624', '1', '713', '787', '0.0134', '12', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('625', '1', '713', '787', '0.0107', '13', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('626', '1', '713', '787', '0.0107', '14', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('627', '1', '713', '787', '0.0107', '15', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('628', '1', '713', '787', '0.0093', '16', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('629', '1', '713', '787', '0.0093', '17', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('630', '1', '713', '787', '0.0093', '18', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('631', '1', '713', '787', '0.0082', '19', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('632', '1', '713', '787', '0.0082', '20', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('633', '1', '713', '787', '0.0082', '21', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('634', '1', '713', '787', '0.0073', '22', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('635', '1', '713', '787', '0.0073', '23', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('636', '1', '713', '787', '0.0073', '24', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('637', '1', '713', '787', '0.0064', '25', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('638', '1', '713', '787', '0.0064', '26', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('639', '1', '713', '787', '0.0064', '27', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('640', '1', '713', '787', '0.0056', '28', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('641', '1', '713', '787', '0.0056', '29', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('642', '1', '713', '787', '0.0056', '30', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('643', '1', '713', '787', '0.0056', '31', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('644', '1', '713', '787', '0.0056', '32', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('645', '1', '713', '787', '0.0056', '33', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('646', '1', '713', '787', '0.0056', '34', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('647', '1', '713', '787', '0.0056', '35', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('648', '1', '713', '787', '0.0056', '36', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('649', '1', '713', '787', '0.0048', '37', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('650', '1', '713', '787', '0.0048', '38', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('651', '1', '713', '787', '0.0048', '39', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('652', '1', '713', '787', '0.0048', '40', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('653', '1', '713', '787', '0.0048', '41', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('654', '1', '713', '787', '0.0048', '42', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('655', '1', '713', '787', '0.0048', '43', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('656', '1', '713', '787', '0.0048', '44', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('657', '1', '713', '787', '0.0048', '45', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('658', '1', '713', '787', '0.0041', '46', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('659', '1', '713', '787', '0.0041', '47', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('660', '1', '713', '787', '0.0041', '48', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('661', '1', '713', '787', '0.0041', '49', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('662', '1', '713', '787', '0.0041', '50', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('663', '1', '713', '787', '0.0041', '51', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('664', '1', '713', '787', '0.0041', '52', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('665', '1', '713', '787', '0.0041', '53', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('666', '1', '713', '787', '0.0041', '54', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('667', '1', '713', '787', '0.0034', '55', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('668', '1', '713', '787', '0.0034', '56', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('669', '1', '713', '787', '0.0034', '57', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('670', '1', '713', '787', '0.0034', '58', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('671', '1', '713', '787', '0.0034', '59', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('672', '1', '713', '787', '0.0034', '60', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('673', '1', '713', '787', '0.0034', '61', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('674', '1', '713', '787', '0.0034', '62', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('675', '1', '713', '787', '0.0034', '63', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('676', '1', '713', '787', '0.0029', '64', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('677', '1', '713', '787', '0.0029', '65', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('678', '1', '713', '787', '0.0029', '66', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('679', '1', '713', '787', '0.0029', '67', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('680', '1', '713', '787', '0.0029', '68', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('681', '1', '713', '787', '0.0029', '69', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('682', '1', '713', '787', '0.0029', '70', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('683', '1', '713', '787', '0.0029', '71', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('684', '1', '713', '787', '0.0029', '72', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('685', '1', '713', '787', '0.0025', '73', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('686', '1', '713', '787', '0.0025', '74', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('687', '1', '713', '787', '0.0025', '75', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('688', '1', '713', '787', '0.0025', '76', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('689', '1', '713', '787', '0.0025', '77', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('690', '1', '713', '787', '0.0025', '78', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('691', '1', '713', '787', '0.0025', '79', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('692', '1', '713', '787', '0.0025', '80', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('693', '1', '713', '787', '0.0025', '81', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('694', '1', '713', '787', '0.0022', '82', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('695', '1', '713', '787', '0.0022', '83', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('696', '1', '713', '787', '0.0022', '84', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('697', '1', '713', '787', '0.0022', '85', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('698', '1', '713', '787', '0.0022', '86', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('699', '1', '713', '787', '0.0022', '87', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('700', '1', '713', '787', '0.0022', '88', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('701', '1', '713', '787', '0.0022', '89', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('702', '1', '713', '787', '0.0022', '90', '90', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('703', '1', '788', '862', '0.1776', '1', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('704', '1', '788', '862', '0.1245', '2', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('705', '1', '788', '862', '0.0804', '3', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('706', '1', '788', '862', '0.0595', '4', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('707', '1', '788', '862', '0.0445', '5', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('708', '1', '788', '862', '0.036', '6', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('709', '1', '788', '862', '0.0298', '7', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('710', '1', '788', '862', '0.0237', '8', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('711', '1', '788', '862', '0.0178', '9', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('712', '1', '788', '862', '0.0131', '10', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('713', '1', '788', '862', '0.0131', '11', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('714', '1', '788', '862', '0.0131', '12', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('715', '1', '788', '862', '0.0106', '13', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('716', '1', '788', '862', '0.0106', '14', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('717', '1', '788', '862', '0.0106', '15', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('718', '1', '788', '862', '0.0091', '16', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('719', '1', '788', '862', '0.0091', '17', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('720', '1', '788', '862', '0.0091', '18', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('721', '1', '788', '862', '0.0081', '19', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('722', '1', '788', '862', '0.0081', '20', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('723', '1', '788', '862', '0.0081', '21', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('724', '1', '788', '862', '0.0072', '22', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('725', '1', '788', '862', '0.0072', '23', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('726', '1', '788', '862', '0.0072', '24', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('727', '1', '788', '862', '0.0063', '25', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('728', '1', '788', '862', '0.0063', '26', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('729', '1', '788', '862', '0.0063', '27', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('730', '1', '788', '862', '0.0055', '28', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('731', '1', '788', '862', '0.0055', '29', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('732', '1', '788', '862', '0.0055', '30', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('733', '1', '788', '862', '0.0055', '31', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('734', '1', '788', '862', '0.0055', '32', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('735', '1', '788', '862', '0.0055', '33', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('736', '1', '788', '862', '0.0055', '34', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('737', '1', '788', '862', '0.0055', '35', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('738', '1', '788', '862', '0.0055', '36', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('739', '1', '788', '862', '0.0047', '37', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('740', '1', '788', '862', '0.0047', '38', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('741', '1', '788', '862', '0.0047', '39', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('742', '1', '788', '862', '0.0047', '40', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('743', '1', '788', '862', '0.0047', '41', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('744', '1', '788', '862', '0.0047', '42', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('745', '1', '788', '862', '0.0047', '43', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('746', '1', '788', '862', '0.0047', '44', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('747', '1', '788', '862', '0.0047', '45', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('748', '1', '788', '862', '0.004', '46', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('749', '1', '788', '862', '0.004', '47', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('750', '1', '788', '862', '0.004', '48', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('751', '1', '788', '862', '0.004', '49', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('752', '1', '788', '862', '0.004', '50', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('753', '1', '788', '862', '0.004', '51', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('754', '1', '788', '862', '0.004', '52', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('755', '1', '788', '862', '0.004', '53', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('756', '1', '788', '862', '0.004', '54', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('757', '1', '788', '862', '0.0033', '55', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('758', '1', '788', '862', '0.0033', '56', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('759', '1', '788', '862', '0.0033', '57', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('760', '1', '788', '862', '0.0033', '58', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('761', '1', '788', '862', '0.0033', '59', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('762', '1', '788', '862', '0.0033', '60', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('763', '1', '788', '862', '0.0033', '61', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('764', '1', '788', '862', '0.0033', '62', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('765', '1', '788', '862', '0.0033', '63', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('766', '1', '788', '862', '0.0029', '64', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('767', '1', '788', '862', '0.0029', '65', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('768', '1', '788', '862', '0.0029', '66', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('769', '1', '788', '862', '0.0029', '67', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('770', '1', '788', '862', '0.0029', '68', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('771', '1', '788', '862', '0.0029', '69', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('772', '1', '788', '862', '0.0029', '70', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('773', '1', '788', '862', '0.0029', '71', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('774', '1', '788', '862', '0.0029', '72', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('775', '1', '788', '862', '0.0025', '73', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('776', '1', '788', '862', '0.0025', '74', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('777', '1', '788', '862', '0.0025', '75', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('778', '1', '788', '862', '0.0025', '76', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('779', '1', '788', '862', '0.0025', '77', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('780', '1', '788', '862', '0.0025', '78', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('781', '1', '788', '862', '0.0025', '79', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('782', '1', '788', '862', '0.0025', '80', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('783', '1', '788', '862', '0.0025', '81', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('784', '1', '788', '862', '0.0022', '82', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('785', '1', '788', '862', '0.0022', '83', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('786', '1', '788', '862', '0.0022', '84', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('787', '1', '788', '862', '0.0022', '85', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('788', '1', '788', '862', '0.0022', '86', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('789', '1', '788', '862', '0.0022', '87', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('790', '1', '788', '862', '0.0022', '88', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('791', '1', '788', '862', '0.0022', '89', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('792', '1', '788', '862', '0.0022', '90', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('793', '1', '788', '862', '0.0019', '91', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('794', '1', '788', '862', '0.0019', '92', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('795', '1', '788', '862', '0.0019', '93', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('796', '1', '788', '862', '0.0019', '94', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('797', '1', '788', '862', '0.0019', '95', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('798', '1', '788', '862', '0.0019', '96', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('799', '1', '788', '862', '0.0019', '97', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('800', '1', '788', '862', '0.0019', '98', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('801', '1', '788', '862', '0.0019', '99', '99', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('802', '1', '863', '937', '0.175', '1', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('803', '1', '863', '937', '0.1226', '2', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('804', '1', '863', '937', '0.0793', '3', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('805', '1', '863', '937', '0.0586', '4', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('806', '1', '863', '937', '0.0438', '5', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('807', '1', '863', '937', '0.0354', '6', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('808', '1', '863', '937', '0.0293', '7', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('809', '1', '863', '937', '0.0233', '8', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('810', '1', '863', '937', '0.0175', '9', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('811', '1', '863', '937', '0.013', '10', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('812', '1', '863', '937', '0.013', '11', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('813', '1', '863', '937', '0.013', '12', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('814', '1', '863', '937', '0.0105', '13', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('815', '1', '863', '937', '0.0105', '14', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('816', '1', '863', '937', '0.0105', '15', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('817', '1', '863', '937', '0.009', '16', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('818', '1', '863', '937', '0.009', '17', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('819', '1', '863', '937', '0.009', '18', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('820', '1', '863', '937', '0.008', '19', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('821', '1', '863', '937', '0.008', '20', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('822', '1', '863', '937', '0.008', '21', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('823', '1', '863', '937', '0.0071', '22', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('824', '1', '863', '937', '0.0071', '23', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('825', '1', '863', '937', '0.0071', '24', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('826', '1', '863', '937', '0.0062', '25', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('827', '1', '863', '937', '0.0062', '26', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('828', '1', '863', '937', '0.0062', '27', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('829', '1', '863', '937', '0.0054', '28', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('830', '1', '863', '937', '0.0054', '29', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('831', '1', '863', '937', '0.0054', '30', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('832', '1', '863', '937', '0.0054', '31', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('833', '1', '863', '937', '0.0054', '32', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('834', '1', '863', '937', '0.0054', '33', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('835', '1', '863', '937', '0.0054', '34', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('836', '1', '863', '937', '0.0054', '35', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('837', '1', '863', '937', '0.0054', '36', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('838', '1', '863', '937', '0.0046', '37', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('839', '1', '863', '937', '0.0046', '38', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('840', '1', '863', '937', '0.0046', '39', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('841', '1', '863', '937', '0.0046', '40', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('842', '1', '863', '937', '0.0046', '41', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('843', '1', '863', '937', '0.0046', '42', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('844', '1', '863', '937', '0.0046', '43', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('845', '1', '863', '937', '0.0046', '44', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('846', '1', '863', '937', '0.0046', '45', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('847', '1', '863', '937', '0.0039', '46', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('848', '1', '863', '937', '0.0039', '47', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('849', '1', '863', '937', '0.0039', '48', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('850', '1', '863', '937', '0.0039', '49', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('851', '1', '863', '937', '0.0039', '50', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('852', '1', '863', '937', '0.0039', '51', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('853', '1', '863', '937', '0.0039', '52', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('854', '1', '863', '937', '0.0039', '53', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('855', '1', '863', '937', '0.0039', '54', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('856', '1', '863', '937', '0.0032', '55', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('857', '1', '863', '937', '0.0032', '56', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('858', '1', '863', '937', '0.0032', '57', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('859', '1', '863', '937', '0.0032', '58', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('860', '1', '863', '937', '0.0032', '59', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('861', '1', '863', '937', '0.0032', '60', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('862', '1', '863', '937', '0.0032', '61', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('863', '1', '863', '937', '0.0032', '62', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('864', '1', '863', '937', '0.0032', '63', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('865', '1', '863', '937', '0.0028', '64', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('866', '1', '863', '937', '0.0028', '65', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('867', '1', '863', '937', '0.0028', '66', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('868', '1', '863', '937', '0.0028', '67', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('869', '1', '863', '937', '0.0028', '68', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('870', '1', '863', '937', '0.0028', '69', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('871', '1', '863', '937', '0.0028', '70', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('872', '1', '863', '937', '0.0028', '71', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('873', '1', '863', '937', '0.0028', '72', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('874', '1', '863', '937', '0.0025', '73', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('875', '1', '863', '937', '0.0025', '74', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('876', '1', '863', '937', '0.0025', '75', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('877', '1', '863', '937', '0.0025', '76', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('878', '1', '863', '937', '0.0025', '77', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('879', '1', '863', '937', '0.0025', '78', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('880', '1', '863', '937', '0.0025', '79', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('881', '1', '863', '937', '0.0025', '80', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('882', '1', '863', '937', '0.0025', '81', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('883', '1', '863', '937', '0.0022', '82', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('884', '1', '863', '937', '0.0022', '83', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('885', '1', '863', '937', '0.0022', '84', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('886', '1', '863', '937', '0.0022', '85', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('887', '1', '863', '937', '0.0022', '86', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('888', '1', '863', '937', '0.0022', '87', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('889', '1', '863', '937', '0.0022', '88', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('890', '1', '863', '937', '0.0022', '89', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('891', '1', '863', '937', '0.0022', '90', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('892', '1', '863', '937', '0.0019', '91', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('893', '1', '863', '937', '0.0019', '92', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('894', '1', '863', '937', '0.0019', '93', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('895', '1', '863', '937', '0.0019', '94', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('896', '1', '863', '937', '0.0019', '95', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('897', '1', '863', '937', '0.0019', '96', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('898', '1', '863', '937', '0.0019', '97', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('899', '1', '863', '937', '0.0019', '98', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('900', '1', '863', '937', '0.0019', '99', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('901', '1', '863', '937', '0.0017', '100', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('902', '1', '863', '937', '0.0017', '101', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('903', '1', '863', '937', '0.0017', '102', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('904', '1', '863', '937', '0.0017', '103', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('905', '1', '863', '937', '0.0017', '104', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('906', '1', '863', '937', '0.0017', '105', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('907', '1', '863', '937', '0.0017', '106', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('908', '1', '863', '937', '0.0017', '107', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('909', '1', '863', '937', '0.0017', '108', '108', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('910', '1', '938', '1012', '0.1721', '1', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('911', '1', '938', '1012', '0.1206', '2', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('912', '1', '938', '1012', '0.0782', '3', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('913', '1', '938', '1012', '0.0578', '4', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('914', '1', '938', '1012', '0.0431', '5', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('915', '1', '938', '1012', '0.0349', '6', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('916', '1', '938', '1012', '0.0289', '7', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('917', '1', '938', '1012', '0.023', '8', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('918', '1', '938', '1012', '0.0172', '9', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('919', '1', '938', '1012', '0.0129', '10', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('920', '1', '938', '1012', '0.0129', '11', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('921', '1', '938', '1012', '0.0129', '12', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('922', '1', '938', '1012', '0.0104', '13', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('923', '1', '938', '1012', '0.0104', '14', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('924', '1', '938', '1012', '0.0104', '15', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('925', '1', '938', '1012', '0.0089', '16', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('926', '1', '938', '1012', '0.0089', '17', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('927', '1', '938', '1012', '0.0089', '18', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('928', '1', '938', '1012', '0.0079', '19', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('929', '1', '938', '1012', '0.0079', '20', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('930', '1', '938', '1012', '0.0079', '21', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('931', '1', '938', '1012', '0.007', '22', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('932', '1', '938', '1012', '0.007', '23', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('933', '1', '938', '1012', '0.007', '24', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('934', '1', '938', '1012', '0.0061', '25', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('935', '1', '938', '1012', '0.0061', '26', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('936', '1', '938', '1012', '0.0061', '27', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('937', '1', '938', '1012', '0.0053', '28', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('938', '1', '938', '1012', '0.0053', '29', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('939', '1', '938', '1012', '0.0053', '30', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('940', '1', '938', '1012', '0.0053', '31', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('941', '1', '938', '1012', '0.0053', '32', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('942', '1', '938', '1012', '0.0053', '33', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('943', '1', '938', '1012', '0.0053', '34', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('944', '1', '938', '1012', '0.0053', '35', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('945', '1', '938', '1012', '0.0053', '36', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('946', '1', '938', '1012', '0.0045', '37', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('947', '1', '938', '1012', '0.0045', '38', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('948', '1', '938', '1012', '0.0045', '39', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('949', '1', '938', '1012', '0.0045', '40', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('950', '1', '938', '1012', '0.0045', '41', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('951', '1', '938', '1012', '0.0045', '42', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('952', '1', '938', '1012', '0.0045', '43', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('953', '1', '938', '1012', '0.0045', '44', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('954', '1', '938', '1012', '0.0045', '45', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('955', '1', '938', '1012', '0.0038', '46', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('956', '1', '938', '1012', '0.0038', '47', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('957', '1', '938', '1012', '0.0038', '48', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('958', '1', '938', '1012', '0.0038', '49', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('959', '1', '938', '1012', '0.0038', '50', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('960', '1', '938', '1012', '0.0038', '51', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('961', '1', '938', '1012', '0.0038', '52', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('962', '1', '938', '1012', '0.0038', '53', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('963', '1', '938', '1012', '0.0038', '54', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('964', '1', '938', '1012', '0.0032', '55', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('965', '1', '938', '1012', '0.0032', '56', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('966', '1', '938', '1012', '0.0032', '57', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('967', '1', '938', '1012', '0.0032', '58', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('968', '1', '938', '1012', '0.0032', '59', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('969', '1', '938', '1012', '0.0032', '60', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('970', '1', '938', '1012', '0.0032', '61', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('971', '1', '938', '1012', '0.0032', '62', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('972', '1', '938', '1012', '0.0032', '63', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('973', '1', '938', '1012', '0.0028', '64', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('974', '1', '938', '1012', '0.0028', '65', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('975', '1', '938', '1012', '0.0028', '66', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('976', '1', '938', '1012', '0.0028', '67', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('977', '1', '938', '1012', '0.0028', '68', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('978', '1', '938', '1012', '0.0028', '69', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('979', '1', '938', '1012', '0.0028', '70', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('980', '1', '938', '1012', '0.0028', '71', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('981', '1', '938', '1012', '0.0028', '72', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('982', '1', '938', '1012', '0.0025', '73', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('983', '1', '938', '1012', '0.0025', '74', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('984', '1', '938', '1012', '0.0025', '75', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('985', '1', '938', '1012', '0.0025', '76', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('986', '1', '938', '1012', '0.0025', '77', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('987', '1', '938', '1012', '0.0025', '78', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('988', '1', '938', '1012', '0.0025', '79', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('989', '1', '938', '1012', '0.0025', '80', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('990', '1', '938', '1012', '0.0025', '81', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('991', '1', '938', '1012', '0.0022', '82', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('992', '1', '938', '1012', '0.0022', '83', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('993', '1', '938', '1012', '0.0022', '84', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('994', '1', '938', '1012', '0.0022', '85', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('995', '1', '938', '1012', '0.0022', '86', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('996', '1', '938', '1012', '0.0022', '87', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('997', '1', '938', '1012', '0.0022', '88', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('998', '1', '938', '1012', '0.0022', '89', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('999', '1', '938', '1012', '0.0022', '90', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1000', '1', '938', '1012', '0.0019', '91', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1001', '1', '938', '1012', '0.0019', '92', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1002', '1', '938', '1012', '0.0019', '93', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1003', '1', '938', '1012', '0.0019', '94', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1004', '1', '938', '1012', '0.0019', '95', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1005', '1', '938', '1012', '0.0019', '96', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1006', '1', '938', '1012', '0.0019', '97', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1007', '1', '938', '1012', '0.0019', '98', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1008', '1', '938', '1012', '0.0019', '99', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1009', '1', '938', '1012', '0.0016', '100', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1010', '1', '938', '1012', '0.0016', '101', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1011', '1', '938', '1012', '0.0016', '102', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1012', '1', '938', '1012', '0.0016', '103', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1013', '1', '938', '1012', '0.0016', '104', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1014', '1', '938', '1012', '0.0016', '105', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1015', '1', '938', '1012', '0.0016', '106', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1016', '1', '938', '1012', '0.0016', '107', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1017', '1', '938', '1012', '0.0016', '108', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1018', '1', '938', '1012', '0.0016', '109', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1019', '1', '938', '1012', '0.0016', '110', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1020', '1', '938', '1012', '0.0016', '111', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1021', '1', '938', '1012', '0.0016', '112', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1022', '1', '938', '1012', '0.0016', '113', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1023', '1', '938', '1012', '0.0016', '114', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1024', '1', '938', '1012', '0.0016', '115', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1025', '1', '938', '1012', '0.0016', '116', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1026', '1', '938', '1012', '0.0016', '117', '117', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1027', '1', '1013', '1087', '0.1701', '1', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1028', '1', '1013', '1087', '0.1192', '2', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1029', '1', '1013', '1087', '0.0773', '3', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1030', '1', '1013', '1087', '0.0571', '4', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1031', '1', '1013', '1087', '0.0426', '5', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1032', '1', '1013', '1087', '0.0345', '6', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1033', '1', '1013', '1087', '0.0286', '7', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1034', '1', '1013', '1087', '0.0228', '8', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1035', '1', '1013', '1087', '0.017', '9', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1036', '1', '1013', '1087', '0.0127', '10', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1037', '1', '1013', '1087', '0.0127', '11', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1038', '1', '1013', '1087', '0.0127', '12', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1039', '1', '1013', '1087', '0.0102', '13', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1040', '1', '1013', '1087', '0.0102', '14', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1041', '1', '1013', '1087', '0.0102', '15', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1042', '1', '1013', '1087', '0.0088', '16', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1043', '1', '1013', '1087', '0.0088', '17', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1044', '1', '1013', '1087', '0.0088', '18', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1045', '1', '1013', '1087', '0.0079', '19', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1046', '1', '1013', '1087', '0.0079', '20', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1047', '1', '1013', '1087', '0.0079', '21', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1048', '1', '1013', '1087', '0.007', '22', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1049', '1', '1013', '1087', '0.007', '23', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1050', '1', '1013', '1087', '0.007', '24', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1051', '1', '1013', '1087', '0.0061', '25', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1052', '1', '1013', '1087', '0.0061', '26', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1053', '1', '1013', '1087', '0.0061', '27', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1054', '1', '1013', '1087', '0.0052', '28', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1055', '1', '1013', '1087', '0.0052', '29', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1056', '1', '1013', '1087', '0.0052', '30', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1057', '1', '1013', '1087', '0.0052', '31', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1058', '1', '1013', '1087', '0.0052', '32', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1059', '1', '1013', '1087', '0.0052', '33', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1060', '1', '1013', '1087', '0.0052', '34', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1061', '1', '1013', '1087', '0.0052', '35', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1062', '1', '1013', '1087', '0.0052', '36', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1063', '1', '1013', '1087', '0.0045', '37', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1064', '1', '1013', '1087', '0.0045', '38', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1065', '1', '1013', '1087', '0.0045', '39', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1066', '1', '1013', '1087', '0.0045', '40', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1067', '1', '1013', '1087', '0.0045', '41', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1068', '1', '1013', '1087', '0.0045', '42', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1069', '1', '1013', '1087', '0.0045', '43', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1070', '1', '1013', '1087', '0.0045', '44', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1071', '1', '1013', '1087', '0.0045', '45', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1072', '1', '1013', '1087', '0.0038', '46', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1073', '1', '1013', '1087', '0.0038', '47', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1074', '1', '1013', '1087', '0.0038', '48', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1075', '1', '1013', '1087', '0.0038', '49', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1076', '1', '1013', '1087', '0.0038', '50', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1077', '1', '1013', '1087', '0.0038', '51', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1078', '1', '1013', '1087', '0.0038', '52', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1079', '1', '1013', '1087', '0.0038', '53', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1080', '1', '1013', '1087', '0.0038', '54', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1081', '1', '1013', '1087', '0.0032', '55', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1082', '1', '1013', '1087', '0.0032', '56', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1083', '1', '1013', '1087', '0.0032', '57', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1084', '1', '1013', '1087', '0.0032', '58', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1085', '1', '1013', '1087', '0.0032', '59', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1086', '1', '1013', '1087', '0.0032', '60', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1087', '1', '1013', '1087', '0.0032', '61', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1088', '1', '1013', '1087', '0.0032', '62', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1089', '1', '1013', '1087', '0.0032', '63', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1090', '1', '1013', '1087', '0.0028', '64', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1091', '1', '1013', '1087', '0.0028', '65', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1092', '1', '1013', '1087', '0.0028', '66', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1093', '1', '1013', '1087', '0.0028', '67', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1094', '1', '1013', '1087', '0.0028', '68', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1095', '1', '1013', '1087', '0.0028', '69', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1096', '1', '1013', '1087', '0.0028', '70', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1097', '1', '1013', '1087', '0.0028', '71', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1098', '1', '1013', '1087', '0.0028', '72', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1099', '1', '1013', '1087', '0.0024', '73', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1100', '1', '1013', '1087', '0.0024', '74', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1101', '1', '1013', '1087', '0.0024', '75', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1102', '1', '1013', '1087', '0.0024', '76', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1103', '1', '1013', '1087', '0.0024', '77', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1104', '1', '1013', '1087', '0.0024', '78', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1105', '1', '1013', '1087', '0.0024', '79', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1106', '1', '1013', '1087', '0.0024', '80', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1107', '1', '1013', '1087', '0.0024', '81', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1108', '1', '1013', '1087', '0.0021', '82', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1109', '1', '1013', '1087', '0.0021', '83', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1110', '1', '1013', '1087', '0.0021', '84', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1111', '1', '1013', '1087', '0.0021', '85', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1112', '1', '1013', '1087', '0.0021', '86', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1113', '1', '1013', '1087', '0.0021', '87', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1114', '1', '1013', '1087', '0.0021', '88', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1115', '1', '1013', '1087', '0.0021', '89', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1116', '1', '1013', '1087', '0.0021', '90', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1117', '1', '1013', '1087', '0.0018', '91', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1118', '1', '1013', '1087', '0.0018', '92', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1119', '1', '1013', '1087', '0.0018', '93', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1120', '1', '1013', '1087', '0.0018', '94', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1121', '1', '1013', '1087', '0.0018', '95', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1122', '1', '1013', '1087', '0.0018', '96', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1123', '1', '1013', '1087', '0.0018', '97', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1124', '1', '1013', '1087', '0.0018', '98', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1125', '1', '1013', '1087', '0.0018', '99', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1126', '1', '1013', '1087', '0.0015', '100', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1127', '1', '1013', '1087', '0.0015', '101', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1128', '1', '1013', '1087', '0.0015', '102', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1129', '1', '1013', '1087', '0.0015', '103', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1130', '1', '1013', '1087', '0.0015', '104', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1131', '1', '1013', '1087', '0.0015', '105', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1132', '1', '1013', '1087', '0.0015', '106', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1133', '1', '1013', '1087', '0.0015', '107', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1134', '1', '1013', '1087', '0.0015', '108', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1135', '1', '1013', '1087', '0.0015', '109', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1136', '1', '1013', '1087', '0.0015', '110', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1137', '1', '1013', '1087', '0.0015', '111', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1138', '1', '1013', '1087', '0.0015', '112', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1139', '1', '1013', '1087', '0.0015', '113', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1140', '1', '1013', '1087', '0.0015', '114', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1141', '1', '1013', '1087', '0.0015', '115', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1142', '1', '1013', '1087', '0.0015', '116', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1143', '1', '1013', '1087', '0.0015', '117', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1144', '1', '1013', '1087', '0.0015', '118', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1145', '1', '1013', '1087', '0.0015', '119', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1146', '1', '1013', '1087', '0.0015', '120', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1147', '1', '1013', '1087', '0.0015', '121', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1148', '1', '1013', '1087', '0.0015', '122', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1149', '1', '1013', '1087', '0.0015', '123', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1150', '1', '1013', '1087', '0.0015', '124', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1151', '1', '1013', '1087', '0.0015', '125', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1152', '1', '1013', '1087', '0.0015', '126', '126', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1153', '1', '1088', '1162', '0.1682', '1', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1154', '1', '1088', '1162', '0.1179', '2', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1155', '1', '1088', '1162', '0.0765', '3', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1156', '1', '1088', '1162', '0.0567', '4', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1157', '1', '1088', '1162', '0.0423', '5', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1158', '1', '1088', '1162', '0.0342', '6', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1159', '1', '1088', '1162', '0.0284', '7', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1160', '1', '1088', '1162', '0.0227', '8', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1161', '1', '1088', '1162', '0.0169', '9', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1162', '1', '1088', '1162', '0.0127', '10', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1163', '1', '1088', '1162', '0.0127', '11', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1164', '1', '1088', '1162', '0.0127', '12', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1165', '1', '1088', '1162', '0.0102', '13', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1166', '1', '1088', '1162', '0.0102', '14', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1167', '1', '1088', '1162', '0.0102', '15', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1168', '1', '1088', '1162', '0.0088', '16', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1169', '1', '1088', '1162', '0.0088', '17', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1170', '1', '1088', '1162', '0.0088', '18', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1171', '1', '1088', '1162', '0.0078', '19', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1172', '1', '1088', '1162', '0.0078', '20', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1173', '1', '1088', '1162', '0.0078', '21', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1174', '1', '1088', '1162', '0.0069', '22', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1175', '1', '1088', '1162', '0.0069', '23', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1176', '1', '1088', '1162', '0.0069', '24', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1177', '1', '1088', '1162', '0.006', '25', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1178', '1', '1088', '1162', '0.006', '26', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1179', '1', '1088', '1162', '0.006', '27', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1180', '1', '1088', '1162', '0.0052', '28', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1181', '1', '1088', '1162', '0.0052', '29', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1182', '1', '1088', '1162', '0.0052', '30', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1183', '1', '1088', '1162', '0.0052', '31', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1184', '1', '1088', '1162', '0.0052', '32', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1185', '1', '1088', '1162', '0.0052', '33', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1186', '1', '1088', '1162', '0.0052', '34', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1187', '1', '1088', '1162', '0.0052', '35', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1188', '1', '1088', '1162', '0.0052', '36', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1189', '1', '1088', '1162', '0.0045', '37', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1190', '1', '1088', '1162', '0.0045', '38', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1191', '1', '1088', '1162', '0.0045', '39', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1192', '1', '1088', '1162', '0.0045', '40', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1193', '1', '1088', '1162', '0.0045', '41', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1194', '1', '1088', '1162', '0.0045', '42', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1195', '1', '1088', '1162', '0.0045', '43', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1196', '1', '1088', '1162', '0.0045', '44', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1197', '1', '1088', '1162', '0.0045', '45', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1198', '1', '1088', '1162', '0.0038', '46', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1199', '1', '1088', '1162', '0.0038', '47', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1200', '1', '1088', '1162', '0.0038', '48', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1201', '1', '1088', '1162', '0.0038', '49', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1202', '1', '1088', '1162', '0.0038', '50', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1203', '1', '1088', '1162', '0.0038', '51', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1204', '1', '1088', '1162', '0.0038', '52', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1205', '1', '1088', '1162', '0.0038', '53', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1206', '1', '1088', '1162', '0.0038', '54', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1207', '1', '1088', '1162', '0.0032', '55', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1208', '1', '1088', '1162', '0.0032', '56', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1209', '1', '1088', '1162', '0.0032', '57', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1210', '1', '1088', '1162', '0.0032', '58', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1211', '1', '1088', '1162', '0.0032', '59', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1212', '1', '1088', '1162', '0.0032', '60', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1213', '1', '1088', '1162', '0.0032', '61', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1214', '1', '1088', '1162', '0.0032', '62', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1215', '1', '1088', '1162', '0.0032', '63', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1216', '1', '1088', '1162', '0.0027', '64', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1217', '1', '1088', '1162', '0.0027', '65', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1218', '1', '1088', '1162', '0.0027', '66', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1219', '1', '1088', '1162', '0.0027', '67', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1220', '1', '1088', '1162', '0.0027', '68', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1221', '1', '1088', '1162', '0.0027', '69', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1222', '1', '1088', '1162', '0.0027', '70', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1223', '1', '1088', '1162', '0.0027', '71', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1224', '1', '1088', '1162', '0.0027', '72', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1225', '1', '1088', '1162', '0.0023', '73', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1226', '1', '1088', '1162', '0.0023', '74', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1227', '1', '1088', '1162', '0.0023', '75', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1228', '1', '1088', '1162', '0.0023', '76', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1229', '1', '1088', '1162', '0.0023', '77', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1230', '1', '1088', '1162', '0.0023', '78', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1231', '1', '1088', '1162', '0.0023', '79', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1232', '1', '1088', '1162', '0.0023', '80', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1233', '1', '1088', '1162', '0.0023', '81', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1234', '1', '1088', '1162', '0.002', '82', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1235', '1', '1088', '1162', '0.002', '83', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1236', '1', '1088', '1162', '0.002', '84', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1237', '1', '1088', '1162', '0.002', '85', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1238', '1', '1088', '1162', '0.002', '86', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1239', '1', '1088', '1162', '0.002', '87', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1240', '1', '1088', '1162', '0.002', '88', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1241', '1', '1088', '1162', '0.002', '89', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1242', '1', '1088', '1162', '0.002', '90', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1243', '1', '1088', '1162', '0.0017', '91', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1244', '1', '1088', '1162', '0.0017', '92', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1245', '1', '1088', '1162', '0.0017', '93', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1246', '1', '1088', '1162', '0.0017', '94', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1247', '1', '1088', '1162', '0.0017', '95', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1248', '1', '1088', '1162', '0.0017', '96', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1249', '1', '1088', '1162', '0.0017', '97', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1250', '1', '1088', '1162', '0.0017', '98', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1251', '1', '1088', '1162', '0.0017', '99', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1252', '1', '1088', '1162', '0.0014', '100', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1253', '1', '1088', '1162', '0.0014', '101', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1254', '1', '1088', '1162', '0.0014', '102', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1255', '1', '1088', '1162', '0.0014', '103', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1256', '1', '1088', '1162', '0.0014', '104', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1257', '1', '1088', '1162', '0.0014', '105', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1258', '1', '1088', '1162', '0.0014', '106', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1259', '1', '1088', '1162', '0.0014', '107', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1260', '1', '1088', '1162', '0.0014', '108', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1261', '1', '1088', '1162', '0.0014', '109', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1262', '1', '1088', '1162', '0.0014', '110', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1263', '1', '1088', '1162', '0.0014', '111', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1264', '1', '1088', '1162', '0.0014', '112', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1265', '1', '1088', '1162', '0.0014', '113', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1266', '1', '1088', '1162', '0.0014', '114', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1267', '1', '1088', '1162', '0.0014', '115', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1268', '1', '1088', '1162', '0.0014', '116', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1269', '1', '1088', '1162', '0.0014', '135', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1270', '1', '1088', '1162', '0.0014', '118', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1271', '1', '1088', '1162', '0.0014', '119', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1272', '1', '1088', '1162', '0.0014', '120', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1273', '1', '1088', '1162', '0.0014', '121', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1274', '1', '1088', '1162', '0.0014', '122', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1275', '1', '1088', '1162', '0.0014', '123', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1276', '1', '1088', '1162', '0.0014', '124', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1277', '1', '1088', '1162', '0.0014', '125', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1278', '1', '1088', '1162', '0.0014', '126', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1279', '1', '1088', '1162', '0.0014', '127', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1280', '1', '1088', '1162', '0.0014', '128', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1281', '1', '1088', '1162', '0.0014', '129', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1282', '1', '1088', '1162', '0.0014', '130', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1283', '1', '1088', '1162', '0.0014', '131', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1284', '1', '1088', '1162', '0.0014', '132', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1285', '1', '1088', '1162', '0.0014', '133', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1286', '1', '1088', '1162', '0.0014', '134', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1287', '1', '1088', '1162', '0.0014', '135', '135', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1288', '1', '1163', '1237', '0.168', '1', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1289', '1', '1163', '1237', '0.1178', '2', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1290', '1', '1163', '1237', '0.0764', '3', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1291', '1', '1163', '1237', '0.0566', '4', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1292', '1', '1163', '1237', '0.0422', '5', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1293', '1', '1163', '1237', '0.0341', '6', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1294', '1', '1163', '1237', '0.0284', '7', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1295', '1', '1163', '1237', '0.0226', '8', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1296', '1', '1163', '1237', '0.0168', '9', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1297', '1', '1163', '1237', '0.0127', '10', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1298', '1', '1163', '1237', '0.0127', '11', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1299', '1', '1163', '1237', '0.0127', '12', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1300', '1', '1163', '1237', '0.0102', '13', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1301', '1', '1163', '1237', '0.0102', '14', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1302', '1', '1163', '1237', '0.0102', '15', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1303', '1', '1163', '1237', '0.0088', '16', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1304', '1', '1163', '1237', '0.0088', '17', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1305', '1', '1163', '1237', '0.0088', '18', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1306', '1', '1163', '1237', '0.0078', '19', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1307', '1', '1163', '1237', '0.0078', '20', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1308', '1', '1163', '1237', '0.0078', '21', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1309', '1', '1163', '1237', '0.0069', '22', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1310', '1', '1163', '1237', '0.0069', '23', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1311', '1', '1163', '1237', '0.0069', '24', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1312', '1', '1163', '1237', '0.006', '25', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1313', '1', '1163', '1237', '0.006', '26', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1314', '1', '1163', '1237', '0.006', '27', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1315', '1', '1163', '1237', '0.0051', '28', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1316', '1', '1163', '1237', '0.0051', '29', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1317', '1', '1163', '1237', '0.0051', '30', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1318', '1', '1163', '1237', '0.0051', '31', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1319', '1', '1163', '1237', '0.0051', '32', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1320', '1', '1163', '1237', '0.0051', '33', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1321', '1', '1163', '1237', '0.0051', '34', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1322', '1', '1163', '1237', '0.0051', '35', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1323', '1', '1163', '1237', '0.0051', '36', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1324', '1', '1163', '1237', '0.0044', '37', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1325', '1', '1163', '1237', '0.0044', '38', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1326', '1', '1163', '1237', '0.0044', '39', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1327', '1', '1163', '1237', '0.0044', '40', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1328', '1', '1163', '1237', '0.0044', '41', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1329', '1', '1163', '1237', '0.0044', '42', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1330', '1', '1163', '1237', '0.0044', '43', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1331', '1', '1163', '1237', '0.0044', '44', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1332', '1', '1163', '1237', '0.0044', '45', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1333', '1', '1163', '1237', '0.0037', '46', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1334', '1', '1163', '1237', '0.0037', '47', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1335', '1', '1163', '1237', '0.0037', '48', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1336', '1', '1163', '1237', '0.0037', '49', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1337', '1', '1163', '1237', '0.0037', '50', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1338', '1', '1163', '1237', '0.0037', '51', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1339', '1', '1163', '1237', '0.0037', '52', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1340', '1', '1163', '1237', '0.0037', '53', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1341', '1', '1163', '1237', '0.0037', '54', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1342', '1', '1163', '1237', '0.0031', '55', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1343', '1', '1163', '1237', '0.0031', '56', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1344', '1', '1163', '1237', '0.0031', '57', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1345', '1', '1163', '1237', '0.0031', '58', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1346', '1', '1163', '1237', '0.0031', '59', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1347', '1', '1163', '1237', '0.0031', '60', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1348', '1', '1163', '1237', '0.0031', '61', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1349', '1', '1163', '1237', '0.0031', '62', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1350', '1', '1163', '1237', '0.0031', '63', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1351', '1', '1163', '1237', '0.0026', '64', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1352', '1', '1163', '1237', '0.0026', '65', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1353', '1', '1163', '1237', '0.0026', '66', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1354', '1', '1163', '1237', '0.0026', '67', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1355', '1', '1163', '1237', '0.0026', '68', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1356', '1', '1163', '1237', '0.0026', '69', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1357', '1', '1163', '1237', '0.0026', '70', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1358', '1', '1163', '1237', '0.0026', '71', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1359', '1', '1163', '1237', '0.0026', '72', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1360', '1', '1163', '1237', '0.0022', '73', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1361', '1', '1163', '1237', '0.0022', '74', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1362', '1', '1163', '1237', '0.0022', '75', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1363', '1', '1163', '1237', '0.0022', '76', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1364', '1', '1163', '1237', '0.0022', '77', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1365', '1', '1163', '1237', '0.0022', '78', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1366', '1', '1163', '1237', '0.0022', '79', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1367', '1', '1163', '1237', '0.0022', '80', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1368', '1', '1163', '1237', '0.0022', '81', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1369', '1', '1163', '1237', '0.002', '82', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1370', '1', '1163', '1237', '0.002', '83', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1371', '1', '1163', '1237', '0.002', '84', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1372', '1', '1163', '1237', '0.002', '85', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1373', '1', '1163', '1237', '0.002', '86', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1374', '1', '1163', '1237', '0.002', '87', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1375', '1', '1163', '1237', '0.002', '88', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1376', '1', '1163', '1237', '0.002', '89', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1377', '1', '1163', '1237', '0.002', '90', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1378', '1', '1163', '1237', '0.0016', '91', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1379', '1', '1163', '1237', '0.0016', '92', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1380', '1', '1163', '1237', '0.0016', '93', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1381', '1', '1163', '1237', '0.0016', '94', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1382', '1', '1163', '1237', '0.0016', '95', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1383', '1', '1163', '1237', '0.0016', '96', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1384', '1', '1163', '1237', '0.0016', '97', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1385', '1', '1163', '1237', '0.0016', '98', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1386', '1', '1163', '1237', '0.0016', '99', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1387', '1', '1163', '1237', '0.0013', '100', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1388', '1', '1163', '1237', '0.0013', '101', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1389', '1', '1163', '1237', '0.0013', '102', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1390', '1', '1163', '1237', '0.0013', '103', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1391', '1', '1163', '1237', '0.0013', '104', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1392', '1', '1163', '1237', '0.0013', '105', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1393', '1', '1163', '1237', '0.0013', '106', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1394', '1', '1163', '1237', '0.0013', '107', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1395', '1', '1163', '1237', '0.0013', '108', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1396', '1', '1163', '1237', '0.0013', '109', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1397', '1', '1163', '1237', '0.0013', '110', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1398', '1', '1163', '1237', '0.0013', '111', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1399', '1', '1163', '1237', '0.0013', '112', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1400', '1', '1163', '1237', '0.0013', '113', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1401', '1', '1163', '1237', '0.0013', '114', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1402', '1', '1163', '1237', '0.0013', '115', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1403', '1', '1163', '1237', '0.0013', '116', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1404', '1', '1163', '1237', '0.0013', '144', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1405', '1', '1163', '1237', '0.0013', '118', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1406', '1', '1163', '1237', '0.0013', '119', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1407', '1', '1163', '1237', '0.0013', '120', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1408', '1', '1163', '1237', '0.0013', '121', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1409', '1', '1163', '1237', '0.0013', '122', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1410', '1', '1163', '1237', '0.0013', '123', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1411', '1', '1163', '1237', '0.0013', '124', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1412', '1', '1163', '1237', '0.0013', '125', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1413', '1', '1163', '1237', '0.0013', '126', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1414', '1', '1163', '1237', '0.0013', '127', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1415', '1', '1163', '1237', '0.0013', '128', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1416', '1', '1163', '1237', '0.0013', '129', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1417', '1', '1163', '1237', '0.0013', '130', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1418', '1', '1163', '1237', '0.0013', '131', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1419', '1', '1163', '1237', '0.0013', '132', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1420', '1', '1163', '1237', '0.0013', '133', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1421', '1', '1163', '1237', '0.0013', '134', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1422', '1', '1163', '1237', '0.0013', '135', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1423', '1', '1163', '1237', '0.0013', '136', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1424', '1', '1163', '1237', '0.0013', '137', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1425', '1', '1163', '1237', '0.0013', '138', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1426', '1', '1163', '1237', '0.0013', '139', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1427', '1', '1163', '1237', '0.0013', '140', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1428', '1', '1163', '1237', '0.0013', '141', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1429', '1', '1163', '1237', '0.0013', '142', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1430', '1', '1163', '1237', '0.0013', '143', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1431', '1', '1163', '1237', '0.0013', '144', '144', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1432', '1', '1238', '100000', '0.166', '1', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1433', '1', '1238', '100000', '0.1164', '2', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1434', '1', '1238', '100000', '0.0756', '3', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1435', '1', '1238', '100000', '0.0562', '4', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1436', '1', '1238', '100000', '0.0419', '5', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1437', '1', '1238', '100000', '0.0338', '6', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1438', '1', '1238', '100000', '0.0282', '7', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1439', '1', '1238', '100000', '0.0225', '8', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1440', '1', '1238', '100000', '0.0166', '9', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1441', '1', '1238', '100000', '0.0127', '10', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1442', '1', '1238', '100000', '0.0127', '11', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1443', '1', '1238', '100000', '0.0127', '12', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1444', '1', '1238', '100000', '0.0101', '13', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1445', '1', '1238', '100000', '0.0101', '14', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1446', '1', '1238', '100000', '0.0101', '15', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1447', '1', '1238', '100000', '0.0087', '16', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1448', '1', '1238', '100000', '0.0087', '17', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1449', '1', '1238', '100000', '0.0087', '18', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1450', '1', '1238', '100000', '0.0077', '19', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1451', '1', '1238', '100000', '0.0077', '20', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1452', '1', '1238', '100000', '0.0077', '21', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1453', '1', '1238', '100000', '0.0068', '22', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1454', '1', '1238', '100000', '0.0068', '23', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1455', '1', '1238', '100000', '0.0068', '24', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1456', '1', '1238', '100000', '0.0059', '25', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1457', '1', '1238', '100000', '0.0059', '26', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1458', '1', '1238', '100000', '0.0059', '27', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1459', '1', '1238', '100000', '0.005', '28', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1460', '1', '1238', '100000', '0.005', '29', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1461', '1', '1238', '100000', '0.005', '30', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1462', '1', '1238', '100000', '0.005', '31', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1463', '1', '1238', '100000', '0.005', '32', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1464', '1', '1238', '100000', '0.005', '33', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1465', '1', '1238', '100000', '0.005', '34', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1466', '1', '1238', '100000', '0.005', '35', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1467', '1', '1238', '100000', '0.005', '36', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1468', '1', '1238', '100000', '0.0043', '37', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1469', '1', '1238', '100000', '0.0043', '38', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1470', '1', '1238', '100000', '0.0043', '39', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1471', '1', '1238', '100000', '0.0043', '40', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1472', '1', '1238', '100000', '0.0043', '41', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1473', '1', '1238', '100000', '0.0043', '42', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1474', '1', '1238', '100000', '0.0043', '43', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1475', '1', '1238', '100000', '0.0043', '44', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1476', '1', '1238', '100000', '0.0043', '45', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1477', '1', '1238', '100000', '0.0036', '46', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1478', '1', '1238', '100000', '0.0036', '47', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1479', '1', '1238', '100000', '0.0036', '48', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1480', '1', '1238', '100000', '0.0036', '49', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1481', '1', '1238', '100000', '0.0036', '50', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1482', '1', '1238', '100000', '0.0036', '51', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1483', '1', '1238', '100000', '0.0036', '52', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1484', '1', '1238', '100000', '0.0036', '53', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1485', '1', '1238', '100000', '0.0036', '54', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1486', '1', '1238', '100000', '0.003', '55', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1487', '1', '1238', '100000', '0.003', '56', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1488', '1', '1238', '100000', '0.003', '57', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1489', '1', '1238', '100000', '0.003', '58', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1490', '1', '1238', '100000', '0.003', '59', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1491', '1', '1238', '100000', '0.003', '60', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1492', '1', '1238', '100000', '0.003', '61', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1493', '1', '1238', '100000', '0.003', '62', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1494', '1', '1238', '100000', '0.003', '63', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1495', '1', '1238', '100000', '0.0025', '64', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1496', '1', '1238', '100000', '0.0025', '65', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1497', '1', '1238', '100000', '0.0025', '66', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1498', '1', '1238', '100000', '0.0025', '67', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1499', '1', '1238', '100000', '0.0025', '68', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1500', '1', '1238', '100000', '0.0025', '69', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1501', '1', '1238', '100000', '0.0025', '70', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1502', '1', '1238', '100000', '0.0025', '71', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1503', '1', '1238', '100000', '0.0025', '72', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1504', '1', '1238', '100000', '0.0022', '73', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1505', '1', '1238', '100000', '0.0022', '74', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1506', '1', '1238', '100000', '0.0022', '75', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1507', '1', '1238', '100000', '0.0022', '76', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1508', '1', '1238', '100000', '0.0022', '77', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1509', '1', '1238', '100000', '0.0022', '78', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1510', '1', '1238', '100000', '0.0022', '79', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1511', '1', '1238', '100000', '0.0022', '80', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1512', '1', '1238', '100000', '0.0022', '81', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1513', '1', '1238', '100000', '0.0019', '82', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1514', '1', '1238', '100000', '0.0019', '83', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1515', '1', '1238', '100000', '0.0019', '84', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1516', '1', '1238', '100000', '0.0019', '85', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1517', '1', '1238', '100000', '0.0019', '86', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1518', '1', '1238', '100000', '0.0019', '87', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1519', '1', '1238', '100000', '0.0019', '88', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1520', '1', '1238', '100000', '0.0019', '89', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1521', '1', '1238', '100000', '0.0019', '90', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1522', '1', '1238', '100000', '0.0016', '91', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1523', '1', '1238', '100000', '0.0016', '92', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1524', '1', '1238', '100000', '0.0016', '93', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1525', '1', '1238', '100000', '0.0016', '94', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1526', '1', '1238', '100000', '0.0016', '95', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1527', '1', '1238', '100000', '0.0016', '96', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1528', '1', '1238', '100000', '0.0016', '97', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1529', '1', '1238', '100000', '0.0016', '98', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1530', '1', '1238', '100000', '0.0016', '99', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1531', '1', '1238', '100000', '0.0013', '100', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1532', '1', '1238', '100000', '0.0013', '101', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1533', '1', '1238', '100000', '0.0013', '102', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1534', '1', '1238', '100000', '0.0013', '103', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1535', '1', '1238', '100000', '0.0013', '104', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1536', '1', '1238', '100000', '0.0013', '105', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1537', '1', '1238', '100000', '0.0013', '106', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1538', '1', '1238', '100000', '0.0013', '107', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1539', '1', '1238', '100000', '0.0013', '108', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1540', '1', '1238', '100000', '0.0013', '109', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1541', '1', '1238', '100000', '0.0013', '110', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1542', '1', '1238', '100000', '0.0013', '111', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1543', '1', '1238', '100000', '0.0013', '112', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1544', '1', '1238', '100000', '0.0013', '113', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1545', '1', '1238', '100000', '0.0013', '114', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1546', '1', '1238', '100000', '0.0013', '115', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1547', '1', '1238', '100000', '0.0013', '116', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1548', '1', '1238', '100000', '0.0013', '153', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1549', '1', '1238', '100000', '0.0013', '118', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1550', '1', '1238', '100000', '0.0013', '119', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1551', '1', '1238', '100000', '0.0013', '120', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1552', '1', '1238', '100000', '0.0013', '121', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1553', '1', '1238', '100000', '0.0013', '122', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1554', '1', '1238', '100000', '0.0013', '123', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1555', '1', '1238', '100000', '0.0013', '124', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1556', '1', '1238', '100000', '0.0013', '125', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1557', '1', '1238', '100000', '0.0013', '126', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1558', '1', '1238', '100000', '0.0013', '127', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1559', '1', '1238', '100000', '0.0013', '128', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1560', '1', '1238', '100000', '0.0013', '129', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1561', '1', '1238', '100000', '0.0013', '130', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1562', '1', '1238', '100000', '0.0013', '131', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1563', '1', '1238', '100000', '0.0013', '132', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1564', '1', '1238', '100000', '0.0013', '133', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1565', '1', '1238', '100000', '0.0013', '134', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1566', '1', '1238', '100000', '0.0013', '135', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1567', '1', '1238', '100000', '0.0013', '136', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1568', '1', '1238', '100000', '0.0013', '137', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1569', '1', '1238', '100000', '0.0013', '138', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1570', '1', '1238', '100000', '0.0013', '139', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1571', '1', '1238', '100000', '0.0013', '140', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1572', '1', '1238', '100000', '0.0013', '141', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1573', '1', '1238', '100000', '0.0013', '142', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1574', '1', '1238', '100000', '0.0013', '143', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1575', '1', '1238', '100000', '0.0013', '144', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1576', '1', '1238', '100000', '0.0013', '145', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1577', '1', '1238', '100000', '0.0013', '146', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1578', '1', '1238', '100000', '0.0013', '147', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1579', '1', '1238', '100000', '0.0013', '148', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1580', '1', '1238', '100000', '0.0013', '149', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1581', '1', '1238', '100000', '0.0013', '150', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1582', '1', '1238', '100000', '0.0013', '151', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1583', '1', '1238', '100000', '0.0013', '152', '153', '1', '2015-06-30 16:26:34');
INSERT INTO `prize` VALUES ('1584', '1', '1238', '100000', '0.0013', '153', '153', '1', '2015-06-30 16:26:34');

-- ----------------------------
-- Table structure for prizetemplate
-- ----------------------------
DROP TABLE IF EXISTS `prizetemplate`;
CREATE TABLE `prizetemplate` (
  `PRIZETEMPID` int(11) NOT NULL AUTO_INCREMENT,
  `MAXRANKING` smallint(6) NOT NULL,
  `PRIZETEMPNAME` varchar(90) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  `CREATETIME` datetime NOT NULL,
  PRIMARY KEY (`PRIZETEMPID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prizetemplate
-- ----------------------------
INSERT INTO `prizetemplate` VALUES ('1', '153', 'WPT赛事奖励模板', '1', '2015-05-13 17:50:38');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ROLEID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLENAME` varchar(60) NOT NULL,
  `ROLESTATE` tinyint(4) NOT NULL COMMENT '角色状态：1、有效；-1、已删除',
  `ROLEDESC` varchar(150) DEFAULT NULL,
  `ROLENAMESHOW` varchar(60) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL COMMENT '1、wpt',
  `CREATETIME` datetime NOT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `DELTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ROLEID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='权限角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员权限', '1', null, '管理员权限', '1', '2015-04-09 13:41:50', null, null);
INSERT INTO `role` VALUES ('2', '报名处', '1', null, '报名处', '1', '2015-07-28 15:28:29', null, null);
INSERT INTO `role` VALUES ('3', '入场安检', '1', null, '入场安检', '1', '2015-07-28 15:29:05', null, null);
INSERT INTO `role` VALUES ('4', '裁判', '1', null, '裁判', '1', '2015-07-28 15:29:53', null, null);

-- ----------------------------
-- Table structure for role_priv_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_priv_relation`;
CREATE TABLE `role_priv_relation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRIVID` int(11) NOT NULL,
  `ROLEID` int(11) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='角色权限的关系，功能权限和角色关联';

-- ----------------------------
-- Records of role_priv_relation
-- ----------------------------
INSERT INTO `role_priv_relation` VALUES ('1', '1', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('2', '2', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('3', '3', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('4', '4', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('5', '5', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('6', '6', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('7', '7', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('8', '8', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('9', '9', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('10', '10', '1', '1');
INSERT INTO `role_priv_relation` VALUES ('11', '1', '2', '1');
INSERT INTO `role_priv_relation` VALUES ('12', '2', '2', '1');
INSERT INTO `role_priv_relation` VALUES ('13', '5', '2', '1');
INSERT INTO `role_priv_relation` VALUES ('14', '7', '3', '1');
INSERT INTO `role_priv_relation` VALUES ('15', '8', '4', '1');

-- ----------------------------
-- Table structure for round
-- ----------------------------
DROP TABLE IF EXISTS `round`;
CREATE TABLE `round` (
  `BLINDID` int(11) NOT NULL AUTO_INCREMENT,
  `ROUNDTEMPID` int(11) NOT NULL,
  `ROUNDSTATUS` tinyint(4) NOT NULL COMMENT '盲注记录的状态：-1、跳过；0、空闲；1、进行中；2、已完结',
  `BIGBLIND` int(11) NOT NULL,
  `SMALLBLIND` int(11) NOT NULL,
  `STEPLEN` int(11) NOT NULL COMMENT '盲注级别之间的计时时间，如果是break，就是休息时间，单位都是秒',
  `ROUNDTYPE` tinyint(4) NOT NULL COMMENT '类别：1、计时盲注；0、休息间隔',
  `ROUNDRANK` smallint(6) NOT NULL,
  `BEFORECHIP` int(11) NOT NULL DEFAULT '0' COMMENT '每个盲注级别需要的前注，如果是0，这个级别的盲注没有前注',
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`BLINDID`)
) ENGINE=InnoDB AUTO_INCREMENT=480 DEFAULT CHARSET=utf8 COMMENT='盲注记录';

-- ----------------------------
-- Records of round
-- ----------------------------
INSERT INTO `round` VALUES ('1', '1', '0', '100', '50', '3600', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('2', '1', '0', '150', '75', '3600', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('3', '1', '0', '0', '0', '900', '0', '2', '0', '1');
INSERT INTO `round` VALUES ('4', '1', '0', '150', '75', '3600', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('5', '1', '0', '200', '100', '3600', '1', '4', '25', '1');
INSERT INTO `round` VALUES ('6', '1', '0', '0', '0', '900', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('7', '1', '0', '300', '150', '3600', '1', '5', '50', '1');
INSERT INTO `round` VALUES ('8', '1', '0', '400', '200', '3600', '1', '6', '50', '1');
INSERT INTO `round` VALUES ('9', '1', '0', '0', '0', '900', '0', '6', '0', '1');
INSERT INTO `round` VALUES ('10', '1', '0', '500', '250', '3600', '1', '7', '75', '1');
INSERT INTO `round` VALUES ('11', '1', '0', '600', '300', '3600', '1', '8', '75', '1');
INSERT INTO `round` VALUES ('12', '1', '0', '0', '0', '900', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('13', '1', '0', '800', '400', '3600', '1', '9', '100', '1');
INSERT INTO `round` VALUES ('14', '1', '0', '1000', '500', '3600', '1', '10', '100', '1');
INSERT INTO `round` VALUES ('15', '1', '0', '0', '0', '900', '0', '10', '0', '1');
INSERT INTO `round` VALUES ('16', '1', '0', '1200', '600', '3600', '1', '11', '200', '1');
INSERT INTO `round` VALUES ('17', '1', '0', '1600', '800', '3600', '1', '12', '200', '1');
INSERT INTO `round` VALUES ('18', '1', '0', '0', '0', '900', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('19', '1', '0', '2000', '1000', '3600', '1', '13', '300', '1');
INSERT INTO `round` VALUES ('20', '1', '0', '2400', '1200', '3600', '1', '14', '400', '1');
INSERT INTO `round` VALUES ('21', '1', '0', '0', '0', '900', '0', '14', '0', '1');
INSERT INTO `round` VALUES ('22', '1', '0', '3000', '1500', '3600', '1', '15', '500', '1');
INSERT INTO `round` VALUES ('23', '1', '0', '4000', '2000', '3600', '1', '16', '500', '1');
INSERT INTO `round` VALUES ('24', '1', '0', '0', '0', '900', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('25', '1', '0', '5000', '2500', '3600', '1', '17', '500', '1');
INSERT INTO `round` VALUES ('26', '1', '0', '6000', '3000', '3600', '1', '18', '1000', '1');
INSERT INTO `round` VALUES ('27', '1', '0', '0', '0', '900', '0', '18', '0', '1');
INSERT INTO `round` VALUES ('28', '1', '0', '8000', '4000', '3600', '1', '19', '1000', '1');
INSERT INTO `round` VALUES ('29', '1', '0', '10000', '5000', '3600', '1', '20', '1000', '1');
INSERT INTO `round` VALUES ('30', '1', '0', '0', '0', '900', '0', '20', '0', '1');
INSERT INTO `round` VALUES ('31', '1', '0', '12000', '6000', '3600', '1', '21', '2000', '1');
INSERT INTO `round` VALUES ('32', '1', '0', '16000', '8000', '3600', '1', '22', '2000', '1');
INSERT INTO `round` VALUES ('33', '1', '0', '0', '0', '900', '0', '22', '0', '1');
INSERT INTO `round` VALUES ('34', '1', '0', '20000', '10000', '3600', '1', '23', '3000', '1');
INSERT INTO `round` VALUES ('35', '1', '0', '24000', '12000', '3600', '1', '24', '4000', '1');
INSERT INTO `round` VALUES ('36', '1', '0', '0', '0', '900', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('37', '1', '0', '30000', '15000', '3600', '1', '25', '5000', '1');
INSERT INTO `round` VALUES ('38', '1', '0', '40000', '20000', '3600', '1', '26', '5000', '1');
INSERT INTO `round` VALUES ('39', '1', '0', '0', '0', '900', '0', '26', '0', '1');
INSERT INTO `round` VALUES ('40', '1', '0', '50000', '25000', '3600', '1', '27', '5000', '1');
INSERT INTO `round` VALUES ('41', '1', '0', '60000', '30000', '3600', '1', '28', '10000', '1');
INSERT INTO `round` VALUES ('42', '1', '0', '0', '0', '900', '0', '28', '0', '1');
INSERT INTO `round` VALUES ('43', '1', '0', '80000', '40000', '3600', '1', '29', '10000', '1');
INSERT INTO `round` VALUES ('44', '1', '0', '100000', '50000', '3600', '1', '30', '15000', '1');
INSERT INTO `round` VALUES ('45', '1', '0', '0', '0', '900', '0', '30', '0', '1');
INSERT INTO `round` VALUES ('46', '1', '0', '120000', '60000', '3600', '1', '31', '20000', '1');
INSERT INTO `round` VALUES ('47', '1', '0', '150000', '75000', '3600', '1', '32', '25000', '1');
INSERT INTO `round` VALUES ('48', '1', '0', '0', '0', '900', '0', '32', '0', '1');
INSERT INTO `round` VALUES ('49', '1', '0', '200000', '100000', '3600', '1', '33', '25000', '1');
INSERT INTO `round` VALUES ('50', '1', '0', '250000', '125000', '3600', '1', '34', '25000', '1');
INSERT INTO `round` VALUES ('51', '1', '0', '0', '0', '900', '0', '34', '0', '1');
INSERT INTO `round` VALUES ('52', '1', '0', '300000', '150000', '3600', '1', '35', '50000', '1');
INSERT INTO `round` VALUES ('53', '1', '0', '400000', '200000', '3600', '1', '36', '50000', '1');
INSERT INTO `round` VALUES ('54', '1', '0', '0', '0', '900', '0', '36', '0', '1');
INSERT INTO `round` VALUES ('55', '1', '0', '500000', '250000', '3600', '1', '37', '75000', '1');
INSERT INTO `round` VALUES ('56', '1', '0', '600000', '300000', '3600', '1', '38', '75000', '1');
INSERT INTO `round` VALUES ('57', '2', '0', '150', '75', '3600', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('58', '2', '0', '0', '0', '600', '0', '2', '0', '1');
INSERT INTO `round` VALUES ('59', '2', '0', '150', '75', '3600', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('60', '2', '0', '200', '100', '3600', '1', '4', '25', '1');
INSERT INTO `round` VALUES ('61', '2', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('62', '2', '0', '300', '150', '3600', '1', '5', '50', '1');
INSERT INTO `round` VALUES ('63', '2', '0', '400', '200', '3600', '1', '6', '50', '1');
INSERT INTO `round` VALUES ('64', '2', '0', '0', '0', '600', '0', '6', '0', '1');
INSERT INTO `round` VALUES ('65', '2', '0', '500', '250', '3600', '1', '7', '75', '1');
INSERT INTO `round` VALUES ('66', '2', '0', '600', '300', '3600', '1', '8', '75', '1');
INSERT INTO `round` VALUES ('67', '2', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('68', '2', '0', '800', '400', '3600', '1', '9', '100', '1');
INSERT INTO `round` VALUES ('69', '2', '0', '1000', '500', '3600', '1', '10', '100', '1');
INSERT INTO `round` VALUES ('70', '2', '0', '0', '0', '600', '0', '10', '0', '1');
INSERT INTO `round` VALUES ('71', '2', '0', '1200', '600', '3600', '1', '11', '200', '1');
INSERT INTO `round` VALUES ('72', '2', '0', '1600', '800', '3600', '1', '12', '200', '1');
INSERT INTO `round` VALUES ('73', '2', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('74', '2', '0', '2000', '1000', '3600', '1', '13', '300', '1');
INSERT INTO `round` VALUES ('75', '2', '0', '2400', '1200', '3600', '1', '14', '400', '1');
INSERT INTO `round` VALUES ('76', '2', '0', '0', '0', '600', '0', '14', '0', '1');
INSERT INTO `round` VALUES ('77', '2', '0', '3000', '1500', '3600', '1', '15', '500', '1');
INSERT INTO `round` VALUES ('78', '2', '0', '4000', '2000', '3600', '1', '16', '500', '1');
INSERT INTO `round` VALUES ('79', '2', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('80', '2', '0', '5000', '2500', '3600', '1', '17', '500', '1');
INSERT INTO `round` VALUES ('81', '2', '0', '6000', '3000', '3600', '1', '18', '1000', '1');
INSERT INTO `round` VALUES ('82', '2', '0', '0', '0', '600', '0', '18', '0', '1');
INSERT INTO `round` VALUES ('83', '2', '0', '8000', '4000', '3600', '1', '19', '1000', '1');
INSERT INTO `round` VALUES ('84', '2', '0', '10000', '5000', '3600', '1', '20', '1000', '1');
INSERT INTO `round` VALUES ('85', '2', '0', '0', '0', '600', '0', '20', '0', '1');
INSERT INTO `round` VALUES ('86', '2', '0', '12000', '6000', '3600', '1', '21', '2000', '1');
INSERT INTO `round` VALUES ('87', '2', '0', '16000', '8000', '3600', '1', '22', '2000', '1');
INSERT INTO `round` VALUES ('88', '2', '0', '0', '0', '600', '0', '22', '0', '1');
INSERT INTO `round` VALUES ('89', '2', '0', '20000', '10000', '3600', '1', '23', '3000', '1');
INSERT INTO `round` VALUES ('90', '2', '0', '24000', '12000', '3600', '1', '24', '4000', '1');
INSERT INTO `round` VALUES ('91', '2', '0', '0', '0', '600', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('92', '2', '0', '30000', '15000', '3600', '1', '25', '5000', '1');
INSERT INTO `round` VALUES ('93', '2', '0', '40000', '20000', '3600', '1', '26', '5000', '1');
INSERT INTO `round` VALUES ('94', '2', '0', '0', '0', '600', '0', '26', '0', '1');
INSERT INTO `round` VALUES ('95', '2', '0', '50000', '25000', '3600', '1', '27', '5000', '1');
INSERT INTO `round` VALUES ('96', '2', '0', '60000', '30000', '3600', '1', '28', '10000', '1');
INSERT INTO `round` VALUES ('97', '2', '0', '0', '0', '600', '0', '28', '0', '1');
INSERT INTO `round` VALUES ('98', '2', '0', '80000', '40000', '3600', '1', '29', '10000', '1');
INSERT INTO `round` VALUES ('99', '2', '0', '100000', '50000', '3600', '1', '30', '15000', '1');
INSERT INTO `round` VALUES ('100', '2', '0', '0', '0', '600', '0', '30', '0', '1');
INSERT INTO `round` VALUES ('101', '2', '0', '120000', '60000', '3600', '1', '31', '20000', '1');
INSERT INTO `round` VALUES ('102', '2', '0', '150000', '75000', '3600', '1', '32', '25000', '1');
INSERT INTO `round` VALUES ('103', '2', '0', '0', '0', '600', '0', '32', '0', '1');
INSERT INTO `round` VALUES ('104', '2', '0', '200000', '100000', '3600', '1', '33', '25000', '1');
INSERT INTO `round` VALUES ('105', '2', '0', '250000', '125000', '3600', '1', '34', '25000', '1');
INSERT INTO `round` VALUES ('106', '2', '0', '0', '0', '600', '0', '34', '0', '1');
INSERT INTO `round` VALUES ('107', '2', '0', '300000', '150000', '3600', '1', '35', '50000', '1');
INSERT INTO `round` VALUES ('108', '2', '0', '400000', '200000', '3600', '1', '36', '50000', '1');
INSERT INTO `round` VALUES ('109', '2', '0', '0', '0', '600', '0', '36', '0', '1');
INSERT INTO `round` VALUES ('110', '2', '0', '500000', '250000', '3600', '1', '37', '75000', '1');
INSERT INTO `round` VALUES ('111', '2', '0', '600000', '300000', '3600', '1', '38', '75000', '1');
INSERT INTO `round` VALUES ('112', '3', '0', '50', '25', '1500', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('113', '3', '0', '100', '50', '1500', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('114', '3', '0', '200', '100', '1500', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('115', '3', '0', '400', '200', '1500', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('116', '3', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('117', '3', '0', '600', '300', '1500', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('118', '3', '0', '800', '400', '1500', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('119', '3', '0', '1000', '500', '1500', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('120', '3', '0', '1200', '600', '1500', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('121', '3', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('122', '3', '0', '1600', '800', '1500', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('123', '3', '0', '2000', '1000', '1500', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('124', '3', '0', '2400', '1200', '1500', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('125', '3', '0', '3000', '1500', '1500', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('126', '3', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('127', '3', '0', '4000', '2000', '1500', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('128', '3', '0', '5000', '2500', '1500', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('129', '3', '0', '6000', '3000', '1500', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('130', '3', '0', '8000', '4000', '1500', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('131', '3', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('132', '3', '0', '10000', '5000', '1500', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('133', '3', '0', '12000', '6000', '1500', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('134', '3', '0', '16000', '8000', '1500', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('135', '3', '0', '20000', '10000', '1500', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('136', '4', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('137', '4', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('138', '4', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('139', '4', '0', '400', '200', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('140', '4', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('141', '4', '0', '600', '300', '1800', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('142', '4', '0', '800', '400', '1800', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('143', '4', '0', '1000', '500', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('144', '4', '0', '1200', '600', '1800', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('145', '4', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('146', '4', '0', '1600', '800', '1800', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('147', '4', '0', '2000', '1000', '1800', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('148', '4', '0', '2400', '1200', '1800', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('149', '4', '0', '3000', '1500', '1800', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('150', '4', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('151', '4', '0', '4000', '2000', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('152', '4', '0', '5000', '2500', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('153', '4', '0', '6000', '3000', '1800', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('154', '4', '0', '8000', '4000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('155', '4', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('156', '4', '0', '10000', '5000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('157', '4', '0', '12000', '6000', '1800', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('158', '4', '0', '16000', '8000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('159', '4', '0', '20000', '10000', '1800', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('160', '5', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('161', '5', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('162', '5', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('163', '5', '0', '400', '200', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('164', '5', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('165', '5', '0', '600', '300', '1800', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('166', '5', '0', '800', '400', '1800', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('167', '5', '0', '1000', '500', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('168', '5', '0', '1200', '600', '1800', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('169', '5', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('170', '5', '0', '1600', '800', '1800', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('171', '5', '0', '2000', '1000', '1800', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('172', '5', '0', '2400', '1200', '1800', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('173', '5', '0', '3000', '1500', '1800', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('174', '5', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('175', '5', '0', '4000', '2000', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('176', '5', '0', '5000', '2500', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('177', '5', '0', '6000', '3000', '1800', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('178', '5', '0', '8000', '4000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('179', '5', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('180', '5', '0', '10000', '5000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('181', '5', '0', '12000', '6000', '1800', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('182', '5', '0', '16000', '8000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('183', '5', '0', '20000', '10000', '1800', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('184', '6', '0', '200', '100', '3600', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('185', '6', '0', '400', '200', '3600', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('186', '6', '0', '0', '0', '600', '0', '2', '0', '1');
INSERT INTO `round` VALUES ('187', '6', '0', '600', '300', '3600', '1', '3', '100', '1');
INSERT INTO `round` VALUES ('188', '6', '0', '800', '400', '3600', '1', '4', '100', '1');
INSERT INTO `round` VALUES ('189', '6', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('190', '6', '0', '1000', '500', '3600', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('191', '6', '0', '1200', '600', '3600', '1', '6', '200', '1');
INSERT INTO `round` VALUES ('192', '6', '0', '0', '0', '600', '0', '6', '0', '1');
INSERT INTO `round` VALUES ('193', '6', '0', '1600', '800', '3600', '1', '7', '200', '1');
INSERT INTO `round` VALUES ('194', '6', '0', '2000', '1000', '3600', '1', '8', '300', '1');
INSERT INTO `round` VALUES ('195', '6', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('196', '6', '0', '2400', '1200', '3600', '1', '9', '400', '1');
INSERT INTO `round` VALUES ('197', '6', '0', '3000', '1500', '3600', '1', '10', '500', '1');
INSERT INTO `round` VALUES ('198', '6', '0', '0', '0', '600', '0', '10', '0', '1');
INSERT INTO `round` VALUES ('199', '6', '0', '4000', '2000', '3600', '1', '11', '500', '1');
INSERT INTO `round` VALUES ('200', '6', '0', '5000', '2500', '3600', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('201', '6', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('202', '6', '0', '6000', '3000', '3600', '1', '13', '1000', '1');
INSERT INTO `round` VALUES ('203', '6', '0', '8000', '4000', '3600', '1', '14', '1000', '1');
INSERT INTO `round` VALUES ('204', '6', '0', '0', '0', '600', '0', '14', '0', '1');
INSERT INTO `round` VALUES ('205', '6', '0', '10000', '5000', '3600', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('206', '6', '0', '12000', '6000', '3600', '1', '16', '2000', '1');
INSERT INTO `round` VALUES ('207', '6', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('208', '6', '0', '16000', '8000', '3600', '1', '17', '2000', '1');
INSERT INTO `round` VALUES ('209', '6', '0', '20000', '10000', '3600', '1', '18', '3000', '1');
INSERT INTO `round` VALUES ('210', '6', '0', '0', '0', '600', '0', '18', '0', '1');
INSERT INTO `round` VALUES ('211', '6', '0', '24000', '12000', '3600', '1', '19', '4000', '1');
INSERT INTO `round` VALUES ('212', '6', '0', '30000', '15000', '3600', '1', '20', '5000', '1');
INSERT INTO `round` VALUES ('213', '6', '0', '0', '0', '600', '0', '20', '0', '1');
INSERT INTO `round` VALUES ('214', '6', '0', '40000', '20000', '3600', '1', '21', '5000', '1');
INSERT INTO `round` VALUES ('215', '6', '0', '50000', '25000', '3600', '1', '22', '5000', '1');
INSERT INTO `round` VALUES ('216', '6', '0', '0', '0', '600', '0', '22', '0', '1');
INSERT INTO `round` VALUES ('217', '6', '0', '60000', '30000', '3600', '1', '23', '10000', '1');
INSERT INTO `round` VALUES ('218', '6', '0', '80000', '40000', '3600', '1', '24', '10000', '1');
INSERT INTO `round` VALUES ('219', '6', '0', '0', '0', '600', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('220', '6', '0', '100000', '50000', '3600', '1', '25', '15000', '1');
INSERT INTO `round` VALUES ('221', '7', '0', '50', '25', '900', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('222', '7', '0', '100', '50', '900', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('223', '7', '0', '200', '100', '900', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('224', '7', '0', '400', '200', '900', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('225', '7', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('226', '7', '0', '600', '300', '900', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('227', '7', '0', '800', '400', '900', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('228', '7', '0', '1000', '500', '900', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('229', '7', '0', '1200', '600', '900', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('230', '7', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('231', '7', '0', '1600', '800', '900', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('232', '7', '0', '2000', '1000', '900', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('233', '7', '0', '2400', '1200', '900', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('234', '7', '0', '3000', '1500', '900', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('235', '7', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('236', '7', '0', '4000', '2000', '900', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('237', '7', '0', '5000', '2500', '900', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('238', '7', '0', '6000', '3000', '900', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('239', '7', '0', '8000', '4000', '900', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('240', '7', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('241', '7', '0', '10000', '5000', '900', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('242', '7', '0', '12000', '6000', '900', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('243', '7', '0', '16000', '8000', '900', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('244', '7', '0', '20000', '10000', '900', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('245', '8', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('246', '8', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('247', '8', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('248', '8', '0', '400', '200', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('249', '8', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('250', '8', '0', '600', '300', '1800', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('251', '8', '0', '800', '400', '1800', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('252', '8', '0', '1000', '500', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('253', '8', '0', '1200', '600', '1800', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('254', '8', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('255', '8', '0', '1600', '800', '1800', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('256', '8', '0', '2000', '1000', '1800', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('257', '8', '0', '2400', '1200', '1800', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('258', '8', '0', '3000', '1500', '1800', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('259', '8', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('260', '8', '0', '4000', '2000', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('261', '8', '0', '5000', '2500', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('262', '8', '0', '6000', '3000', '1800', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('263', '8', '0', '8000', '4000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('264', '8', '0', '0', '0', '1800', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('265', '8', '0', '10000', '5000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('266', '8', '0', '12000', '6000', '1800', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('267', '8', '0', '16000', '8000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('268', '9', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('269', '9', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('270', '9', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('271', '9', '0', '400', '200', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('272', '9', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('273', '9', '0', '600', '300', '1800', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('274', '9', '0', '800', '400', '1800', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('275', '9', '0', '1000', '500', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('276', '9', '0', '1200', '600', '1800', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('277', '9', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('278', '9', '0', '1600', '800', '1800', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('279', '9', '0', '2000', '1000', '1800', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('280', '9', '0', '2400', '1200', '1800', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('281', '9', '0', '3000', '1500', '1800', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('282', '9', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('283', '9', '0', '4000', '2000', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('284', '9', '0', '5000', '2500', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('285', '9', '0', '6000', '3000', '1800', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('286', '9', '0', '8000', '4000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('287', '9', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('288', '9', '0', '10000', '5000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('289', '9', '0', '12000', '6000', '1800', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('290', '9', '0', '16000', '8000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('291', '9', '0', '20000', '10000', '1800', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('292', '10', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('293', '10', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('294', '10', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('295', '10', '0', '400', '200', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('296', '10', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('297', '10', '0', '600', '300', '1800', '1', '5', '100', '1');
INSERT INTO `round` VALUES ('298', '10', '0', '800', '400', '1800', '1', '6', '100', '1');
INSERT INTO `round` VALUES ('299', '10', '0', '1000', '500', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('300', '10', '0', '1200', '600', '1800', '1', '8', '200', '1');
INSERT INTO `round` VALUES ('301', '10', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('302', '10', '0', '1600', '800', '1800', '1', '9', '200', '1');
INSERT INTO `round` VALUES ('303', '10', '0', '2000', '1000', '1800', '1', '10', '300', '1');
INSERT INTO `round` VALUES ('304', '10', '0', '2400', '1200', '1800', '1', '11', '400', '1');
INSERT INTO `round` VALUES ('305', '10', '0', '3000', '1500', '1800', '1', '12', '500', '1');
INSERT INTO `round` VALUES ('306', '10', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('307', '10', '0', '4000', '2000', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('308', '10', '0', '5000', '2500', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('309', '10', '0', '6000', '3000', '1800', '1', '15', '1000', '1');
INSERT INTO `round` VALUES ('310', '10', '0', '8000', '4000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('311', '10', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('312', '10', '0', '10000', '5000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('313', '10', '0', '12000', '6000', '1800', '1', '18', '2000', '1');
INSERT INTO `round` VALUES ('314', '10', '0', '16000', '8000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('315', '10', '0', '20000', '10000', '1800', '1', '20', '3000', '1');
INSERT INTO `round` VALUES ('316', '11', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('317', '11', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('318', '11', '0', '200', '100', '1800', '1', '3', '0', '1');
INSERT INTO `round` VALUES ('319', '11', '0', '400', '200', '1800', '1', '4', '0', '1');
INSERT INTO `round` VALUES ('320', '11', '0', '0', '0', '600', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('321', '11', '0', '600', '300', '1800', '1', '5', '0', '1');
INSERT INTO `round` VALUES ('322', '11', '0', '800', '400', '1800', '1', '6', '0', '1');
INSERT INTO `round` VALUES ('323', '11', '0', '1000', '500', '1800', '1', '7', '0', '1');
INSERT INTO `round` VALUES ('324', '11', '0', '1200', '600', '1800', '1', '8', '0', '1');
INSERT INTO `round` VALUES ('325', '11', '0', '0', '0', '600', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('326', '11', '0', '1600', '800', '1800', '1', '9', '0', '1');
INSERT INTO `round` VALUES ('327', '11', '0', '2000', '1000', '1800', '1', '10', '0', '1');
INSERT INTO `round` VALUES ('328', '11', '0', '3000', '1500', '1800', '1', '11', '0', '1');
INSERT INTO `round` VALUES ('329', '11', '0', '4000', '2000', '1800', '1', '12', '0', '1');
INSERT INTO `round` VALUES ('330', '11', '0', '0', '0', '600', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('331', '11', '0', '6000', '3000', '1800', '1', '13', '0', '1');
INSERT INTO `round` VALUES ('332', '11', '0', '8000', '4000', '1800', '1', '14', '0', '1');
INSERT INTO `round` VALUES ('333', '11', '0', '10000', '5000', '1800', '1', '15', '0', '1');
INSERT INTO `round` VALUES ('334', '11', '0', '12000', '6000', '1800', '1', '16', '0', '1');
INSERT INTO `round` VALUES ('335', '11', '0', '0', '0', '600', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('336', '11', '0', '16000', '8000', '1800', '1', '17', '0', '1');
INSERT INTO `round` VALUES ('337', '11', '0', '20000', '10000', '1800', '1', '18', '0', '1');
INSERT INTO `round` VALUES ('338', '2', '0', '100', '50', '3600', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('339', '12', '0', '50', '25', '1800', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('340', '12', '0', '100', '50', '1800', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('341', '12', '0', '200', '100', '1800', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('342', '12', '0', '300', '150', '1800', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('343', '12', '0', '0', '0', '900', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('344', '12', '0', '400', '200', '1800', '1', '5', '50', '1');
INSERT INTO `round` VALUES ('345', '12', '0', '500', '250', '1800', '1', '6', '75', '1');
INSERT INTO `round` VALUES ('346', '12', '0', '600', '300', '1800', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('347', '12', '0', '800', '400', '1800', '1', '8', '100', '1');
INSERT INTO `round` VALUES ('348', '12', '0', '0', '0', '900', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('349', '12', '0', '1000', '500', '1800', '1', '9', '100', '1');
INSERT INTO `round` VALUES ('350', '12', '0', '1200', '600', '1800', '1', '10', '200', '1');
INSERT INTO `round` VALUES ('351', '12', '0', '1600', '800', '1800', '1', '11', '200', '1');
INSERT INTO `round` VALUES ('352', '12', '0', '2000', '1000', '1800', '1', '12', '300', '1');
INSERT INTO `round` VALUES ('353', '12', '0', '0', '0', '900', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('354', '12', '0', '3000', '1500', '1800', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('355', '12', '0', '4000', '2000', '1800', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('356', '12', '0', '5000', '2500', '1800', '1', '15', '500', '1');
INSERT INTO `round` VALUES ('357', '12', '0', '6000', '3000', '1800', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('358', '12', '0', '0', '0', '900', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('359', '12', '0', '8000', '4000', '1800', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('360', '12', '0', '10000', '5000', '1800', '1', '18', '1000', '1');
INSERT INTO `round` VALUES ('361', '12', '0', '12000', '6000', '1800', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('362', '12', '0', '16000', '8000', '1800', '1', '20', '2000', '1');
INSERT INTO `round` VALUES ('363', '12', '0', '0', '0', '900', '0', '20', '0', '1');
INSERT INTO `round` VALUES ('364', '12', '0', '20000', '10000', '1800', '1', '21', '3000', '1');
INSERT INTO `round` VALUES ('365', '12', '0', '24000', '12000', '1800', '1', '22', '4000', '1');
INSERT INTO `round` VALUES ('366', '12', '0', '30000', '15000', '1800', '1', '23', '5000', '1');
INSERT INTO `round` VALUES ('367', '12', '0', '40000', '20000', '1800', '1', '24', '5000', '1');
INSERT INTO `round` VALUES ('368', '12', '0', '0', '0', '900', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('369', '12', '0', '50000', '25000', '1800', '1', '25', '5000', '1');
INSERT INTO `round` VALUES ('370', '12', '0', '60000', '30000', '1800', '1', '26', '10000', '1');
INSERT INTO `round` VALUES ('371', '12', '0', '80000', '40000', '1800', '1', '27', '10000', '1');
INSERT INTO `round` VALUES ('372', '12', '0', '100000', '50000', '1800', '1', '28', '15000', '1');
INSERT INTO `round` VALUES ('373', '12', '0', '0', '0', '900', '0', '28', '0', '1');
INSERT INTO `round` VALUES ('374', '12', '0', '120000', '60000', '1800', '1', '29', '20000', '1');
INSERT INTO `round` VALUES ('375', '12', '0', '150000', '75000', '1800', '1', '30', '25000', '1');
INSERT INTO `round` VALUES ('376', '12', '0', '200000', '100000', '1800', '1', '31', '25000', '1');
INSERT INTO `round` VALUES ('377', '12', '0', '250000', '125000', '1800', '1', '32', '25000', '1');
INSERT INTO `round` VALUES ('378', '12', '0', '0', '0', '900', '0', '32', '0', '1');
INSERT INTO `round` VALUES ('379', '12', '0', '300000', '150000', '1800', '1', '33', '50000', '1');
INSERT INTO `round` VALUES ('380', '12', '0', '400000', '200000', '1800', '1', '34', '50000', '1');
INSERT INTO `round` VALUES ('381', '12', '0', '500000', '250000', '1800', '1', '35', '50000', '1');
INSERT INTO `round` VALUES ('382', '13', '0', '50', '25', '2400', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('383', '13', '0', '100', '50', '2400', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('384', '13', '0', '200', '100', '2400', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('385', '13', '0', '0', '0', '900', '0', '3', '0', '1');
INSERT INTO `round` VALUES ('386', '13', '0', '300', '150', '2400', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('387', '13', '0', '400', '200', '2400', '1', '5', '50', '1');
INSERT INTO `round` VALUES ('388', '13', '0', '500', '250', '2400', '1', '6', '75', '1');
INSERT INTO `round` VALUES ('389', '13', '0', '0', '0', '900', '0', '6', '0', '1');
INSERT INTO `round` VALUES ('390', '13', '0', '600', '300', '2400', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('391', '13', '0', '800', '400', '2400', '1', '8', '100', '1');
INSERT INTO `round` VALUES ('392', '13', '0', '1000', '500', '2400', '1', '9', '100', '1');
INSERT INTO `round` VALUES ('393', '13', '0', '0', '0', '900', '0', '9', '0', '1');
INSERT INTO `round` VALUES ('394', '13', '0', '1200', '600', '2400', '1', '10', '200', '1');
INSERT INTO `round` VALUES ('395', '13', '0', '1600', '800', '2400', '1', '11', '200', '1');
INSERT INTO `round` VALUES ('396', '13', '0', '2000', '1000', '2400', '1', '12', '300', '1');
INSERT INTO `round` VALUES ('397', '13', '0', '0', '0', '900', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('398', '13', '0', '3000', '1500', '2400', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('399', '13', '0', '4000', '2000', '2400', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('400', '13', '0', '5000', '2500', '2400', '1', '15', '500', '1');
INSERT INTO `round` VALUES ('401', '13', '0', '0', '0', '900', '0', '15', '0', '1');
INSERT INTO `round` VALUES ('402', '13', '0', '6000', '3000', '2400', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('403', '13', '0', '8000', '4000', '2400', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('404', '13', '0', '10000', '5000', '2400', '1', '18', '1000', '1');
INSERT INTO `round` VALUES ('405', '13', '0', '0', '0', '900', '0', '18', '0', '1');
INSERT INTO `round` VALUES ('406', '13', '0', '12000', '6000', '2400', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('407', '13', '0', '16000', '8000', '2400', '1', '20', '2000', '1');
INSERT INTO `round` VALUES ('408', '13', '0', '20000', '10000', '2400', '1', '21', '3000', '1');
INSERT INTO `round` VALUES ('409', '13', '0', '0', '0', '900', '0', '21', '0', '1');
INSERT INTO `round` VALUES ('410', '13', '0', '24000', '12000', '2400', '1', '22', '4000', '1');
INSERT INTO `round` VALUES ('411', '13', '0', '30000', '15000', '2400', '1', '23', '5000', '1');
INSERT INTO `round` VALUES ('412', '13', '0', '40000', '20000', '2400', '1', '24', '5000', '1');
INSERT INTO `round` VALUES ('413', '13', '0', '0', '0', '900', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('414', '13', '0', '50000', '25000', '2400', '1', '25', '5000', '1');
INSERT INTO `round` VALUES ('415', '13', '0', '60000', '30000', '2400', '1', '26', '10000', '1');
INSERT INTO `round` VALUES ('416', '13', '0', '80000', '40000', '2400', '1', '27', '10000', '1');
INSERT INTO `round` VALUES ('417', '13', '0', '0', '0', '900', '0', '27', '0', '1');
INSERT INTO `round` VALUES ('418', '13', '0', '100000', '50000', '2400', '1', '28', '15000', '1');
INSERT INTO `round` VALUES ('419', '13', '0', '120000', '60000', '2400', '1', '29', '20000', '1');
INSERT INTO `round` VALUES ('420', '13', '0', '150000', '75000', '2400', '1', '30', '25000', '1');
INSERT INTO `round` VALUES ('421', '13', '0', '0', '0', '900', '0', '30', '0', '1');
INSERT INTO `round` VALUES ('422', '13', '0', '200000', '100000', '2400', '1', '31', '25000', '1');
INSERT INTO `round` VALUES ('423', '13', '0', '250000', '125000', '2400', '1', '32', '25000', '1');
INSERT INTO `round` VALUES ('424', '13', '0', '300000', '150000', '2400', '1', '33', '50000', '1');
INSERT INTO `round` VALUES ('425', '13', '0', '0', '0', '900', '0', '33', '0', '1');
INSERT INTO `round` VALUES ('426', '13', '0', '400000', '200000', '2400', '1', '34', '50000', '1');
INSERT INTO `round` VALUES ('427', '13', '0', '500000', '250000', '2400', '1', '35', '50000', '1');
INSERT INTO `round` VALUES ('428', '14', '0', '50', '25', '3600', '1', '1', '0', '1');
INSERT INTO `round` VALUES ('429', '14', '0', '100', '50', '3600', '1', '2', '0', '1');
INSERT INTO `round` VALUES ('430', '14', '0', '0', '0', '900', '0', '2', '0', '1');
INSERT INTO `round` VALUES ('431', '14', '0', '200', '100', '3600', '1', '3', '25', '1');
INSERT INTO `round` VALUES ('432', '14', '0', '300', '150', '3600', '1', '4', '50', '1');
INSERT INTO `round` VALUES ('433', '14', '0', '0', '0', '900', '0', '4', '0', '1');
INSERT INTO `round` VALUES ('434', '14', '0', '400', '200', '3600', '1', '5', '50', '1');
INSERT INTO `round` VALUES ('435', '14', '0', '500', '250', '3600', '1', '6', '75', '1');
INSERT INTO `round` VALUES ('436', '14', '0', '0', '0', '900', '0', '6', '0', '1');
INSERT INTO `round` VALUES ('437', '14', '0', '600', '300', '3600', '1', '7', '100', '1');
INSERT INTO `round` VALUES ('438', '14', '0', '800', '400', '3600', '1', '8', '100', '1');
INSERT INTO `round` VALUES ('439', '14', '0', '0', '0', '900', '0', '8', '0', '1');
INSERT INTO `round` VALUES ('440', '14', '0', '1000', '500', '3600', '1', '9', '100', '1');
INSERT INTO `round` VALUES ('441', '14', '0', '1200', '600', '3600', '1', '10', '200', '1');
INSERT INTO `round` VALUES ('442', '14', '0', '0', '0', '900', '0', '10', '0', '1');
INSERT INTO `round` VALUES ('443', '14', '0', '1600', '800', '3600', '1', '11', '200', '1');
INSERT INTO `round` VALUES ('444', '14', '0', '2000', '1000', '3600', '1', '12', '300', '1');
INSERT INTO `round` VALUES ('445', '14', '0', '0', '0', '900', '0', '12', '0', '1');
INSERT INTO `round` VALUES ('446', '14', '0', '3000', '1500', '3600', '1', '13', '500', '1');
INSERT INTO `round` VALUES ('447', '14', '0', '4000', '2000', '3600', '1', '14', '500', '1');
INSERT INTO `round` VALUES ('448', '14', '0', '0', '0', '900', '0', '14', '0', '1');
INSERT INTO `round` VALUES ('449', '14', '0', '5000', '2500', '3600', '1', '15', '500', '1');
INSERT INTO `round` VALUES ('450', '14', '0', '6000', '3000', '3600', '1', '16', '1000', '1');
INSERT INTO `round` VALUES ('451', '14', '0', '0', '0', '900', '0', '16', '0', '1');
INSERT INTO `round` VALUES ('452', '14', '0', '8000', '4000', '3600', '1', '17', '1000', '1');
INSERT INTO `round` VALUES ('453', '14', '0', '10000', '5000', '3600', '1', '18', '1000', '1');
INSERT INTO `round` VALUES ('454', '14', '0', '0', '0', '900', '0', '18', '0', '1');
INSERT INTO `round` VALUES ('455', '14', '0', '12000', '6000', '3600', '1', '19', '2000', '1');
INSERT INTO `round` VALUES ('456', '14', '0', '16000', '8000', '3600', '1', '20', '2000', '1');
INSERT INTO `round` VALUES ('457', '14', '0', '0', '0', '900', '0', '20', '0', '1');
INSERT INTO `round` VALUES ('458', '14', '0', '20000', '10000', '3600', '1', '21', '3000', '1');
INSERT INTO `round` VALUES ('459', '14', '0', '24000', '12000', '3600', '1', '22', '4000', '1');
INSERT INTO `round` VALUES ('460', '14', '0', '0', '0', '900', '0', '22', '0', '1');
INSERT INTO `round` VALUES ('461', '14', '0', '30000', '15000', '3600', '1', '23', '5000', '1');
INSERT INTO `round` VALUES ('462', '14', '0', '40000', '20000', '3600', '1', '24', '5000', '1');
INSERT INTO `round` VALUES ('463', '14', '0', '0', '0', '900', '0', '24', '0', '1');
INSERT INTO `round` VALUES ('464', '14', '0', '50000', '25000', '3600', '1', '25', '5000', '1');
INSERT INTO `round` VALUES ('465', '14', '0', '60000', '30000', '3600', '1', '26', '10000', '1');
INSERT INTO `round` VALUES ('466', '14', '0', '0', '0', '900', '0', '26', '0', '1');
INSERT INTO `round` VALUES ('467', '14', '0', '80000', '40000', '3600', '1', '27', '10000', '1');
INSERT INTO `round` VALUES ('468', '14', '0', '100000', '50000', '3600', '1', '28', '15000', '1');
INSERT INTO `round` VALUES ('469', '14', '0', '0', '0', '900', '0', '28', '0', '1');
INSERT INTO `round` VALUES ('470', '14', '0', '120000', '60000', '3600', '1', '29', '20000', '1');
INSERT INTO `round` VALUES ('471', '14', '0', '150000', '75000', '3600', '1', '30', '25000', '1');
INSERT INTO `round` VALUES ('472', '14', '0', '0', '0', '900', '0', '30', '0', '1');
INSERT INTO `round` VALUES ('473', '14', '0', '200000', '100000', '3600', '1', '31', '25000', '1');
INSERT INTO `round` VALUES ('474', '14', '0', '250000', '125000', '3600', '1', '32', '25000', '1');
INSERT INTO `round` VALUES ('475', '14', '0', '0', '0', '900', '0', '32', '0', '1');
INSERT INTO `round` VALUES ('476', '14', '0', '300000', '150000', '3600', '1', '33', '50000', '1');
INSERT INTO `round` VALUES ('477', '14', '0', '400000', '200000', '3600', '1', '34', '50000', '1');
INSERT INTO `round` VALUES ('478', '14', '0', '0', '0', '900', '0', '34', '0', '1');
INSERT INTO `round` VALUES ('479', '14', '0', '500000', '250000', '3600', '1', '35', '50000', '1');

-- ----------------------------
-- Table structure for roundtemplate
-- ----------------------------
DROP TABLE IF EXISTS `roundtemplate`;
CREATE TABLE `roundtemplate` (
  `ROUNDTEMPID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(90) NOT NULL,
  `SYSTYPE` tinyint(4) NOT NULL,
  `CREATETIME` datetime NOT NULL,
  PRIMARY KEY (`ROUNDTEMPID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roundtemplate
-- ----------------------------
INSERT INTO `roundtemplate` VALUES ('1', '2015WPT主赛', '1', '2015-05-20 17:32:02');
INSERT INTO `roundtemplate` VALUES ('2', '新起点锦标赛', '1', '2015-10-13 14:01:51');
INSERT INTO `roundtemplate` VALUES ('3', '无限德州快速赛', '1', '2015-10-24 22:05:05');
INSERT INTO `roundtemplate` VALUES ('4', '无限德州猎人赛', '1', '2015-10-24 22:05:45');
INSERT INTO `roundtemplate` VALUES ('5', '无限德州深筹码赛', '1', '2015-10-24 22:06:20');
INSERT INTO `roundtemplate` VALUES ('6', '巅峰赛', '1', '2015-10-24 22:06:46');
INSERT INTO `roundtemplate` VALUES ('7', '团队赛', '1', '2015-10-24 22:07:28');
INSERT INTO `roundtemplate` VALUES ('8', '无限德州全明星赛', '1', '2015-10-24 22:07:59');
INSERT INTO `roundtemplate` VALUES ('9', '无限德州美女焦点赛', '1', '2015-10-24 22:08:15');
INSERT INTO `roundtemplate` VALUES ('10', '无限德州高额快速赛', '1', '2015-10-24 22:08:58');
INSERT INTO `roundtemplate` VALUES ('11', '底池限注奥马哈（8人桌）', '1', '2015-10-24 22:09:13');
INSERT INTO `roundtemplate` VALUES ('12', '龙巡赛DAY1', '1', '2015-10-30 12:10:17');
INSERT INTO `roundtemplate` VALUES ('13', '龙巡赛DAY2', '1', '2015-10-30 12:10:37');
INSERT INTO `roundtemplate` VALUES ('14', '龙巡赛DAY3', '1', '2015-10-30 12:10:48');

-- ----------------------------
-- Table structure for screen
-- ----------------------------
DROP TABLE IF EXISTS `screen`;
CREATE TABLE `screen` (
  `DEVIMEI` varchar(90) NOT NULL,
  `COMPID` int(11) NOT NULL,
  `IP` bigint(20) NOT NULL,
  `DEVNAME` varchar(30) DEFAULT NULL,
  `DEVSTATE` tinyint(4) NOT NULL COMMENT '设备状态：0、掉线；1、在线',
  `PUSHTYPE` tinyint(4) NOT NULL COMMENT '推送类型：0、不推送；1、推送当天未结束比赛列表；2、推送计时信息；3、推送会员信息；4、推送入场安检信息',
  `COMPNAME` varchar(90) DEFAULT NULL,
  `LANGUAGE` tinyint(4) NOT NULL COMMENT '0、中文；1、英文',
  `SYSTYPE` tinyint(4) NOT NULL,
  `CREATETIME` datetime NOT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  PRIMARY KEY (`DEVIMEI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='大屏幕设备信息表';

-- ----------------------------
-- Records of screen
-- ----------------------------
INSERT INTO `screen` VALUES ('532f1df7a4f434c6', '46', '3232235829', '主厅53', '0', '2', '主赛事DAY 5', '0', '1', '2015-10-26 15:12:32', '2015-11-02 12:46:51');
INSERT INTO `screen` VALUES ('5724f8c68e582d3a', '46', '3232235832', '主厅56(安检)', '0', '2', '主赛事DAY 5', '0', '1', '2015-10-25 20:19:57', '2015-11-02 12:47:20');
INSERT INTO `screen` VALUES ('5a5254aeed35c1b0', '38', '3232235834', '慕蝶远厕所58', '0', '2', '巅峰赛DAY 3', '0', '1', '2015-10-26 17:48:33', '2015-11-02 10:23:11');
INSERT INTO `screen` VALUES ('717a66feba3b40e7', '0', '3232235837', 'Led 备用61', '0', '0', null, '0', '1', '2015-10-27 13:43:56', '2015-10-27 13:49:36');
INSERT INTO `screen` VALUES ('94cc6e18b1e1fc96', '46', '3232235836', 'Led 大屏60', '0', '2', '主赛事DAY 5', '0', '1', '2015-10-27 13:37:06', '2015-11-02 12:50:31');
INSERT INTO `screen` VALUES ('b4ec6f81452b8146', '17', '3232235835', '慕蝶靠厕所(断电)', '0', '2', '#6 无限德州猎人赛', '0', '1', '2015-10-26 17:52:34', '2015-10-29 20:08:20');
INSERT INTO `screen` VALUES ('bd5d983aec8f09bd', '47', '3232235830', '主厅54', '0', '2', '巅峰赛DAY4', '0', '1', '2015-10-26 15:14:27', '2015-11-02 17:24:13');
INSERT INTO `screen` VALUES ('d54d47e305eeacaa', '46', '3232235828', '主厅52(最左)', '0', '2', '主赛事DAY 5', '0', '1', '2015-10-26 15:29:02', '2015-11-02 12:46:45');
INSERT INTO `screen` VALUES ('ea6bacc3b514fbd', '47', '3232235831', '主厅55(最右)', '0', '2', '巅峰赛DAY4', '0', '1', '2015-10-26 15:24:14', '2015-11-03 01:06:00');
INSERT INTO `screen` VALUES ('ed91fe296de19e67', '32', '3232235838', '慕蝶靠厕所_备用(启用)62', '0', '2', '底池限注奥马哈', '0', '1', '2015-10-27 13:48:04', '2015-11-02 10:22:17');
INSERT INTO `screen` VALUES ('fc3dc987d42a05b1', '15', '3232235833', '雅蝶(进正门左边)', '0', '2', '#4 底池限注奥马哈', '0', '1', '2015-10-26 17:08:14', '2015-10-29 20:11:37');

-- ----------------------------
-- Table structure for unallotseatinfo
-- ----------------------------
DROP TABLE IF EXISTS `unallotseatinfo`;
CREATE TABLE `unallotseatinfo` (
  `TABLENO` int(11) NOT NULL,
  `SEATNO` tinyint(4) NOT NULL,
  `COMPID` int(11) NOT NULL,
  `TABLETYPE` tinyint(4) NOT NULL COMMENT '10：10人桌\r\n            9：9人桌\r\n            6：6人桌',
  `CREATETIME` bigint(20) NOT NULL,
  `LEVEL` tinyint(4) NOT NULL COMMENT '10人桌：234678为level=1;1为level=2;5为level=3;9为level=4;10为level=5\r\n            9人桌：234678为level=1;1为level=2;5为level=3;9为level=4\r\n            6人桌：2345为level=1;1为level=2;6为level=3',
  `SYSTYPE` tinyint(4) NOT NULL,
  PRIMARY KEY (`TABLENO`,`SEATNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='比赛待分配座位信息表，开启牌桌待分配的座位信息。';

-- ----------------------------
-- Records of unallotseatinfo
-- ----------------------------
