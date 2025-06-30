create table users
(
    studentId     varchar(50)  not null
        primary key,
    password      varchar(100) not null,
    email         varchar(50)  not null,
    name          varchar(50)  not null,
    major         varchar(50)  not null,
    student_class varchar(50)  not null,
    grade         varchar(50)  not null,
    phone         varchar(50)  not null,
    constraint email
        unique (email)
);