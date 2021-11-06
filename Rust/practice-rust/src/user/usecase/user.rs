use crate::user::domain::user::CreateUserEventRepository;
use crate::user::domain::user::UserIdGenerator;
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

struct CreateUserUseCase {
    genertor: Box<dyn UserIdGenerator>,
    repsitory: Box<dyn CreateUserEventRepository>
}

impl CreateUserUseCase {
    pub fn create_user() -> Result<CreatedUserId, UseCaseErrors> {
        todo!();
    }
}

#[cfg(test)]
mod test {
    mod create_user_use_case {
        
    }
}