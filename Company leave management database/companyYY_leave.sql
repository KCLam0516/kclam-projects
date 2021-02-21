--script to create Company YY leave database
--created on 3/31/20 JM
---drop table and sequence
DROP TABLE employee_type CASCADE CONSTRAINT;
DROP TABLE branch CASCADE CONSTRAINT;
DROP TABLE department CASCADE CONSTRAINT;
DROP TABLE employee CASCADE CONSTRAINT;
DROP TABLE leave_type CASCADE CONSTRAINT;
DROP TABLE leave_report CASCADE CONSTRAINT;
DROP TABLE leave_balance CASCADE CONSTRAINT;
DROP SEQUENCE seq_emp_leave_id;
 
---create table
CREATE TABLE employee_type(  
employee_type_id VARCHAR2(3) NOT NULL,
        	role VARCHAR2(30) NOT NULL,
        	role_description VARCHAR2(255) NOT NULL,
        	CONSTRAINT empt_employee_type_id_pk PRIMARY KEY(employee_type_id)
);
 
CREATE TABLE branch (
        	branch_id VARCHAR(2) NOT NULL,
        	name VARCHAR(50) NOT NULL,
        	email VARCHAR(60) NOT NULL,
        	establish_date DATE 	NOT NULL,
        	address VARCHAR(255) NOT NULL,
        	phone_no VARCHAR(12) NOT NULL,
        	CONSTRAINT branch_branch_id_pk PRIMARY KEY(branch_id)
);
 
CREATE TABLE department (
        	dept_id VARCHAR(3) NOT NULL,
        	branch_id VARCHAR(2) NOT NULL,
        	dept_name VARCHAR(50) NOT NULL,
        	dept_total_emp INT NOT NULL,
        	CONSTRAINT dept_id PRIMARY KEY (dept_id), 
CONSTRAINT department_branch_id_fk FOREIGN KEY(branch_id) REFERENCES branch(branch_id)
);
 
CREATE TABLE employee (
employee_id VARCHAR2(8) NOT NULL, 
        	dept_id VARCHAR2(3) NOT NULL, 
        	branch_id VARCHAR2(2) NOT NULL, 
        	employee_type_id VARCHAR2(3) NOT NULL ,
        	name VARCHAR2(50) NOT NULL, 
birth_date DATE NOT NULL, 
        	gender VARCHAR2(1) NOT NULL, 
        	marriage_status VARCHAR2(10) NOT NULL, 
        	num_of_children INT NOT NULL, 
        	contact_no VARCHAR2(12) NOT NULL, 
email VARCHAR2(60) NOT NULL, 
        	nationality VARCHAR2(20) NOT NULL, 
        	nric VARCHAR2(20) NOT NULL, 
        	hired_date DATE NOT NULL, 
        	manager_id VARCHAR2(8),
        	salary NUMBER(8,2)  NOT NULL,
CONSTRAINT employee_employee_id_pk PRIMARY KEY(employee_id),
CONSTRAINT employee_dept_id_fk FOREIGN KEY(dept_id) REFERENCES department(dept_id),
CONSTRAINT employee_branch_id_fk FOREIGN KEY(branch_id) REFERENCES branch(branch_id),
CONSTRAINT employee_type_id_fk FOREIGN KEY(employee_type_id) REFERENCES employee_type(employee_type_id)
);
 
 
CREATE TABLE leave_type (
leave_id NUMBER(3) NOT NULL,
leave_type VARCHAR2(50) NOT NULL,
description VARCHAR2(255),
leave_days_per_year NUMBER(3),
max_days_carryforward NUMBER(3) NOT NULL,
CONSTRAINT leave_type_pk PRIMARY KEY(leave_id)
);
 
CREATE TABLE leave_report (
report_id NUMBER(5) NOT NULL,
employee_id VARCHAR2(6) NOT NULL,
leave_id NUMBER(3) NOT NULL,
application_status VARCHAR2(9) NOT NULL,
description VARCHAR2(255) NOT NULL,
leave_date DATE NOT NULL,
end_date DATE NOT NULL,
days_request NUMBER(3) NOT NULL,
CONSTRAINT leave_report_pk PRIMARY KEY(report_id),
CONSTRAINT leave_report_leave_id_fk FOREIGN KEY(leave_id) REFERENCES leave_type(leave_id),
CONSTRAINT leave_report_employee_id_fk FOREIGN KEY(employee_id) REFERENCES employee(employee_id)
);

