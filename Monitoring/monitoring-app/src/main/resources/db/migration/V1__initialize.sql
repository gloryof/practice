CREATE TABLE monitor_user (
	id bigint NOT NULL PRIMARY KEY,
	name varchar(50) NOT NULL,
	age integer NOT NULL
);

INSERT INTO
	monitor_user(id, name, age) 
SELECT
	result.id,
	'name-' || result.id AS name,
	mod(result.id, 100) + 1 AS age
FROM
	(
		SELECT * FROM generate_series(1,10000) AS func(id)
	) AS result;