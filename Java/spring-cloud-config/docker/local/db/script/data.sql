CREATE TABLE details(
    id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO details(id, name)
VALUES
    (1, 'local-one'),
    (2, 'local-two'),
    (3, 'local-three');