CREATE TABLE leave_balance
(
emp_leave_id NUMBER(5),
employee_id VARCHAR2(6),
leave_id NUMBER(3),
total_leave_taken NUMBER(2),
leave_remain NUMBER(3),
CONSTRAINT leave_balance_emp_leave_id_pk PRIMARY KEY(emp_leave_id),
CONSTRAINT leave_balance_employee_id_fk FOREIGN KEY(employee_id) REFERENCES employee(employee_id),
CONSTRAINT leave_balance_leave_id_fk FOREIGN KEY(leave_id) REFERENCES leave_type(leave_id)
);

---create sequence
CREATE SEQUENCE seq_emp_leave_id
MINVALUE 1
START WITH 1
INCREMENT BY 1
MAXVALUE       99900
NOCACHE
NOCYCLE;
 
--- inserting records into employee_type
INSERT INTO employee_type 
VALUES ('1','Full-Time Employee','Employee that works
regularly.');
INSERT INTO employee_type 
VALUES ('2','Trainee','An employee in training.');
INSERT INTO employee_type 
VALUES ('3','Manager','The person who manage the
department.');
INSERT INTO employee_type 
VALUES ('4','Team Leader','Team leader that leads a small group of employees to finish the task in time.');
INSERT INTO employee_type 
VALUES ('5','Part-Time Employee','Employees that work a few
days a week only, no fixed working hours.');
INSERT INTO employee_type 
VALUES('6','CEO','Chief executive in charge of managing of YY company.');

--- inserting records into branch
INSERT INTO branch VALUES ('A', 'Phoenix Ipoh', 'phoenixa@gmail.com', '16-MAY-18', '237,Jalan Tai Fi Tong,30010 Ipoh Perak', '03-2335463');
INSERT INTO branch VALUES ('B', 'Phoenix Penang',
'phoenixb@gmail.com', '26-SEP-16', '136, One Precinct, 11950, Bayan Baru, 11900
Bayan Lepas, Pulau Pinang', '03-2335411');
INSERT INTO branch VALUES ('C', 'Phoenix Kuala Lumpur',
'phoenixc@gmail.com', '18-MAR-14', '257, Titiwangsa Sentral, 53000 Kuala
Lumpur', '03-2335422');
INSERT INTO branch VALUES ('D', 'Phoenix Johor',
'phoenixd@gmail.com', '07-DEC-15', '415, Jalan Tampoi 7, 81200 Johor Bahru,
Johor', '03-2335433');
INSERT INTO branch VALUES ('E', 'Phoenix Taiping',
'phoenixe@gmail.com', '30-SEP-19', '31, Jalan Medan Taiping 5, Medan Taiping,
34000 Taiping, Perak', '03-2335466');
 
--- inserting records into department
INSERT INTO department VALUES ('A1', 'A', 'Security Department', '36');
INSERT INTO department VALUES ('B1', 'B', 'IT Department', '40');
INSERT INTO department VALUES ('B2', 'B', 'Marketing Department', '40');
INSERT INTO department VALUES ('C1', 'C', 'Human Resource Department', '35');
INSERT INTO department VALUES ('C2', 'C', 'Sales Department', '110');
INSERT INTO department VALUES ('D1', 'D', 'Design Department', '42');
INSERT INTO department VALUES ('E1', 'E', 'Finance Department', '45');

--- inserting records into employee
INSERT INTO employee
VALUES('A10000','B1','B','1','Lee Jiu Zhe',date
'1990-03-25','M','SINGLE','0','012-1122335','jiuzhe90@gmail.com','Malaysia','900325-01-1234',date
'2014-03-16','A10004','5000');
INSERT INTO employee
VALUES('A10001','B1','B','2','Ooi Hong Goh',date
'2000-01-11','M','MARRIED','2','011-3216548','honggoh87@gmail.com','Malaysia','000111-02-1122',date
'2019-03-16','A10004','4500');
INSERT INTO employee
VALUES('A10002','B1','B','3','Ng Xiang Wei',date
'1991-09-27','F','SINGLE','0','012-5114489','xiangwei91@gmail.com','Korea','910927-05-5566',date
'2014-05-24','A10003','10000');
INSERT INTO employee
VALUES('A10003','B1','B','6','Lee Ting Wei',date
'1988-06-24','F','SINGLE','0','013-5266854','weiting88@gmail.com','China','880624-09-6541',date
'2013-03-16','NULL','50000');
INSERT INTO employee
VALUES('A10004','B1','B','4','Henry Tan',date
'1993-12-25','M','MARRIED','5','010-9879878',
'henry1993@gmail.com','Malaysia','931225-01-2645',date
'2017-09-10','A10002','8000');
 INSERT INTO employee
VALUES('A10005','B1','B','5','Lee Jun Yan',date
'2001-01-05','M','SINGLE','0','016-9523621','junyan95@gmail.com','Malaysia','010105-07-7063',date
'2019-01-09','A10004','2000');
INSERT INTO employee
VALUES('A10006','B1','B','1','Tan Jian Ming',date
'1985-09-20','M', 'MARRIED','3','012-9856362','jianming85@1utar.my','Malaysia','850920-06-9514',date
'2013-06-21','A10004','5000');
INSERT INTO employee
VALUES('A10007','B1','B','1','Ooi Wei Xiang',date
'1993-12-12','M','SINGLE','0','019-4825964','weixiang93@gmail.com','Malaysia','931212-03-6529',date '2018-04-28','A10004','5000');
INSERT INTO employee
VALUES('A10008','B1','B','1','Lim Yan Hui',date
'1990-08-06','F','MARRIED','1','012-4862365','yanhui90@gmail.com','Malaysia','900806-04-6084',date
'2020-11-21','A10004','5000');
INSERT INTO employee
VALUES('A20001','B2','B','1','Ng Wei Li',date
'1992-11-18','M','SINGLE','0','012-3456854','weili92@gmail.com','Malaysia','921118-03-9063',date
'2017-05-06','A20002','5000');
INSERT INTO employee
VALUES('A20002','B2','B','4','Ng Pei Feng',date
'1985-01-20','F','SINGLE','0','019-6543217','peifeng85@gmail.com','Malaysia','850120-05-0458',date
'2016-05-27','A10004','8000');
INSERT INTO employee
VALUES('A20003','B2','B','2','Tan Zong Guan',date
'1998-04-19','M','MARRIED','0','016-5289641','zongguan91@gmail.com','Thailand','9810419-05-9514',date
'2018-05-06','A20002','4500');
INSERT INTO employee
VALUES('A20004','B2','B','3','Chuah Fong Jing',date
'1972-07-25','M','SINGLE','0','014-9965874','fongjing72@gmail.com','Malaysia','720101-07-5236',date
'2011-05-13','A10003','10000');
INSERT INTO employee
VALUES('A20005','B2','B','5','Goh Zhan Hong',date
'1999-08-28','M','SINGLE','0','018-5858641','zhanhong84@gmail.com','Singapore','990828-07-9865',date
'2019-06-30','A20002','2000');
INSERT INTO employee
VALUES('A20006','B2','B','1','Tan Jia Xuan',date
'1982-02-24','M','SINGLE','0','017-5450362','jiaxuan82@gmail.com','Malaysia','820224-07-8516',date
'2019-12-01','A20002','5000');
INSERT INTO employee
VALUES('A20007','B2','B','1','Fong Xuan Zi',date
'1979-09-20','F','MARRIED','1','014-7539841','xuanzi79@gmail.com','Malaysia','790920-08-6152',date
'2018-04-01','A20002','5000');
INSERT INTO employee
VALUES('A20008','B2','B','1','Ang Quan Xing',date
'1980-08-31','M','SINGLE','0','012-5498632','quanxing80@gmail.com','Malaysia','800831-03-6621',date
'2016-02-09','A20002','5000');

--- inserting records into leave_type 
INSERT INTO leave_type VALUES('1','Annual leave(Junior)','Annual Leave for employees working less than 5 years','12','12');
INSERT INTO leave_type VALUES('2','Annual
Leave(Senior)','Annual :Leave for employees working more than 5 years','14','10');
INSERT INTO leave_type VALUES('3','Unpaid Leave','Leave
that are not paid', NULL, '0');
INSERT INTO leave_type VALUES('4','Sick Leave','Applicable when sick',null,'0');
INSERT INTO leave_type VALUES('5','Hospitalization
Leave','Applicable when hospitalized','20','0');
INSERT INTO leave_type VALUES('6','Prolong Illness
Leave','Applicable when employee is in a debilitating condition','40','0');
INSERT INTO leave_type VALUES('7','Examination
Leave','Applicable when sent out for examination','15','0');
INSERT INTO leave_type VALUES('8','Maternity
Leave','Applicable for mother absence before and after childbirth','90','0');
INSERT INTO leave_type VALUES('9','Paternity
leave','Applicable for father for time with his newborn','14','0');
INSERT INTO leave_type VALUES('10','Marriage leave','Applicable
for single status employees marriage without wage loss','4','0');
INSERT INTO leave_type VALUES('11','Childcare
Leave','applicable for working parents to look after sick child under 12 years
old','6','0');
INSERT INTO leave_type VALUES('12','Compassionate
Leave','Applicable when a family member of an employee has passed
away','4','0');
INSERT INTO leave_type VALUES('13','Miscellaneous
Leave','Applicable for leave that were not specified and categorised','4','0');

--- inserting records into leave_report  
INSERT INTO leave_report 
VALUES('1','A10007','1','APPROVED','Family trip', date '2019-12-16',date '2019-12-20','5');
INSERT INTO leave_report
VALUES('2','A10005','4','APPROVED','Fever',date '2019-12-18',date '2019-12-20','3');
INSERT INTO leave_report
VALUES('3','A10001','3','FORFEITED','Early christmas celebration',date '2019-12-24',date '2019-12-24','1');
INSERT INTO leave_report
VALUES('4','A20001','12','APPROVED','Family member passed away',date '2019-11-25',date '2019-11-27','3');
INSERT INTO leave_report
VALUES('5','A20002','10','APPROVED','Marriage',date '2020-01-06',date '2020-01-07','2');
INSERT INTO leave_report
VALUES('6','A10002','2','APPROVED','Back to home country',date '2019-12-08',date
'2019-12-12','5');
INSERT INTO leave_report
VALUES('7','A20008','3','APPROVED','Family trip',date '2020-05-21',date '2020-05-23','3');
INSERT INTO leave_report VALUES('8','A20007','7','APPROVED','Examination',date '2020-02-02',date '2020-02-08','7');
INSERT INTO leave_report
VALUES('9','A10004','11','APPROVED','Children sick',date '2020-02-25',date '2020-02-27','3');
INSERT INTO leave_report
VALUES('10','A10003','5','APPROVED','Coronavirus',date '2020-03-18',date '2020-04-06','20');
INSERT INTO leave_report VALUES('11','A20007','7','PENDING','Examination for Master',date '2020-09-02',date '2020-09-06','5');
INSERT INTO leave_report
VALUES('12','A20008','1','APPROVED', 'Family trip',date '2020-01-10',date '2020-01-21','12');

------ inserting records into leave_report  
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10000', 2, 2, 15);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10000', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10000', 7, 0, 15);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10000', 10, 0, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10000', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 2, 5, 13);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 5, 2, 18);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 7, 2, 13);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 8, 0, 90);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 10, 0, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10002', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 2, 2, 13);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 5, 20, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 7, 0, 15);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 8, 0, 90);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 10, 4, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10003', 13, 1, 3);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 1, 4, 10);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 7, 0, 15);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 9, 14, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 11, 3, 3);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10004', 13, 2, 2);

INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 2, 1, 13);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 7, 0, 15);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 9, 0, 14);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 11, 2, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10005', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10007', 1, 1, 11);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10007', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10007', 7, 0, 15);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10007', 10, 0, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A10007', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 1, 3, 11);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 7, 0, 15);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 8, 0, 90);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 11, 2, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20001', 13, 1, 3);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20002', 1, 5, 7);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20002', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20002', 7, 5, 10);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20002', 10, 4, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20002', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 1, 2, 10);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 5, 1, 19);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 7, 0, 14);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 8, 0, 90);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 10, 0, 4);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20003', 13, 1, 3);

INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 2, 0, 14);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 7, 0, 14);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 8, 0, 90);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 10, 0, 4);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20005', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20007', 1, 1, 11);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20007', 5, 0, 20);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20007', 7, 7, 8);       	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20007', 10, 0, 4);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20007', 13, 0, 4);
 
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 1, 12, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 5, 4, 16);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 7, 0, 15);     	
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 8, 90, 0);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 11, 1, 5);
INSERT INTO leave_balance VALUES
(seq_emp_leave_id.nextval, 'A20008', 13, 0, 4);

commit;
