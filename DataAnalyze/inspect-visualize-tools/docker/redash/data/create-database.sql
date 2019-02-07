CREATE USER "redash-user" WITH PASSWORD 'redash-password';
CREATE DATABASE "redash-database" WITH OWNER = "redash-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "redash-database" TO "redash-user";