DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS content;
DROP TABLE IF EXISTS content_group;

-- создать таблицу Учетная запись
CREATE TABLE account (
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	login varchar(25) DEFAULT NULL,
	password varchar(512) NOT NULL,
	admin BOOLEAN DEFAULT FALSE,
	PRIMARY KEY (id),
	CONSTRAINT l_uc UNIQUE (login));

-- create sequence HIBERNATE_SEQUENCE start with 4 CACHE NULL;

-- создать таблицу Группа единиц контента
CREATE TABLE content_group (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT n_uc UNIQUE (name));


-- создать таблицу Контент
CREATE TABLE content (
  id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(100) NOT NULL,
  author VARCHAR(60) DEFAULT NULL,
  description VARCHAR(1024) DEFAULT NULL,
  groupId BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (groupId) REFERENCES content_group (id)
    ON DELETE CASCADE ON UPDATE RESTRICT);