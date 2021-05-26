create table school(
id_school INT(6) unsigned auto_increment primary key,
name varchar(50) not null unique
);

create table program (
id_program INT(6) unsigned auto_increment primary key,
id_school INT(6) unsigned not null,
name varchar(50) not null,
number_years int(6),
foreign key (id_school) references school(id_school) on delete cascade on update cascade
);

create table subject (
id_subject INT(6) unsigned auto_increment primary key,
id_school INT(6) unsigned not null,
name varchar(50) not null,
domain varchar(50),
foreign key (id_school) references school(id_school) on delete cascade on update cascade
);

-- create table catalogue (
-- id_class INT(6) unsigned not null,
-- id_grade INT(6) unsigned,
-- id_absence INT(6) unsigned,
-- foreign key (id_class) references class(id_class),
-- foreign key (id_grade) references grade(id_grade),
-- foreign key (id_absence) references absence(id_absence)
-- );

-- drop table catalogue;

create table class (
id_class INT(6) unsigned auto_increment primary key,
id_school INT(6) unsigned not null,
id_program INT(6) unsigned not null,
year varchar(10) not null,
year_period varchar(30) not null,
letter varchar(10) not null,
foreign key (id_school) references school(id_school) on delete cascade on update cascade,
foreign key (id_program) references program(id_program) on delete cascade on update cascade
);

-- alter table class add id_school INT(6) unsigned not null;
-- alter table class add constraint foreign key (id_school) references school(id_school) on delete cascade on update cascade;

-- create table class_students (
-- id_class INT(6) unsigned not null,
-- pid_student varchar(50) not null,
-- foreign key (id_class) references class(id_class),
-- foreign key (pid_student) references student(pid_ student),
-- unique (id_class, pid_student)
-- );
-- drop table class_students;

-- drop table class_subjects;

create table student (
pid_student varchar(50) primary key,
id_school INT(6) unsigned not null,
id_class INT(6) unsigned not null,
first_name varchar(30) not null,
last_name varchar(30) not null,
birth_date datetime,
foreign key (id_school) references school(id_school) on delete cascade on update cascade,
foreign key (id_class) references class(id_class) on delete cascade on update cascade
);

create table teacher (
pid_teacher varchar(50) primary key,
id_school INT(6) unsigned not null,
first_name varchar(30) not null,
last_name varchar(30) not null,
birth_date datetime,
foreign key (id_school) references school(id_school) on delete cascade on update cascade
);

create table class_subjects (
id_class INT(6) unsigned not null,
id_subject INT(6) unsigned not null,
pid_teacher varchar(50) not null,
foreign key (id_class) references class(id_class) on delete cascade on update cascade,
foreign key (pid_teacher) references teacher(pid_teacher) on delete cascade on update cascade,
foreign key (id_subject) references subject(id_subject) on delete cascade on update cascade,
unique (id_class, pid_teacher, id_subject)
);

-- create table teacher_classes (
-- pid_teacher varchar(50) not null,
-- id_class INT(6) unsigned not null,
-- foreign key (pid_teacher) references teacher(pid_teacher),
-- foreign key (id_class) references class(id_class),
-- unique(pid_teacher, id_class)
-- );
-- drop table teacher_classes;

create table absence (
id_absence INT(6) unsigned auto_increment primary key,
pid_student varchar(50) not null,
pid_teacher varchar(50),
id_subject INT(6) unsigned not null,
id_class INT(6) unsigned not null,
date datetime,
motivated bit,
foreign key (pid_student) references student(pid_student),
foreign key (pid_teacher) references teacher(pid_teacher),
foreign key (id_subject) references subject(id_subject),
foreign key (id_class) references class(id_class)
);

create table grade (
id_grade INT(6) unsigned auto_increment primary key,
pid_student varchar(50) not null,
pid_teacher varchar(50),
id_subject INT(6) unsigned not null,
id_class INT(6) unsigned not null,
score decimal(6,3) not null,
date datetime,
evaluation_method varchar(100),
foreign key (pid_student) references student(pid_student), 
foreign key (pid_teacher) references teacher(pid_teacher),
foreign key (id_subject) references subject(id_subject),
foreign key (id_class) references class(id_class)
);
-- drop table absence;
-- alter table absence add constraint foreign key (id_class) references class(id_class);
-- alter table grade add constraint foreign key (id_class) references class(id_class);