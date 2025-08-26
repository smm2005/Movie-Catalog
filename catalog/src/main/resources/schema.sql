create table if not exists USERS(
    id serial primary key,
    realname    varchar(255) not null,
    username varchar(255) not null,
    email   varchar(255) not null,
    password   varchar(MAX) not null
);

create table if not exists MOVIES(
    user_id  serial primary key,
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

create table if not exists FAVOURITES(
    favourite_id serial primary key,
    movie_id bigint not null,
    created_at timestamp not null,
    constraint fk_movie_id foreign key (movie_id) references MOVIES(user_id)
);
    