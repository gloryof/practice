import pytest

from src.user.domain.model import UserName

class TestUserName(object):

    class TestWhenBothIsSet():
        def setup_method(self):
            self.sut = UserName("テスト", "太郎")

        def test_get_full_name(self):
            assert "テスト 太郎" == self.sut.get_full_name()

    class TestExceptionCase():
        def test_when_last_name_is_none(self):
            with pytest.raises(AssertionError):
                UserName(None, "テスト")

        def test_when_last_name_is_empty(self):
            with pytest.raises(AssertionError):
                UserName("", "テスト")

        def test_when_first_name_is_empty(self):
            with pytest.raises(AssertionError):
                UserName("テスト", "")

        def test_when_first_name_is_none(self):
            with pytest.raises(AssertionError):
                UserName("テスト", None)