from typing import Optional

from pydantic import BaseModel


class Token(BaseModel):
    access_token: str
    token_type: str


class TokenData(BaseModel):
    username: Optional[str] = None


class User(BaseModel):
    username: str
    email: str
    name: str


class UserInDB(User):
    hashed_password: str

    def to_user(self):
        return User(username=self.username, email=self.email, name=self.name)


class UserCreateIn(User):
    password: str
