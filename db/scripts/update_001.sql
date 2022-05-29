drop table if exists candidates cascade ;
create table if not exists candidates (
                                     id serial primary key,
                                     name text,
                                     experience integer,
                                     salary integer
);