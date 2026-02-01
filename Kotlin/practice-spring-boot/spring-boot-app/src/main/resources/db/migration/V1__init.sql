CREATE TABLE users (
    user_id VARCHAR(36) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE auths (
    login_id VARCHAR(100) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
