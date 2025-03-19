DELETE FROM doors_users where (select count(*) from doors_users) > 0;
DELETE FROM users_roles where (select count(*) from users_roles) > 0;
DELETE FROM tenants_apartments where (select count(*) from tenants_apartments) > 0;
DELETE FROM lease_tenants where (select count(*) from lease_tenants) > 0;
DELETE FROM tenants where (select count(*) from tenants) > 0;
DELETE FROM complaints where (select count(*) from complaints) > 0;
DELETE FROM leases where (select count(*) from leases) > 0;
DELETE FROM entry_codes where (select count(*) from entry_codes) > 0;
DELETE FROM users where (select count(*) from users) > 0;
DELETE FROM roles where (select count(*) from roles) > 0;
DELETE FROM doors where (select count(*) from doors) > 0;
DELETE FROM parkings where(select count(*) from parkings) > 0;
DELETE FROM lockers where(select count(*) from lockers) > 0;
DELETE FROM apartments where (select count(*) from apartments) > 0;
DELETE FROM buildings where (select count(*) from buildings) > 0;


INSERT INTO roles (id,name) VALUES (1,'TENANT'),(2,'ADMIN');
INSERT INTO users (id, username, password, email, name)
VALUES
    (1, 'superuser', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'superuser1@test.nl', 'Super User 1'),
    (2, 'jdoe', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'jdoe@test.nl', 'John Doe'),
    (3, 'asmith', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'asmith@test.nl', 'Alice Smith'),
    (4, 'bwilson', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'bwilson@test.nl', 'Bob Wilson'),
    (5, 'cmiller', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'cmiller@test.nl', 'Charlie Miller'),
    (6, 'djones', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'djones@test.nl', 'David Jones'),
    (7, 'ekim', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'ekim@test.nl', 'Emily Kim'),
    (8, 'fmurphy', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'fmurphy@test.nl', 'Frank Murphy'),
    (9, 'glopez', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'glopez@test.nl', 'Grace Lopez'),
    (10, 'hnguyen', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'hnguyen@test.nl', 'Henry Nguyen'),
    (11, 'ipatel', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'ipatel@test.nl', 'Isabella Patel'),
    (12, 'jrodriguez', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'jrodriguez@test.nl', 'Jacob Rodriguez');



INSERT INTO users_roles (user_id, role_id)
VALUES
    (1,1),(1,2),
    (2,1),
    (3,1),
    (4,1),
    (5,1),
    (6,1),
    (7,1),
    (8,1),
    (9,1),
    (10,1),
    (11,1),
    (12,1);

INSERT INTO buildings (id) values (1);

INSERT INTO apartments (id, apartment_number,building_id) VALUES (1,1,1),
                                                                 (2,2,1),
                                                                 (3,3,1),
                                                                 (4,4,1),
                                                                 (5,5,1),
                                                                 (6,6,1),
                                                                 (7,7,1);

INSERT INTO parkings (id,number_of_guest_spots, number_of_tenant_spots, building_id) VALUES (1 ,10, 80,1);

INSERT INTO doors (id,door_status,building_id) VALUES (1,'LOCKED',1);

INSERT INTO doors (id,door_status,apartment_id) VALUES  (2,'LOCKED',1);

INSERT INTO doors (id,door_status,parking_id) VALUES (3,'LOCKED',1);

INSERT INTO tenants_apartments (user_id,apartment_id) VALUES (1,1),
                                                             (2,1),
                                                             (3,2),
                                                             (4,2),
                                                             (5,3),
                                                             (6,4),
                                                             (7,4),
                                                             (8,5),
                                                             (9,5),
                                                             (10,6),
                                                             (11,6),
                                                             (12,7);

INSERT INTO doors_users (user_id, door_id) VALUES (1,1);

INSERT INTO lockers (id, locker_number, building_id, apartment_id, update_date)
VALUES
    (1, 101, 1, NULL, '2025-03-19T10:00:00Z'),
    (2, 102, 1, NULL, '2025-03-19T10:05:00Z'),
    (3, 103, 1, NULL, '2025-03-19T10:10:00Z'),
    (4, 104, 1, NULL, '2025-03-19T10:15:00Z'),
    (5, 105, 1, NULL, '2025-03-19T10:20:00Z');

