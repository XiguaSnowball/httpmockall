DROP TABLE IF EXISTS `agentcheck`;
CREATE TABLE `agentcheck` (
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_code` varchar(100) NOT NULL,
  `namespace` varchar(50) NOT NULL,
  `version` varchar(30) DEFAULT NULL,
  `wcount` int(11) DEFAULT NULL,
  `rcount` int(11) DEFAULT '0',
  UNIQUE KEY `agentcheck_app_code_namespace_pk` (`app_code`,`namespace`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dycase`;
CREATE TABLE `dycase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caseid` varchar(200) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL,
  `haskey` tinyint(1) DEFAULT '0',
  `response_result` text,
  `source` varchar(45) DEFAULT NULL COMMENT 'Repeater:流量回放\\nMock:mock平台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dyenv`;
CREATE TABLE `dyenv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appcode` varchar(60) DEFAULT NULL,
  `namespace` varchar(50) DEFAULT NULL,
  `typeadd` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dyenv_appcode_uindex` (`appcode`,`namespace`,`typeadd`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dyid`;
CREATE TABLE `dyid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caseid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dyid_caseid_uindex` (`caseid`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dyparam`;
CREATE TABLE `dyparam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caseid` varchar(200) DEFAULT NULL,
  `paramkey` varchar(100) DEFAULT NULL,
  `paramvalue` varchar(300) DEFAULT NULL,
  `paramindex` varchar(21) DEFAULT NULL,
  `param_type` varchar(30) DEFAULT NULL,
  `parampath` varchar(500) DEFAULT NULL,
  `response_result` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `history_logs`;
CREATE TABLE `history_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) DEFAULT NULL COMMENT '用户',
  `department` varchar(100) DEFAULT NULL,
  `operate_type` varchar(45) DEFAULT NULL COMMENT '操作类型',
  `operate_info` longtext COMMENT '操作内容',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12488 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `httpmock`;
CREATE TABLE `httpmock` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_name` varchar(500) DEFAULT NULL COMMENT '服务名',
  `method_type` varchar(20) DEFAULT NULL COMMENT '方法类型',
  `request_path` varchar(500) DEFAULT NULL COMMENT '请求path',
  `request_query` varchar(500) DEFAULT NULL COMMENT '请求query',
  `request_body` longtext COMMENT '请求body',
  `response_body` longtext COMMENT '响应response',
  `description` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='httpmock信息';

DROP TABLE IF EXISTS `httpmock_his`;
CREATE TABLE `httpmock_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) DEFAULT NULL COMMENT '用户',
  `department` varchar(100) DEFAULT NULL,
  `method_type` varchar(45) DEFAULT NULL COMMENT '请求类型',
  `request_path` longtext COMMENT '接口路径',
  `request_body` longtext COMMENT '请求body',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4429 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `httpmock_log`;
CREATE TABLE `httpmock_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(500) DEFAULT NULL COMMENT '服务名',
  `method_type` varchar(45) DEFAULT NULL COMMENT '请求类型',
  `request_path` longtext COMMENT '接口路径',
  `request_body` longtext COMMENT '请求body',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6637 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mock_logs`;
CREATE TABLE `mock_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `env_name` varchar(100) DEFAULT NULL,
  `app_code` varchar(200) DEFAULT NULL,
  `class_name` varchar(500) DEFAULT NULL,
  `method_name` varchar(200) DEFAULT NULL,
  `mock_info` longtext,
  `return_obj` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31244 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `mockcase`;
CREATE TABLE `mockcase` (
  `id` varchar(200) NOT NULL,
  `app_code` varchar(200) DEFAULT NULL,
  `request_class` varchar(500) NOT NULL,
  `request_method` varchar(200) NOT NULL,
  `response_result` longtext NOT NULL,
  `wid` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `namespace` varchar(100) DEFAULT NULL,
  `env` varchar(100) DEFAULT NULL,
  `tp` varchar(10) DEFAULT NULL,
  `hasip` tinyint(1) DEFAULT '0',
  `hastag` tinyint(1) DEFAULT '0',
  `haskey` tinyint(1) DEFAULT '0',
  `tag` int(11) DEFAULT '0',
  `resclass` varchar(200) DEFAULT NULL,
  `lab` varchar(500) DEFAULT NULL COMMENT '标签，用于分组使用，可以设置多个标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='mock base';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `pwd` varchar(100) DEFAULT NULL COMMENT '密码',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试用户表';

