CREATE TABLE details(
    id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO details(id, name)
VALUES
    (1, 'stage-one'),
    (2, 'stage-two'),
    (3, 'stage-three');