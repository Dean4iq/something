CREATE SEQUENCE IF NOT EXISTS authority_id_seq;

CREATE TABLE IF NOT EXISTS public.authority
(
    id integer NOT NULL DEFAULT nextval('authority_id_seq'),
    role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER SEQUENCE authority_id_seq
OWNED BY public.authority.id;

CREATE TABLE IF NOT EXISTS public."user"
(
    login character varying(255) COLLATE pg_catalog."default" NOT NULL,
    birth_date timestamp without time zone NOT NULL,
    enabled boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    register_date timestamp without time zone NOT NULL,
    surname character varying(255) COLLATE pg_catalog."default" NOT NULL,
    authority integer NOT NULL,
    PRIMARY KEY (login),
    CONSTRAINT fk_user_authority_id FOREIGN KEY (authority)
        REFERENCES public.authority (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;