CREATE TABLE user(
    user_id                 varchar(20) not null,
    username                varchar(30) not null,
    name                    varchar(30),
    constraint              user_pk primary key(user_id)
);

CREATE TABLE task(
    task_id                 int not null auto_increment,
    user_id                 varchar(20),
    description             varchar(255),
    priority                int,
    due_data                date,
    constraint              task_pk primary key(task_id),
    constraint              task_fk foreign key(user_id) references user(user_id),
    constraint              chk_priority_value check (priority between 1 and 3)
);



