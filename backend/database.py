import os
from typing import Optional, Tuple

import psycopg2
from models.user import UserInDB


class DatabaseManager:
    def __init__(self):
        self.conn = psycopg2.connect(os.environ.get("PG_CONNECTION"))

    @classmethod
    def _user_tuple_to_user(cls, user: Tuple) -> Optional[UserInDB]:
        try:
            return UserInDB(
                username=user[0], hashed_password=user[1], name=user[2], email=user[3]
            )
        except (IndexError, TypeError):
            return None

    def get_user(self, username) -> UserInDB:
        cur = self.conn.cursor()
        cur.execute("SELECT * FROM users where username = (%s);", (username,))
        user = DatabaseManager._user_tuple_to_user(cur.fetchone())
        return user

    def create_user(self, user: UserInDB):
        cur = self.conn.cursor()
        cur.execute(
            "INSERT INTO users (username, hashed_password, name, email) VALUES (%s, %s, %s, %s);",
            (user.username, user.hashed_password, user.name, user.email),
        )
        self.conn.commit()
