INSERT INTO public.authority (id, role) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
INSERT INTO public.user (login, password, name, surname, register_date, enabled, birth_date, authority) VALUE ('admin', '$2y$11$vh8O5kLsk9Sfoo8rZFLnmukw2eKlQ2queg4KbnDacYnE5PFNIDjkq', 'ADMIN', 'ADMIN', '2000-01-01 00:00:00', true, '2000-01-01 00:00:00', 1);
INSERT INTO public.user (login, password, name, surname, register_date, enabled, birth_date, authority) VALUE ('user1234', '$2y$11$vh8O5kLsk9Sfoo8rZFLnmukw2eKlQ2queg4KbnDacYnE5PFNIDjkq', 'Den', 'Onyshchenko', '2019-08-01 10:10:10', true, '1996-08-31 00:00:00', 1);