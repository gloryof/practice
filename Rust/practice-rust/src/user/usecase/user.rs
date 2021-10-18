use crate::base::usecase::UseCaseErrors;

pub struct CreateUserInput {
    pub name: String,
    pub birth_day: String,
    pub given_name: String,
    pub family_name: String,
}

pub struct UpdateUserInput {
    pub id: String,
    pub birth_day: String,
    pub given_name: String,
    pub family_name: String,
}

pub struct CreatedUserId {
    pub value: String,
}

pub struct UpdatedUserId {
    pub value: String,
}

pub fn create_user() -> Result<CreatedUserId, UseCaseErrors> {
    todo!();
}
