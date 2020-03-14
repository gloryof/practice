CREATE DATABASE "dev-database" ENCODING = "UTF-8";

CREATE ROLE db_admin SUPERUSER LOGIN PASSWORD 'admin-password';
CREATE ROLE "static-user" WITH LOGIN PASSWORD 'static-password';

GRANT CONNECT ON DATABASE "dev-database" TO "static-user";