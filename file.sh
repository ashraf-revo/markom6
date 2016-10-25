#!/usr/bin/env bash
CREATE TABLE IF NOT EXISTS user (id CHAR(24) PRIMARY KEY NOT NULL,username TEXT,image TEXT);
CREATE TABLE IF NOT EXISTS message ( id CHAR(24) PRIMARY KEY NOT NULL,content TEXT,createdate CHAR(13),
from_id CHAR(24) NOT NULL,
to_id CHAR(24) NOT NULL,
FOREIGN KEY(from_id) REFERENCES user(id),FOREIGN KEY(to_id) REFERENCES user(id));