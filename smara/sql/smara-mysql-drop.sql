ALTER TABLE `CloudService` DROP FOREIGN KEY `CloudService_fk0`;

ALTER TABLE `BusinessUnit` DROP FOREIGN KEY `BusinessUnit_fk0`;

ALTER TABLE `Users` DROP FOREIGN KEY `Users_fk0`;

ALTER TABLE `Instances` DROP FOREIGN KEY `Instances_fk0`;

ALTER TABLE `Instances` DROP FOREIGN KEY `Instances_fk1`;

ALTER TABLE `Instances` DROP FOREIGN KEY `Instances_fk2`;

ALTER TABLE `Projects` DROP FOREIGN KEY `Projects_fk0`;

ALTER TABLE `ServiceSubscription` DROP FOREIGN KEY `ServiceSubscription_fk0`;

ALTER TABLE `ServiceSubscription` DROP FOREIGN KEY `ServiceSubscription_fk1`;

DROP TABLE IF EXISTS `CloudProvider`;

DROP TABLE IF EXISTS `CloudService`;

DROP TABLE IF EXISTS `Organization`;

DROP TABLE IF EXISTS `BusinessUnit`;

DROP TABLE IF EXISTS `Users`;

DROP TABLE IF EXISTS `Instances`;

DROP TABLE IF EXISTS `Projects`;

DROP TABLE IF EXISTS `ServiceSubscription`;

