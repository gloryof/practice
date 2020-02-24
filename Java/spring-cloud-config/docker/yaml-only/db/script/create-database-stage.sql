CREATE USER "stage-user" WITH PASSWORD 'stage-password';
CREATE DATABASE "stage-database" WITH OWNER = "stage-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "stage-database" TO "stage-user";