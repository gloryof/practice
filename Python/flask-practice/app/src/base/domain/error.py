from dataclasses import dataclass
from enum import Enum

class ErrorInfo(Enum):
    Required = "{0[0]}は必須です。"
    MaxLengthOver = "{0[0]}は{0[1]:,}文字以内で入力してください。"
    InvalidFormatDate = "日付形式が不正です。YYYY-MM-DD形式かつ存在する日付で入力してください。"
    NotExistUser = "ユーザが存在しません。"

@dataclass(frozen=True)
class ValidationError:
    errorInfo: ErrorInfo
    messageParam: list[object]

    def create_message(self) -> str:
        return self.errorInfo.value.format(self.messageParam)

class ValidationErrors:

    def __init__(self):
        self.__errors = list()

    def add_required_error(self, item_name: str):
        self.__errors.append(
            ValidationError(
                errorInfo = ErrorInfo.Required,
                messageParam = [item_name]
            )
        )

    def add_max_length(self, item_name: str, length: int):
        self.__errors.append(
            ValidationError(
                errorInfo = ErrorInfo.MaxLengthOver,
                messageParam = [item_name, length]
            )
        )

    def add_invalid_format_date(self, item_name: str):
        self.__errors.append(
            ValidationError(
                errorInfo = ErrorInfo.InvalidFormatDate,
                messageParam = [item_name]
            )
        )

    def add_not_exist_user(self):
        self.__errors.append(
            ValidationError(
                errorInfo = ErrorInfo.NotExistUser,
                messageParam = []
            )
        )

    def has_error(self) -> bool:
        return 0 < len(self.__errors)

    def to_list(self) -> list[ValidationError]:
        return self.__errors