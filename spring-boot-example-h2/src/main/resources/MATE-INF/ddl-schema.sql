DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL DEFAULT '',
    `content` text NOT NULL,
    PRIMARY KEY (`id`)
);



