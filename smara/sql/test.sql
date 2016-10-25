CREATE TABLE `CloudProvider` (
	`key` varchar(36) NOT NULL UNIQUE,
	`name` varchar(128) NOT NULL,
	`url` varchar(256) NOT NULL,
	PRIMARY KEY (`key`)
);

INSERT INTO CloudProvider VALUES ("5c739df5-cadb-485b-95ce-c16810984220", "Google Cloud Service", "http://gcloud.google.com");
INSERT INTO CloudProvider VALUES ("420ee88b-e0ce-4cf6-895c-8a514ca73a6f", "Azure - Microsoft Cloud Service", "http://azure.microsoft.com");
INSERT INTO CloudProvider VALUES ("cf04a934-e99d-40c3-abc5-b4d564960ffc", "AWS - Amazon Web Services", "http://aws.amazon.com");
INSERT INTO CloudProvider VALUES ("743969ae-e004-470a-bcc2-92466b9e3a13", "Digital Ocean", "http://digitalocean.com");