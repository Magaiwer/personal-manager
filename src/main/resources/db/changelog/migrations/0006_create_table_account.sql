--liquibase formatted sql

--changeset magaiver:create-table-account

CREATE TABLE account
(
    id          SERIAL      NOT NULL,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    CONSTRAINT pk_account_id PRIMARY KEY (id)
);