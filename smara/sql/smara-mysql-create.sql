CREATE TABLE `CloudProvider` (
	`key` varchar(36) NOT NULL UNIQUE,
	`name` varchar(128) NOT NULL,
	`url` varchar(256) NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `CloudService` (
	`key` varchar(36) NOT NULL  UNIQUE,
	`name` varchar(128) NOT NULL UNIQUE,
	`url` varchar(256) NOT NULL UNIQUE,
	`fk_CloudProvider` varchar(36) NOT NULL,
	`status` bool NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `Organization` (
	`key` varchar(36) NOT NULL UNIQUE,
	`name` varchar(128) NOT NULL,
	`url` varchar(256) NOT NULL,
	`contact` varchar(256) NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `BusinessUnit` (
	`key` varchar(36) NOT NULL,
	`name` varchar(128) NOT NULL ,
	`tags` TEXT,
	`contact` varchar(256),
	`fk_Organization` varchar(36) NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `Users` (
	`email` varchar(256) NOT NULL,
	`fk_BusinessUnit` varchar(256) NOT NULL,
	PRIMARY KEY (`email`)
);

CREATE TABLE `Instances` (
	`key` varchar(36) NOT NULL,
	`name` varchar(128) NOT NULL,
	`fk_CloudService` varchar(36) NOT NULL,
	`fk_BusinessUnit` varchar(36) NOT NULL,
	`fk_User` varchar(256) NOT NULL,
	`data` TEXT NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `Projects` (
	`key` varchar(36) NOT NULL,
	`fk_BusinessUnit` varchar(36) NOT NULL,
	`tags` TEXT NOT NULL,
	`name` varchar(128) NOT NULL,
	PRIMARY KEY (`key`)
);

CREATE TABLE `ServiceSubscription` (
	`key` varchar(36) NOT NULL,
	`fk_BusinessUnit` varchar(36) NOT NULL,
	`fk_CloudService` varchar(36) NOT NULL,
	PRIMARY KEY (`key`)
);

ALTER TABLE `CloudService` ADD CONSTRAINT `CloudService_fk0` FOREIGN KEY (`fk_CloudProvider`) REFERENCES `CloudProvider`(`key`);

ALTER TABLE `BusinessUnit` ADD CONSTRAINT `BusinessUnit_fk0` FOREIGN KEY (`fk_Organization`) REFERENCES `Organization`(`key`);

ALTER TABLE `Users` ADD CONSTRAINT `Users_fk0` FOREIGN KEY (`fk_BusinessUnit`) REFERENCES `BusinessUnit`(`key`);

ALTER TABLE `Instances` ADD CONSTRAINT `Instances_fk0` FOREIGN KEY (`fk_CloudService`) REFERENCES `CloudService`(`key`);

ALTER TABLE `Instances` ADD CONSTRAINT `Instances_fk1` FOREIGN KEY (`fk_BusinessUnit`) REFERENCES `BusinessUnit`(`key`);

ALTER TABLE `Instances` ADD CONSTRAINT `Instances_fk2` FOREIGN KEY (`fk_User`) REFERENCES `Users`(`email`);

ALTER TABLE `Projects` ADD CONSTRAINT `Projects_fk0` FOREIGN KEY (`fk_BusinessUnit`) REFERENCES `BusinessUnit`(`key`);

ALTER TABLE `ServiceSubscription` ADD CONSTRAINT `ServiceSubscription_fk0` FOREIGN KEY (`fk_BusinessUnit`) REFERENCES `BusinessUnit`(`key`);

ALTER TABLE `ServiceSubscription` ADD CONSTRAINT `ServiceSubscription_fk1` FOREIGN KEY (`fk_CloudService`) REFERENCES `CloudService`(`key`);

