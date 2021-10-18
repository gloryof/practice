use std::collections::HashMap;

pub struct UseCaseErrors {
    keys: Vec<String>,
    errors: HashMap<String, UseCaseError>,
}

pub struct UseCaseError {
    item_name: String,
    details: Vec<UseCaseErrorDetail>,
}

pub struct UseCaseErrorDetail {
    error_type: UseCaseErrorType,
    attributes: HashMap<String, String>,
}

#[derive(Debug, PartialEq)]
pub enum UseCaseErrorType {
    Required,
    MaxLength,
}

impl UseCaseErrors {
    pub fn new() -> UseCaseErrors {
        return UseCaseErrors { keys: Vec::new(), errors: HashMap::new() };
    }

    pub fn has_error(&self) -> bool {
        !self.keys.is_empty()
    }

    pub fn add_error(&mut self, error: UseCaseError) {
        if !error.has_error() {
            return;
        }

        let item_name = error.item_name.clone();
        if !self.keys.contains(&item_name) {
            self.keys.push(item_name.clone());
        }

        match self.errors.get_mut(&item_name) {
            Some(origin_error) => origin_error.add_all(error),
            None => {
                self.errors.insert(String::from(item_name), error);
            }
        }
    }
}

impl UseCaseError {
    pub fn new(item_name: String) -> UseCaseError {
        return UseCaseError {
            item_name: item_name,
            details: Vec::new(),
        };
    }

    pub fn has_error(&self) -> bool {
        !self.details.is_empty()
    }

    pub fn add_required(&mut self) {
        let detail = UseCaseErrorDetail {
            error_type: UseCaseErrorType::Required,
            attributes: HashMap::new()
        };

        self.details.push(detail);
    }
    pub fn add_max_length(&mut self, length: u32) {
        let mut attributes = HashMap::new();
        attributes.insert(String::from("length"), length.to_string());
        let detail = UseCaseErrorDetail {
            error_type: UseCaseErrorType::MaxLength,
            attributes: attributes
        };

        self.details.push(detail);
    }
    fn add_all(&mut self, error: UseCaseError) {

        if !error.has_error() {
            return;
        }

        for detail in error.details {
            self.details.push(detail);
        }
    }
}


#[cfg(test)]
mod test {
    mod use_case_errors {
        mod has_error {
            use crate::base::usecase::UseCaseError;
            use crate::base::usecase::UseCaseErrors;
            #[test]
            fn when_no_error_then_has_error_return_false() {
                let sut = UseCaseErrors::new();
                assert_eq!(false, sut.has_error());
            }

            #[test]
            fn when_an_error_then_has_error_return_true() {
                let mut sut = UseCaseErrors::new();

                let mut error = UseCaseError {
                    item_name: String::from("test"),
                    details: Vec::new(),
                };
                error.add_required();
                sut.add_error(error);

                assert_eq!(true, sut.has_error());
            }
        }
        mod add_error {
            use crate::base::usecase::UseCaseError;
            use crate::base::usecase::UseCaseErrors;
            use crate::base::usecase::UseCaseErrorType;

            #[test]
            fn when_add_empty_error_to_empty_then_not_add() {
                let mut sut = UseCaseErrors::new();

                sut.add_error(UseCaseError::new(String::from("test")));

                let keys = sut.keys;
                assert_eq!(true, keys.is_empty());

                let errors = sut.errors;
                assert_eq!(true, errors.is_empty());
            }

            #[test]
            fn when_add_error_to_empty_then_add_one() {
                let mut sut = UseCaseErrors::new();
                let key = String::from("test");

                let mut error = UseCaseError::new(key.clone());
                error.add_max_length(100);

                sut.add_error(error);
                let actual_keys = sut.keys;
                assert_eq!(1, actual_keys.len());
                assert_eq!(key, actual_keys[0]);

                let actual_errors = sut.errors;
                assert_eq!(1, actual_errors.len());

                let actual_error_opt = actual_errors.get(&key);
                assert_eq!(true, actual_error_opt.is_some());

                let actual_error = actual_error_opt.unwrap();
                assert_eq!(key, actual_error.item_name);

                let actual_details = &actual_error.details;
                assert_eq!(1, actual_details.len());

                let actual_detail = &actual_details[0];
                assert_eq!(UseCaseErrorType::MaxLength, actual_detail.error_type);

                let acutal_attributes = &actual_detail.attributes;
                assert_eq!(1, acutal_attributes.len());
                assert_eq!("100", acutal_attributes["length"]);
            }

