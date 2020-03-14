CREATE TABLE details(
    id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO details(id, name)
VALUES
    (1, 'dev-one'),
    (2, 'dev-two'),
    (3, 'dev-three');

GRANT SELECT ON ALL TABLES IN SCHEMA public TO "static-user";