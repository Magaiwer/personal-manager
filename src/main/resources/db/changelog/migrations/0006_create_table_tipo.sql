--liquibase formatted sql

--changeset magaiver:create-table-tipo

CREATE TABLE tipo
(
    id          SERIAL       NOT NULL,
    description VARCHAR(100) NOT NULL,
    CONSTRAINT pk_type_id PRIMARY KEY (id)
);