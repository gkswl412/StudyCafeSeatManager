----�̿��� ���̺�----
create table cafe_user(
phone_number varchar2(13),
email varchar2(30),
name varchar2(10) constraint cafe_user_nn not null,
id varchar2(20),
password varchar2(20),
remain_time number(9) default 0,
ox varchar2(10) constraint cafe_user_ck check(ox in ('ȸ��','��ȸ��','�Ⱓ��')),
constraint cafe_user_phone_number_pk primary key(phone_number),
constraint cafe_user_id_uk unique(id),
constraint cafe_user_email_uk unique(email)
);

----�¼� ���̺�----
create table seat(
seatno number(3) constraint seat_seatno_pk primary key,
available varchar(1) constraint seat_available_ck check(available in ('O','X')),
bookable varchar(1) constraint seat_bookable_ck check(bookable in ('O','X')),
status varchar(10) constraint seat_status_ck check(status in ('empty','occupied'))
);

----�¼� �̿� ���̺�----
create table status(
seatno number(3) constraint occupy_seatno_fk references seat(seatno),
phone_number varchar2(13) constraint occupy_phone_number_fk references cafe_user(phone_number),
name varchar2(10),
id varchar2(20) constraint occupy_id_fk references cafe_user(id),
start_time date,
remain_time number(9) default 0,
constraint occupy_pk primary key(seatno,phone_number)
);


----�¼� �̿� ��� ���̺�----
create table history(
seatno number(3),
phone_number varchar2(13),
name varchar2(10),
id varchar2(20),
start_time date,
end_time date
);

----�¼� ������ input ���ν���----
set serveroutput on
declare
begin
for n in 1..40 loop
insert into seat values(n,'O','X','empty');
end loop;
end;
/

----ȸ�� ������ input ----
insert into cafe_user values('010-1234-5678','show123@naver.com','������','gkswl412','1234','ȸ��',300);
insert into cafe_user values('010-2222-5678','fa12@hanmail.net','���','llunar2','1234','ȸ��',500);
insert into cafe_user values('010-3333-5678','ead321@google.com','ȫ�浿','gaddong412','1234','ȸ��',400);
insert into cafe_user values('010-4444-5678','hy54ss@nate.com','������','gkfn412','1234','ȸ��',100);
insert into cafe_user values('010-5555-5678','love119@naver.com','������','gkgk412','1234','ȸ��',200);
insert into cafe_user(phone_number,name,remain_time,ox) 
values('010-1111-1111','������',2320,'��ȸ��');
insert into cafe_user(phone_number,name,remain_time,ox) 
values('010-2222-2222','�߻���',3000,'��ȸ��');
insert into cafe_user(phone_number,name,remain_time,ox) 
values('010-3333-3333','��踻',3600,'��ȸ��');

------------------�ð�,��,�� ���� �������-----------------------
alter session set nls_date_format = 'yyyy-MM-dd hh24:mi:ss';
commit;

---1�� �������� �̿�ð��� �����ð� ���� Ŭ ��� status���̺��� �ش� �ο� ����---
create or replace procedure checkTime
is
begin
update cafe_user set remain_time = 0 where phone_number in 
(select phone_number from status where to_number(to_char((sysdate-start_time)*24*60*60)) > remain_time);
delete status where to_number(to_char((sysdate-start_time)*24*60*60)) > remain_time;
end;
/

---��� Ʈ����---
create or replace trigger afterDelete
after delete 
on status 
for each row
begin
insert into history values(
:old.seatno,:old.phone_number,:old.name,:old.id,:old.start_time,sysdate
);
update seat set status='empty' where seatno = :old.seatno;
end;
/

---job���---
declare
    jno number;
begin
    dbms_job.submit(:jno,'checkTime;',sysdate,'sysdate+1/1440',false);
end;
----job���� ��ɾ�---
BEGIN
 dbms_job.remove(4);
END;
/
---7���� ���� data�� history���̺��� ����---
create or replace procedure deletehistory
is
begin
delete history where (sysdate-end_time) > 7;
commit;
end;
/
---job���---
declare
    jno number;
begin
    dbms_job.submit(:jno,'deletehistory;',sysdate,'sysdate+1',false);
end;
-------------------------
