drop table User_table;
create table User_table
( 
  user_id number(38) not null,
  name varchar2(100),
  username varchar2(100) not null unique,
  password varchar2(100) not null,
  email varchar2(100) not null unique,
  date_of_birth date,
  CONSTRAINT User_pk PRIMARY KEY (user_id)
);
  drop table Memory;
  create table Memory
  (
    memory_id number(38) not null,
    title varchar2(50) not null,
    description varchar2(1000) not null,
    memory_location varchar2(50) ,
    dateM date not null,
    privacy varchar2(20),
    CONSTRAINT Memory_pk PRIMARY KEY (memory_id)
    );
  
  drop table Timeline;
  create table Timeline
  ( 
    memory_id number(38) not null,
    user_id number(38) not null,
     CONSTRAINT Memory_fk FOREIGN KEY (memory_id)
         REFERENCES Memory(memory_id),
      CONSTRAINT User_fk FOREIGN KEY (user_id)
         REFERENCES User_table(user_id),
      CONSTRAINT Timeline_pair unique (memory_id,user_id)
  );
  drop table Relationship;
  create table Relationship(
  
    user1 number(38) not null,
    user2 number(38) not null,
     CONSTRAINT User1_fk FOREIGN KEY (user1)
         REFERENCES User_table(user_id),
      CONSTRAINT User2_fk FOREIGN KEY (user2)
         REFERENCES User_table(user_id),
      CONSTRAINT friend_pair unique (user1,user2)
  );

drop table Notification;
create table Notification
(
  notification_id number not null,
  user_id number(38) not null,
  dateN date not null,
  notificationText varchar2(100) not null,
  CONSTRAINT User3_fk FOREIGN KEY (user_id)
         REFERENCES User_table(user_id),
  CONSTRAINT notification_pk PRIMARY KEY (notification_id)
);
drop table Media;
create table Media(
  media_id number(38) not null,
  media_type varchar2(30) not null,
  memory_id number(38) not null,
  media_path varchar2(500) not null,
  CONSTRAINT Media_pk PRIMARY KEY (media_id),
  CONSTRAINT Memory2_fk FOREIGN KEY (memory_id)
         REFERENCES Memory(memory_id)
);
drop  table Comment_table;
create table Comment_table(
  comment_id number(38) not null,
  user_id number(38) not null,
  memory_id number(38) not null,
  comment_text varchar(100) not null,
  comment_date date,
  CONSTRAINT Comment_pk PRIMARY KEY (comment_id),
  CONSTRAINT Memory4_fk FOREIGN KEY (memory_id)
         REFERENCES Memory(memory_id),
   CONSTRAINT user4_fk FOREIGN KEY (user_id)
         REFERENCES User_table(user_id)      
);
drop table Tag;
create table Tag(
  tag_name varchar2(30) not null unique,
  tag_id number(38) not null,
  CONSTRAINT Tag_pk PRIMARY KEY (tag_id)
);
drop table Tag_Memory;
create table Tag_Memory(
  tag_id number(38) not null,
  memory_id number(38) not null,
  CONSTRAINT Memory3_fk FOREIGN KEY (memory_id)
         REFERENCES Memory(memory_id),
   CONSTRAINT Tag_fk FOREIGN KEY (tag_id)
         REFERENCES Tag(tag_id),
    CONSTRAINT Tag_pair unique (tag_id,memory_id)
);

create database link Student_link 
connect to STUDENT
identified by STUDENT 
using '(DESCRIPTION = 
(ADDRESS_LIST = 
(ADDRESS =(PROTOCOL = TCP) 
(HOST = localhost) 
(PORT = 1521))) 
(CONNECT_DATA = (SID = xe )))' ;

set SERVEROUTPUT ON;

declare
CURSOR lista_prenume  IS
       SELECT  substr(name, 0, instr(name, ' ')-1) FROM users@Student_link;
CURSOR lista_nume  IS
       SELECT  substr(name, instr(name, ' ')+1, length(name)) FROM users@Student_link;
v_prenume VARCHAR2(30);
v_nume VARCHAR2(30);
v_id NUMBER(38) := 0;
v_username VARCHAR2(100) := '';
v_password VARCHAR2(100);
v_email VARCHAR2(100);
v_data date;
v_allname VARCHAR2(100);
begin

OPEN lista_nume;
    LOOP
        FETCH lista_nume INTO v_nume;
        EXIT WHEN lista_nume%NOTFOUND;
    OPEN lista_prenume;
    LOOP
        FETCH lista_prenume INTO v_prenume;
        EXIT WHEN lista_prenume%NOTFOUND;
        v_username := lower(v_prenume)||'.'||lower(v_nume)||v_id;
        v_password := dbms_random.string('L',10);
        v_email := lower(v_nume)||v_id||'@info.uaic.ro';
        v_data := TO_DATE(TRUNC(DBMS_RANDOM.value(TO_CHAR(date '1920-01-01','J'),TO_CHAR(date '1999-12-30','J'))),'J');
        v_allname := v_nume||' '||v_prenume;
 
        INSERT INTO USER_TABLE 
        (
        USER_ID,
        NAME,
        USERNAME,
        PASSWORD,
        EMAIL,
        DATE_OF_BIRTH
        )
        VALUES
        (
        v_id,
        v_allname,
        v_username,
        v_password,
        v_email,
        v_data
        );
        v_id := v_id +1; 
    END LOOP;
CLOSE lista_prenume;    
    END LOOP;
CLOSE lista_nume;
end;


load txt
INFILE 'cuvinte.dat'
INTO TABLE tag
APPEND
FIELDS TERMINATED BY ','
(tag_id SEQUENCE (MAX,1),
 tag_name VARCHAR2(30),
 text LOBFILE(ext_fname) TERMINATED BY EOF)
