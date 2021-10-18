use std::collections::HashMap;

pub struct SpecErrors {
    pub errors: Vec<SpecError>,
}

pub struct SpecError {
    pub item_name: String,
    pub details: Vec<SpecErrorDetail>,
}

pub struct SpecErrorDetail {
    pub error_type: SpecErrorType,
    pub attributes: HashMap<String, String>,
}

#[derive(Debug, PartialEq)]
pub enum SpecErrorType {
    Required,
    MaxLength,
}

impl SpecErrors {
    pub fn new() -> SpecErrors {
        return SpecErrors { errors: Vec::new() };
    }
    pub fn has_error(&self) -> bool {
        !self.errors.is_empty()
    }
    pub fn add_error(&mut self, error: SpecError) {
        if error.has_error() {
            self.errors.push(error)
        }
    }
}

impl SpecError {
    pub fn new(item_name: String) -> SpecError {
        SpecError {
            item_name: item_name,
            details: Vec::new(),
        }
    }
    pub fn has_error(&self) -> bool {
        !self.details.is_empty()
    }
    pub fn add_required(&mut self) {
        let detail = SpecErrorDetail {
            error_type: SpecErrorType::Required,
            attributes: HashMap::new()
        };

        self.details.push(detail);
    }
    pub fn add_max_length(&mut self, max_length: usize) {
        let mut attributes = HashMap::new();
        attributes.insert(String::from("max_length"), max_length.to_string());
        let detail = SpecErrorDetail {
            error_type: SpecErrorType::MaxLength,
            attributes: attributes
        };

        self.details.push(detail);
    }
}

#[cfg(test)]
mod test {
    mod spec_errors {
        mod has_error {
            use crate::base::domain::SpecError;
            use crate::base::domain::SpecErrors;
            #[test]
            fn when_no_error_then_has_error_return_false() {
                let sut = SpecErrors::new();
                assert_eq!(false, sut.has_error());
            }

            #[test]
            fn when_an_error_then_has_error_return_true() {
                let mut sut = SpecErrors::new();

                let mut error = SpecError {
                    item_name: String::from("test"),
                    details: Vec::new(),
                };
                error.add_required();
                sut.add_error(error);

                assert_eq!(true, sut.has_error());
            }
        }
        mod add_error {
            use crate::base::domain::SpecErrorType;
            use crate::base::domain::SpecError;
            use crate::base::domain::SpecErrors;
            #[test]
            fn when_has_error_then_add_to_errors() {
                let mut sut = SpecErrors::new();

                let mut error = SpecError {
                    item_name: String::from("test"),
                    details: Vec::new(),
                };
                error.add_required();
                sut.add_error(error);

                assert_eq!(true, sut.has_error());
                assert_eq!(1, sut.errors.len());

                let error = &sut.errors[0];
                assert_eq!("test", error.item_name);

                let error_detail = &error.details[0];
                assert_eq!(SpecErrorType::Required, error_detail.error_type);
            }
            #[test]
            fn when_no_error_then_dose_not_add_to_errors() {
                let mut sut = SpecErrors::new();

                let error = SpecError {
                    item_name: String::from("test"),
                    details: Vec::new(),
                };
                sut.add_error(error);

                assert_eq!(false, sut.has_error());
                assert_eq!(0, sut.errors.len());
            }
        }
    }
    mod spec_error {
        mod has_error {
            use crate::base::domain::SpecError;
            #[test]
            fn when_no_error_then_has_error_return_false() {
                let sut = SpecError::new(String::from("test"));
                assert_eq!(false, sut.has_error());
            }

            #[test]
            fn when_an_error_then_has_error_return_true() {
                let mut sut = SpecError::new(String::from("test"));
                sut.add_required();
                assert_eq!(true, sut.has_error());
            }
        }
        mod add_required {
            use crate::base::domain::SpecErrorType;
            use crate::base::domain::SpecError;
            #[test]
            fn add_to_detail() {
                let mut sut = SpecError::new(String::from("test"));
                sut.add_required();

                assert_eq!(true, sut.has_error());

                assert_eq!(1, sut.details.len());

                let detail = &sut.details[0];
                assert_eq!(SpecErrorType::Required, detail.error_type);
                assert_eq!(true, detail.attributes.is_empty());
            }
        }
        mod add_max_length {
            use crate::base::domain::SpecErrorType;
            use crate::base::domain::SpecError;
            #[test]
            fn add_to_detail() {
                let mut sut = SpecError::new(String::from("test"));
                sut.add_max_length(100);

                assert_eq!(true, sut.has_error());

                assert_eq!(1, sut.details.len());

                let detail = &sut.details[0];
                assert_eq!(SpecErrorType::MaxLength, detail.error_type);

                let attributes = &detail.attributes;
                assert_eq!(1, attributes.len());
                assert_eq!("100", attributes["max_length"]);
            }
        }
    }
}
