CREATE USER "local-user" WITH PASSWORD 'local-password';
CREATE DATABASE "local-database" WITH OWNER = "local-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "local-database" TO "local-user";