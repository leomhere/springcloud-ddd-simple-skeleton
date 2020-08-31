CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_info` (
  `id` varchar(32) NOT NULL,
  `created_at` timestamp(6) NULL DEFAULT NULL,
  `created_user` varchar(255) DEFAULT NULL,
  `updated_at` timestamp(6) NULL DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `profile_photo` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8