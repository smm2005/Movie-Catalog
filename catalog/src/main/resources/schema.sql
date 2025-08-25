create table if not exists USERS(
    id serial primary key,
    realname    varchar(255) not null,
    username varchar(255) not null,
    email   varchar(255) not null,
    password   varchar(MAX) not null
);

create table if not exists MOVIES(
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
    