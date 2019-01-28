CREATE USER "analyze-user" WITH PASSWORD 'analyze-password';
CREATE DATABASE "analyze-database" WITH OWNER = "analyze-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "analyze-database" TO "analyze-user";