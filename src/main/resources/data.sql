DELETE
FROM doors_users
where (select count(*) from doors_users) > 0;
DELETE
FROM users_roles
where (select count(*) from users_roles) > 0;
DELETE
FROM tenants_apartments
where (select count(*) from tenants_apartments) > 0;
DELETE
FROM lease_tenants
where (select count(*) from lease_tenants) > 0;
DELETE
FROM tenants
where (select count(*) from tenants) > 0;
DELETE
FROM complaints
where (select count(*) from complaints) > 0;
DELETE
FROM leases
where (select count(*) from leases) > 0;
DELETE
FROM entry_codes
where (select count(*) from entry_codes) > 0;
DELETE
FROM users
where (select count(*) from users) > 0;
DELETE
FROM roles
where (select count(*) from roles) > 0;
DELETE
FROM doors
where (select count(*) from doors) > 0;
DELETE
FROM parkings
where (select count(*) from parkings) > 0;
DELETE
FROM lockers
where (select count(*) from lockers) > 0;
DELETE
FROM apartments
where (select count(*) from apartments) > 0;
DELETE
FROM buildings
where (select count(*) from buildings) > 0;


INSERT INTO roles (id, name)
VALUES (1, 'TENANT'),
       (2, 'ADMIN');
INSERT INTO users (id, username, password, email, name)
VALUES (1001, 'superuser', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'superuser1@test.nl',
        'Super User 1'),
       (1002, 'jdoe', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'jdoe@test.nl', 'John Doe'),
       (1003, 'asmith', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'asmith@test.nl',
        'Alice Smith'),
       (1004, 'bwilson', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'bwilson@test.nl',
        'Bob Wilson'),
       (1005, 'cmiller', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'cmiller@test.nl',
        'Charlie Miller'),
       (1006, 'djones', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'djones@test.nl',
        'David Jones'),
       (1007, 'ekim', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'ekim@test.nl', 'Emily Kim'),
       (1008, 'fmurphy', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'fmurphy@test.nl',
        'Frank Murphy'),
       (1009, 'glopez', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'glopez@test.nl',
        'Grace Lopez'),
       (1010, 'hnguyen', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'hnguyen@test.nl',
        'Henry Nguyen'),
       (1011, 'ipatel', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'ipatel@test.nl',
        'Isabella Patel'),
       (1012, 'jrodriguez', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'jrodriguez@test.nl',
        'Jacob Rodriguez'),
       (1013, 'shannan', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'phunbunch@gmail.com',
        'shannan'),
       (1014, 'ruben', '$2a$10$DngToCGxdfBYmj9O2l01c.JtNfvKSBOqgYbuHlMLBf52f9s6wJO7u', 'rubengarcia0515@gmail.com',
        'ruben g ');


INSERT INTO users_roles (user_id, role_id)
VALUES (1001, 1),
       (1001, 2),
       (1002, 1),
       (1003, 1),
       (1004, 1),
       (1005, 1),
       (1006, 1),
       (1007, 1),
       (1008, 1),
       (1009, 1),
       (1010, 1),
       (1011, 1),
       (1012, 1),
       (1013, 1),
       (1013, 2),
       (1014, 1),
       (1014, 2);

INSERT INTO complaints (id, submitter_user_id, time_created, complaint_status, complaint_type)
values (1001, 1013, '2025-03-19T10:00:00Z', 'NEW', 'OTHER'),
       (1002, 1013, '2025-03-20T10:00:00Z', 'PENDING', 'REPAIR'),
       (1003, 1014, '2025-03-25T10:00:00Z', 'PENDING', 'NOISE'),
       (1004, 1014, '2025-02-25T10:00:00Z', 'RESOLVED', 'NOISE'),
       (1005, 1014, '2025-03-22T10:00:00Z', 'RESOLVED', 'NOISE');


INSERT INTO buildings (id)
values (1001);

INSERT INTO apartments (id, apartment_number, building_id)
VALUES (1001, 1, 1001),
       (1002, 2, 1001),
       (1003, 3, 1001),
       (1004, 4, 1001),
       (1005, 5, 1001),
       (1006, 6, 1001),
       (1007, 7, 1001),
       (1008, 300,1001);

INSERT INTO parkings (id, number_of_guest_spots, number_of_tenant_spots, building_id)
VALUES (1001, 10, 80, 1001);

INSERT INTO doors (id, door_status, building_id)
VALUES (1001, 'LOCKED', 1001);

INSERT INTO doors (id, door_status, apartment_id)
VALUES (1002, 'LOCKED', 1001);

INSERT INTO doors (id, door_status, parking_id)
VALUES (1003, 'LOCKED', 1001);

INSERT INTO tenants_apartments (user_id, apartment_id)
VALUES (1001, 1001),
       (1002, 1001),
       (1003, 1002),
       (1004, 1002),
       (1005, 1003),
       (1006, 1004),
       (1007, 1004),
       (1008, 1005),
       (1009, 1005),
       (1010, 1006),
       (1011, 1006),
       (1012, 1007),
       (1014, 1008);

INSERT INTO doors_users (user_id, door_id)
VALUES (1001, 1001);

INSERT INTO lockers (id, locker_number, building_id, apartment_id, update_date)
VALUES (1001, 101, 1001, NULL, '2025-03-19T10:00:00Z'),
       (1002, 102, 1001, NULL, '2025-03-19T10:05:00Z'),
       (1003, 103, 1001, NULL, '2025-03-19T10:10:00Z'),
       (1004, 104, 1001, NULL, '2025-03-19T10:15:00Z'),
       (1005, 105, 1001, NULL, '2025-03-19T10:20:00Z');

INSERT INTO tenants (id, user_profile_id) values (1001,1014);
INSERT INTO leases (id, lease_start_date, lease_end_date,external_id, status,apartment_id,dropbox_document_download_url) values
(1001,'2025-02-25T10:00:00Z','2026-02-25T10:00:00Z',1,'SIGNED',1008,'https://app.hellosign.com/editor/view/super_group_guid/e79bdb2ace1d29a19f8fbf96676074d2b7188a55');
INSERT INTO lease_tenants values (1001,1001);



