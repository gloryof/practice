CREATE USER "monitor-user" WITH PASSWORD 'monitor-pass';
CREATE DATABASE "monitor-db" WITH OWNER = "monitor-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "monitor-db" TO "monitor-user";

CREATE SCHEMA "postgres_exporter";
GRANT USAGE ON SCHEMA "postgres_exporter" TO "monitor-user";

CREATE FUNCTION get_pg_stat_activity() RETURNS SETOF pg_stat_activity AS
$$ SELECT * FROM pg_catalog.pg_stat_activity; $$
LANGUAGE sql
VOLATILE
SECURITY DEFINER;

CREATE VIEW postgres_exporter.pg_stat_activity
AS
  SELECT * from get_pg_stat_activity();

GRANT SELECT ON postgres_exporter.pg_stat_activity TO "monitor-user";

CREATE FUNCTION get_pg_stat_replication() RETURNS SETOF pg_stat_replication AS
$$ SELECT * FROM pg_catalog.pg_stat_replication; $$
LANGUAGE sql
VOLATILE
SECURITY DEFINER;

CREATE VIEW postgres_exporter.pg_stat_replication
AS
  SELECT * FROM get_pg_stat_replication();

GRANT SELECT ON postgres_exporter.pg_stat_replication TO "monitor-user";