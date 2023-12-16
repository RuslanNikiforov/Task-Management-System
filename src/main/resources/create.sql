drop table if exists tasks cascade;
drop table if exists users cascade;
drop table if exists tasks_executors;
drop table if exists comments;
create table comments
(
    id      bigserial not null,
    task_id bigint,
    user_id bigint,
    time_sent timestamp,
    text    varchar(255),
    primary key (id)
);

create table tasks
(
    author_id   bigint,
    id          bigserial,
    description varchar(255),
    header      varchar(255),
    priority    varchar(255) check (priority in ('LOW', 'MEDIUM', 'HIGH')) default 'MEDIUM',
    status      varchar(255) check (status in ('WAITING', 'IN_PROGRESS', 'FINISHED')) default 'WAITING',
    primary key (id)
);
create table tasks_executors
(
    executors_id bigint not null,
    task_id      bigint not null
);
create table users
(
    id       bigserial not null,
    email    varchar(255),
    name     varchar(255),
    password varchar(255),
    primary key (id)
);
create unique index uniqie_email on users(email);
alter table if exists comments
    add constraint FKi7pp0331nbiwd2844kg78kfwc foreign key (task_id) references tasks on delete cascade;
alter table if exists comments
    add constraint FK8omq0tc18jd43bu5tjh6jvraq foreign key (user_id) references users;
alter table if exists tasks
    add constraint FKhods8r8oyyx7tuj3c91ki2sk1 foreign key (author_id) references users;
alter table if exists tasks_executors
    add constraint FKs0qbyoewi5uq71fudyx57cueo foreign key (executors_id) references users;
