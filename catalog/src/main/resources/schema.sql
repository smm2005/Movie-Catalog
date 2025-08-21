create table USERS(
    id serial primary key,
    realname    varchar(255) not null,
    username varchar(255) not null,
    email   varchar(255) not null,
    pass    varchar(255) not null
);

create table MOVIES(
    id  serial primary key,
    release_date    varchar(255) not null,
    title   varchar(255) not null,
    overview text not null,
    popularity  int not null,
    vote_count  int not null,
    vote_average    float not null,
    original_language   varchar(255) not null,
    genre   varchar(255) not null,
    poster_path varchar(MAX) not null
);
    