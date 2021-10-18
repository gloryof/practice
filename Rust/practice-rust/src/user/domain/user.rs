use crate::base::domain::SpecError;
use crate::base::domain::SpecErrors;
use chrono::Datelike;
use chrono::NaiveDate;
use unicode_segmentation::UnicodeSegmentation;


pub struct User {
    pub id: UserId,
    pub name: Name,
    pub birth_day: NaiveDate,
}

pub struct Name {
    pub given_name: String,
    pub family_name: String,
}

pub struct UserId {
    pub value: String,
}

pub struct CreateUserEvent {
    pub birth_day: NaiveDate,
    pub given_name: String,
    pub family_name: String,
}

pub struct UpdateUserEvent {
    pub id: String,
    pub birth_day: NaiveDate,
    pub given_name: String,
    pub family_name: String,
}

pub struct DeleteUserEvent {
    pub id: String,
}

impl User {
    pub fn calculate_age(&self, today: NaiveDate) -> i32 {
        let base_age = today.year() - self.birth_day.year();

        if self.birth_day.ordinal() < today.ordinal() {
            return base_age - 1;
        }

        return base_age;
    }
}

pub fn validate_create_user(event: CreateUserEvent) -> SpecErrors {
    let mut errors = SpecErrors::new();

    errors.add_error(validate_family_name(event.family_name));
    errors.add_error(validate_given_name(event.given_name));

    return errors;
}

fn validate_family_name(family_name: String) -> SpecError {
    let mut error = SpecError::new(String::from("family_name"));
    let max_length = 50;

    if family_name.is_empty() {
        error.add_required();
    }

    if family_name.graphemes(true).count() > max_length {
        error.add_max_length(max_length)
    }

    return error;
}

fn validate_given_name(given_name: String) -> SpecError {
    let mut error = SpecError::new(String::from("given_name"));
    let max_length = 50;

    if given_name.is_empty() {
        error.add_required();
    }

    if given_name.graphemes(true).count() > max_length {
        error.add_max_length(max_length)
    }

    return error;
}

#[cfg(test)]
mod test {
    mod user {
        mod calculate_age {
            use crate::user::domain::user::Name;
            use crate::user::domain::user::User;
            use crate::user::domain::user::UserId;
            use chrono::NaiveDate;
            #[test]
            fn when_birth_day_is_same_day() {
                let today = NaiveDate::from_ymd(2020, 2, 29);
                let user = User {
                    id: UserId {
                        value: String::from("test-user-id"),
                    },
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name"),
                    },
                    birth_day: today,
                };
                let actual = user.calculate_age(today);
                assert_eq!(0, actual);
            }
            #[test]
            fn when_birth_day_is_1year_before_1days() {
                let user = User {
                    id: UserId {
                        value: String::from("test-user-id"),
                    },
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name"),
                    },
                    birth_day: NaiveDate::from_ymd(2020, 2, 29),
                };
                let actual = user.calculate_age(NaiveDate::from_ymd(2020, 2, 28));
                assert_eq!(0, actual);
            }
            #[test]
            fn when_birth_day_is_after_1year() {
                let user = User {
                    id: UserId {
                        value: String::from("test-user-id"),
                    },
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name"),
                    },
                    birth_day: NaiveDate::from_ymd(2019, 2, 28),
                };
                let actual = user.calculate_age(NaiveDate::from_ymd(2020, 2, 28));
                assert_eq!(1, actual);
            }
        }
    }

    mod validate_create_user {
        use crate::base::domain::SpecErrorType;
        use crate::user::domain::user::validate_create_user;
        use crate::user::domain::user::CreateUserEvent;
        use chrono::NaiveDate;
        #[test]
        fn success() {
            let event = CreateUserEvent {
                birth_day: NaiveDate::from_ymd(1986, 12, 16),
                given_name: String::from("test-given-name𠮷７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０"),
                family_name: String::from("test-family-name𠮷８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０"),
            };

            let actual = validate_create_user(event);
            assert_eq!(false, actual.has_error())
        }
        #[test]
        fn return_error_when_given_name_is_empty() {
            let event = CreateUserEvent {
                birth_day: NaiveDate::from_ymd(1986, 12, 16),
                given_name: String::from(""),
                family_name: String::from("test-family-name"),
            };

            let actual = validate_create_user(event);
            assert_eq!(true, actual.has_error());

            let actual_error = &actual.errors[0];
            assert_eq!("given_name", actual_error.item_name);

            let actual_error_details = &actual_error.details;
            assert_eq!(1, actual_error_details.len());

            let actual_error_details = &actual_error_details[0];
            assert_eq!(SpecErrorType::Required, actual_error_details.error_type);
        }
        #[test]
        fn return_error_when_given_name_is_over_50count() {
            let event = CreateUserEvent {
                birth_day: NaiveDate::from_ymd(1986, 12, 16),
                given_name: String::from("test-given-name𠮷７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０×"),
                family_name: String::from("test-family-name"),
            };

            let actual = validate_create_user(event);
            assert_eq!(true, actual.has_error());

            let actual_error = &actual.errors[0];
            assert_eq!("given_name", actual_error.item_name);

            let actual_error_details = &actual_error.details;
            assert_eq!(1, actual_error_details.len());

            let actual_error_details = &actual_error_details[0];
            assert_eq!(SpecErrorType::MaxLength, actual_error_details.error_type);

            let acutal_attributes = &actual_error_details.attributes;
            assert_eq!("50", acutal_attributes["max_length"]);
        }
        #[test]
        fn return_error_when_family_name_is_empty() {
            let event = CreateUserEvent {
                birth_day: NaiveDate::from_ymd(1986, 12, 16),
                given_name: String::from("test-given-name"),
                family_name: String::from(""),
            };

            let actual = validate_create_user(event);
            assert_eq!(true, actual.has_error());

            let actual_error = &actual.errors[0];
            assert_eq!("family_name", actual_error.item_name);

            let actual_error_details = &actual_error.details;
            assert_eq!(1, actual_error_details.len());

            let actual_error_details = &actual_error_details[0];
            assert_eq!(SpecErrorType::Required, actual_error_details.error_type);
        }
        #[test]
        fn return_error_when_family_name_is_over_50count() {
            let event = CreateUserEvent {
                birth_day: NaiveDate::from_ymd(1986, 12, 16),
                given_name: String::from("test-given-name"),
                family_name: String::from("test-family-name𠮷８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０×"),
            };

            let actual = validate_create_user(event);
            assert_eq!(true, actual.has_error());

            let actual_error = &actual.errors[0];
            assert_eq!("family_name", actual_error.item_name);

            let actual_error_details = &actual_error.details;
            assert_eq!(1, actual_error_details.len());

            let actual_error_details = &actual_error_details[0];
            assert_eq!(SpecErrorType::MaxLength, actual_error_details.error_type);

            let acutal_attributes = &actual_error_details.attributes;
            assert_eq!("50", acutal_attributes["max_length"]);
        }
    }
}