            #[test]
            fn when_add_two_error_then_add_two() {
                let mut sut = UseCaseErrors::new();
                let key1 = String::from("test1");
                let key2 = String::from("test2");

                let mut error1 = UseCaseError::new(key1.clone());
                error1.add_max_length(101);

                let mut error2 = UseCaseError::new(key2.clone());
                error2.add_max_length(102);

                sut.add_error(error1);
                sut.add_error(error2);

                let actual_keys = sut.keys;
                assert_eq!(2, actual_keys.len());
                assert_eq!(key1, actual_keys[0]);
                assert_eq!(key2, actual_keys[1]);

                let actual_errors = sut.errors;
                assert_eq!(2, actual_errors.len());

                let actual_error_opt1 = actual_errors.get(&key1);
                assert_eq!(true, actual_error_opt1.is_some());
                let actual_error_opt2 = actual_errors.get(&key2);
                assert_eq!(true, actual_error_opt2.is_some());

                let actual_error1 = actual_error_opt1.unwrap();
                assert_eq!(key1, actual_error1.item_name);
                let actual_error2 = actual_error_opt2.unwrap();
                assert_eq!(key2, actual_error2.item_name);

                let actual_details1 = &actual_error1.details;
                assert_eq!(1, actual_details1.len());
                let actual_details2 = &actual_error2.details;
                assert_eq!(1, actual_details2.len());

                let actual_detail1 = &actual_details1[0];
                assert_eq!(UseCaseErrorType::MaxLength, actual_detail1.error_type);
                let acutal_attributes1 = &actual_detail1.attributes;
                assert_eq!(1, acutal_attributes1.len());
                assert_eq!("101", acutal_attributes1["length"]);


                let actual_detail2 = &actual_details2[0];
                assert_eq!(UseCaseErrorType::MaxLength, actual_detail2.error_type);
                let acutal_attributes2 = &actual_detail2.attributes;
                assert_eq!(1, acutal_attributes2.len());
                assert_eq!("102", acutal_attributes2["length"]);
            }

            #[test]
            fn when_add_two_error_then_add_to_message_exist_one() {
                let mut sut = UseCaseErrors::new();
                let key = String::from("test1");

                let mut error1 = UseCaseError::new(key.clone());
                error1.add_max_length(101);

                let mut error2 = UseCaseError::new(key.clone());
                error2.add_max_length(102);

                sut.add_error(error1);
                sut.add_error(error2);

                let actual_keys = sut.keys;
                assert_eq!(1, actual_keys.len());
                assert_eq!(key, actual_keys[0]);

                let actual_errors = sut.errors;
                assert_eq!(1, actual_errors.len());

                let actual_error_opt = actual_errors.get(&key);
                assert_eq!(true, actual_error_opt.is_some());

                let actual_error = actual_error_opt.unwrap();
                assert_eq!(key, actual_error.item_name);

                let actual_details = &actual_error.details;
                assert_eq!(2, actual_details.len());

                let actual_detail1 = &actual_details[0];
                assert_eq!(UseCaseErrorType::MaxLength, actual_detail1.error_type);
                let actual_detail2 = &actual_details[1];
                assert_eq!(UseCaseErrorType::MaxLength, actual_detail2.error_type);

                let acutal_attributes1 = &actual_detail1.attributes;
                assert_eq!(1, acutal_attributes1.len());
                assert_eq!("101", acutal_attributes1["length"]);

                let acutal_attributes2 = &actual_detail2.attributes;
                assert_eq!(1, acutal_attributes2.len());
                assert_eq!("102", acutal_attributes2["length"]);
            }
        }
    }
    mod use_case_error {

        mod has_error {
            use crate::base::usecase::UseCaseError;
            #[test]
            fn when_no_error_then_has_error_return_false() {
                let sut = UseCaseError::new(String::from("test"));
                assert_eq!(false, sut.has_error());
            }

            #[test]
            fn when_an_error_then_has_error_return_true() {
                let mut sut = UseCaseError::new(String::from("test"));
                sut.add_required();
                assert_eq!(true, sut.has_error());
            }
        }
        mod add_required {
            use crate::base::usecase::UseCaseErrorType;
            use crate::base::usecase::UseCaseError;
            #[test]
            fn add_to_message() {
                let mut sut = UseCaseError::new(String::from("test"));
                sut.add_required();

                assert_eq!(true, sut.has_error());

                assert_eq!(1, sut.details.len());

                let detail = &sut.details[0];
                assert_eq!(UseCaseErrorType::Required, detail.error_type);
                assert_eq!(true, detail.attributes.is_empty());
            }
        }
        mod add_max_length {
            use crate::base::usecase::UseCaseErrorType;
            use crate::base::usecase::UseCaseError;
            #[test]
            fn add_to_detail() {
                let mut sut = UseCaseError::new(String::from("test"));
                sut.add_max_length(100);

                assert_eq!(true, sut.has_error());

                assert_eq!(1, sut.details.len());

                let detail = &sut.details[0];
                assert_eq!(UseCaseErrorType::MaxLength, detail.error_type);

                let attributes = &detail.attributes;
                assert_eq!(1, attributes.len());
                assert_eq!("100", attributes["length"]);
            }
        }
        mod add_all {
            use crate::base::usecase::UseCaseErrorType;
            use crate::base::usecase::UseCaseError;

            #[test]
            fn add_all_to_details() {
                let mut sut = UseCaseError::new(String::from("dist"));

                let mut src = UseCaseError::new(String::from("src"));
                src.add_max_length(100);
                src.add_required();

                sut.add_all(src);

                assert_eq!(true, sut.has_error());

                assert_eq!(2, sut.details.len());

                let detail1 = &sut.details[0];
                assert_eq!(UseCaseErrorType::MaxLength, detail1.error_type);
                let attributes = &detail1.attributes;
                assert_eq!(1, attributes.len());
                assert_eq!("100", attributes["length"]);

                let detail2 = &sut.details[1];
                assert_eq!(UseCaseErrorType::Required, detail2.error_type);
                assert_eq!(true, detail2.attributes.is_empty());
            }

            #[test]
            fn when_empty_error_then_not_add() {
                let mut sut = UseCaseError::new(String::from("dist"));

                let src = UseCaseError::new(String::from("src"));
                sut.add_all(src);

                assert_eq!(false, sut.has_error());
                assert_eq!(0, sut.details.len());
            }
        }
    }
}