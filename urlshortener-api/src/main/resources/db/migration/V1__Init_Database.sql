CREATE TABLE IF NOT EXISTS short_url
(
    id BIGSERIAL NOT NULL,
    code character varying(20) COLLATE pg_catalog."default",
    original_url character VARCHAR(2000) COLLATE pg_catalog."default",
    created_at timestamp(6) with time zone,
    CONSTRAINT short_url_pkey PRIMARY KEY (id),
    CONSTRAINT code_ukey UNIQUE (code)
);