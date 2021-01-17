import pytest
import datetime

from src.user.domain.event import UserSaveEvent
from src.user.domain.model import (
    UserId,
    RegisteredUserId,
    NotRegisteredUserId,
    UserName,
    BirthDay
)

class TestUserSaveEvent:

    class TestWhenRegisteredUser:

        def setup_method(self):
            self.sut = UserSaveEvent(
                userId = RegisteredUserId(1000),
                userName = UserName("テスト姓", "テスト名"),
                birthDay = BirthDay(datetime.date(1986, 12, 16))
            )

        def test_get_type(self):
            assert UserSaveEvent.Type.Update == self.sut.get_type()

    class TestWhenNotRegisteredUser:

        def setup_method(self):
            self.sut = UserSaveEvent(
                userId = NotRegisteredUserId,
                userName = UserName("テスト姓", "テスト名"),
                birthDay = BirthDay(datetime.date(1986, 12, 16))
            )

        def test_get_type(self):
            assert UserSaveEvent.Type.Register == self.sut.get_type()