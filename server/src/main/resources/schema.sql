create table if not exists USERS(
    id serial primary key,
    realname    varchar(255) not null,
    username varchar(255) not null,
    email   varchar(255) not null,
    password   varchar(MAX) not null
);

create table if not exists MOVIES(
    movies_id  serial primary key,
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
    user_id bigint not null,
    movie_id bigint not null,
    created timestamp not null,
    constraint fk_user_id foreign key (user_id) references USERS(id),
    constraint fk_movie_id foreign key (movie_id) references MOVIES(movies_id)
);

create table if not exists TOKENS(
    token_id bigint not null,
    token varchar(1000) not null,
    expiry timestamp not null,
    userId bigint not null,
    revoked bit
)
    