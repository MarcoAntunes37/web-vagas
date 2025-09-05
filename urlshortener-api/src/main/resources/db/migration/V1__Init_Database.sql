CREATE TABLE IF NOT EXISTS short_url
(
    id BIGSERIAL NOT NULL,
    code character varying(255) COLLATE pg_catalog."default",
    original_url character varying(255) COLLATE pg_catalog."default",
    created_at timestamp(6) with time zone,
    CONSTRAINT short_url_pkey PRIMARY KEY (id),
    CONSTRAINT code_ukey UNIQUE (code)
);