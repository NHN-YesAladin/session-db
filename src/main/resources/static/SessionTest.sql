CREATE TABLE `session`
(
    id varchar(100) not null primary key,
    creation_time long not null,
    last_accessed_time long not null,
    max_inactive_interval int not null,
    session_store varchar(500) not null,
    is_new boolean not null
);