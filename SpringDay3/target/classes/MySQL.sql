CREATE TABLE BOARD(
	IDX INT NOT NULL AUTO_INCREMENT,
	TITLE VARCHAR(100) NOT NULL,
	CONTENT VARCHAR(2000) NOT NULL,
	WRITER VARCHAR(30) NOT NULL,
	INDATE DATETIME DEFAULT NOW(),
	COUNT INT DEFAULT 0,
	PRIMARY KEY(IDX)
);

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('100억 부자 유병재 vs 100억 빚 차은우','차은우!','???');
INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('1000억 부자 유병재 vs 100억 빚 차은우','차은우!','???');
INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('1조 부자 유병재 vs 100억 빚 차은우','차은우!','???');
INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('10조 부자 유병재 vs 100억 빚 차은우','차은우!','???');
INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('100조 부자 유병재 vs 100억 빚 차은우','차은우!','???');

SELECT * FROM BOARD;

-- member 테이블
create table member(
	memIdx int auto_increment,
	memId varchar(20) not null,
	memPassword varchar(20) not null,
	memName varchar(20) not null,
	memAge int,
	memGender varchar(20),
	memEmail varchar(50),
	memProfile varchar(50),
	primary key(memIdx)
);

-- test 데이터 넣기
insert into member(memId, memPassword, memName, memAge, memGender, memEmail)
values('admin', '1234', '관리자', 20, '여자', 'admin@gmail.com');
insert into member(memId, memPassword, memName, memAge, memGender, memEmail)
values('smart', '1234', '스마트', 30, '남자', 'smart@gmail.com');
insert into member(memId, memPassword, memName, memAge, memGender, memEmail)
values('juhee', '1111', '이주희', 19, '여자', 'juhee@gmail.com');

-- 조회하기
select * from member;

select * from member where memId='admin';









