# Tasks schema

# --- !Ups

create sequence task_seq;
create table task (
  id			integer not null default nextval('task_seq'),
  label			varchar(255),
  constraint pk_task primary key (id)
);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists task_seq;

