
-----------------------------------------------
--180 B Github Project(Team 2) DDL Scripts
-----------------------------------------------

drop database if exists team2project;
create database team2project;
use team2project;

CREATE TABLE user (
  user_id       integer,
  password      varchar(15),
  e_password    varchar(200),
  name          varchar(15) not null,
  email  	    varchar(50) not null,
  city          varchar(15),
  state         varchar(15),
  country       varchar(15),
  
  primary key (user_id)
);


CREATE TABLE Followers (
  user_id         integer,
  follower        integer,
  
  primary key (user_id,follower),
  foreign key (user_id) references user(user_id)
);

CREATE TABLE respository (
  repo_id         integer,
  repo_url        varchar(50) not null,
  repo_name       varchar(15) not null,
  repo_desc       varchar(50),
  repo_created_at date,
  user_id         integer,
  
  primary key (repo_id),
  unique (repo_url),
  foreign key (user_id) references user(user_id)
);

CREATE TABLE branches (
  repo_id             integer,
  branch_id           integer,
  branch_name		  varchar(15),
  
  primary key (repo_id,branch_id),
  foreign key (repo_id) references respository(repo_id),
  unique (branch_id)
);

CREATE TABLE project_files (
  repo_id			  integer,
  branch_id           integer,
  project_id          integer,
  
  primary key (repo_id,branch_id,project_id),
  foreign key (repo_id) references respository(repo_id),
  foreign key (branch_id) references branches(branch_id),
  unique (project_id)
);


CREATE TABLE project_details (
  project_id          integer,
  project_name        varchar(15) not null,
  project_created_at  date,
  
  primary key (project_id),
  unique (project_name),
  foreign key (project_id) references project_files(project_id)
);


CREATE TABLE commits (
  repo_id             integer,
  commit_id           integer,
  branch_id           integer,
  
  primary key (repo_id,commit_id,branch_id),
  foreign key (repo_id) references respository(repo_id),
  foreign key (branch_id) references branches(branch_id),
  unique (commit_id)
);

CREATE TABLE pull_requests (
  repo_id             integer,
  pullreq_id          integer,
  branch_id           integer,
  
  primary key (repo_id,pullreq_id,branch_id),
  foreign key (repo_id) references respository(repo_id),
  foreign key (branch_id) references branches(branch_id),
  unique (pullreq_id)
);


CREATE TABLE Makes (
  user_id           integer,
  repo_id           integer,
  branch_id         integer,
  commit_id         integer,
  
  primary key (user_id,repo_id,branch_id,commit_id),
  foreign key (user_id) references user(user_id),
  foreign key (repo_id) references respository(repo_id),
  foreign key (branch_id) references branches(branch_id),
  foreign key (commit_id) references commits(commit_id)
);

CREATE TABLE commit_details (
  commit_id           integer,
  comments            varchar(15),
  commit_created_at   date,
  
  primary key (commit_id),
  foreign key (commit_id) references commits(commit_id)
);



CREATE TABLE requests (
  user_id           integer,
  repo_id           integer,
  branch_id         integer,
  pullreq_id        integer,
  
  primary key (user_id,repo_id,branch_id,pullreq_id),
  foreign key (user_id) references user(user_id),
  foreign key (repo_id) references respository(repo_id),
  foreign key (branch_id) references branches(branch_id),
  foreign key (pullreq_id) references pull_requests(pullreq_id)
);


CREATE TABLE pullreq_details (
  pullreq_id         integer,
  pull_request_date  date,
  
  primary key (pullreq_id),
  foreign key (pullreq_id) references pull_requests(pullreq_id)
);
