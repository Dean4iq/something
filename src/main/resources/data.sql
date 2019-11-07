INSERT INTO public."authority" (id, role)
    VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER')
        ON CONFLICT ON CONSTRAINT authority_pkey DO NOTHING;
INSERT INTO public."user" (login, password, name, surname, register_date, enabled, birth_date, authority)
    VALUES ('admin', '$2a$11$AgerhbzpMMGaOPrWmww6XeN16y372o153J0md9AZgrXSy5rYgBnTi', 'ADMIN', 'ADMIN', '2000-01-01 00:00:00', true, '2000-01-01 00:00:00', 1)
        ON CONFLICT ON CONSTRAINT user_pkey DO NOTHING;
INSERT INTO public."user" (login, password, name, surname, register_date, enabled, birth_date, authority)
    VALUES ('user1234', '$2a$11$3nSgv7mAkd4lcRgnvCRRLuWTAYbznQpIMwSVf9lKuhDnn6sgH5qgC', 'Den', 'Onyshchenko', '2019-08-01 10:10:10', true, '1996-08-31 00:00:00', 2)
        ON CONFLICT ON CONSTRAINT user_pkey DO NOTHING;