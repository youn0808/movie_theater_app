/* Function for a setting updated_at column. Used by movie updater */
CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;


/* Theatre Table */
create table theatres
(
    theatre_id serial not null,
    name varchar not null,
    location varchar not null
);

create unique index theatres_theatre_id_uindex
	on theatres (theatre_id);

alter table theatres
    add constraint theatres_pk
        primary key (theatre_id);

/* Movies Table */
create table movies
(
    movie_id serial not null,
    title varchar not null,
    description varchar not null,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    tmdb_id int not null,
    rating int not null,
    poster_url varchar not null,
    genres varchar[] not null,
);

/* Register Trigger for updating timestamps */
CREATE TRIGGER set_timestamp
BEFORE UPDATE ON movies
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

create unique index movies_movie_id_uindex
	on movies (movie_id);

alter table movies
    add constraint movies_pk
        primary key (movie_id);

/* Airings Table */
create table airings
(
    airings_id serial not null,
    movie_id int not null
        constraint airings_movies_movie_id_fk
            references movies,
    theatre_id int not null
        constraint airings_theatres_theatre_id_fk
            references theatres,
    air_time timestamp not null,
    total_seats int default 100 not null,
    available_seats int default 100 not null
);

create index airings_air_time_index
	on airings (air_time);

create unique index airings_airings_id_uindex
	on airings (airings_id);

alter table airings
    add constraint airings_pk
        primary key (airings_id);

/* Users Table */
create table users (
    username varchar unique not null primary key,
    hashed_password varchar not null,
    name varchar not null,
    email varchar not null
)
