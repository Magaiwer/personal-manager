--liquibase formatted sql

--changeset magaiver:insert-default-user

INSERT INTO users VALUES(1, 'magaiwer', 'magaiwer@hotmail.com.br', '$2a$10$IVBSSesFznDKYkTiUVjQ9esw1NY0l.JTghlHSP0jrR4B9I8yBGVya', true, current_date, current_date );
INSERT INTO grupo VALUES(1, 'Administrador', 'Usuários que possuem permissão de administrador do sistema');
INSERT INTO permission VALUES(1, 'ROLE_ADMIN', 'Administrador dos sistema');
INSERT INTO users_groups VALUES(1, 1);
INSERT INTO group_permission VALUES(1, 1);