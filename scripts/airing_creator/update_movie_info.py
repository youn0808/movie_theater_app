from typing import Iterable

import arrow
import os

import psycopg2

CONNECTION_STRING = "postgresql://{}:{}@db.sceneit.linney.dev:5433/sceneit"
FETCH_MOVIES_QUERY = "SELECT movie_id from movies"

MOVIE_RANGE = range(1, 8)
THEATRE_RANGE = range(1, 8)


def main(con_string: str):
    connection = None
    try:
        connection = psycopg2.connect(con_string)
    except Exception as e:
        print(f"Error connecting to DB: {str(e)}")
        return

    for movie in MOVIE_RANGE:  # Placeholder: Avengers
        create_airings_for_movie(movie_id=movie, connection=connection, theatres=THEATRE_RANGE)


def create_airings_for_movie(movie_id: int, connection, theatres: Iterable[int]):
    curs = connection.cursor()
    START_DATE = arrow.get("2021-01-01")

    for date in (START_DATE.shift(days=n) for n in range(365)):
        for theatre in theatres:
            curs.execute(
                "INSERT INTO airings (movie_id, theatre_id, air_time) VALUES (%s, %s, %s)",
                (movie_id, theatre, date.shift(hours=12).datetime),
            )

            curs.execute(
                "INSERT INTO airings (movie_id, theatre_id, air_time) VALUES (%s, %s, %s)",
                (movie_id, theatre, date.shift(hours=18).datetime),
            )

            curs.execute(
                "INSERT INTO airings (movie_id, theatre_id, air_time) VALUES (%s, %s, %s)",
                (movie_id, theatre, date.shift(hours=22).datetime),
            )

    connection.commit()


if __name__ == "__main__":
    con_string = None
    if user := os.environ.get("DB_USER"):
        if pw := os.environ.get("DB_PW"):
            con_string = CONNECTION_STRING.format(user, pw)

    if con_string is None:
        print("Cannot authenticate to DB! Please specify $DB_USER and $DB_PW.")
        exit(1)

    main(con_string)
