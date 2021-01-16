import datetime

from dataclasses import dataclass
from dateutil.relativedelta import relativedelta

class UserId:
    pass

@dataclass
class RegisteredUserId(UserId):
    value: int

class NotRegisteredUserId(UserId):
    pass

@dataclass
class UserName:
    lastName: str
    firstName: str

    def __post_init__(self):
        assert self.lastName is not None
        assert self.firstName is not None
        assert len(self.lastName) > 0
        assert len(self.firstName) > 0

    def get_full_name(self) -> str:
        return self.lastName + " " + self.firstName

@dataclass
class Age:
    value: int

def get_zero_age() -> int:
    return Age(0)

@dataclass
class BirthDay:
    value: datetime.date

    def calculate_age(self) -> Age:
        today = datetime.date.today()
        year = relativedelta(today, self.value).years

        if year < 0:
            return get_zero_age()
        else:
            return Age(year)

@dataclass
class User:
    userId: RegisteredUserId
    userName: UserName
    birthDay: BirthDay