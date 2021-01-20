import pytest

from src.base.domain.error import (
    ErrorInfo,
    ValidationError,
    ValidationErrors
)

class TestValidationError:
    class TestParameterIsNone:
        def setup_method(self):
            self.sut = ValidationError(
                ErrorInfo.InvalidFormatDate,
                []
            )

        def test_create_message(self):
            assert "日付形式が不正です。YYYY-MM-DD形式かつ存在する日付で入力してください。" == self.sut.create_message()

    class TestParameterIsSingle:
        def setup_method(self):
            self.sut = ValidationError(
                ErrorInfo.Required,
                ["テスト項目名"]
            )

        def test_create_message(self):
            assert "テスト項目名は必須です。" == self.sut.create_message()

    class TestParameterIsMultiple:
        def setup_method(self):
            self.sut = ValidationError(
                ErrorInfo.MaxLengthOver,
                ["テスト項目名", 20]
            )

        def test_create_message(self):
            assert "テスト項目名は20文字以内で入力してください。" == self.sut.create_message()

class TestValidationErrors:
    class TestWhenEmpty:
        def setup_method(self):
            self.sut = ValidationErrors()

        def test_has_error(self):
            assert self.sut.has_error() == False

        def test_to_list(self):
            assert len(self.sut.to_list()) == 0

    class TestWhenAddRequired:
        def setup_method(self):
            self.sut = ValidationErrors()
            self.sut.add_required_error("テスト項目")

        def test_has_error(self):
            assert self.sut.has_error() == True

        def test_to_list(self):
            actual = self.sut.to_list()
            assert len(actual) == 1

            actualError = actual[0]
            assert "テスト項目は必須です。" == actualError.create_message()

    class TestWhenAddMaxLength:
        def setup_method(self):
            self.sut = ValidationErrors()
            self.sut.add_max_length("テスト項目", 1000)

        def test_has_error(self):
            assert self.sut.has_error() == True

        def test_to_list(self):
            actual = self.sut.to_list()
            assert len(actual) == 1

            actualError = actual[0]
            assert "テスト項目は1,000文字以内で入力してください。" == actualError.create_message()

    class TestWhenInvalidFormatDate:
        def setup_method(self):
            self.sut = ValidationErrors()
            self.sut.add_invalid_format_date("テスト項目")

        def test_has_error(self):
            assert self.sut.has_error() == True

        def test_to_list(self):
            actual = self.sut.to_list()
            assert len(actual) == 1

            actualError = actual[0]
            assert "日付形式が不正です。YYYY-MM-DD形式かつ存在する日付で入力してください。" == actualError.create_message()

    class TestWhenNotExistUser:
        def setup_method(self):
            self.sut = ValidationErrors()
            self.sut.add_not_exist_user()

        def test_has_error(self):
            assert self.sut.has_error() == True

        def test_to_list(self):
            actual = self.sut.to_list()
            assert len(actual) == 1

            actualError = actual[0]
            assert "ユーザが存在しません。" == actualError.create_message()