CREATE TABLE details(
    id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    PRIMARY KEY(id)
);
CREATE SEQUENCE id_seq;

INSERT INTO details(id, name)
VALUES
    (nextval('id_seq'), 'local-one'),
    (nextval('id_seq'), 'local-two'),
    (nextval('id_seq'), 'local-three');