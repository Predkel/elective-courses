DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `DTYPE` varchar(31),
  `id` bigint(20),
  `documentId` varchar(255),
  `firstName` varchar(255),
  `lastName` varchar(255),
  `password` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE (`documentId`)
);

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` bigint(20),
  `description` longtext,
  `title` varchar(255),
  `teacher_id` bigint(20),
  PRIMARY KEY (`id`),
  UNIQUE (`teacher_id`,`title`)
);

ALTER TABLE course ADD FOREIGN KEY (teacher_id) REFERENCES user(id);
ALTER TABLE course MODIFY teacher_id BIGINT(20) NOT NULL ;

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20)
);

DROP TABLE IF EXISTS `mark`;
CREATE TABLE `mark` (
  `id` bigint(20),
  `value` int(11),
  `course_id` bigint(20),
  `student_id` bigint(20),
  PRIMARY KEY (`id`)
);

ALTER TABLE mark ADD FOREIGN KEY (course_id) REFERENCES course(id);
ALTER TABLE mark ADD FOREIGN KEY (student_id) REFERENCES user(id);


