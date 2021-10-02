use chrono::NaiveDate;
use chrono::Datelike;

pub struct User {
    pub name: Name,
    pub birth_day: NaiveDate
}

pub struct Name {
    pub given_name: String,
    pub family_name: String,
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

#[cfg(test)]
mod test {
    mod user {
        mod calculate_age {
            use crate::user::domain::user::User;
            use crate::user::domain::user::Name;
            use chrono::NaiveDate;
            #[test]
            fn when_birth_day_is_same_day() {
                let today = NaiveDate::from_ymd(2020, 2, 29);
                let user = User {
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name")
                    },
                    birth_day: today
                };
                let actual = user.calculate_age(today);
                assert_eq!(0, actual);
            }
            #[test]
            fn when_birth_day_is_1year_before_1days() {
                let user = User {
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name")
                    },
                    birth_day: NaiveDate::from_ymd(2020, 2, 29)
                };
                let actual = user.calculate_age(NaiveDate::from_ymd(2020, 2, 28));
                assert_eq!(0, actual);
            }
            #[test]
            fn when_birth_day_is_after_1year() {
                let user = User {
                    name: Name {
                        given_name: String::from("given-name"),
                        family_name: String::from("family-name")
                    },
                    birth_day: NaiveDate::from_ymd(2019, 2, 28)
                };
                let actual = user.calculate_age(NaiveDate::from_ymd(2020, 2, 28));
                assert_eq!(1, actual);
            }
        }
    }
}