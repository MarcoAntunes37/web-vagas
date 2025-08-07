CREATE TABLE IF NOT EXISTS user_preferences
(
    id uuid NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    contract_type character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    distance integer,
    include_unknown boolean NOT NULL,
    keywords character varying(255) COLLATE pg_catalog."default",
    max integer,
    min integer,
    search_type character varying(255) COLLATE pg_catalog."default",
    user_id uuid NOT NULL,
    working_day_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_preferences_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_ukey UNIQUE (user_id),
    CONSTRAINT user_preferences_contract_type_check CHECK (contract_type::text = ANY (ARRAY['ALL'::character varying, 'TEMPORARY'::character varying, 'PERMANENT'::character varying, 'CONTRACT'::character varying]::text[])),
    CONSTRAINT user_preferences_search_type_check CHECK (search_type::text = ANY (ARRAY['WHAT'::character varying, 'WHAT_AND'::character varying, 'WHAT_PRHASE'::character varying, 'WHAT_EXCLUDE'::character varying, 'TITLE_ONLY'::character varying]::text[])),
    CONSTRAINT user_preferences_working_day_type_check CHECK (working_day_type::text = ANY (ARRAY['BOTH'::character varying, 'FULL_TIME'::character varying, 'PART_TIME'::character varying]::text[]))
);

CREATE TABLE IF NOT EXISTS user_preferences_jsearch
(
    id uuid NOT NULL,
    employment_types character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    keywords character varying(255) COLLATE pg_catalog."default",
    user_id uuid NOT NULL,
    exclude_job_publishers character varying(255) COLLATE pg_catalog."default",
    remote_work boolean NOT NULL DEFAULT false,
    CONSTRAINT user_preferences_jsearch_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_jsearch_ukey UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS jobs_user
(
    job_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id uuid NOT NULL,
    received_at timestamp(6) with time zone,
    CONSTRAINT jobs_user_pkey PRIMARY KEY (job_id, user_id)
);

CREATE TABLE IF NOT EXISTS products (
    id character varying(255) NOT NULL,
    active boolean NOT NULL,
    default_price character varying(255),
    client_price double precision NOT NULL,
    description character varying(255),
    client_descriptions JSONB,
    name character varying(255),
    created timestamp(6) with time zone NOT NULL,
    updated timestamp(6) with time zone NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);