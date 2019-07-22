CREATE TABLE user
(
	id varchar(64) NOT NULL,
	name varchar(64) NOT NULL,
	password varchar(128) NOT NULL,
	primary key(id)
);

CREATE TABLE keyword
(
	idx integer NOT NULL AUTO_INCREMENT,
	keyword varchar(128) NOT NULL,
	searchCnt integer NOT NULL,
	primary key(idx)
);

CREATE TABLE userHistory
(
	idx integer NOT NULL,
	id varchar(64) NOT NULL,
	keyword varchar(128) NOT NULL,
	searchDTime timestamp NOT NULL,
	primary key(idx)
);

insert into keyword (keyword, searchCnt) values ('우리',128),('사랑',1024),('영원',438),('토익',10878),('백종원',7878),('리액트',778),('카카오',883877),('Korea',3213);