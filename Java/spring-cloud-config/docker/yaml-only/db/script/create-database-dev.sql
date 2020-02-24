CREATE USER "dev-user" WITH PASSWORD 'dev-password';
CREATE DATABASE "dev-database" WITH OWNER = "dev-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "dev-database" TO "dev-user";