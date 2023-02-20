--liquibase formatted sql

--changeset magaiver:insert-table-category

INSERT INTO category ( name, description, icon) VALUES('Educação','','');
INSERT INTO category ( name, description, icon) VALUES('Saúde','','');
INSERT INTO category ( name, description, icon) VALUES('Moradia','','');
INSERT INTO category ( name, description, icon) VALUES('Transporte','','');
INSERT INTO category ( name, description, icon) VALUES('Alimentação','','');
INSERT INTO category ( name, description, icon) VALUES('Lazer','','');