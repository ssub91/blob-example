CREATE TABLE IMAGE_TABLE
(
	NO		NUMBER(10) PRIMARY KEY NOT NULL,
	DATA	BLOB
);

CREATE SEQUENCE IMAGE_TABLE_SEQ
START WITH 1 
INCREMENT BY 1 
MAXVALUE 9999999999;