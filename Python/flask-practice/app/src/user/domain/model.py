from dataclasses import dataclass

@dataclass
class UserName:
    lastName: str
    firstName: str

    def __post_init__(self):
        assert self.lastName is not None
        assert self.firstName is not None
        assert len(self.lastName) > 0
        assert len(self.firstName) > 0

    def get_full_name(self):
        return self.lastName + " " + self.firstName