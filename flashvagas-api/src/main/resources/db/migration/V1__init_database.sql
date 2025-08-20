CREATE TABLE IF NOT EXISTS user_preferences
(
    id uuid NOT NULL,
    employment_types character varying(255) COLLATE pg_catalog."default",
    country character varying(255) COLLATE pg_catalog."default",
    keywords character varying(255) COLLATE pg_catalog."default",
    user_id uuid NOT NULL,
    exclude_job_publishers character varying(255) COLLATE pg_catalog."default",
    remote_work boolean NOT NULL DEFAULT false,
    CONSTRAINT user_preferences_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_ukey UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS jobs_user
(
    job_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id uuid NOT NULL,
    received_at timestamp(6) with time zone,
    CONSTRAINT jobs_user_pkey PRIMARY KEY (job_id, user_id)
);