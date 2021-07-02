psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER docker;
    CREATE DATABASE personal_manager;
    GRANT ALL PRIVILEGES ON DATABASE personal_manager TO docker;
EOSQL
