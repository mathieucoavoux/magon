CREATE SCHEMA `provider` ;

CREATE TABLE `provider`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

  
  
 insert into provider.book (title) values('cambodia');