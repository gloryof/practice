import pytest
import datetime

from src.user.domain.model import UserName, BirthDay

from dateutil.relativedelta import relativedelta

class TestUserName():

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

class TestBirthDay():

    class TestWhenSameDateToday():
        def setup_method(self):
            value = datetime.date.today() - relativedelta(years=20)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 20 == self.sut.calculate_age().value

    class TestWhenSameDateYesterday():
        def setup_method(self):
            value = datetime.date.today() - relativedelta(years=20, days=1)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 20 == self.sut.calculate_age().value

    class TestWhenSameDateTomorrow():
        def setup_method(self):
            value = datetime.date.today() - relativedelta(years=20) + relativedelta(days=1)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 19 == self.sut.calculate_age().value

    class TestWhenToday():
        def setup_method(self):
            value = datetime.date.today()
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 0 == self.sut.calculate_age().value

    class TestWhenYesterday():
        def setup_method(self):
            value = datetime.date.today() - relativedelta(days=1)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 0 == self.sut.calculate_age().value

    class TestWhenTomorrow():
        def setup_method(self):
            value = datetime.date.today() + relativedelta(days=1)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 0 == self.sut.calculate_age().value

    class TestWhenFutureOver1Year():
        def setup_method(self):
            value = datetime.date.today() + relativedelta(years=1)
            self.sut = BirthDay(value)

        def test_calculate_age(self):
            assert 0 == self.sut.calculate_age().value
