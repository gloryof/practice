from enum import Enum
from dataclasses import dataclass

from src.user.domain.model import (
    UserId,
    RegisteredUserId,
    UserName,
    BirthDay
)

@dataclass
class UserSaveEvent:
    userId: UserId
    userName: UserName
    birthDay: BirthDay

    class Type(Enum):
        Update = 1
        Register = 2

    def get_type(self) -> Type:
        if type(self.userId) is RegisteredUserId:
            return UserSaveEvent.Type.Update
        else:
            return UserSaveEvent.Type.Register