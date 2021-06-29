--liquibase formatted sql

--changeset magaiver:create-table-account

CREATE TABLE account
(
    id          SERIAL      NOT NULL,
    description VARCHAR(50) NOT NULL,
    CONSTRAINT pk_account_id PRIMARY KEY (id)
);