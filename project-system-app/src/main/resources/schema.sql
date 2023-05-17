create table project
(
    id          bigint primary key generated by default as identity,
    description text,
    name        text not null,
    status      text not null
);

create table team
(
    id         bigint primary key generated by default as identity,
    project_id bigint not null,
    foreign key (project_id) references project (id)
);

create table member
(
    id         bigint primary key generated by default as identity,
    account    text unique,
    email      text unique,
    firstname  text not null,
    lastname   text not null,
    middleName text,
    position   text,
    status     text not null
);

create table team_membership
(
    team_id   bigint not null,
    member_id bigint not null,
    role      text   not null,
    foreign key (team_id) references team,
    foreign key (member_id) references member,
    primary key (team_id, member_id)
);

create table task
(
    id                 bigint primary key generated by default as identity,
    complexity         int         not null,
    creation_date      timestamptz not null,
    deadline           timestamptz not null,
    description        text,
    last_modified_date timestamptz,
    name               text        not null,
    status             text        not null,
    asignee_id         bigint references member (id),
    author_id          bigint references member (id)
);

insert into member (account, email, firstname, lastname, middleName, position, status)
values ('ivanov.ivan', 'ivanov.ivan@example.com', 'Ivan', 'Ivanov', NULL, 'tester', 'active'),
       ('roman.12', 'roman.12@example.com', 'Roman', 'Smith', Null, 'developer', 'active'),
       ('germandd', 'german@example.com', 'German', 'bboy', 'B.', 'analyst', 'active');

insert into project (description, name, status)
values ('This is project 1', 'project 1', 'draft'),
       ('This is project 2', 'project 2', 'in_develop'),
       ('This is project 3', 'project 3', 'completed');

insert into team(project_id)
values (1),
       (2);

select *
from member;

insert into team_membership(team_id, member_id, role)
values (1, 1, 'tester'),
       (1, 2, 'dev'),
       (1, 3, 'dev'),
       (2, 1, 'tester'),
       (2, 3, 'dev');

select *
from team_membership;

insert into task (complexity, creation_date, deadline, description, last_modified_date, name, status, asignee_id,
                  author_id)
values (3, CURRENT_TIMESTAMP, '2023-05-20 12:00:00+00', 'Task 1 description', NULL, 'Task 1', 'new', 1, 2),
       (2, CURRENT_TIMESTAMP, '2023-05-22 15:30:00+00', 'Task 2 description', NULL, 'Task 2', 'InProgress', 2, 1),
       (1, CURRENT_TIMESTAMP, '2023-05-25 10:00:00+00', 'Task 3 description', NULL, 'Task 3', 'closed', 3, 3);

SELECT m.id, m.lastname, m.firstname, m.middleName, m.position, m.account, m.email, m.status
FROM team_membership tm left join member m ON tm.member_id = m.id
where 1=1 and m.email='ivanov.ivan@example.com';


